package integration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ImportadorArquivo<T> {

    /**
     * 
     * @param caminhoFicheiro Caminho do arquivo a ser lido.
     * @return list de objetos do tipo T.
     * @throws IOException lança exceção para que o Main decida como exibir o erro.
     */
    public final List<T> importar(String caminhoFicheiro) throws IOException {
        List<T> resultados = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoFicheiro))) {
            String linha;
            
            if (temCabecalho()) {
                br.readLine(); // pula o cabeçalho
            }

            while ((linha = br.readLine()) != null) {
                T objeto = transformarLinhaEmObjeto(linha);
                if (objeto != null) {
                    resultados.add(objeto);
                }
            }
        } 

        
        return resultados;
    }


    protected abstract T transformarLinhaEmObjeto(String linha);

    protected boolean temCabecalho() {
        return true;
    }
}