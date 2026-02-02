package search;

import builder.Anuncio;

public class FiltroTitulo implements FiltroAnuncio {
    private String termo;

    public FiltroTitulo(String termo) {
        this.termo = termo.toLowerCase();
    }

    @Override
    public boolean filtrar(Anuncio anuncio) {
        return anuncio.getTitulo().toLowerCase().contains(termo);
    }
}