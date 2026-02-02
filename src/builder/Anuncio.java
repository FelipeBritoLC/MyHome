package builder;

import model.Imovel;
import model.Usuario;
import state.*;
import observerAndstrategy.CanalNotificacao;
import memento.AnuncioMemento;
import util.ConsoleLogger;

import java.util.ArrayList;
import java.util.List;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;
    private Usuario anunciante; 
    private EstadoAnuncio estadoAtual;
    private List<CanalNotificacao> observadores = new ArrayList<>();

    protected Anuncio() {
        this.estadoAtual = new EstadoRascunho();
    }

    // --- Gestão de Observers (RF05) ---
    public void adicionarCanal(CanalNotificacao canal) {
        this.observadores.add(canal);
        ConsoleLogger.log("Canal de notificação registrado com sucesso.");
    }

    public void notificar(String mensagem) {
        for (CanalNotificacao canal : observadores) {
            canal.enviar(mensagem);
        }
    }

    // --- Máquina de Estados (RF04) ---
    public void setEstado(EstadoAnuncio novoEstado) {
        this.estadoAtual = novoEstado;
        notificar("Anúncio '" + titulo + "' agora está no estado: " + novoEstado.getNomeEstado());
    }

    public void solicitarPublicacao() {
        estadoAtual.publicar(this);
    }

    public void solicitarCancelamento() {
        estadoAtual.cancelar(this);
    }

    // --- Memento (RF08) ---
    public AnuncioMemento criarSnapshot() {
        ConsoleLogger.log("[SISTEMA] Estado guardado com sucesso.");
        return new AnuncioMemento(titulo, descricao, preco);
    }

    public void restaurar(AnuncioMemento memento) {
        if (memento != null) {
            this.titulo = memento.getTitulo();
            this.descricao = memento.getDescricao();
            this.preco = memento.getPreco();
            ConsoleLogger.log("[SISTEMA] Estado restaurado para: " + titulo);
        }
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public Usuario getAnunciante() { return anunciante; }
    public String getStatus() { return estadoAtual.getNomeEstado(); }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (Anunciante: %s)", 
            getStatus(), titulo, preco, anunciante.getNome());
    }

    // --- Builder (RF01) ---
    public static class Builder {
        private Anuncio anuncio = new Anuncio();

        public Builder comTitulo(String t) { anuncio.titulo = t; return this; }
        public Builder comDescricao(String d) { anuncio.descricao = d; return this; }
        public Builder comPreco(double p) { anuncio.preco = p; return this; }
        public Builder paraImovel(Imovel i) { anuncio.imovel = i; return this; }
        public Builder comAnunciante(Usuario a) { anuncio.anunciante = a; return this; }

        public Anuncio build() {
            if (anuncio.titulo == null || anuncio.anunciante == null || anuncio.imovel == null) {
                throw new IllegalStateException("Anúncio incompleto: Título, Anunciante e Imóvel são obrigatórios.");
            }
            return anuncio;
        }
    }
}