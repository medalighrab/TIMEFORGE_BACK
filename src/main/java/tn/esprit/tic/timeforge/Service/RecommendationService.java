package tn.esprit.tic.timeforge.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecommendationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getRecommendation(String text) {
        String url = "http://localhost:5005/recommend";

        // Création de la requête JSON
        Map<String, String> request = new HashMap<>();
        request.put("text", text);

        // Préparer headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer l'objet HTTP
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Appel POST
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        // Extraire la recommandation
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("recommendation");
        } else {
            return "No recommendation";
        }
    }
}