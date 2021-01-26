package ch.hfict.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import ch.hfict.springboot.model.UserDto;

import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void listAllUsers() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/users", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(2));

        assertThat(root.get(0).get("username").asText(), equalTo("homer"));
        assertThat(root.get(1).get("username").asText(), equalTo("marge"));
    }

    @Test
    public void createUser() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("bart");
        user.setPassword("1234");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/users", request,
                String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.get("username").asText(), equalTo("bart"));
    }
}
