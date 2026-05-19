package cl.duoc.cloudmarket.repository;

import cl.duoc.cloudmarket.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BoletaRepository extiende JpaRepository para obtener operaciones CRUD
 * sobre la entidad Boleta sin necesidad de escribir SQL manualmente.
 */
@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
}
