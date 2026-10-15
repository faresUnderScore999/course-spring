package com.example.demo.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.jayway.jsonpath.JsonPath;

/**
 * End-to-end smoke test of the Agence / Client / Vehicule / Paiement /
 * Reservation REST API: full CRUD, list and fetch-by endpoints, nested
 * references, HTTP status codes and cycle-free JSON serialization.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CrudEndpointsIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	// ---------------------------------------------------------------- Agence

	@Test
	void agenceFullCrudAndFindByVille() throws Exception {
		MvcResult created = mockMvc.perform(post("/api/agences")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nom":"Agence Centrale","ville":"Tunis","adresse":"12 Rue du Lac","telephone":"71000000"}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.idAgence").isNumber())
				.andExpect(jsonPath("$.ville").value("Tunis"))
				.andReturn();
		int id = JsonPath.parse(created.getResponse().getContentAsString()).read("$.idAgence", Integer.class);

		mockMvc.perform(get("/api/agences"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

		mockMvc.perform(get("/api/agences/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("Agence Centrale"));

		mockMvc.perform(get("/api/agences/ville/{ville}", "Tunis"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idAgence").value(id));

		mockMvc.perform(get("/api/agences/ville/{ville}", "Inconnue"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		mockMvc.perform(put("/api/agences/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nom":"Agence Nord","ville":"Sfax","adresse":"Avenue 1","telephone":"74000000"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nom").value("Agence Nord"))
				.andExpect(jsonPath("$.ville").value("Sfax"));

		mockMvc.perform(get("/api/agences/{id}", 999999))
				.andExpect(status().isNotFound());

		mockMvc.perform(delete("/api/agences/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/agences/{id}", id))
				.andExpect(status().isNotFound());
	}

	// ---------------------------------------------------------------- Client

	@Test
	void clientCrudAndFindByEmail() throws Exception {
		MvcResult created = mockMvc.perform(post("/api/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nom":"Ben Ali","prenom":"Sami","email":"sami@example.com","telephone":"22111222",
						 "numPermis":"TN-123","dateInscription":"2026-01-15"}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.dateInscription").value("2026-01-15"))
				.andReturn();
		int id = JsonPath.parse(created.getResponse().getContentAsString()).read("$.idClient", Integer.class);

		mockMvc.perform(get("/api/clients/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.prenom").value("Sami"));

		mockMvc.perform(get("/api/clients/email/{email}", "sami@example.com"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idClient").value(id));

		mockMvc.perform(get("/api/clients/email/{email}", "absent@example.com"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		mockMvc.perform(put("/api/clients/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nom":"Ben Ali","prenom":"Karim","email":"karim@example.com","telephone":"22333444",
						 "numPermis":"TN-456","dateInscription":"2026-01-15"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.prenom").value("Karim"));

		mockMvc.perform(delete("/api/clients/{id}", id))
				.andExpect(status().isNoContent());
	}

	// --------------------------------------------------------------- Vehicule

	@Test
	void vehiculeCrudWithAgenceReferenceAndFinders() throws Exception {
		int agenceId = createAgence("Agence Sud", "Sousse");

		MvcResult created = mockMvc.perform(post("/api/vehicules")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"immatriculation":"123-TN-456","marque":"Peugeot","modele":"208",
						 "categorie":"CITADINE","tarifJournalier":85.5,"statut":"DISPONIBLE",
						 "agence":{"idAgence":%d}}
						""".formatted(agenceId)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.agence.nom").value("Agence Sud"))
				.andReturn();
		int id = JsonPath.parse(created.getResponse().getContentAsString()).read("$.idVehicule", Integer.class);

		mockMvc.perform(get("/api/vehicules"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.idVehicule == " + id + ")].agence.nom", hasItem("Agence Sud")));

		mockMvc.perform(get("/api/vehicules/statut/{statut}", "DISPONIBLE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idVehicule").value(id));

		mockMvc.perform(get("/api/vehicules/statut/{statut}", "BOGUS"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/api/vehicules/agence/{idAgence}", agenceId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].immatriculation").value("123-TN-456"));

		// PUT without "agence" key => current link must be preserved
		mockMvc.perform(put("/api/vehicules/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"immatriculation":"123-TN-456","marque":"Peugeot","modele":"208",
						 "categorie":"BERLINE","tarifJournalier":95.0,"statut":"LOUE"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categorie").value("BERLINE"))
				.andExpect(jsonPath("$.statut").value("LOUE"))
				.andExpect(jsonPath("$.agence.idAgence").value(agenceId));

		// Unknown agency reference => 404
		mockMvc.perform(post("/api/vehicules")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"immatriculation":"999-TN-000","marque":"Renault","modele":"Clio",
						 "categorie":"CITADINE","tarifJournalier":70.0,"statut":"DISPONIBLE",
						 "agence":{"idAgence":999999}}
						"""))
				.andExpect(status().isNotFound());

		mockMvc.perform(delete("/api/vehicules/{id}", id))
				.andExpect(status().isNoContent());
	}

	// --------------------------------------------------------------- Paiement

	@Test
	void paiementFullCrud() throws Exception {
		MvcResult created = mockMvc.perform(post("/api/paiements")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"montant":598.5,"datePaiement":"2026-03-08","modePaiement":"CARTE"}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.modePaiement").value("CARTE"))
				.andReturn();
		int id = JsonPath.parse(created.getResponse().getContentAsString()).read("$.idPaiement", Integer.class);

		mockMvc.perform(get("/api/paiements"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

		mockMvc.perform(get("/api/paiements/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.montant").value(598.5));

		mockMvc.perform(put("/api/paiements/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"montant":650.0,"datePaiement":"2026-03-09","modePaiement":"VIREMENT"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.modePaiement").value("VIREMENT"));

		mockMvc.perform(delete("/api/paiements/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/paiements/{id}", id))
				.andExpect(status().isNotFound());
	}

	// ------------------------------------------------------------- Reservation

	@Test
	void reservationCrudWithNestedReferencesAndFinders() throws Exception {
		int agenceId = createAgence("Agence Est", "Sfax");
		int vehiculeId = createVehicule(agenceId, "444-TN-777");
		int clientId = createClient("trabelsi@example.com");

		MvcResult created = mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"dateDebut":"2026-03-01","dateFin":"2026-03-08","statut":"EN_ATTENTE",
						 "vehicule":{"idVehicule":%d},"client":{"idClient":%d}}
						""".formatted(vehiculeId, clientId)))
				.andExpect(status().isCreated())
				// deep nesting must serialize without cycles: vehicule -> agence
				.andExpect(jsonPath("$.vehicule.idVehicule").value(vehiculeId))
				.andExpect(jsonPath("$.vehicule.agence.nom").value("Agence Est"))
				.andExpect(jsonPath("$.client.email").value("trabelsi@example.com"))
				.andReturn();
		int id = JsonPath.parse(created.getResponse().getContentAsString()).read("$.idReservation", Integer.class);

		mockMvc.perform(get("/api/reservations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].vehicule.agence.idAgence").value(agenceId));

		mockMvc.perform(get("/api/reservations/statut/{statut}", "EN_ATTENTE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idReservation").value(id));

		mockMvc.perform(get("/api/reservations/client/{idClient}", clientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].idReservation").value(id));

		mockMvc.perform(get("/api/reservations/vehicule/{idVehicule}", vehiculeId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].client.idClient").value(clientId));

		// PUT without nested keys => vehicule / client links must be preserved
		mockMvc.perform(put("/api/reservations/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"dateDebut":"2026-03-02","dateFin":"2026-03-10","statut":"CONFIRMEE"}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.statut").value("CONFIRMEE"))
				.andExpect(jsonPath("$.vehicule.idVehicule").value(vehiculeId))
				.andExpect(jsonPath("$.client.idClient").value(clientId));

		// Unknown vehicule reference => 404
		mockMvc.perform(post("/api/reservations")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"dateDebut":"2026-04-01","dateFin":"2026-04-05","statut":"EN_ATTENTE",
						 "vehicule":{"idVehicule":999999},"client":{"idClient":%d}}
						""".formatted(clientId)))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/api/reservations/{id}", 999999))
				.andExpect(status().isNotFound());

		mockMvc.perform(delete("/api/reservations/{id}", id))
				.andExpect(status().isNoContent());
	}

	// -------------------------------------------------------------- helpers

	private int createAgence(String nom, String ville) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/agences")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"nom\":\"%s\",\"ville\":\"%s\"}".formatted(nom, ville)))
				.andExpect(status().isCreated())
				.andReturn();
		return JsonPath.parse(result.getResponse().getContentAsString()).read("$.idAgence", Integer.class);
	}

	private int createVehicule(int agenceId, String immatriculation) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/vehicules")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"immatriculation":"%s","marque":"Renault","modele":"Clio",
						 "categorie":"CITADINE","tarifJournalier":70.0,"statut":"DISPONIBLE",
						 "agence":{"idAgence":%d}}
						""".formatted(immatriculation, agenceId)))
				.andExpect(status().isCreated())
				.andReturn();
		return JsonPath.parse(result.getResponse().getContentAsString()).read("$.idVehicule", Integer.class);
	}

	private int createClient(String email) throws Exception {
		MvcResult result = mockMvc.perform(post("/api/clients")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"nom":"Trabelsi","prenom":"Leila","email":"%s","telephone":"55666777",
						 "numPermis":"TN-999","dateInscription":"2026-02-01"}
						""".formatted(email)))
				.andExpect(status().isCreated())
				.andReturn();
		return JsonPath.parse(result.getResponse().getContentAsString()).read("$.idClient", Integer.class);
	}
}
