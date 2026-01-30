package integration;

import model.*;
import util.ConsoleLogger;

public class ImportadorCSVImovel extends ImportadorArquivo<Imovel> {

    @Override
    protected Imovel transformarLinhaEmObjeto(String linha) {
        try {
            String[] colunas = linha.split(";");
            String tipo = colunas[0].toUpperCase();
            String endereco = colunas[1];
            double area = Double.parseDouble(colunas[2]);
            int quartos = Integer.parseInt(colunas[3]);

            if (tipo.equals("CASA")) {
                boolean temQuintal = Boolean.parseBoolean(colunas[4]);
                return new Casa(endereco, area, quartos, temQuintal);
            } else if (tipo.equals("APARTAMENTO")) {
                int andar = Integer.parseInt(colunas[4]);
                return new Apartamento(endereco, area, quartos, andar);
            }
        } catch (Exception e) {
            ConsoleLogger.erro("[AVISO] Linha ignorada por erro de formato: " + linha);
        }
        return null;
    }
}
