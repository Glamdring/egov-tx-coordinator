package bg.egov;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TxDao {

	@Autowired
	private Bucket bucket;

	@Autowired
	private ObjectMapper jsonMapper = new ObjectMapper();
	
	public void save(Tx tx) {
		try {
			bucket.insert(RawJsonDocument.create(tx.getMetadata().getId().toString(), jsonMapper.writeValueAsString(tx)));
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public Tx get(UUID txId) {
		RawJsonDocument doc = bucket.get(txId.toString(), RawJsonDocument.class);
		try {
			return jsonMapper.readValue(doc.content(), Tx.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
