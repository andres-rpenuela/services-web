package org.tokio.spring.servicesweb.core.converter;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.tokio.spring.servicesweb.domain.Resource;
import org.tokio.spring.servicesweb.dto.ResourceDTO;

import java.util.Optional;

public class ResourceToResourceDTOConverter implements Converter<Resource, ResourceDTO> {


    @Override
    public ResourceDTO convert(MappingContext<Resource, ResourceDTO> mappingContext) {

        final ModelMapper modelMapper = new ModelMapper();
        return Optional.ofNullable(mappingContext.getSource())
                .map(resource -> modelMapper.map(mappingContext.getSource(), ResourceDTO.class) )
                .orElseGet(()-> null);
    }
}
