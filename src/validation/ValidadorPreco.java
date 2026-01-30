package validation;

import builder.Anuncio;
import config.ConfigManager;
import util.ConsoleLogger;

public class ValidadorPreco extends ValidadorAnuncio {
    @Override
    public boolean validar(Anuncio anuncio) {
        double precoMinimo = Double.parseDouble(ConfigManager.getInstance().getConfig("preco.minimo"));
        
        if (anuncio.getPreco() < precoMinimo) {
            ConsoleLogger.erro("ERRO VALIDAÇÃO: Preço R$ " + anuncio.getPreco() + " abaixo do permitido.");
            return false;
        }
        return verificarProximo(anuncio);
    }
}
