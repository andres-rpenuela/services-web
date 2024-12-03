package org.tokio.spring.servicesweb.core.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.tokio.spring.servicesweb.core.converter.ResourceToResourceDTOConverter;
import org.tokio.spring.servicesweb.domain.Product;
import org.tokio.spring.servicesweb.dto.ProductDTO;

@Configuration
public class ProductDTOMapper {
    private final ModelMapper modelMapper;

    public ProductDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        init();
    }

    private void init() {
        modelMapper.typeMap(Product.class, ProductDTO.class)
                .addMappings(mapping -> mapping.using(new ResourceToResourceDTOConverter())
                                .map(Product::getResource,ProductDTO::setResourceDTO));
    }
}
