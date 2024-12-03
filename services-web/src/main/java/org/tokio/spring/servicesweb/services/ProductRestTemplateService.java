package org.tokio.spring.servicesweb.services;

import org.tokio.spring.servicesweb.dto.ProductDTO;

import java.util.List;

public interface ProductRestTemplateService {

    List<ProductDTO> getProducts();
    List<ProductDTO> getProductsAsResponseEntity();
    ProductDTO postProductsAsResponseEntity(ProductDTO productDTO);
}
