package org.tokio.spring.servicesweb.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tokio.spring.servicesweb.dto.ProductDTO;
import org.tokio.spring.servicesweb.services.ProductRestTemplateService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRestTemplateServiceImpl implements ProductRestTemplateService {

    private final RestTemplate restTemplate;

    @Override
    public List<ProductDTO> getProducts() {
        String url = "http://localhost:8080/products";
        ProductDTO[] response = restTemplate.getForObject(url, ProductDTO[].class);
        if( response == null || response.length == 0 ) {
            return Collections.emptyList();
        }
        return Arrays.asList(response);
    }

    @Override
    public List<ProductDTO> getProductsAsResponseEntity() {
        String url = "http://localhost:8080/products";
        ResponseEntity<ProductDTO[]> responseEntity = restTemplate.getForEntity(url, ProductDTO[].class);
        log.info("Response Status code received: {}", responseEntity.getStatusCode() );

        if( responseEntity.getBody() == null || responseEntity.getBody().length == 0 ) {
            return Collections.emptyList();
        }
        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public ProductDTO postProductsAsResponseEntity(ProductDTO productDTO) {
        if(productDTO == null) {
            productDTO = new ProductDTO();
            productDTO.setName("Product add of rest template");
        }

        String url = "http://localhost:8080/products";
        ResponseEntity<ProductDTO> responseEntity = restTemplate.postForEntity(url,productDTO, ProductDTO.class);
        log.info("Response Status code received: {}", responseEntity.getStatusCode() );

        if( responseEntity.getBody() == null) {
            return null;
        }
        return responseEntity.getBody();
    }
}
