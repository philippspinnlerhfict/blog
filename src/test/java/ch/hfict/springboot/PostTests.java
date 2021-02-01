package ch.hfict.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void findByUserId() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/posts?findByUserId=1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(1));

        assertThat(root.get(0).get("user").get("username").asText(), equalTo("homer"));
    }
}
