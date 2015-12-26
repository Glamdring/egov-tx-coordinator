package bg.egov;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/transaction")
public class TxCoordinatorController {

	@Autowired
	private TxDao dao;
	
	@RequestMapping("/create")
	@ResponseBody
	public TxMetadata createTransaction(TxRequest request) {

		validateClientId(request.getClientId());
		validateAuthenticationAndSignature(request.getClientId(), request.getContent(), request.getSignedContent(), request.getCertificate());
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

	@RequestMapping("/verify")
	@ResponseBody
	public boolean verifyTransaction(@RequestParam UUID txId, TxRequest request) {

		Tx tx = dao.get(txId);

		if (!request.equals(tx.getRequest())) {
			throw new IllegalArgumentException("Incompatible transaciton requests");
		}
		
		return true;
	}
	
	private void validateAccess(TxRequest request) {
		if (request.getEndpointType() == EndpointType.DATA && !request.getRequestedDataFields().isEmpty()) {
				if (!hasAccess(request.getClientId(), request.getRequestedDataFields())) {
					throw new IllegalArgumentException("No access to requested data"); //TODO change to a more structured error response
				}
		} else if (request.getEndpointType() == EndpointType.SERVICE && request.getRequestedEndpointUrl() != null){
			
		}
	}

	private boolean hasAccess(String clientId, Set<String> dataFields) {
		// call IISDA
		return true;
	}

	private void validateAuthenticationAndSignature(String clientId, String content, String signedContent, String certificate) {
		// OCSP/CRL, check signature
	}

	private void validateClientId(String clientId) {
		// call IISDA
	}
}
