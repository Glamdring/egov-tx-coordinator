package bg.egov;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Root of the application.
 * 
 * Configuration is split in two:
 * 
 * - java-style using @Bean, for programmatic bean instantiation
 * - xml-style (root: resources.xml) for configuration-related beans
 * @author bozho
 *
 */
@SpringBootApplication
@ImportResource("classpath:/resources.xml")
public class TxCoordinatorApplication {
	
	@Autowired
	private CouchbaseCluster couchbaseCluster;
	
	@Bean
	public ObjectMapper jsonMapper() {
		ObjectMapper jsonMapper = new ObjectMapper();
	
		jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		jsonMapper.registerModule(new Jdk8Module());
		jsonMapper.registerModule(new JavaTimeModule());
		jsonMapper.setSerializationInclusion(Include.NON_NULL);
		jsonMapper.setTimeZone(TimeZone.getTimeZone("EET"));
		
		return jsonMapper;
	}
	
	@Bean
	public Bucket couchbaseBucket() {
		return couchbaseCluster.openBucket("tx-coordinator"); //TODO externalize
	}
	
    public static void main(String[] args) {
        SpringApplication.run(TxCoordinatorApplication.class, args);
    }
}
