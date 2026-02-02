package validation;

import builder.Anuncio;
import config.ConfigManager;

public class ValidadorTermosProibidos extends ValidadorAnuncio {
    
    @Override
    public boolean validar(Anuncio anuncio) {
        String termos = ConfigManager.getInstance().getConfig("termos.proibidos");
        
        if (termos == null) {
            return (proximo == null) || proximo.validar(anuncio);
        }

        String[] listaTermos = termos.split(",");
        String descricao = anuncio.getTitulo().toLowerCase();

        for (String termo : listaTermos) {
            if (descricao.contains(termo.trim().toLowerCase())) {
                return false;
            }
        }


        return (proximo == null) || proximo.validar(anuncio);
    }
}