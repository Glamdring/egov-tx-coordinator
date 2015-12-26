package bg.egov;

import java.security.cert.X509Certificate;

import org.springframework.stereotype.Service;

@Service
public class CertificateFetcher {

	public X509Certificate getCertificate(String clientId) {
		//TODO call IISDA
		return null;
	}
}
