package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.service.CompanyService;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @CrossOrigin
    @PostMapping("/company")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyDTO companyDTO){
        log.debug("Rest request to save Company : {}",companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id){
        log.debug("Rest request to get Company by id : {}",id);
        CompanyDTO result = companyService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllCompany")
    public ResponseEntity<List<CompanyDTO>> getAllCompany(){
        log.debug("Rest request to get all companies: ");
        List<CompanyDTO> result = companyService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCompanyById/{id}")
    public void deleteBonus(@PathVariable Long id){
        log.debug("Rest request to delete Company by id : {}",id);
        companyService.deleteById(id);
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<CompanyDTO> updateCompany (@RequestBody CompanyDTO companyDTO){
        log.debug("Rest request to update company : {}",companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    // task 6 :Calculate monthly expenses for Company //
    @GetMapping("/getCompanyMonthlyExpences/{id}")
    public Double companyExpences(@PathVariable Long id) {
        log.debug("Rest request to get monthly expenses of Company by id : {}",id);
        return companyService.companyMonthlyExpences(id);
    }

    // task 8 : Get All products for a Company //
    @GetMapping("/getCompanyProducts/{id}")
    public Map<String, List<Product>> getCompanyProducts(@PathVariable Long id){
        Map<String, List<Product>> productMap;
        productMap = companyService.getCompanyProducts(id);
        return productMap;
    }
}
