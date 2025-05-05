package tn.esprit.tic.timeforge.service;



import org.springframework.data.jpa.domain.Specification;
import tn.esprit.tic.timeforge.entity.Ennum.StatusTask;
import tn.esprit.tic.timeforge.entity.Task;

import java.time.LocalDate;

public class TaskSpecification {

    public static Specification<Task> hasNameLike(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Task> hasDescriptionLike(String description) {
        return (root, query, cb) ->
                description == null ? null : cb.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Task> hasStartDateAfter(LocalDate startDate) {
        return (root, query, cb) ->
                startDate == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Task> hasDeadlineBefore(LocalDate deadline) {
        return (root, query, cb) ->
                deadline == null ? null : cb.lessThanOrEqualTo(root.get("deadline"), deadline);
    }

    public static Specification<Task> hasStatus(StatusTask status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Task> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("isdeleted"));
    }
}
