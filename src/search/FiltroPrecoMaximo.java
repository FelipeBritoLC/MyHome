package search;

import builder.Anuncio;

public class FiltroPrecoMaximo implements FiltroAnuncio {
    private double precoLimite;

    public FiltroPrecoMaximo(double precoLimite) {
        this.precoLimite = precoLimite;
    }

    @Override
    public boolean isSatisfeitoPor(Anuncio anuncio) {
        return anuncio.getPreco() <= precoLimite;
    }
}