package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.enums.VacationStatus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.*;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
@Log4j2
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final VacationRequestMapper vacationRequestMapper;
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";


    public VacationRequestService(VacationRequestRepository vacationRequestRepository, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO) {
        log.debug("Request to save Vacation request : {}", vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest = vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDto(vacationRequest);
    }

    @Transactional(readOnly = true)
    public VacationRequestDTO getById(Long id) {
        VacationRequestDTO result;
        log.debug("Request to get Employee by id : {}", id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if (vacationRequest.isPresent()) {
            result = vacationRequestMapper.toDto(vacationRequest.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<VacationRequestDTO> getAll() {
        log.debug("Request to get all products :");
        List<VacationRequest> vacationRequest = vacationRequestRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return Arrays.asList(mapper.map(vacationRequest, VacationRequestDTO[].class));
    }

    public void deleteById(Long id) {
        log.debug("Request to delete employee by id : {}", id);
        vacationRequestRepository.deleteById(id);
    }

    // Task 2 : Vacation Request //
    public VacationRequestDTO vacayRequest(VacayRequestDTO vacayRequestDTO) {
        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
        EmployeeDTO employeeDTO;

        log.debug("Request to get vacation : {}", vacayRequestDTO);

        //Get the days for the vacation
        long requestDays = DAYS.between(vacayRequestDTO.getStartDate(), vacayRequestDTO.getEndDate());
        int result = (int) requestDays - vacayRequestDTO.getHoliday() + 1;

        //Get employee by id
        Optional<Employee> employee = employeeRepository.findById(vacayRequestDTO.getEmployee().getId());

        //If the employee exists
        if (employee.isPresent()) {
            //Employee entity to dto
            employeeDTO = employeeMapper.toDto(employee.get());
            //Else trow exception
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        //Check if the employee's vacation days are enough
        if (employeeDTO.getVacationDays() >= result && result != 0) {

            //Set object Vacation Request
            vacationRequestDTO.setStatus(String.valueOf(VacationStatus.Pending));
            vacationRequestDTO.setEmployee(employeeDTO);
            vacationRequestDTO.setDays(result);
            vacationRequestDTO.setStartDate(vacayRequestDTO.getStartDate());
            vacationRequestDTO.setEndDate(vacayRequestDTO.getEndDate());
            save(vacationRequestDTO);
        } else {
            throw new RuntimeException("Incorrect days");
        }

        //Return the vacation request
        return vacationRequestDTO;
    }


    // Task 4 : Vacation  Requests by Company //
    public List<VacationRequest> companyVacationRequest(VacayCompanyDTO vacayCompanyDTO) {
        log.debug("Request to get vacation requests of a company: {}", vacayCompanyDTO);
        // Get vacation request based on startDate, endDate , status for a specific company
        return vacationRequestRepository.getVacationRequest(vacayCompanyDTO.getStartDate(),
                                                            vacayCompanyDTO.getEndDate(),
                                                            vacayCompanyDTO.getStatus(),
                                                            vacayCompanyDTO.getCompany());
    }


    // Task 5 : Accept or Reject Vacation Request //
    public VacationRequestDTO acceptedOrRejectedVacay2(AcceptOrRejectDTO acceptOrRejectDTO){
        EmployeeDTO employeeDTO;
        int resultDays;

        //Get vacation requests by id
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(acceptOrRejectDTO.getVacation().getId());

        //Entity to DTO
        VacationRequestDTO vacationRequestDTO = vacationRequestMapper.toDto(vacationRequest.get());

            //If a request exists
        if (acceptOrRejectDTO.getStatus().equals(vacationRequest.get().getStatus())) {

           //If the request is approved
            if(acceptOrRejectDTO.getStatus().equals("approved")) {

                //Update the vacation days of the employee and the VacationRequest object
                resultDays = vacationRequestDTO.getEmployee().getVacationDays() - vacationRequest.get().getDays();
                employeeDTO = vacationRequestDTO.getEmployee();
                employeeDTO.setVacationDays(resultDays);
                vacationRequestDTO.setStatus("approved");

                //Save
                save(vacationRequestDTO);
                employeeRepository.save(employeeMapper.toEntity(employeeDTO));
            }
               //If the request is rejected
            else if(acceptOrRejectDTO.getStatus().equals("rejected")){

                   //Update status
                   vacationRequestDTO.setStatus("rejected");
                   //Save
                   save(vacationRequestDTO);
            }
               //If the status is not correct, throw an exception
            else {
                   throw new RuntimeException("Incorrect status");
            }
        }
            //If the request does not exist
        else {
                throw new IllegalArgumentException("Something is wrong");
        }
        //Return the vacation request
        return vacationRequestDTO;
    }
}
