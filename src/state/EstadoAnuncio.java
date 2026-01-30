package state;

import builder.Anuncio;

public interface EstadoAnuncio {
    void publicar(Anuncio contexto);
    void cancelar(Anuncio contexto);
    String getNomeEstado();
}
