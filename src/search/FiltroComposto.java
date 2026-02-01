package search;

import builder.Anuncio;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe base abstrata para filtros compostos (Composite Pattern).
 * Permite agregar múltiplos filtros filhos e tratá-los uniformemente.
 */
public abstract class FiltroComposto implements FiltroAnuncio {
    protected List<FiltroAnuncio> filtros = new ArrayList<>();

    public FiltroComposto() {
    }

    public FiltroComposto(FiltroAnuncio... filtros) {
        this.filtros.addAll(Arrays.asList(filtros));
    }

    public void adicionarFiltro(FiltroAnuncio filtro) {
        this.filtros.add(filtro);
    }

    public void removerFiltro(FiltroAnuncio filtro) {
        this.filtros.remove(filtro);
    }

    public List<FiltroAnuncio> getFiltros() {
        return new ArrayList<>(filtros);
    }

    @Override
    public abstract boolean isSatisfeitoPor(Anuncio anuncio);
}
