package builder;

import model.Imovel;
import state.*;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;
    private EstadoAnuncio estadoAtual;

    protected Anuncio() {
        this.estadoAtual = new EstadoRascunho(); 
    }

    // Métodos de Estado
    public void setEstado(EstadoAnuncio novoEstado) { 
        this.estadoAtual = novoEstado; 
    }

    public void solicitarPublicacao() { 
        estadoAtual.publicar(this); 
    }

    public void solicitarCancelamento() { 
        estadoAtual.cancelar(this); 
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

    public String getStatus() { 
        return estadoAtual.getNomeEstado(); 
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f", getStatus(), titulo, preco);
    }

    public static class Builder {
        private Anuncio anuncio = new Anuncio();
        public Builder comTitulo(String t) { anuncio.titulo = t; return this; }
        public Builder comDescricao(String d) { anuncio.descricao = d; return this; }
        public Builder comPreco(double p) { anuncio.preco = p; return this; }
        public Builder paraImovel(Imovel i) { anuncio.imovel = i; return this; }
        public Anuncio build() { return anuncio; }
    }
}