package bsd.kschmitt.eval;

import bsd.kschmitt.eval.model.Convention;
import bsd.kschmitt.eval.model.Salarie;
import bsd.kschmitt.eval.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class EvaluationJavaApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		objectMapper = new ObjectMapper();
	}

	@Test
	void connexion_avecUtilisateurValide_reponse200() throws Exception {
		Users utilisateur = new Users();
		utilisateur.setEmail("a@a.com");
		utilisateur.setPassword("root");

		mvc.perform(post("/connexion")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(utilisateur)))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGEuY29tIn0.WLK31zXu66JK56jOUK91gZg4HZv3InjphZVqCf80XlU")));
	}

	@Test
	void recuperation_utilisateurParId_utilisateursTrouve() throws Exception {
		Integer userId = 1;

		mvc.perform(get("/api/user")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("id")))
				.andExpect(content().string(org.hamcrest.Matchers.containsString("email")));
	}

	@Test
	void creation_salarie_parUtilisateurNonAutorise_reponse403() throws Exception {
		Salarie salarie = new Salarie();
		Convention convention = new Convention();
		convention.setId(1);
		convention.setNom("Convention Test 1");
		convention.setSalarieMaximum(5);
		salarie.setMatricule("MAT123");
		salarie.setCodeBarre("123456789");
		salarie.setConvention(convention);

		mvc.perform(post("/api/salaries")
						.with(user("user").roles("UTILISATEUR"))
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(salarie)))
				.andExpect(status().isForbidden());
	}
}