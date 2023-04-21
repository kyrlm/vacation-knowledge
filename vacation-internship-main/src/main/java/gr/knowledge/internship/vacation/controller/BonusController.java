package gr.knowledge.internship.vacation.controller;


import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@Log4j2
@RequestMapping("/api")
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }


    @CrossOrigin
    @PostMapping("/bonus")
    public ResponseEntity<BonusDTO> createBonus (@RequestBody BonusDTO bonusDTO){
        log.debug("Rest request to save Bonus : {}",bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> getBonus(@PathVariable Long id){
        log.debug("Rest request to get Bonus by id : {}",id);
        BonusDTO result = bonusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllBonus")
    public ResponseEntity<List<BonusDTO>> getAllBonus(){
        log.debug("Rest request to get Bonus by id : ");
       List<BonusDTO> result = bonusService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBonusById/{id}")
    public void deleteBonus(@PathVariable Long id){
        log.debug("Rest request to delete Bonus by id : {}",id);
        bonusService.deleteById(id);
    }

    @PutMapping("/updateBonus")
    public ResponseEntity<BonusDTO> updateBonus ( @RequestBody BonusDTO bonusDTO){
        log.debug("Rest request to update Bonus : {}",bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // task 3 : Calculation of Bonus//
    @GetMapping("/getBonusForSeason")
    public Double getBonus(@RequestParam Double salary, @RequestParam String season){
        log.debug("Rest request to get Bonus for season : {}",season);
        return bonusService.calculateBonus(salary,season);
    }

    // task 7 : Create Bonuses for a Company //
    @GetMapping("/getCompanyBonus")
    public List<Bonus> companyBonus(@RequestParam Long companyId, @RequestParam String season){
        log.debug("Rest request to get Bonus for a company : {}",season);
        return bonusService.companyBonus(companyId,season);
    }

}
