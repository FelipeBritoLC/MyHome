package validation;

import builder.Anuncio;
import config.ConfigManager;
import util.ConsoleLogger;

public class ValidadorTermosProibidos extends ValidadorAnuncio {
    @Override
    public boolean validar(Anuncio anuncio) {
        String termos = ConfigManager.getInstance().getConfig("termos.proibidos");
        String[] listaTermos = termos.split(",");

        for (String termo : listaTermos) {
            if (anuncio.getDescricao().toLowerCase().contains(termo.toLowerCase())) {
                ConsoleLogger.erro("ERRO VALIDAÇÃO: Descrição contém termo proibido: " + termo);
                return false;
            }
        }
        return verificarProximo(anuncio);
    }
}
