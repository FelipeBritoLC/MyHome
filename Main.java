import builder.Anuncio;
import model.*;
import search.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Criar uma base de dados fictícia
        List<Anuncio> baseDeDados = new ArrayList<>();
        
        baseDeDados.add(new Anuncio.Builder()
            .comTitulo("Apartamento no Centro")
            .comPreco(2000.0)
            .paraImovel(new Apartamento("Rua A", 50, 1, 2))
            .build());

        baseDeDados.add(new Anuncio.Builder()
            .comTitulo("Casa com Piscina")
            .comPreco(7000.0)
            .paraImovel(new Casa("Rua B", 200, 4, true))
            .build());

        baseDeDados.add(new Anuncio.Builder()
            .comTitulo("Apartamento Vista Mar")
            .comPreco(4500.0)
            .paraImovel(new Apartamento("Av. Beira Mar", 80, 2, 10))
            .build());

        // 2. Configurar a Busca Avançada (RF06)
        // Queremos: Título contém "Apartamento" E Preço até 3000
        FiltroAnd buscaComplexa = new FiltroAnd();
        buscaComplexa.adicionarFiltro(new FiltroTituloContem("Apartamento"));
        buscaComplexa.adicionarFiltro(new FiltroPrecoMaximo(3000.0));

        // 3. Executar a busca
        System.out.println("--- Resultados da Busca ---");
        for (Anuncio a : baseDeDados) {
            if (buscaComplexa.isSatisfeitoPor(a)) {
                System.out.println("Encontrado: " + a.getTitulo() + " - R$ " + a.getPreco());
            }
        }
    }
}