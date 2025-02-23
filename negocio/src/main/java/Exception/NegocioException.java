package Exception;

/**
 *
 * @author JR
 */
public class NegocioException extends Exception {
    
    public NegocioException(String mensaje) {
        super(mensaje);
    }

    public NegocioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
