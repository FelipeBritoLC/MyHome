
import config.ConfigManager;
import builder.Anuncio;
import model.*;

public class Main {
    public static void main(String[] args) {
        // 1. Singleton em ação
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Config de Moeda: " + config.getConfig("moeda"));

        // 2. Prototype em ação (RF02)
        // Criamos um modelo padrão de apartamento
        Apartamento modeloApto = new Apartamento("Rua das Palmeiras, 100", 65.0, 2, 5);
        
        // O usuário quer anunciar um apto igual no mesmo prédio, só muda o andar
        Apartamento novoApto = (Apartamento) modeloApto.clone();
        novoApto.setEndereco("Rua das Palmeiras, 101"); // Pequeno ajuste

        // 3. Builder em ação (RF01)
        Anuncio meuAnuncio = new Anuncio.Builder()
            .comTitulo("Lindo Apto no Centro")
            .comDescricao("Oportunidade única para estudantes.")
            .comPreco(1500.0)
            .paraImovel(novoApto)
            .build();

        System.out.println("\n--- Sistema MyHome ---");
        System.out.println(meuAnuncio);
    }
}