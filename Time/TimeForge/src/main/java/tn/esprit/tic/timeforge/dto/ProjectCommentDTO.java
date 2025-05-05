// ProjectCommentDTO.java
package tn.esprit.tic.timeforge.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectCommentDTO {
    private Long id;
    private String comment;
    private String createdAt;
    private String username; // on prend sub (email)
    private String name;     // nom complet (ex: Alice Dupont)
}
