package search;

import builder.Anuncio;

public class FiltroTituloContem implements FiltroAnuncio {
    private String termo;

    public FiltroTituloContem(String termo) {
        this.termo = termo;
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        return anuncio.getTitulo().toLowerCase().contains(termo.toLowerCase());
    }
}
