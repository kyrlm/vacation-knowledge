package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
public class AcceptOrRejectDTO {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VacationRequestDTO vacation;

    String status;
}
