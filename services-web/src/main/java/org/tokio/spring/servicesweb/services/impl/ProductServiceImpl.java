package org.tokio.spring.servicesweb.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tokio.spring.servicesweb.domain.Product;
import org.tokio.spring.servicesweb.dto.ProductDTO;
import org.tokio.spring.servicesweb.report.ProductDao;
import org.tokio.spring.servicesweb.services.ProductService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public Set<ProductDTO> getProducts() {
        return findAllProducts().stream()
                .map(ProductServiceImpl::mapperProductToProductDTO)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<ProductDTO> getProductsByCategory(String category) {
        Set<Product> products = Optional.ofNullable(category)
                .map(StringUtils::stripToNull)
                .map(this::findAllProductsByCategory)
                .orElseGet(this::findAllProducts);

        return products.stream()
                .map(ProductServiceImpl::mapperProductToProductDTO)
                .collect(Collectors.toSet());
    }

    private Set<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    private Set<Product> findAllProductsByCategory(@NonNull String category) {
        return productDao.findByCategoryOrderById(category);
    }

    private static ProductDTO mapperProductToProductDTO(@NonNull Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }
}
