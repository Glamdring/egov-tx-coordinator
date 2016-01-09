package bg.egov;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

@Controller
@RequestMapping("/transaction")
public class TxCoordinatorController {

	@Autowired
	private TxDao dao;
	
	@Autowired
	private CertificateFetcher certificateFetcher;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public TxMetadata createTransaction(@RequestBody TxRequest request, @RequestHeader("X-Signature") String signature,
			@RequestHeader("X-Signature-Algorithm") String signatureAlgorithm,
			HttpEntity<String> entity) {

		validateClientId(request.getClientId());
		validateAuthenticationAndSignature(request.getClientId(), entity.getBody(), signature, signatureAlgorithm);
		validateAccess(request);

		TxMetadata response = new TxMetadata();
		response.setCreated(LocalDateTime.now());
		response.setId(UUID.randomUUID());
		
		Tx tx = new Tx();
		tx.setMetadata(response);
		tx.setRequest(request);
		dao.save(tx);
		
		return response;
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public boolean verifyTransaction(@RequestParam UUID txId, @RequestBody TxRequest request) {

		Tx tx = dao.get(txId);

		if (!request.equals(tx.getRequest())) {
			throw new IllegalArgumentException("Incompatible transaciton requests");
		}
		
		return true;
	}
	
	private void validateAccess(TxRequest request) {
		if (request.getEndpointType() == EndpointType.DATA && !request.getRequestedDataFields().isEmpty()) {
				if (!hasAccess(request.getClientId(), request.getServiceId(), request.getRequestedDataFields())) {
					throw new IllegalArgumentException("No access to requested data"); //TODO change to a more structured error response
				}
		} else if (request.getEndpointType() == EndpointType.SERVICE && request.getRequestedEndpointUrl() != null){
			
		}
	}

	private boolean hasAccess(String clientId, String serviceId, Set<String> dataFields) {
		// call IISDA
		// serviceId = id of the service for which the data is needed. 
		return true;
	}

	private void validateAuthenticationAndSignature(String clientId, String requestBody, String signature, String signatureAlgorithm) {
		X509Certificate certificate = certificateFetcher.getCertificate(clientId);
		try {
			Signature sig = Signature.getInstance(signatureAlgorithm);
			sig.initVerify(certificate.getPublicKey());
			sig.update(requestBody.getBytes(Charsets.UTF_8));
			if (!sig.verify(BaseEncoding.base64().decode(signature))) {
				throw new IllegalArgumentException("Signature is invalid");
			}
		} catch (NoSuchAlgorithmException | SignatureException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		}
		// OCSP/CRL, check signature
	}

	private void validateClientId(String clientId) {
		// call IISDA
	}
}
