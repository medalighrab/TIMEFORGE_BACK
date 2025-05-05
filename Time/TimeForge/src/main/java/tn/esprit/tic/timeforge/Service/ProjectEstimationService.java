package tn.esprit.tic.timeforge.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import tn.esprit.tic.timeforge.Entity.Project;
import tn.esprit.tic.timeforge.Repository.ProjectRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectEstimationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    // URLs des APIs Flask pour prédiction et solutions
    private static final String FLASK_API_URL = "http://127.0.0.1:5000/predict";
    private static final String FLASK_SOLUTION_API_URL = "http://127.0.0.1:5000/suggest-solution";

    /**
     * Construit les données à envoyer à l'API Flask pour un projet donné.
     * Lève une exception si le projet n'a pas de deadline définie.
     */
    private Map<String, Object> buildProjectRequestData(Project project, Long projectId) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("project_id", projectId);
        requestData.put("task_duration", project.getTaskDuration());
        requestData.put("task_status", project.getTaskStatus());
        requestData.put("blocked_duration", project.getBlockedDuration());
        requestData.put("project_duration", project.getProjectDuration());

        if (project.getDeadline() != null) {
            LocalDate deadlineLocalDate = project.getDeadline().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            requestData.put("project_deadline", deadlineLocalDate.toString());
        } else {
            // Exception claire si aucune deadline n'est définie
            throw new IllegalArgumentException("Le projet avec l'ID " + projectId + " n'a pas de deadline définie.");
        }
        return requestData;
    }

    /**
     * Prédit la date de fin du projet en appelant l'API Flask.
     * Retourne la date calculée en LocalDate.
     */
    public LocalDate estimateProjectEndDate(Long projectId) {
        // Récupérer le projet ou lever une exception si non trouvé
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Projet introuvable avec ID: " + projectId));

        Map<String, Object> requestData = buildProjectRequestData(project, projectId);
        ResponseEntity<Map> response = restTemplate.postForEntity(FLASK_API_URL, requestData, Map.class);

        if (response.getBody() == null || response.getBody().get("estimated_end_date") == null) {
            throw new RuntimeException("Erreur lors de la récupération de la date estimée depuis le service IA.");
        }
        String estimatedDateStr = response.getBody().get("estimated_end_date").toString();
        return LocalDate.parse(estimatedDateStr);
    }

    /**
     * Suggère une solution pour le projet en utilisant le service Flask.
     * Retourne la solution sous forme de texte.
     */
    public String suggestSolutionForProject(Long projectId) {
        // Récupérer le projet ou levée d'une exception si non trouvé
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Projet introuvable avec ID: " + projectId));

        // Estimer d'abord la date de fin
        Map<String, Object> predictRequest = buildProjectRequestData(project, projectId);
        ResponseEntity<Map> predictResponse = restTemplate.postForEntity(FLASK_API_URL, predictRequest, Map.class);

        if (predictResponse.getBody() == null || !predictResponse.getBody().containsKey("estimated_end_date")) {
            throw new RuntimeException("La prédiction de fin de projet a échoué.");
        }
        String estimatedEndDate = predictResponse.getBody().get("estimated_end_date").toString();

        // Appeler l'API Flask pour obtenir une solution basée sur la date estimée
        Map<String, Object> solutionRequest = new HashMap<>();
        solutionRequest.putAll(predictRequest);
        solutionRequest.put("estimated_end_date", estimatedEndDate);
        ResponseEntity<Map> solutionResponse = restTemplate.postForEntity(FLASK_SOLUTION_API_URL, solutionRequest, Map.class);

        if (solutionResponse.getBody() == null || !solutionResponse.getBody().containsKey("solution")) {
            throw new RuntimeException("Aucune solution n'a été trouvée pour ce projet.");
        }
        return solutionResponse.getBody().get("solution").toString();
    }

    /**
     * Applique la solution suggérée en mettant à jour la deadline du projet.
     * - Récupère le projet et lève une exception si absent.
     * - Enregistre l'ancienne deadline dans 'oldDeadline' si nécessaire.
     * - Extrait le nombre de jours à ajouter depuis la solution et met à jour la deadline.
     * - Sauvegarde les modifications.
     */
    @Transactional
    public void applySuggestedSolution(Long projectId) {
        // Récupération du projet ou exception si non trouvé
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Projet introuvable avec ID: " + projectId));

        // Récupération de la solution suggérée
        String solution = suggestSolutionForProject(projectId);

        // Vérifier si la solution concerne la prolongation de la deadline
        if (solution.contains("Prolonger")) {
            // Sauvegarder l'ancienne deadline si elle n'était pas définie
            if (project.getOld_deadline() == null) {
                project.setOld_deadline(project.getDeadline());
            }
            try {
                // Extraire le nombre de jours à ajouter depuis la solution (digits seulement)
                int daysToAdd = Integer.parseInt(solution.replaceAll("\\D+", ""));
                LocalDate currentDeadline = project.getDeadline().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate newDeadlineLocal = currentDeadline.plusDays(daysToAdd);
                project.setDeadline(Date.from(newDeadlineLocal.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                projectRepository.save(project);
            } catch (NumberFormatException e) {
                // Exception claire en cas d'erreur de parsing
                throw new IllegalArgumentException("Erreur lors du parsing du nombre de jours depuis la solution : " + solution);
            }
        } else {
            // Si la solution ne concerne pas la prolongation, lever une exception explicite
            throw new IllegalArgumentException("La solution suggérée n'implique pas la prolongation de la deadline : " + solution);
        }
    }
}
