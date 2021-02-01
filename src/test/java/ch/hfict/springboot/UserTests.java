package ch.hfict.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.lessThan;

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

    @Test
    public void doNotReturnPassword() throws Exception {
        // Create User
        UserDto user = new UserDto();
        user.setUsername("lisa");
        user.setPassword("1234");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.get("password"), nullValue());

        // Get User List
        ResponseEntity<String> responseList = this.restTemplate.getForEntity("http://localhost:" + port + "/users",
                String.class);
        assertThat(responseList.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode rootList = this.mapper.readTree(responseList.getBody());
        assertThat(rootList.get(0).get("password"), nullValue());

        // Single User
        ResponseEntity<String> responseUser = this.restTemplate.getForEntity("http://localhost:" + port + "/users/1",
                String.class);
        assertThat(responseUser.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode rootUser = this.mapper.readTree(responseUser.getBody());
        assertThat(rootUser.get("password"), nullValue());
    }

    @Test
    public void uniqueUsername() throws Exception {
        // Create User
        UserDto user = new UserDto();
        user.setUsername("maggie");
        user.setPassword("1234");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ResponseEntity<String> responseDuplicate = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                request, String.class);

        assertThat(responseDuplicate.getStatusCode(), equalTo(HttpStatus.CONFLICT));
    }

    @Test
    public void emptyPassword() throws Exception {
        // Create User with no password
        UserDto user = new UserDto();
        user.setUsername("carl");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserDto> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CONFLICT));

        // Create User with empty password
        user.setPassword("");
        request = new HttpEntity<>(user, headers);

        response = this.restTemplate.postForEntity("http://localhost:" + port + "/users",
                request, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CONFLICT));
    }

    @Test
    public void newestIdFirst() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/users?newestIdFirst=1",
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        JsonNode root = this.mapper.readTree(response.getBody());
        assertThat(root.size(), equalTo(2));

        Long lastId = null;
        for (int x=0; x < root.size(); x++) {
            if (lastId == null) {
                lastId = root.get(x).get("id").asLong();
                continue;
            }

            assertThat(root.get(x).get("id").asLong(), lessThan(lastId));
            lastId = root.get(x).get("id").asLong();
        }
    }
}
