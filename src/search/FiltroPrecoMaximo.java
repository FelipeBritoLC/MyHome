package search;

import builder.Anuncio;

public class FiltroPrecoMaximo implements FiltroAnuncio {
    private double precoMax;

    public FiltroPrecoMaximo(double precoMax) {
        this.precoMax = precoMax;
    }

    @Override
    public boolean filtrar(Anuncio anuncio) {
        return anuncio.getPreco() <= precoMax;
    }
}