    package tn.esprit.tic.timeforge.Entity;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    import java.util.Set;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        // Getter for 'id'
        public Long getId() {
            return id;
        }

        // Setter for 'id'
        public void setId(Long id) {
            this.id = id;
        }

        // Getter for 'name'
        public String getName() {
            return name;
        }

        // Setter for 'name'
        public void setName(String name) {
            this.name = name;
        }
    }
