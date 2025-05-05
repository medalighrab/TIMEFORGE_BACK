package tn.esprit.tic.timeforge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Task;
import tn.esprit.tic.timeforge.repository.TaskRepository;
import tn.esprit.tic.timeforge.service.security.EmailService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskReminderService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 * * * * ?")
    public void sendReminderForUpcomingDeadlines() {
        LocalDate deadlineInTwoDays = LocalDate.now().plusDays(2);
        List<Task> tasks = taskRepository.findByDeadline(deadlineInTwoDays);

        for (Task task : tasks) {
            String email = task.getEmployee11().getUsername();
            String subject = "Rappel : Tâche à terminer bientôt";
            String body = "<html>" +
                    "<body style='font-family: Arial, sans-serif; background-color: #f4f7fc; padding: 20px;'>" +
                    "<table role='presentation' style='width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                    "<tr>" +
                    "<td style='background-color: #3498db; padding: 20px; border-top-left-radius: 10px; border-top-right-radius: 10px;'>" +
                    "<h2 style='color: #ffffff; text-align: center;'>Rappel de Deadline : Tâche à terminer</h2>" +
                    "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 20px;'>" +
                    "<p style='font-size: 16px; color: #333333;'>Bonjour <strong>" + task.getEmployee11().getName() + "</strong>,</p>" +
                    "<p style='font-size: 16px; color: #333333;'>Nous vous rappelons que la tâche suivante a une deadline dans 2 jours :</p>" +
                    "<h3 style='color: #e74c3c; font-size: 20px; text-align: center;'>" + task.getName() + "</h3>" +
                    "<p style='font-size: 16px; color: #333333;'>Veuillez la terminer à temps pour respecter les délais.</p>" +
                    "<p style='font-size: 16px; color: #333333;'>Merci de votre attention !</p>" +
                    "<hr style='border: 0; border-top: 1px solid #f1f1f1; margin: 20px 0;'>" +
                    "<p style='font-size: 14px; color: #7f8c8d; text-align: center;'>Cordialement,<br/>L'équipe de gestion des tâches</p>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</body>" +
                    "</html>";

            emailService.sendEmail(email, subject, body);
        }
    }



}
