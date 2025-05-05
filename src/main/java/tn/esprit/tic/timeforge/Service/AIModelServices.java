package tn.esprit.tic.timeforge.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIModelServices {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getTechniqueRecommendation(String text) {
        String url = "http://localhost:5005/recommend";

        // Préparer le corps de la requête
        Map<String, String> request = new HashMap<>();
        request.put("text", text);

        // Préparer les headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construire l'objet HTTP
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        // Faire l'appel POST
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        // Retourner la recommandation
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("recommendation");
        } else {
            return "No recommendation available.";
        }
    }
}
