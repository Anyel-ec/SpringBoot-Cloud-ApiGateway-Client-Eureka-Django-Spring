package top.anyel.gateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.anyel.gateway.model.Users;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUsernameAndEstadoRegistroTrue(String username);

  Optional<Users> findByIdUserAndEstadoRegistroTrue(Long id);

  @Query("SELECT u FROM Users u WHERE u.estadoRegistro = true and u.username = :username")
  Users obtenerPorUsername(@Param("username") String username);
}