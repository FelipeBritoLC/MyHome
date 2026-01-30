package integration;

import model.Imovel;
import util.ConsoleLogger;

public class ImportadorCSVImovel extends ImportadorArquivo<Imovel> {

    @Override
    protected Imovel transformarLinhaEmObjeto(String linha) {
        try {
            String[] colunas = linha.split(";");
            String tipo = colunas[0];
            
            Imovel imovel = ImovelFactory.criar(tipo, colunas);
            
            if (imovel == null) {
                ConsoleLogger.erro("Tipo de imóvel desconhecido: " + tipo);
            }
            
            return imovel;
        } catch (Exception e) {
            ConsoleLogger.erro("Erro ao processar linha: " + linha + " -> " + e.getMessage());
            return null;
        }
    }
}
