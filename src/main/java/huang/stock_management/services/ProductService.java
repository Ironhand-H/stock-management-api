package huang.stock_management.services;

import huang.stock_management.dto.ProductDTO;
import huang.stock_management.model.Product;
import huang.stock_management.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(@RequestBody ProductDTO productDTO) {
        return productRepository.save(productDTO);
    }

    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody Product productDetails){

        return productRepository.findById(id)
                .map(productDTO -> {
                    productDTO.setName(productDetails.getName());
                    productDTO.setPrice(productDetails.getPrice());
                    productDTO.setQuantity(productDetails.getQuantity());
                    return ResponseEntity.ok(productRepository.save(productDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        if (!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
