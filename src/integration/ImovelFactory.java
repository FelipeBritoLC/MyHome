package integration;

import model.*;
import java.util.HashMap;
import java.util.Map;

public class ImovelFactory {
    private static final Map<String, CriadorImovel> REGISTO = new HashMap<>();

    static {
        REGISTO.put("CASA", col -> new Casa(col[1], Double.parseDouble(col[2]), Integer.parseInt(col[3]), Boolean.parseBoolean(col[4])));
        REGISTO.put("APARTAMENTO", col -> new Apartamento(col[1], Double.parseDouble(col[2]), Integer.parseInt(col[3]), Integer.parseInt(col[4])));
        REGISTO.put("TERRENO", col -> new Terreno(col[1], Double.parseDouble(col[2]), col[4]));
        REGISTO.put("COMERCIAL", col -> new ImovelComercial(col[1], Double.parseDouble(col[2]), Integer.parseInt(col[3]), Boolean.parseBoolean(col[4])));
    }

    public static Imovel criar(String tipo, String[] colunas) {
        CriadorImovel criador = REGISTO.get(tipo.toUpperCase());
        if (criador == null) return null;
        return criador.criar(colunas);
    }
}
