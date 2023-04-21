package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {



    // task 6 :Calculate monthly expenses for Company //
    @Query("SELECT SUM(e.salary) FROM Company c INNER JOIN Employee e ON c.id = e.employeeCompany.id WHERE c.id =:companyId")
    Double getCompanyMonthlyExpenses( @Param("companyId") Long companyId);


    // task 7: Create Bonuses for a Company //
    // task 8 : Get All products for a Company //
    @Query("SELECT e FROM Company c INNER JOIN Employee e ON e.employeeCompany.id  = c.id WHERE e.employeeCompany.id =:companyId")
    List<Employee> getEmployeesOfACompany(@Param("companyId") Long companyId);


}
