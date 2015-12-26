package bg.egov;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.common.io.BaseEncoding;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public class TxCoordinatorControllerTest extends BaseIntegrationTest {

	private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	
	@Autowired
	private TxCoordinatorController controller;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void txSuccessTest() throws Exception {
		TxRequest request = createRequest();

		String content = mapper.writeValueAsString(request);
		
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = gen.generateKeyPair();
		
		String signature = createSignature(content, pair);

		mockCertificateFetcher(pair);
		
		TxMetadata metadata = controller.createTransaction(request, signature, SIGNATURE_ALGORITHM, new HttpEntity<>(content));

		boolean valid = controller.verifyTransaction(metadata.getId(), request);
		assertThat(valid, equalTo(true));
	}

	@Test
	public void txInvalidContentSignatureTest() throws Exception {
		TxRequest request = createRequest();

		String content = mapper.writeValueAsString(request);
		
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = gen.generateKeyPair();
		
		String signature = createSignature(content + "otherstuff", pair);

		mockCertificateFetcher(pair);
		
		try {
			controller.createTransaction(request, signature, SIGNATURE_ALGORITHM, new HttpEntity<>(content));
			Assert.fail("Signature validation must fail");
		} catch (Exception ex) {
			//TODO verify proper exception
		}
	}
	
	@Test
	public void txInvalidKeySignatureTest() throws Exception {
		TxRequest request = createRequest();

		String content = mapper.writeValueAsString(request);
		
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		KeyPair pair = gen.generateKeyPair();
		
		String signature = createSignature(content + "otherstuff", pair);

		mockCertificateFetcher(gen.generateKeyPair()); //Generate a different pair for the public key
		
		try {
			controller.createTransaction(request, signature, SIGNATURE_ALGORITHM, new HttpEntity<>(content));
			Assert.fail("Signature validation must fail");
		} catch (Exception ex) {
			//TODO verify proper exception
		}
	}
	
	private String createSignature(String content, KeyPair pair)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
		sig.initSign(pair.getPrivate());
		sig.update(content.getBytes("UTF-8"));
		String signature = BaseEncoding.base64().encode(sig.sign());
		return signature;
	}

	private void mockCertificateFetcher(KeyPair pair) throws GeneralSecurityException, IOException {
		CertificateFetcher mockFetcher = mock(CertificateFetcher.class);
		when(mockFetcher.getCertificate(anyString())).thenReturn(generateCertificate("CN=TEST", pair, SIGNATURE_ALGORITHM));
		ReflectionTestUtils.setField(controller, "certificateFetcher", mockFetcher);
	}

	private TxRequest createRequest() {
		TxRequest request = new TxRequest();
		request.setClientId("12345");
		request.setDestinationId("54321");
		request.setEndpointType(EndpointType.DATA);
		request.setRequestedDataFields(Sets.newHashSet("data1", "data2"));
		request.setRequestType(RequestType.SYNCHRONOUS);
		return request;
	}

	/**
	 * Create a self-signed X.509 Certificate
	 * 
	 * @param dn the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param pair the KeyPair
	 * @param days how many days from now the Certificate is valid for
	 * @param algorithm the signing algorithm, eg "SHA1withRSA"
	 */
	X509Certificate generateCertificate(String dn, KeyPair pair, String algorithm) throws GeneralSecurityException, IOException {
		
		int days = 365;
		PrivateKey privkey = pair.getPrivate();
		X509CertInfo info = new X509CertInfo();
		Date from = new Date();
		Date to = new Date(from.getTime() + days * 86400000l);
		CertificateValidity interval = new CertificateValidity(from, to);
		BigInteger sn = new BigInteger(64, new SecureRandom());
		X500Name owner = new X500Name(dn);

		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
		info.set(X509CertInfo.SUBJECT, owner);
		info.set(X509CertInfo.ISSUER, owner);
		info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
		AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

		// Sign the cert to identify the algorithm that's used.
		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);

		// Update the algorith, and resign.
		algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
		info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
		cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		return cert;
	}
}
