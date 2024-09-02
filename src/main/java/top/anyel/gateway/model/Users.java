package top.anyel.gateway.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "unique_username", columnNames = { "username" }) })
public class Users implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idUser;

  @NotBlank
  @Size(min = 3, max = 50)
  private String username;

  @Email
  @NotNull
  private String email;

  @NotNull
  @NotBlank
  @Size(min = 6, max = 300)
  private String password;

  @NotBlank
  private String descripcion;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean estadoRegistro;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaCreacion;

  @Column(name = "fecha_actualizacion", nullable = false, updatable = true)
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaActualizacion;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
  private Set<Rol> roles = new HashSet<>();

}