package cl.duoc.cloudmarket.exception;

/**
 * Excepcion que se lanza cuando no se encuentra un recurso en la base de datos.
 * Por ejemplo: buscar un producto con un id que no existe.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
