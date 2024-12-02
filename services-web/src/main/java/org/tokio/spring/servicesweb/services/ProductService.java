package org.tokio.spring.servicesweb.services;

import org.tokio.spring.servicesweb.dto.ProductDTO;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Optional<ProductDTO> findById(Long id) throws IllegalArgumentException;
    Set<ProductDTO> getProducts();
    Set<ProductDTO> getProductsByCategory(String category);

    ProductDTO addProduct(ProductDTO productDTO);
}
