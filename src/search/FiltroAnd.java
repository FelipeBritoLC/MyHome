package search;

import builder.Anuncio;

/**
 * Filtro composto que implementa a lógica AND (conjunção).
 * Retorna true somente se TODOS os filtros filhos forem satisfeitos.
 */
public class FiltroAnd extends FiltroComposto {

    public FiltroAnd() {
        super();
    }

    public FiltroAnd(FiltroAnuncio... filtros) {
        super(filtros);
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        for (FiltroAnuncio filtro : filtros) {
            if (!filtro.isSatisfeitoPor(anuncio)) {
                return false;
            }
        }
        return true;
    }
}
