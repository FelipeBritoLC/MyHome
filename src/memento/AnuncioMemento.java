package memento;
/**
 * Representa o estado guardado de um Anúncio.
 */
public class AnuncioMemento {
    private final String titulo;
    private final String descricao;
    private final double preco;

    public AnuncioMemento(String titulo, String descricao, double preco) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getTitulo() { 
        return titulo; 
    }

    public String getDescricao() { 
        return descricao; 
    }

    public double getPreco() { 
        return preco; 
    }
}