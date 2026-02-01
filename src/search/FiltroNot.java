package search;

import builder.Anuncio;

/**
 * Filtro decorador que inverte o resultado de outro filtro (negação lógica).
 * Implementa o operador NOT do padrão Composite.
 */
public class FiltroNot implements FiltroAnuncio {
    private final FiltroAnuncio filtro;

    public FiltroNot(FiltroAnuncio filtro) {
        this.filtro = filtro;
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        return !filtro.isSatisfeitoPor(anuncio);
    }

    public FiltroAnuncio getFiltro() {
        return filtro;
    }
}
