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

// importar -> usuarios -> clonar -> criar -> errar -> desfazer -> validar -> publicar -> buscar -> favoritar
public class Main {

    public static void main(String[] args) {
        ConsoleLogger.log("=== Iniciando demonstracao do MyHome ===");

        // 1. Singleton (RF07)
        ConfigManager config = ConfigManager.getInstance();
        ConsoleLogger.log("Configuracoes carregadas. Preco minimo: " + config.getConfig("preco.minimo"));

        // 2. Template Method + Factory (E1)
        ConsoleLogger.log("\n--- FASE 1: Importacao de Dados (CSV) ---");
        ImportadorCSVImovel importador = new ImportadorCSVImovel();
        List<Imovel> imoveisCarregados = importador.importar("imoveis.csv");

        // NOVO: Criação de Usuários
        ConsoleLogger.log("\n--- FASE 2: Cadastro de Usuarios ---");
        Anunciante joao = new Anunciante("Joao Silva", "joao@email.com", "(83) 99999-1111");
        Anunciante maria = new Anunciante("Maria Souza", "maria@email.com", "(83) 99999-2222");
        Comprador pedro = new Comprador("Pedro Santos", "pedro@email.com", "(83) 99999-3333");
        Comprador ana = new Comprador("Ana Lima", "ana@email.com", "(83) 99999-4444");

        ConsoleLogger.log("Usuarios cadastrados:");
        ConsoleLogger.log("  " + joao);
        ConsoleLogger.log("  " + maria);
        ConsoleLogger.log("  " + pedro);
        ConsoleLogger.log("  " + ana);

        // 3. Prototype (RF02)
        ConsoleLogger.log("\n--- FASE 3: Uso de Prototipos (Clonagem) ---");
        Imovel modelo = imoveisCarregados.get(0);
        Imovel copiaParaNovoAnuncio = modelo.clone();
        copiaParaNovoAnuncio.setEndereco("Rua das Flores, 124 (Vizinho)");
        ConsoleLogger.log("Prototipo clonado e ajustado: " + copiaParaNovoAnuncio.getEndereco());

        // 4. Builder (RF01) - Agora com Anunciante
        ConsoleLogger.log("\n--- FASE 4: Criacao de Anuncio com Builder ---");
        Anuncio meuAnuncio = joao.criarAnuncio()
                .comTitulo("Casa de Veraneio")
                .comDescricao("Otima oportunidade!")
                .comPreco(2500.0)
                .paraImovel(copiaParaNovoAnuncio)
                .build();
        ConsoleLogger.log("Anuncio criado: " + meuAnuncio);

        // 5. Memento (RF08)
        ConsoleLogger.log("\n--- FASE 5: Historico e Undo/Redo (Memento) ---");
        HistoricoAnuncio historico = new HistoricoAnuncio();
        historico.guardar(meuAnuncio.criarSnapshot());

        ConsoleLogger.log("Editando anuncio com erro...");
        Anuncio anuncioComErro = joao.criarAnuncio()
                .comTitulo("TITULO ERRADO")
                .comPreco(0.1)
                .paraImovel(copiaParaNovoAnuncio)
                .build();
        ConsoleLogger.log("Estado com erro: " + anuncioComErro);

        ConsoleLogger.log("Acionando DESFAZER...");
        meuAnuncio.restaurar(historico.desfazer());
        ConsoleLogger.log("Estado recuperado: " + meuAnuncio);

        // 6. Observer + Strategy (RF05) - Comprador se inscreve para notificacoes
        ConsoleLogger.log("\n--- FASE 6: Notificacoes Dinamicas ---");
        meuAnuncio.adicionarCanal(new NotificadorWhatsApp());
        pedro.inscreverPara(meuAnuncio); // Comprador como Observer
        ana.inscreverPara(meuAnuncio);   // Outro comprador interessado

        // 7. Chain of Responsibility (RF03) + State (RF04)
        ConsoleLogger.log("\n--- FASE 7: Validacao e Ciclo de Vida ---");
        ValidadorAnuncio corrente = new ValidadorPreco();
        corrente.setProximo(new ValidadorTermosProibidos());

        if (corrente.validar(meuAnuncio)) {
            ConsoleLogger.log("Validacao aprovada! Mudando estados...");
            meuAnuncio.solicitarPublicacao(); // rascunho -> moderando
            meuAnuncio.solicitarPublicacao(); // moderando -> ativo
        }

        // 8. Composite Pattern (RF06) - Catalogo com anuncios de diferentes anunciantes
        ConsoleLogger.log("\n--- FASE 8: Busca Avancada (Composite Pattern) ---");
        List<Anuncio> catalogo = new ArrayList<>();
        catalogo.add(meuAnuncio);

        // Anuncios do Joao
        catalogo.add(joao.criarAnuncio()
                .comTitulo("Apartamento Luxo")
                .comPreco(8000.0)
                .paraImovel(imoveisCarregados.get(1))
                .build());

        // Anuncios da Maria
        catalogo.add(maria.criarAnuncio()
                .comTitulo("Casa Popular")
                .comPreco(1500.0)
                .paraImovel(imoveisCarregados.get(0).clone())
                .build());

        catalogo.add(maria.criarAnuncio()
                .comTitulo("Terreno Comercial")
                .comPreco(12000.0)
                .paraImovel(imoveisCarregados.size() > 2 ? imoveisCarregados.get(2) : imoveisCarregados.get(0).clone())
                .build());

        ConsoleLogger.log("\nAnuncios do " + joao.getNome() + ": " + joao.getMeusAnuncios().size());
        ConsoleLogger.log("Anuncios da " + maria.getNome() + ": " + maria.getMeusAnuncios().size());

        // Demonstracao 1: Filtro AND (conjuncao)
        ConsoleLogger.log("\n[BUSCA 1] Filtro AND: 'Luxo' E preco ate R$ 9000");
        FiltroAnuncio filtroAnd = new FiltroAnd(
            new FiltroTituloContem("Luxo"),
            new FiltroPrecoMaximo(9000.0)
        );
        buscarEExibir(catalogo, filtroAnd);

        // Demonstracao 2: Filtro OR (disjuncao)
        ConsoleLogger.log("\n[BUSCA 2] Filtro OR: 'Casa' OU 'Apartamento'");
        FiltroAnuncio filtroOr = new FiltroOr(
            new FiltroTituloContem("Casa"),
            new FiltroTituloContem("Apartamento")
        );
        buscarEExibir(catalogo, filtroOr);

        // Demonstracao 3: Filtro NOT (negacao)
        ConsoleLogger.log("\n[BUSCA 3] Filtro NOT: NAO contem 'Comercial'");
        FiltroAnuncio filtroNot = new FiltroNot(new FiltroTituloContem("Comercial"));
        buscarEExibir(catalogo, filtroNot);

        // Demonstracao 4: Composicao complexa - (Casa OU Apartamento) E preco <= 5000 E NAO Luxo
        ConsoleLogger.log("\n[BUSCA 4] Filtro COMPLEXO: (Casa OU Apartamento) E preco <= R$ 5000 E NAO 'Luxo'");
        FiltroAnuncio filtroComplexo = new FiltroAnd(
            new FiltroOr(
                new FiltroTituloContem("Casa"),
                new FiltroTituloContem("Apartamento")
            ),
            new FiltroPrecoMaximo(5000.0),
            new FiltroNot(new FiltroTituloContem("Luxo"))
        );
        buscarEExibir(catalogo, filtroComplexo);

        // 9. NOVO: Comprador usando o sistema de busca e favoritos
        ConsoleLogger.log("\n--- FASE 9: Interacao do Comprador ---");

        // Pedro busca casas ate R$ 3000
        ConsoleLogger.log("\n" + pedro.getNome() + " busca: 'Casa' ate R$ 3000");
        FiltroAnuncio filtroPedro = new FiltroAnd(
            new FiltroTituloContem("Casa"),
            new FiltroPrecoMaximo(3000.0)
        );
        List<Anuncio> resultadosPedro = pedro.buscar(catalogo, filtroPedro);
        for (Anuncio a : resultadosPedro) {
            ConsoleLogger.log("  -> " + a);
            pedro.favoritar(a); // Favorita os resultados
        }

        // Ana busca apartamentos ou terrenos
        ConsoleLogger.log("\n" + ana.getNome() + " busca: 'Apartamento' OU 'Terreno'");
        FiltroAnuncio filtroAna = new FiltroOr(
            new FiltroTituloContem("Apartamento"),
            new FiltroTituloContem("Terreno")
        );
        List<Anuncio> resultadosAna = ana.buscar(catalogo, filtroAna);
        for (Anuncio a : resultadosAna) {
            ConsoleLogger.log("  -> " + a);
        }
        // Ana favorita apenas o primeiro
        if (!resultadosAna.isEmpty()) {
            ana.favoritar(resultadosAna.get(0));
        }

        // Resumo dos favoritos
        ConsoleLogger.log("\n--- Resumo de Favoritos ---");
        ConsoleLogger.log("Favoritos de " + pedro.getNome() + ": " + pedro.getFavoritos().size());
        for (Anuncio a : pedro.getFavoritos()) {
            ConsoleLogger.log("  * " + a.getTitulo());
        }
        ConsoleLogger.log("Favoritos de " + ana.getNome() + ": " + ana.getFavoritos().size());
        for (Anuncio a : ana.getFavoritos()) {
            ConsoleLogger.log("  * " + a.getTitulo());
        }

        // Demonstra notificacao quando anuncio muda de estado
        ConsoleLogger.log("\n--- Demonstracao: Notificacao ao Vender ---");
        ConsoleLogger.log("Joao marca 'Casa de Veraneio' como vendida...");
        meuAnuncio.setEstado(new EstadoVendido());

        ConsoleLogger.log("\n=== Fim da demonstracao do MyHome ===");
    }

    /**
     * Metodo auxiliar para buscar anuncios usando um filtro e exibir resultados.
     * Demonstra o uso polimorfico do padrao Composite.
     */
    private static void buscarEExibir(List<Anuncio> catalogo, FiltroAnuncio filtro) {
        boolean encontrou = false;
        for (Anuncio anuncio : catalogo) {
            if (filtro.isSatisfeitoPor(anuncio)) {
                ConsoleLogger.log("  -> Encontrado: " + anuncio.getTitulo() + " (R$ " + anuncio.getPreco() + ")");
                encontrou = true;
            }
        }
        if (!encontrou) {
            ConsoleLogger.log("  -> Nenhum resultado encontrado.");
        }
    }
}