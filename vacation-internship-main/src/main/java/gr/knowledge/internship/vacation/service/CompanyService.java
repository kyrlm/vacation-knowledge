package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.*;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final EmployeeProductRepository employeeProductRepository;
    private final ProductRepository productRepository;
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";


    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, EmployeeProductRepository employeeProductRepository, ProductRepository productRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.employeeProductRepository = employeeProductRepository;
        this.productRepository = productRepository;
    }


    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO){
        log.debug("Request to save Company : {}",companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id){
        CompanyDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            result = companyMapper.toDto(company.get());
        }else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<CompanyDTO> getAll() {
        log.debug("Request to get all companies : ");
        List<Company> company = companyRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return Arrays.asList(mapper.map(company, CompanyDTO[].class));
    }

    public void deleteById(Long id){
        log.debug("Request to delete Company by id : {}",id);
        companyRepository.deleteById(id);
    }


    // Task 6 :Calculate monthly expenses for Company //
    public Double companyMonthlyExpences(Long companyId){
        log.debug("Request to get the monthly expenses of a company : {}",companyId);
        Double expenses;

        //Get company by id
        Optional<Company> company = companyRepository.findById(companyId);

        //If the company with the specific id exists
        if(company.isPresent()){
            //Calculate the expenses
            expenses = companyRepository.getCompanyMonthlyExpenses(companyId);

        //If the specific company id does not exist, throw an exception
        }else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        //Return the expenses of the company
        return expenses ;
    }

    // Task 8 : Get All products for a Company //
    public Map<String, List<Product>> getCompanyProducts(Long companyId){
        log.debug("Request to get the products that employees have, in a company : {}",companyId);
        Map<String, List<Product>> productMap = new HashMap<>();

        //Get the employees that work in the company with a specific id
       List<Employee> employeeList = companyRepository.getEmployeesOfACompany(companyId);

        //For each employee I get the id
       for( Employee employee : employeeList) {
           Long employeeId = employee.getId();

           //Get the employees that have a product
           Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(employeeId);
           if (employeeProduct.isPresent()) {
               //Get the product id's
               Long productId = employeeProduct.get().getProduct().getId();

               //Get the products
               Optional<Product> product = productRepository.findById(productId);

               //If the product exists
               if(product.isPresent()){
                   //concat name and surname
                   String key = employee.getName() + " " + employee.getSurName();
                   List<Product> productList;

                   //If the map contains a key
                   if(productMap.containsKey(key)){
                       //Get the key
                       productList = productMap.get(key);
                   } else {
                       //create a new list
                       productList = new ArrayList<>();
                   }

                   //Add the product to the list
                   productList.add(product.get());

                   //Put key and value to the map
                   productMap.put(key, productList);
               }
           }
       }
        //Return employee's name-surname and the products he has
        return productMap;
    }
}
