package top.anyel.gateway.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

import lombok.Data;

@Data
@Entity
@Table(name = "rol", uniqueConstraints = { @UniqueConstraint(name = "unique_name", columnNames = { "name" }) })
public class Rol implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 50)
  private String name;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean estadoRegistro;

  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;

}