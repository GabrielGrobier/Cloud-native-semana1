package cl.duoc.cloudmarket.repository;

import cl.duoc.cloudmarket.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductoRepository extiende JpaRepository para obtener operaciones CRUD
 * sobre la entidad Producto sin necesidad de escribir SQL manualmente.
 *
 * Metodos disponibles de forma automatica:
 * - findAll()         -> lista todos los productos
 * - findById(id)      -> busca un producto por su id
 * - save(producto)    -> guarda o actualiza un producto
 * - delete(producto)  -> elimina un producto
 * - count()           -> cuenta cuantos productos hay
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
