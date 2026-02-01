package builder;

import model.Anunciante;
import model.Imovel;
import state.*;
import util.ConsoleLogger;
import observerAndstrategy.CanalNotificacao;
import memento.AnuncioMemento;
import java.util.ArrayList;
import java.util.List;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;
    private Anunciante anunciante;
    private EstadoAnuncio estadoAtual;
    private List<CanalNotificacao> observadores = new ArrayList<>();

    protected Anuncio() {
        this.estadoAtual = new EstadoRascunho();
    }

    // --- métodos do memento (RF08) ---
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
        } else {
            ConsoleLogger.erro("[ERRO] Nada para desfazer.");
        }
    }

    // --- métodos existentes ---
    public void adicionarCanal(CanalNotificacao canal) { 
        observadores.add(canal);
        ConsoleLogger.log("Canal de notificação registrado com sucesso");
    }

    public void notificar(String msg) { 
        observadores.forEach(c -> c.enviar(msg)); 
    }

    public void setEstado(EstadoAnuncio novo) {
        this.estadoAtual = novo;
        notificar("Anúncio '" + titulo + "' agora é " + novo.getNomeEstado());
    }

    public void solicitarPublicacao() { 
        estadoAtual.publicar(this); 
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Anunciante getAnunciante() {
        return anunciante;
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
        String dono = anunciante != null ? " por " + anunciante.getNome() : "";
        return String.format("[%s] %s - R$ %.2f%s", getStatus(), titulo, preco, dono);
    }

    public static class Builder {
        private Anuncio anuncio = new Anuncio();

        public Builder comTitulo(String t) { anuncio.titulo = t; return this; }
        public Builder comDescricao(String d) { anuncio.descricao = d; return this; }
        public Builder comPreco(double p) { anuncio.preco = p; return this; }
        public Builder paraImovel(Imovel i) { anuncio.imovel = i; return this; }
        public Builder comAnunciante(Anunciante a) { anuncio.anunciante = a; return this; }

        public Anuncio build() {
            // Registra o anuncio na lista do anunciante, se houver
            if (anuncio.anunciante != null) {
                anuncio.anunciante.registrarAnuncio(anuncio);
            }
            return anuncio;
        }
    }
}