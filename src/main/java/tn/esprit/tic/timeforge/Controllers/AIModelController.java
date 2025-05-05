package tn.esprit.tic.timeforge.Controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIModelController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/recommend")
    public ResponseEntity<?> recommendTechnique(@RequestBody Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

            String fastApiUrl = "http://localhost:5005/recommend"; // 🔥 lien vers ton serveur FastAPI

            ResponseEntity<String> response = restTemplate.postForEntity(fastApiUrl, requestEntity, String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error connecting to AI Recommendation service: " + e.getMessage());
        }
    }
}
