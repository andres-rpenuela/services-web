package org.tokio.spring.servicesweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tokio.spring.servicesweb.dto.ProductDTO;
import org.tokio.spring.servicesweb.services.ProductRestTemplateService;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ProductExternalController {

    private final ProductRestTemplateService productRestTemplateService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
            return ResponseEntity.ok( productRestTemplateService.getProducts() );
    }

    @GetMapping("/products/v2")
    public ResponseEntity<List<ProductDTO>> getAllProductsAsResponseEntity(){
        return ResponseEntity.ok( productRestTemplateService.getProductsAsResponseEntity() );
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody(required = false) ProductDTO productDTO){
        return ResponseEntity.ok( productRestTemplateService.postProductsAsResponseEntity(productDTO) );
    }
}
