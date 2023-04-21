package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestDTO implements Serializable {

    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    LocalDate startDate;

    LocalDate endDate;

    @Size(max = 20)
    String status;

    Integer days;
}
