import builder.Anuncio;
import config.ConfigManager;
import integration.ImportadorCSVImovel;
import model.*;
import observerAndstrategy.*;
import util.ConsoleLogger;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleLogger.log("=== INICIANDO DEMONSTRAÇÃO MYHOME ===");
        ConfigManager config = ConfigManager.getInstance();

        ImportadorCSVImovel importador = new ImportadorCSVImovel();
        List<Imovel> baseImoveis = importador.importar("imoveis.csv");

        // Use dados fictícios para o corretor se não quiser receber a msg de "dono"
        Anunciante corretor = new Anunciante("Felipe Corretor", "corretor@imobiliaria.com", "00000", "CRECI-123");
        
        // O Comprador terá o SEU chat_id real vindo do config.properties
        Comprador cliente = new Comprador("Felipe Swiftie", "felipe@teste.com", "00000");

        Anuncio meuAnuncio = new Anuncio.Builder()
                .comAnunciante(corretor)
                .comTitulo("Cobertura Luxo")
                .comPreco(8000.0)
                .paraImovel(baseImoveis.get(0))
                .build();

        meuAnuncio.adicionarCanal(new NotificadorTelegram());
        
        // FASE DE INTERAÇÃO
        meuAnuncio.favoritar(cliente);
        
        // GATILHO DE NOTIFICAÇÃO
        ConsoleLogger.log("\nSimulando queda de preço...");
        meuAnuncio.setPreco(7500.0); 

        ConsoleLogger.log("\n=== FIM DA DEMONSTRAÇÃO ===");
    }
}