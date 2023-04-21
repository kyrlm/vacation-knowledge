package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO){
        log.debug("Request to save Company : {}",employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getById(Long id){
        EmployeeDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            result = employeeMapper.toDto(employee.get());
        }else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        log.debug("Request to get all employees : ");
        List<Employee> employee = employeeRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return Arrays.asList(mapper.map(employee, EmployeeDTO[].class));
    }

    public void deleteById(Long id){
        log.debug("Request to delete employee by id : {}",id);
        employeeRepository.deleteById(id);
    }
}
