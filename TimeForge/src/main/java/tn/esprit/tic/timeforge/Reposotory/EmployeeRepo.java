package tn.esprit.tic.timeforge.Reposotory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tic.timeforge.Entity.Employee;
import tn.esprit.tic.timeforge.Entity.Teamlead;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
