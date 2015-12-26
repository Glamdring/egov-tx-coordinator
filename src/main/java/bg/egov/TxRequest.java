package bg.egov;

import java.util.Set;

public class TxRequest {

	private String clientId;
	private String destinationId;
	private String content;
	private String signedContent;
	private String certificate;
	private RequestType requestType;
	private EndpointType endpointType;
	private Set<String> requestedDataFields;
	private String requestedEndpointUrl;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSignedContent() {
		return signedContent;
	}
	public void setSignedContent(String signedContent) {
		this.signedContent = signedContent;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((certificate == null) ? 0 : certificate.hashCode());
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((destinationId == null) ? 0 : destinationId.hashCode());
		result = prime * result + ((endpointType == null) ? 0 : endpointType.hashCode());
		result = prime * result + ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result + ((requestedDataFields == null) ? 0 : requestedDataFields.hashCode());
		result = prime * result + ((requestedEndpointUrl == null) ? 0 : requestedEndpointUrl.hashCode());
		result = prime * result + ((signedContent == null) ? 0 : signedContent.hashCode());
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
		if (certificate == null) {
			if (other.certificate != null)
				return false;
		} else if (!certificate.equals(other.certificate))
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (destinationId == null) {
			if (other.destinationId != null)
				return false;
		} else if (!destinationId.equals(other.destinationId))
			return false;
		if (endpointType != other.endpointType)
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
		if (signedContent == null) {
			if (other.signedContent != null)
				return false;
		} else if (!signedContent.equals(other.signedContent))
			return false;
		return true;
	}
}
