package tn.esprit.tic.timeforge.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Entity.Task;
import tn.esprit.tic.timeforge.Repository.GoalsRepository;
import tn.esprit.tic.timeforge.Repository.TaskRepository;
import tn.esprit.tic.timeforge.Service.GoalsService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/goals")
public class GoalsController {

    @Autowired
    GoalsService goalsService;

    @Autowired
    GoalsRepository goalsRepository;

    @Autowired
    TaskRepository taskRepository;


    // ✅ Activation de la validation en ajoutant @Valid
    @PostMapping("add/{TaskId}")
    public ResponseEntity<Goals> ajout(@Valid @RequestBody Goals goals, @PathVariable long TaskId) {
        Goals savedGoal = goalsService.addGoalAndAssignToTask(goals, TaskId);
        return ResponseEntity.ok(savedGoal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalsService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goals> getGoalById(@PathVariable Long id) {
        Goals goal = goalsService.getGoalById(id);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Goals>> getAllGoals() {
        return ResponseEntity.ok(goalsService.getAllGoals());
    }

    @GetMapping("goalbytaks/{taskid}")
    public List<Goals> getgoalsbytaskid(@PathVariable long taskid) {
        return goalsRepository.findGoalsByTask13_Id(taskid);
    }

    @GetMapping("/{id}/with-goals")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskRepository.findById(id).orElse(null));
    }

    @GetMapping("/taksbygoals")
    public List<Task> Retreivealltasks() {
        return taskRepository.findAll();
    }
    @GetMapping("/next")
    public ResponseEntity<String> predictNextGoal() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "ml/predict_next_goal.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return ResponseEntity.ok(output);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur dans le script IA : " + output);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la prédiction : " + e.getMessage());
        }
    }
    @PostMapping("/detect-toxicity")
    public ResponseEntity<Map<String, Object>> detectToxicity(@RequestBody String text) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "C:\\Users\\Lenovo\\Desktop\\toxic_detector.py", text);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Erreur lors de l'exécution du script Python");
            }

            // Extraire uniquement le JSON depuis la première accolade
            int jsonStart = output.indexOf("{");
            if (jsonStart == -1) {
                throw new RuntimeException("Aucun JSON détecté dans la sortie Python");
            }

            String jsonString = output.substring(jsonStart);

            // Convertir le JSON string en Map et le retourner
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(jsonString, Map.class);

            return ResponseEntity.ok(jsonMap);

        } catch (Exception e) {
            throw new RuntimeException("Erreur dans GoalController detectToxicity()", e);
        }
    }



    @GetMapping("/predict")
    public String predictGoal() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python", "C:\\Users\\Lenovo\\Desktop\\goal_predictor_simple.py"
            );
            pb.redirectErrorStream(true);

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();
            return output.toString();
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }



}
    @GetMapping("/user/{userId}/score")
    public ResponseEntity<Double> getUserScore(@PathVariable Long userId) {
        double score = goalsService.calculateUserScore(userId);
        return ResponseEntity.ok(score);
    }
    @GetMapping("/{id}/report")
    public ResponseEntity<byte[]> generateGoalReport(@PathVariable Long id) {
        Goals goal = goalsService.getGoalById(id);
        if (goal == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = tn.esprit.tic.timeforge.Utils.GoalPdfGenerator.generateGoalPdf(goal);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "goal-report-" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

