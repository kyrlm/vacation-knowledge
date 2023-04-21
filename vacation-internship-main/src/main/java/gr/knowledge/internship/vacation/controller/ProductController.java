package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.ProductService;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        log.debug("Rest request to save Product : {}",productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        log.debug("Rest request to get Product by id : {}",id);
        ProductDTO result = productService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        log.debug("Rest request to get all products: ");
        List<ProductDTO> result = productService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProductById/{id}")
    public void deleteBonus(@PathVariable Long id){
        log.debug("Rest request to delete product by id : {}",id);
        productService.deleteById(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDTO> updateProduct (@RequestBody ProductDTO productDTO){
        log.debug("Rest request to update employee : {}",productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
