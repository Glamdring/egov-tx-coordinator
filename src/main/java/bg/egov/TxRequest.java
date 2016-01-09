package bg.egov;

import java.util.Set;

public class TxRequest {

	private String clientId;
	private String destinationId;
	private String serviceId;
	private RequestType requestType;
	private EndpointType endpointType;
	private Set<String> requestedDataFields;
	private String requestedEndpointUrl;
	private String callbackUrl;
	private String requestingPerson;
	private String originatingDocumentId;
	private String originatingDocumentType;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
	public RequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	public EndpointType getEndpointType() {
		return endpointType;
	}
	public void setEndpointType(EndpointType endpointType) {
		this.endpointType = endpointType;
	}
	public Set<String> getRequestedDataFields() {
		return requestedDataFields;
	}
	public void setRequestedDataFields(Set<String> requestedDataFields) {
		this.requestedDataFields = requestedDataFields;
	}
	public String getRequestedEndpointUrl() {
		return requestedEndpointUrl;
	}
	public void setRequestedEndpointUrl(String requestedEndpointUrl) {
		this.requestedEndpointUrl = requestedEndpointUrl;
	}
	
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getRequestingPerson() {
		return requestingPerson;
	}
	public void setRequestingPerson(String requestingPerson) {
		this.requestingPerson = requestingPerson;
	}
	public String getOriginatingDocumentId() {
		return originatingDocumentId;
	}
	public void setOriginatingDocumentId(String originatingDocumentId) {
		this.originatingDocumentId = originatingDocumentId;
	}
	public String getOriginatingDocumentType() {
		return originatingDocumentType;
	}
	public void setOriginatingDocumentType(String originatingDocumentType) {
		this.originatingDocumentType = originatingDocumentType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callbackUrl == null) ? 0 : callbackUrl.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((destinationId == null) ? 0 : destinationId.hashCode());
		result = prime * result + ((endpointType == null) ? 0 : endpointType.hashCode());
		result = prime * result + ((originatingDocumentId == null) ? 0 : originatingDocumentId.hashCode());
		result = prime * result + ((originatingDocumentType == null) ? 0 : originatingDocumentType.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((requestedDataFields == null) ? 0 : requestedDataFields.hashCode());
		result = prime * result + ((requestedEndpointUrl == null) ? 0 : requestedEndpointUrl.hashCode());
		result = prime * result + ((requestingPerson == null) ? 0 : requestingPerson.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TxRequest other = (TxRequest) obj;
		if (callbackUrl == null) {
			if (other.callbackUrl != null)
				return false;
		} else if (!callbackUrl.equals(other.callbackUrl))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (destinationId == null) {
			if (other.destinationId != null)
				return false;
		} else if (!destinationId.equals(other.destinationId))
			return false;
		if (endpointType != other.endpointType)
			return false;
		if (originatingDocumentId == null) {
			if (other.originatingDocumentId != null)
				return false;
		} else if (!originatingDocumentId.equals(other.originatingDocumentId))
			return false;
		if (originatingDocumentType == null) {
			if (other.originatingDocumentType != null)
				return false;
		} else if (!originatingDocumentType.equals(other.originatingDocumentType))
			return false;
		if (requestType != other.requestType)
			return false;
		if (requestedDataFields == null) {
			if (other.requestedDataFields != null)
				return false;
		} else if (!requestedDataFields.equals(other.requestedDataFields))
			return false;
		if (requestedEndpointUrl == null) {
			if (other.requestedEndpointUrl != null)
				return false;
		} else if (!requestedEndpointUrl.equals(other.requestedEndpointUrl))
			return false;
		if (requestingPerson == null) {
			if (other.requestingPerson != null)
				return false;
		} else if (!requestingPerson.equals(other.requestingPerson))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		return true;
	}

}