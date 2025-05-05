package tn.esprit.tic.timeforge.Controllers;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tic.timeforge.Entity.Goals;
import tn.esprit.tic.timeforge.Service.GoalsService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class GoalReportController {

    @Autowired
    private GoalsService goalService;

    @GetMapping("/goals")
    public ResponseEntity<byte[]> generateGoalsReport() throws IOException {
        List<Goals> completedGoals = goalService.getCompletedGoals();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(); // correction ici : bon import
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Rapport des Objectifs Terminés", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE); // tu peux maintenant ajouter Chunk car bon import

        for (Goals goal : completedGoals) {
            Paragraph para = new Paragraph("- " + goal.getTitle() + " (Terminé le : " + goal.getCompletedDate() + ")");
            document.add(para);
        }

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("completed_goals_report.pdf").build());

        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
    }
}
