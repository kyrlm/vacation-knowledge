package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;


    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @CrossOrigin
    @PostMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> createVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("Rest request to save vacation request : {}", vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> getVacationRequest(@PathVariable Long id) {
        log.debug("Rest request to get vacation request by id : {}", id);
        VacationRequestDTO result = vacationRequestService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllVacationRequests")
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequests() {
        log.debug("Rest request to get all vacation requests: ");
        List<VacationRequestDTO> result = vacationRequestService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/deleteVacationRequestById/{id}")
    public void deleteBonus(@PathVariable Long id) {
        log.debug("Rest request to delete vacation request by id : {}", id);
        vacationRequestService.deleteById(id);
    }

    @PutMapping("/updateVacationRequest")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("Rest request to update vacation employee : {}", vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // task 2 : Vacation Request //
    @CrossOrigin
    @PostMapping("/getVacationRequest")
    public ResponseEntity<VacationRequestDTO> getVacationRequest(@RequestBody VacayRequestDTO vacayRequestDTO) {
        log.debug("Rest request to get a vacation request: {}", vacayRequestDTO);
        VacationRequestDTO result = vacationRequestService.vacayRequest(vacayRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // task 4 : Vacation  Requests by Company //
    @CrossOrigin
    @PostMapping("/getCompanyRequests")
    public List<VacationRequest> getCompanyRequests(@RequestBody VacayCompanyDTO vacayCompanyDTO ){
        log.debug("Rest request to get a company request: {}", vacayCompanyDTO);

        return vacationRequestService.companyVacationRequest(vacayCompanyDTO);
    }

    // task 5 : Accept or Reject Vacation Request //
    @PutMapping("/acceptOrRejectRequest")
    public VacationRequestDTO acceptOrReject(@RequestBody AcceptOrRejectDTO acceptOrRejectDTO){
        return vacationRequestService.acceptedOrRejectedVacay2(acceptOrRejectDTO);
    }

}
