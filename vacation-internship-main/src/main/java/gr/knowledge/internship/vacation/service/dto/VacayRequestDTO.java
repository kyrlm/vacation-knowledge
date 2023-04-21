package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
public class VacayRequestDTO {

    LocalDate startDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    LocalDate endDate;

    Integer holiday;
}
