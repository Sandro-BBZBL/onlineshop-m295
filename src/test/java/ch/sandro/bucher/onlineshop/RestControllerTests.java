package ch.sandro.bucher.onlineshop;

import ch.sandro.bucher.onlineshop.category.Category;
import ch.sandro.bucher.onlineshop.category.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        @AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc api;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    void setup() {
        this.categoryRepository.save(new Category("Test-Elektronik"));
    }

    @Test
    @Order(1)
    void testGetCategories() throws Exception {
        String accessToken = obtainAccessToken();

        api.perform(get("/api/categories")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test-Elektronik")));
    }

    @Test
    @Order(2)
    void testSaveCategory() throws Exception {
        Category category = new Category();
        category.setName("Moebel-Test"); // Umlaute in Tests am besten vermeiden ("Buecher" -> "Moebel")

        String accessToken = obtainAccessToken();
        String body = new ObjectMapper().writeValueAsString(category);

        api.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Moebel-Test")));
    }

    private String obtainAccessToken() {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=demoapp" +
                "&grant_type=password" +
                "&scope=openid profile roles offline_access" +
                "&username=admin" +
                "&password=admin";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // TODO: HIER DEINEN REALM EINTRAGEN (Falls er nicht ILV heisst)
        String keycloakUrl = "http://localhost:8080/realms/ILV/protocol/openid-connect/token";

        ResponseEntity<String> resp = rest.postForEntity(keycloakUrl, entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }
}