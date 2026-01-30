

import builder.Anuncio;
import model.Casa;
import validation.*;

public class Main {
    public static void main(String[] args) {
        // 1. Criar um anúncio com o Builder (que contém um termo proibido "urgente")
        Anuncio meuAnuncio = new Anuncio.Builder()
            .comTitulo("Casa de Praia")
            .comDescricao("Venda URGENTE por motivo de viagem!")
            .comPreco(500.0) // Preço abaixo do mínimo (1000.0) definido no ConfigManager
            .paraImovel(new Casa("Orla de JP", 200, 4, true))
            .build();

        System.out.println("Estado Inicial: " + meuAnuncio);

        // 2. Configurar a Corrente de Validação (RF03)
        ValidadorAnuncio checkPreco = new ValidadorPreco();
        ValidadorAnuncio checkTermos = new ValidadorTermosProibidos();
        checkPreco.setProximo(checkTermos);

        // 3. Tentar publicar o anúncio
        System.out.println("\n--- Iniciando Processo de Publicação ---");
        
        if (checkPreco.validar(meuAnuncio)) {
            meuAnuncio.solicitarPublicacao(); // Rascunho -> Moderando
            meuAnuncio.solicitarPublicacao(); // Moderando -> Ativo
        } else {
            System.out.println("Publicação abortada: Corrija os erros acima.");
        }

        System.out.println("Estado Final: " + meuAnuncio);
    }
}