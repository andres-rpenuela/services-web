package org.tokio.spring.servicesweb.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "RESOURCES")
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String type;
    @Lob //I ndica que el campo datos debe ser tratado como un tipo de datos grande (BLOB) en la base de datos.
    private byte[] content;
}
