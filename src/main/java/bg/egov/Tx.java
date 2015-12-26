package bg.egov;

public class Tx {

	private TxMetadata metadata;
	private TxRequest request;
	
	public TxMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(TxMetadata metadata) {
		this.metadata = metadata;
	}
	public TxRequest getRequest() {
		return request;
	}
	public void setRequest(TxRequest request) {
		this.request = request;
	}
}
