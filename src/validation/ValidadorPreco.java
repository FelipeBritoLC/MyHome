package validation;
import builder.Anuncio;
import config.ConfigManager;

public class ValidadorPreco extends ValidadorAnuncio {
    @Override
    public boolean validar(Anuncio anuncio) {

        double precoMinimo = Double.parseDouble(ConfigManager.getInstance().getConfig("preco.minimo"));

        if (anuncio.getPreco() <= precoMinimo) return false;
        return (proximo == null) || proximo.validar(anuncio);
    }
}