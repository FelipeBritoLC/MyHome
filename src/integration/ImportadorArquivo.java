package integration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.ConsoleLogger;

public abstract class ImportadorArquivo<T> {

    // template method
    public final List<T> importar(String caminhoFicheiro) {
        List<T> resultados = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoFicheiro))) {
            String linha;
            if (temCabecalho()) {
                br.readLine(); // pula a primeira linha
            }

            while ((linha = br.readLine()) != null) {
                T objeto = transformarLinhaEmObjeto(linha);
                if (objeto != null) {
                    resultados.add(objeto);
                }
            }
            ConsoleLogger.log("[IMPORTAÇÃO] Processamento concluído: " + resultados.size() + " itens.");
        } catch (IOException e) {
            ConsoleLogger.erro("[ERRO] Falha ao ler o ficheiro: " + e.getMessage());
        }
        
        return resultados;
    }

    // passo específico que as subclasses devem implementar
    protected abstract T transformarLinhaEmObjeto(String linha);

    // um gancho que pode ser sobrescrito se necessário
    protected boolean temCabecalho() {
        return true;
    }
}