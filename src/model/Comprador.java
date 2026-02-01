package model;

import builder.Anuncio;
import observerAndstrategy.CanalNotificacao;
import search.FiltroAnuncio;
import util.ConsoleLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um usuário que busca imóveis para comprar ou alugar.
 * Implementa CanalNotificacao para receber alertas sobre anúncios de interesse.
 */
public class Comprador extends Usuario implements CanalNotificacao {
    private List<Anuncio> favoritos = new ArrayList<>();
    private List<String> notificacoesRecebidas = new ArrayList<>();

    public Comprador(String nome, String email) {
        super(nome, email);
    }

    public Comprador(String nome, String email, String telefone) {
        super(nome, email, telefone);
    }

    /**
     * Adiciona um anúncio à lista de favoritos.
     */
    public void favoritar(Anuncio anuncio) {
        if (!favoritos.contains(anuncio)) {
            favoritos.add(anuncio);
            ConsoleLogger.log(getNome() + " favoritou: " + anuncio.getTitulo());
        }
    }

    /**
     * Remove um anúncio da lista de favoritos.
     */
    public void desfavoritar(Anuncio anuncio) {
        if (favoritos.remove(anuncio)) {
            ConsoleLogger.log(getNome() + " removeu dos favoritos: " + anuncio.getTitulo());
        }
    }

    /**
     * Retorna lista imutável dos anúncios favoritos.
     */
    public List<Anuncio> getFavoritos() {
        return Collections.unmodifiableList(favoritos);
    }

    /**
     * Busca anúncios em um catálogo usando um filtro (Composite Pattern).
     */
    public List<Anuncio> buscar(List<Anuncio> catalogo, FiltroAnuncio filtro) {
        List<Anuncio> resultados = new ArrayList<>();
        for (Anuncio anuncio : catalogo) {
            if (filtro.isSatisfeitoPor(anuncio)) {
                resultados.add(anuncio);
            }
        }
        return resultados;
    }

    /**
     * Inscreve este comprador para receber notificações de um anúncio.
     */
    public void inscreverPara(Anuncio anuncio) {
        anuncio.adicionarCanal(this);
        ConsoleLogger.log(getNome() + " inscrito para notificacoes de: " + anuncio.getTitulo());
    }

    /**
     * Implementação do Observer - recebe notificações sobre anúncios.
     */
    @Override
    public void enviar(String mensagem) {
        notificacoesRecebidas.add(mensagem);
        ConsoleLogger.log("[NOTIFICACAO para " + getNome() + "] " + mensagem);
    }

    /**
     * Retorna histórico de notificações recebidas.
     */
    public List<String> getNotificacoesRecebidas() {
        return Collections.unmodifiableList(notificacoesRecebidas);
    }

    @Override
    public String getTipoUsuario() {
        return "Comprador";
    }
}
