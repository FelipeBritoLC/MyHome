package state;
import builder.Anuncio;

public interface EstadoAnuncio {
    String publicar(Anuncio anuncio);
    String cancelar(Anuncio anuncio);
    String getNomeEstado();
}