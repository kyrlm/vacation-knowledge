package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.enums.BonusRate;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
@Log4j2
public class BonusService {

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";
    private final BonusRepository bonusRepository;
    private final CompanyRepository companyRepository;
    private final BonusMapper bonusMapper;



    public BonusService(BonusRepository bonusRepository, CompanyRepository companyRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.companyRepository = companyRepository;
        this.bonusMapper = bonusMapper;
    }


    @Transactional
    public BonusDTO save(BonusDTO bonusDTO){
        log.debug("Request to save Bonus : {}",bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus = bonusRepository.save(bonus);
        return bonusMapper.toDto(bonus);
    }


    @Transactional(readOnly = true)
    public BonusDTO getById(Long id){
        BonusDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Bonus> bonus = bonusRepository.findById(id);
        if(bonus.isPresent()){
            result = bonusMapper.toDto(bonus.get());
        }else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<BonusDTO> getAll() {
        log.debug("Request to get all Bonuses : ");
        List<Bonus> bonus = bonusRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return Arrays.asList(mapper.map(bonus, BonusDTO[].class));
    }

    public void deleteById(Long id){
        log.debug("Request to delete Bonus by id : {}",id);
        bonusRepository.deleteById(id);
    }


    // Task 3 : Calculation of Bonus//
    public Double calculateBonus(Double salary, String season) {
        log.debug("Request to calculate Bonus by salary * season : {},{}",salary,season);

        //Get the name of the season
        BonusRate bonusRate= BonusRate.valueOf(season.toLowerCase());
        //Get the rate of the season
        Double rate=bonusRate.getRate();
        //Calculation
        return salary*rate;
    }


    // Task 7 : Create Bonuses for a Company //
    public List<Bonus> companyBonus(Long companyId, String season){
        log.debug("Request to calculate Bonus for every employee of a company : {},{}",companyId,season);

        List<Employee> employeeList;
        Bonus bonus = new Bonus();
        List<Bonus> bonusList = new ArrayList<>();

        //Get the employees of a company, based on the company id
        employeeList = companyRepository.getEmployeesOfACompany(companyId);

        Double amount;

        //For each employee
        for(Employee employee : employeeList) {
            //Calculate bonus and set bonus object
            Double employeeSalary = employee.getSalary();
            amount = calculateBonus(employeeSalary, season);
            bonus.setAmount(amount);
            bonus.setEmployee(employee);
            bonus.setEmployeeCompany(employee.getEmployeeCompany());
            bonusList.add(bonus);
        }
        //Save all
        return bonusRepository.saveAll(bonusList);


    }
}