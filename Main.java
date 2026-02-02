import fachada.MyHomeFachada;
import model.*;
import builder.Anuncio;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        MyHomeFachada sistema = new MyHomeFachada();
        
        System.out.println("=== MYHOME: SISTEMA PROFISSIONAL DE ANÚNCIOS ===\n");

        // carrega o config.properties e o imoveis.csv 
        System.out.println(sistema.inicializar());

        Anunciante corretor = new Anunciante("Felipe Brito", "felipe@ifpb.edu.br", "83987762732", "CRECI-123");
        
        // lista de interessados 
        List<Comprador> interessados = new ArrayList<>();
        interessados.add(new Comprador("Joana Elise", "joana@teste.com", "83987768732"));

        // DEMONSTRAÇÃO DE ERRO NA VALIDAÇÃO 
        System.out.println("\n--- FASE: Teste de Segurança (Chain of Responsibility) ---");
        Imovel imovelInvalido = sistema.getImovelDaBase(0);
        
        List<String> logsErro = sistema.cadastrarAnuncioRapido(
            corretor, "Casa gratis", 500.0, imovelInvalido, interessados
        );

        logsErro.forEach(System.out::println);

        Anuncio anuncioInvalido = sistema.getCatalogo().get(0);
        List<String> logsErros = sistema.processarPublicacao(anuncioInvalido);
            logsErros.forEach(System.out::println);

        // pegamos um imóvel carregado do CSV e clonamos para não alterar a base original
        Imovel imovelParaAnuncio = sistema.getImovelDaBase(1);

        if (imovelParaAnuncio != null) {
            
            // cadastro
            System.out.println("\n--- FASE 1: Cadastro e Observadores ---");
            List<String> logsCadastro = sistema.cadastrarAnuncioRapido(
                corretor, "Cobertura de luxo", 7000000.0, imovelParaAnuncio, interessados
            );
            logsCadastro.forEach(System.out::println);

            Anuncio meuAnuncio = sistema.getCatalogo().get(1);

            // 5. backup e erro
            System.out.println("\n--- FASE 2: Histórico e Desfazer (Memento) ---");
            sistema.salvarEstado(meuAnuncio); // salva o estado atual
            System.out.println("[SISTEMA] Estado original salvo no Memento.");

            System.out.println("Simulando alteração acidental de preço para R$ 500.000...");
            meuAnuncio.setPreco(500000);
            System.out.println("Preço atual com erro: R$ " + meuAnuncio.getPreco());

            System.out.println("Acionando comando DESFAZER...");
            System.out.println(sistema.desfazerAlteracao(meuAnuncio));
            System.out.println("Preço recuperado: R$ " + meuAnuncio.getPreco());

            // 6. validação
            System.out.println("\n--- FASE 3: Validação em Cadeia e Ciclo de Vida ---");
            List<String> logsPublicacao = sistema.processarPublicacao(meuAnuncio);
            logsPublicacao.forEach(System.out::println);

            // 7. busca com filtros
            System.out.println("\n--- FASE 4: Filtros de Busca Avançados (Composite) ---");
            String termo = "luxo";
            double limite = 8000000.0;
            
            List<Anuncio> resultados = sistema.buscar(termo, limite);
            
            if (!resultados.isEmpty()) {
                System.out.println("[BUSCA] " + resultados.size() + " anúncio(s) encontrado(s) para '" + termo + "':");
                resultados.forEach(a -> System.out.println("   >> " + a.getTitulo() + " | R$ " + a.getPreco()));
            } else {
                System.out.println("[BUSCA] Nenhum resultado para os filtros aplicados.");
            }

            // 8. notificação
            System.out.println("\n--- FASE 5: Evento de Queda de Preço (Notificações Reais) ---");
            double precoPromo = 6000000.0;
            System.out.println("Promoção Relâmpago! Baixando de 7.000.000 para " + precoPromo);
            
            List<String> logsNotificacao = meuAnuncio.setPreco(precoPromo);

            if (!logsNotificacao.isEmpty()) {
                System.out.println("[SUCESSO] Notificações enviadas para a lista de interessados!");
                logsNotificacao.forEach(log -> System.out.println("   -> " + log));
            } else {
                System.out.println("[AVISO] O preço não baixou ou não há interessados para notificar.");
            }

        } else {
            System.err.println("[ERRO CRÍTICO] Base de dados vazia ou corrompida.");
        }

        System.out.println("\n=== Demonstração finalizada com sucesso ===");
    }
}