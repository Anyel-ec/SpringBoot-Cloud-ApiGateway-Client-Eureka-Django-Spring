package top.anyel.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.anyel.gateway.model.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

}