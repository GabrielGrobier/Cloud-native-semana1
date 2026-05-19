package cl.duoc.cloudmarket.exception;

/**
 * Excepcion que se lanza cuando no hay suficiente stock disponible
 * para completar la compra de un producto.
 */
public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
