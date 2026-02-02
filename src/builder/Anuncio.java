package builder;

import model.*;
import state.*;
import observerAndstrategy.CanalNotificacao;
import memento.AnuncioMemento;
import java.util.*;

public class Anuncio {
    private String titulo;
    private double preco;
    private Imovel imovel;
    private Usuario anunciante;
    private EstadoAnuncio estadoAtual;
    private List<CanalNotificacao> canais = new ArrayList<>();
    private List<Comprador> favoritos = new ArrayList<>();

    protected Anuncio() { this.estadoAtual = new EstadoRascunho(); }

    public String adicionarCanal(CanalNotificacao canal) {
        this.canais.add(canal);
        return "Canal de notificação registrado.";
    }

    public String favoritar(Comprador comprador) {
        if (!favoritos.contains(comprador)) {
            this.favoritos.add(comprador);
            return "[SISTEMA] " + comprador.getNome() + " favoritou: " + titulo;
        }
        return "Usuário já favoritou este anúncio.";
    }

    private List<String> notificarInteressados(String mensagem) {
        List<String> logs = new ArrayList<>();

        for (CanalNotificacao canal : canais) {
            
                if (anunciante != null) {
                    logs.add(canal.enviar(anunciante, mensagem));
                }
                for (Comprador interessado : favoritos) {
                    logs.add(canal.enviar(interessado, mensagem));
                }
        }
        return logs;
    }

    public List<String> setPreco(double novoPreco) {
        List<String> logs = new ArrayList<>();
        
        // verificação de segurança para não notificar erros em rascunhos
        if (novoPreco < this.preco && estadoAtual instanceof state.EstadoAtivo) {
            String alerta = "QUEDA DE PREÇO! O imóvel '" + titulo + "' agora custa R$ " + novoPreco;
            logs.addAll(notificarInteressados(alerta));
        }
        
        this.preco = novoPreco;
        return logs;
    }

    public List<String> solicitarPublicacao() {
        String resultadoEstado = estadoAtual.publicar(this); 
        List<String> logs = new ArrayList<>();
        logs.add(resultadoEstado);
        logs.addAll(notificarInteressados("O anúncio '" + titulo + "' mudou para: " + getStatus()));
        return logs;
    }

    public void setEstado(EstadoAnuncio novoEstado) { this.estadoAtual = novoEstado; }
    public String getStatus() { return estadoAtual.getNomeEstado(); }
    public String getTitulo() { return titulo; }
    public double getPreco() { return preco; }
    public Imovel getImovel() { return imovel; }

    public AnuncioMemento criarSnapshot() { return new AnuncioMemento(titulo, "", preco); }
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