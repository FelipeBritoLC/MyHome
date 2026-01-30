import builder.Anuncio;
import config.ConfigManager;
import integration.ImportadorCSVImovel;
import memento.HistoricoAnuncio;
import model.*;
import observerAndstrategy.*;
import search.*;
import state.*;
import util.ConsoleLogger;
import validation.*;

import java.util.ArrayList;
import java.util.List;

// importar -> clonar -> criar -> errar -> desfazer -> validar -> publicar -> buscar (ordem da apresentação)
public class Main {

    public static void main(String[] args) {
        ConsoleLogger.log("=== Iniciando demonstração do MyHome ===");

        // 1. Singleton (RF07)
        ConfigManager config = ConfigManager.getInstance();
        ConsoleLogger.log("Configurações carregadas. Preço mínimo: " + config.getConfig("preco.minimo"));

        // 2. Template Method + Factory (E1)
        ConsoleLogger.log("\n--- FASE 1: Importação de Dados (CSV) ---");
        ImportadorCSVImovel importador = new ImportadorCSVImovel();
        List<Imovel> imoveisCarregados = importador.importar("imoveis.csv");

        // 3. Prototype (RF02)
        ConsoleLogger.log("\n--- FASE 2: Uso de Protótipos (Clonagem) ---");
        Imovel modelo = imoveisCarregados.get(0); // Pega a primeira Casa
        Imovel copiaParaNovoAnuncio = modelo.clone();
        copiaParaNovoAnuncio.setEndereco("Rua das Flores, 124 (Vizinho)");
        ConsoleLogger.log("Protótipo clonado e ajustado: " + copiaParaNovoAnuncio.getEndereco());

        // 4. Builder (RF01)
        ConsoleLogger.log("\n--- FASE 3: Criação de Anúncio com Builder ---");
        Anuncio meuAnuncio = new Anuncio.Builder()
                .comTitulo("Casa de Veraneio")
                .comDescricao("Ótima oportunidade!")
                .comPreco(2500.0)
                .paraImovel(copiaParaNovoAnuncio)
                .build();
        ConsoleLogger.log("Anúncio criado: " + meuAnuncio);

        // 5. Memento (RF08)
        ConsoleLogger.log("\n--- FASE 4: Histórico e Undo/Redo (Memento) ---");
        HistoricoAnuncio historico = new HistoricoAnuncio();
        historico.guardar(meuAnuncio.criarSnapshot()); // salva estado original

        ConsoleLogger.log("Editando anúncio com erro...");
        meuAnuncio = new Anuncio.Builder()
                .comTitulo("TITULO ERRADO")
                .comPreco(0.1) // preco invalido
                .paraImovel(copiaParaNovoAnuncio)
                .build();
        ConsoleLogger.log("Estado com erro: " + meuAnuncio);

        ConsoleLogger.log("Acionando DESFAZER...");
        meuAnuncio.restaurar(historico.desfazer());
        ConsoleLogger.log("Estado recuperado: " + meuAnuncio);

        // 6. Observer + Strategy (RF05)
        ConsoleLogger.log("\n--- FASE 5: Notificações Dinâmicas ---");
        meuAnuncio.adicionarCanal(new NotificadorEmail());
        meuAnuncio.adicionarCanal(new NotificadorWhatsApp());

        // 7. Chain of Responsibility (RF03) + State (RF04)
        ConsoleLogger.log("\n--- FASE 6: Validação e Ciclo de Vida ---");
        ValidadorAnuncio corrente = new ValidadorPreco();
        corrente.setProximo(new ValidadorTermosProibidos());

        if (corrente.validar(meuAnuncio)) {
            ConsoleLogger.log("Validação aprovada! Mudando estados...");
            meuAnuncio.solicitarPublicacao(); // rascunho -> moderando
            meuAnuncio.solicitarPublicacao(); // moderando -> ativo
            
            // Simulação de finalização
            meuAnuncio.setEstado(new EstadoVendido());
        }

        // 8. Specification (RF06)
        ConsoleLogger.log("\n--- FASE 7: Busca Avançada (Filtros) ---");
        List<Anuncio> catalogo = new ArrayList<>();
        catalogo.add(meuAnuncio); 
        
        // adiciona outro para teste
        catalogo.add(new Anuncio.Builder()
                .comTitulo("Apartamento Luxo")
                .comPreco(8000.0)
                .paraImovel(imoveisCarregados.get(1))
                .build());

        FiltroAnd filtroBusca = new FiltroAnd();
        filtroBusca.adicionarFiltro(new FiltroTituloContem("Luxo"));
        filtroBusca.adicionarFiltro(new FiltroPrecoMaximo(9000.0));

        ConsoleLogger.log("Pesquisando por 'Luxo' até R$ 9000...");
        for (Anuncio a : catalogo) {
            if (filtroBusca.isSatisfeitoPor(a)) {
                ConsoleLogger.log("Resultado encontrado: " + a.getTitulo());
            }
        }

        ConsoleLogger.log("\n=== Fim da demonstração do MyHome ===");
    }
}