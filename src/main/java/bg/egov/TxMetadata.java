package bg.egov;

import java.time.LocalDateTime;
import java.util.UUID;

public class TxMetadata {

	private UUID id;
	
	private LocalDateTime created;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
}
