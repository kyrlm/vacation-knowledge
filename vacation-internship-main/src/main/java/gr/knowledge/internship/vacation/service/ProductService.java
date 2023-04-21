package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        log.debug("Request to save Company : {}",productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id){
        ProductDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            result = productMapper.toDto(product.get());
        }else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        log.debug("Request to get all products : ");
        List<Product> product = productRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return Arrays.asList(mapper.map(product, ProductDTO[].class));
    }

    public void deleteById(Long id){
        log.debug("Request to delete employee by id : {}",id);
        productRepository.deleteById(id);
    }
}
