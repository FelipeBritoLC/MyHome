package builder;

import model.Imovel;
import state.*;
import observerAndstrategy.CanalNotificacao;
import java.util.ArrayList;
import java.util.List;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;
    private EstadoAnuncio estadoAtual;
    private List<CanalNotificacao> observadores = new ArrayList<>();

    protected Anuncio() {
        this.estadoAtual = new EstadoRascunho();
    }

    // Gerenciamento de Observers (RF05)
    public void adicionarCanal(CanalNotificacao canal) { 
        observadores.add(canal); 
    }
    
    public void notificar(String mensagem) {
        for (CanalNotificacao canal : observadores) {
            canal.enviar(mensagem);
        }
    }

    // Métodos de Estado - Agora notificam ao mudar
    public void setEstado(EstadoAnuncio novoEstado) {
        this.estadoAtual = novoEstado;
        notificar("O anúncio '" + titulo + "' mudou para o estado: " + novoEstado.getNomeEstado());
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