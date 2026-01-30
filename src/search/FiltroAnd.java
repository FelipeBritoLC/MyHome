package search;

import builder.Anuncio;
import java.util.List;
import java.util.ArrayList;

public class FiltroAnd implements FiltroAnuncio {
    private List<FiltroAnuncio> filtros = new ArrayList<>();

    public void adicionarFiltro(FiltroAnuncio filtro) {
        this.filtros.add(filtro);
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        for (FiltroAnuncio filtro : filtros) {
            if (!filtro.isSatisfeitoPor(anuncio)) {
                return false; // Se um falhar, o AND falha
            }
        }
        return true;
    }
}
