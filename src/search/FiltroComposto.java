package search;

import builder.Anuncio;
import java.util.ArrayList;
import java.util.List;

public class FiltroComposto implements FiltroAnuncio {
    private List<FiltroAnuncio> filtros = new ArrayList<>();

    public void adicionar(FiltroAnuncio filtro) {
        this.filtros.add(filtro);
    }

    @Override
    public boolean filtrar(Anuncio anuncio) {

        for (FiltroAnuncio f : filtros) {
            if (!f.filtrar(anuncio)) return false;
        }
        return true;
    }
}