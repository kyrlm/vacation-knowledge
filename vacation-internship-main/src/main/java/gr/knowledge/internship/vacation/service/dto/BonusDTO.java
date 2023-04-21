package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BonusDTO implements Serializable {
    Long id;

    @NotNull
    Double amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO employeeCompany;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;
}