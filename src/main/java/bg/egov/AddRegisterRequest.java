package bg.egov;

public class AddRegisterRequest {

	private String administraitonId;
	private String serviceId;
	private String serviceName;
	private String serviceRootUrl;
	
	public String getAdministraitonId() {
		return administraitonId;
	}
	public void setAdministraitonId(String administraitonId) {
		this.administraitonId = administraitonId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceRootUrl() {
		return serviceRootUrl;
	}
	public void setServiceRootUrl(String serviceRootUrl) {
		this.serviceRootUrl = serviceRootUrl;
	}
}
