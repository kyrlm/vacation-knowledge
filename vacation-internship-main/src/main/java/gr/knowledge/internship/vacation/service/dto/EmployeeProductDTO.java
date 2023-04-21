package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
public class EmployeeProductDTO {


    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProductDTO product;
}
