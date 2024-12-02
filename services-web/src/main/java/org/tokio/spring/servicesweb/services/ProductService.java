package org.tokio.spring.servicesweb.services;

import org.springframework.web.multipart.MultipartFile;
import org.tokio.spring.servicesweb.core.exception.ProductNotFoundException;
import org.tokio.spring.servicesweb.dto.ProductDTO;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Optional<ProductDTO> findById(Long id) throws IllegalArgumentException;
    Set<ProductDTO> getProducts();
    Set<ProductDTO> getProductsByCategory(String category);

    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO addProduct(ProductDTO productDTO, MultipartFile file,String description) throws IOException;

    ProductDTO updateProduct(long id, ProductDTO productDTO) throws ProductNotFoundException;
}
