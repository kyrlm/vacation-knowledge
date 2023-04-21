package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "product_seq")
    @Column(name = "id" , nullable = false)
    Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    String name;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description")
    String description;

    @NotNull
    @Size(max = 255)
    @Column(name = "barcode")
    String barcode;

}
