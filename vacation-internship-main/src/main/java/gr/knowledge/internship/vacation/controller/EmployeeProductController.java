package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeProductService;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeProductController {

    private final EmployeeProductService employeeProductService;

    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    @CrossOrigin
    @PostMapping("/employeeProduct")
    public ResponseEntity<EmployeeProductDTO> createEmployeeProduct(@RequestBody EmployeeProductDTO employeeProductDTO){
        log.debug("Rest request to save EmployeeProduct : {}",employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> getEmployeeProduct(@PathVariable Long id){
        log.debug("Rest request to get EmployeeProduct by id : {}",id);
        EmployeeProductDTO result = employeeProductService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployeeProducts")
    public ResponseEntity<List<EmployeeProductDTO>> getAllEmployeeProducts(){
        log.debug("Rest request to get all employee products: ");
        List<EmployeeProductDTO> result = employeeProductService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeProductById/{id}")
    public void deleteBonus(@PathVariable Long id){
        log.debug("Rest request to delete employee product by id : {}",id);
        employeeProductService.deleteById(id);
    }

    @PutMapping("/updateEmployeeProduct")
    public ResponseEntity<EmployeeProductDTO> updateEmployeeProduct (@RequestBody EmployeeProductDTO employeeProductDTO){
        log.debug("Rest request to update employee : {}",employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
