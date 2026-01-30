import builder.Anuncio;
import model.Casa;
import validation.*;
import observerAndstrategy.*;

public class Main {
    public static void main(String[] args) {
        // 1. Criar o anúncio (agora com preço válido para não dar erro)
        Anuncio meuAnuncio = new Anuncio.Builder()
            .comTitulo("Cobertura em Tambaú")
            .comDescricao("Lindo imóvel com vista para o mar.")
            .comPreco(5000.0) 
            .paraImovel(new Casa("Av. Cabo Branco", 150, 3, false))
            .build();

        // 2. Configurar Notificações (Observer + Strategy)
        meuAnuncio.adicionarCanal(new NotificadorEmail());
        meuAnuncio.adicionarCanal(new NotificadorWhatsApp());

        // 3. Corrente de Validação
        ValidadorAnuncio corrente = new ValidadorPreco();
        corrente.setProximo(new ValidadorTermosProibidos());

        System.out.println("--- Início do Processo ---");
        
        if (corrente.validar(meuAnuncio)) {
            // Cada chamada de solicitarPublicacao vai disparar os Observers
            meuAnuncio.solicitarPublicacao(); // Rascunho -> Moderando
            System.out.println("---");
            meuAnuncio.solicitarPublicacao(); // Moderando -> Ativo
        }

        System.out.println("\nEstado Final: " + meuAnuncio);
    }
}