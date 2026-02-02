package integration;

import model.Imovel;


public class ImportadorCSVImovel extends ImportadorArquivo<Imovel> {

    @Override
    protected Imovel transformarLinhaEmObjeto(String linha) {

        try {
            if (linha == null || linha.trim().isEmpty()) {
                return null;
            }

            String[] colunas = linha.split(";");
            String tipo = colunas[0];
            
            return ImovelFactory.criar(tipo, colunas);

        } catch (Exception e) {

            return null;
        }
    }
}