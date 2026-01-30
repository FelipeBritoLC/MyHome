import model.Imovel;
import integration.ImportadorCSVImovel;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Sistema MyHome: Carga de Dados (E1) ---");

        // 1. Instanciar o importador (Template Method)
        ImportadorCSVImovel importador = new ImportadorCSVImovel();

        // 2. Executar a importação
        // Nota: Certifique-se de que o arquivo existe ou trate a exceção
        List<Imovel> imoveisCarregados = importador.importar("imoveis.csv");

        // 3. Exibir resultados
        for (Imovel i : imoveisCarregados) {
            System.out.println("Carregado: " + i.getTipo() + " em " + i.getEndereco());
        }
        
        System.out.println("\n--- FIM DO PROCESSO ---");
    }
}