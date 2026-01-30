package validation;

import builder.Anuncio;
import config.ConfigManager;

public class ValidadorTermosProibidos extends ValidadorAnuncio {
    @Override
    public boolean validar(Anuncio anuncio) {
        String termos = ConfigManager.getInstance().getConfig("termos.proibidos");
        String[] listaTermos = termos.split(",");

        for (String termo : listaTermos) {
            if (anuncio.getDescricao().toLowerCase().contains(termo.toLowerCase())) {
                System.out.println("ERRO VALIDAÇÃO: Descrição contém termo proibido: " + termo);
                return false;
            }
        }
        return verificarProximo(anuncio);
    }
}
