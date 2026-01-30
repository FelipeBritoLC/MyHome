import builder.Anuncio;
import model.Apartamento;
import memento.HistoricoAnuncio;

public class Main {
    public static void main(String[] args) {
        // 1. Criar anúncio inicial
        Anuncio meuAnuncio = new Anuncio.Builder()
            .comTitulo("Versão 1: Apto Simples")
            .comDescricao("Descrição básica.")
            .comPreco(1000.0)
            .paraImovel(new Apartamento("Rua 1", 40, 1, 1))
            .build();

        HistoricoAnuncio historico = new HistoricoAnuncio();

        // 2. Guardar o primeiro estado (Backup)
        historico.guardar(meuAnuncio.criarSnapshot());

        System.out.println("Atual: " + meuAnuncio);

        // 3. Fazer uma alteração "errada"
        System.out.println("\n--- Utilizador altera o anúncio para algo errado ---");
        // Nota: Como não usamos Spring, fazemos a alteração via "edição" simulada
        // Para este teste, vamos reconstruir ou permitir setters. 
        // No Builder puro, simulamos criando um novo estado a partir do memento.
        
        // Simulação de edição:
        meuAnuncio = new Anuncio.Builder()
            .comTitulo("ERRO: Título Bagunçado")
            .comDescricao("asdfghjkl")
            .comPreco(999999.0)
            .paraImovel(new Apartamento("Rua 1", 40, 1, 1))
            .build();

        System.out.println("Estado Atual (com erro): " + meuAnuncio);

        // 4. Utilizador clica em "DESFAZER"
        System.out.println("\n--- Clicando em DESFAZER ---");
        meuAnuncio.restaurar(historico.desfazer());

        System.out.println("Estado Final (recuperado): " + meuAnuncio);
    }
}