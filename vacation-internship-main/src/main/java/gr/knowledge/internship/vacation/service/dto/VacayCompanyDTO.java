package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacayCompanyDTO implements Serializable {

    Long company;

    String status;

    LocalDate startDate;

    LocalDate endDate;
}
