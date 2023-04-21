package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, JpaSpecificationExecutor<VacationRequest> {


    // task 4 : Vacation  Requests by Company //
    @Query("SELECT v FROM VacationRequest v INNER JOIN Employee e ON e.id = v.employee.id INNER JOIN Company c ON e.employeeCompany.id = c.id WHERE v.startDate >= :startDate AND v.endDate <= :endDate and v.status =:status and c.id =:companyId")
    List<VacationRequest> getVacationRequest(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate,
                                             @Param("status") String status,
                                             @Param("companyId") Long companyId);
}
