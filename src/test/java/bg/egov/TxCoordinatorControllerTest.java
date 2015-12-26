package bg.egov;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;

public class TxCoordinatorControllerTest extends BaseIntegrationTest {

	@Autowired
	private TxCoordinatorController controller;
	
	@Test
	public void txTest() {
		TxRequest request = new TxRequest();
		request.setClientId("12345");
		request.setDestinationId("54321");
		request.setEndpointType(EndpointType.DATA);
		request.setRequestedDataFields(Sets.newHashSet("data1", "data2"));
		request.setRequestType(RequestType.SYNCHRONOUS);
		
		request.setSignedContent("TODO");
		request.setCertificate("TODO");
		request.setContent("TODO");
		
		TxMetadata metadata = controller.createTransaction(request);
		
		boolean valid = controller.verifyTransaction(metadata.getId(), request);
		assertThat(valid, equalTo(true));
	}
}
