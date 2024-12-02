package org.tokio.spring.servicesweb.services;

import org.tokio.spring.servicesweb.dto.ProductDTO;

import java.util.Set;

public interface ProductService {
    Set<ProductDTO> getProducts();
    Set<ProductDTO> getProductsByCategory(String category);
}
