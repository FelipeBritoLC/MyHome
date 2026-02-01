package search;

import builder.Anuncio;

/**
 * Filtro composto que implementa a lógica OR (disjunção).
 * Retorna true se PELO MENOS UM dos filtros filhos for satisfeito.
 */
public class FiltroOr extends FiltroComposto {

    public FiltroOr() {
        super();
    }

    public FiltroOr(FiltroAnuncio... filtros) {
        super(filtros);
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        if (filtros.isEmpty()) {
            return true;
        }

        for (FiltroAnuncio filtro : filtros) {
            if (filtro.isSatisfeitoPor(anuncio)) {
                return true;
            }
        }
        return false;
    }
}
