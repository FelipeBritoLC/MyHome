package builder;

import model.Imovel;
import model.Usuario;
import model.Comprador;
import state.*;
import observerAndstrategy.CanalNotificacao;
import memento.AnuncioMemento;
import util.ConsoleLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;
    private Usuario anunciante;
    private EstadoAnuncio estadoAtual;
    private List<CanalNotificacao> canais = new ArrayList<>();
    private List<Comprador> favoritos = new ArrayList<>();

    protected Anuncio() {
        this.estadoAtual = new EstadoRascunho();
    }

    public void adicionarCanal(CanalNotificacao canal) {
        this.canais.add(canal);
    }

    public void favoritar(Comprador comprador) {
        if (!favoritos.contains(comprador)) {
            this.favoritos.add(comprador);
            ConsoleLogger.log("[SISTEMA] " + comprador.getNome() + " favoritou: " + titulo);
        }
    }

    private void notificarInteressados(String mensagem) {
        for (CanalNotificacao canal : canais) {
            // Criamos um conjunto para rastrear QUEM já recebeu a mensagem por esse canal
            Set<String> destinosJaAvisados = new HashSet<>();

            // 1. Notificar o Anunciante (Dono)
            if (anunciante != null && anunciante.getTelefone() != null) {
                canal.enviar(anunciante, mensagem);
                destinosJaAvisados.add(anunciante.getTelefone());
            }

            // 2. Notificar os Favoritos (Compradores)
            for (Comprador interessado : favoritos) {
                // SÓ envia se o telefone for diferente de quem já recebeu
                if (!destinosJaAvisados.contains(interessado.getTelefone())) {
                    canal.enviar(interessado, mensagem);
                    destinosJaAvisados.add(interessado.getTelefone());
                }
            }
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setPreco(double novoPreco) {
        if (novoPreco < this.preco) {
            String alerta = "QUEDA DE PREÇO! O imóvel '" + titulo + "' agora custa R$ " + novoPreco;
            notificarInteressados(alerta);
        }
        this.preco = novoPreco;
    }

    public void setEstado(EstadoAnuncio novoEstado) {
        this.estadoAtual = novoEstado;
        notificarInteressados("O anúncio '" + titulo + "' agora está: " + novoEstado.getNomeEstado());
    }

    public void solicitarPublicacao() { estadoAtual.publicar(this); }
    public String getStatus() { return estadoAtual.getNomeEstado(); }
    public String getTitulo() { return titulo; }

    public AnuncioMemento criarSnapshot() { return new AnuncioMemento(titulo, descricao, preco); }
    public void restaurar(AnuncioMemento m) { if (m != null) { this.titulo = m.getTitulo(); this.preco = m.getPreco(); } }

    public static class Builder {
        private Anuncio anuncio = new Anuncio();
        public Builder comTitulo(String t) { anuncio.titulo = t; return this; }
        public Builder comPreco(double p) { anuncio.preco = p; return this; }
        public Builder paraImovel(Imovel i) { anuncio.imovel = i; return this; }
        public Builder comAnunciante(Usuario a) { anuncio.anunciante = a; return this; }
        public Anuncio build() { return anuncio; }
    }
}