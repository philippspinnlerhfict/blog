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
public class CommentTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void limit() throws Exception {
        // limit 1
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/comments?limit=1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(1));

        // limit 2
        response = this.restTemplate.getForEntity("http://localhost:" + port + "/comments?limit=2", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(2));

        // limit 3
        response = this.restTemplate.getForEntity("http://localhost:" + port + "/comments?limit=3", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(2));

        // limit 0
        response = this.restTemplate.getForEntity("http://localhost:" + port + "/comments?limit=0", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(0));
    }
}
