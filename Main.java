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

public class Main {
    public static void main(String[] args) {
        ConsoleLogger.log("=== INICIANDO DEMONSTRAÇÃO MYHOME ===");

        // 1. Configurações Globais (Singleton - RF07)
        ConfigManager config = ConfigManager.getInstance();
        
        // 2. Carga de Dados (Template Method + Factory + E1)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 1: Importação de Imóveis (CSV) ---");
        ImportadorCSVImovel importador = new ImportadorCSVImovel();
        List<Imovel> baseImoveis = importador.importar("imoveis.csv");

        // 3. Criação de Perfis de Usuários (Domínio)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 2: Identificação de Usuários ---");
        Anunciante anunciante = new Anunciante("Felipe Corretor", "felipe@teste.com", "8399999999", "CRECI-123");
        Comprador comprador = new Comprador("Carlos Cliente", "carlos@gmail.com", "8388888888");
        ConsoleLogger.log("Usuários criados: " + anunciante.getNome() + " e " + comprador.getNome());

        // 4. Criação do Anúncio (Builder + Prototype - RF01/RF02)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 3: Elaboração do Anúncio ---");
        Imovel imovelSelecionado = baseImoveis.get(0).clone(); // Usando protótipo
        Anuncio meuAnuncio = new Anuncio.Builder()
                .comAnunciante(anunciante)
                .comTitulo("Cobertura Vista Mar")
                .comDescricao("Luxuoso apartamento mobiliado.")
                .comPreco(5000.0)
                .paraImovel(imovelSelecionado)
                .build();
        ConsoleLogger.log("Anúncio em rascunho: " + meuAnuncio);

        // 5. Histórico de Edição (Memento - RF08)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 4: Teste de Histórico (Undo) ---");
        HistoricoAnuncio historico = new HistoricoAnuncio();
        historico.guardar(meuAnuncio.criarSnapshot());

        ConsoleLogger.log("Fazendo alteração acidental...");
        meuAnuncio = new Anuncio.Builder()
                .comAnunciante(anunciante)
                .comTitulo("ERRO NO TITULO")
                .comPreco(0.50)
                .paraImovel(imovelSelecionado)
                .build();
        
        ConsoleLogger.log("Estado com erro: " + meuAnuncio);
        meuAnuncio.restaurar(historico.desfazer());
        ConsoleLogger.log("Após desfazer: " + meuAnuncio);

        // 6. Configuração de Notificações (Observer + Strategy - RF05)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 5: Configuração de Canais ---");
        meuAnuncio.adicionarCanal(new NotificadorEmail());
        meuAnuncio.adicionarCanal(new NotificadorWhatsApp());

        // 7. Moderação e Ciclo de Vida (Chain + State - RF03/RF04)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 6: Moderação e Publicação ---");
        ValidadorAnuncio corrente = new ValidadorPreco();
        corrente.setProximo(new ValidadorTermosProibidos());

        if (corrente.validar(meuAnuncio)) {
            meuAnuncio.solicitarPublicacao(); // Rascunho -> Moderando
            meuAnuncio.solicitarPublicacao(); // Moderando -> Ativo
        }

        // 8. Busca Avançada pelo Comprador (Specification - RF06)
        ConsoleLogger.log("");
        ConsoleLogger.log("--- FASE 7: Experiência do Comprador ---");
        List<Anuncio> catalogo = new ArrayList<>();
        catalogo.add(meuAnuncio);

        FiltroAnd busca = new FiltroAnd();
        busca.adicionarFiltro(new FiltroTituloContem("Cobertura"));
        busca.adicionarFiltro(new FiltroPrecoMaximo(10000.0));

        ConsoleLogger.log("Comprador " + comprador.getNome() + " pesquisando...");
        for (Anuncio a : catalogo) {
            if (busca.isSatisfeitoPor(a)) {
                ConsoleLogger.log("Resultado encontrado: " + a.getTitulo() + " - " + a.getStatus());
            }
        }

        ConsoleLogger.log("");
        ConsoleLogger.log("=== FIM DA DEMONSTRAÇÃO MYHOME ===");
    }
}