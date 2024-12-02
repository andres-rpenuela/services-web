package org.tokio.spring.servicesweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ResourceDTO {
    private Long id;
    private String name;
    private String description;
    private String type;
    private byte[] content;
}
