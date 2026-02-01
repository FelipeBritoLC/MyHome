package model;

import builder.Anuncio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um usuário que pode criar e gerenciar anúncios de imóveis.
 */
public class Anunciante extends Usuario {
    private List<Anuncio> meusAnuncios = new ArrayList<>();

    public Anunciante(String nome, String email) {
        super(nome, email);
    }

    public Anunciante(String nome, String email, String telefone) {
        super(nome, email, telefone);
    }

    /**
     * Inicia a criação de um novo anúncio usando o Builder.
     * O anunciante é automaticamente associado ao anúncio.
     */
    public Anuncio.Builder criarAnuncio() {
        return new Anuncio.Builder().comAnunciante(this);
    }

    /**
     * Registra um anúncio na lista do anunciante.
     * Chamado internamente pelo Builder.
     */
    public void registrarAnuncio(Anuncio anuncio) {
        if (!meusAnuncios.contains(anuncio)) {
            meusAnuncios.add(anuncio);
        }
    }

    /**
     * Retorna lista imutável dos anúncios deste anunciante.
     */
    public List<Anuncio> getMeusAnuncios() {
        return Collections.unmodifiableList(meusAnuncios);
    }

    /**
     * Conta quantos anúncios ativos o anunciante possui.
     */
    public long contarAnunciosAtivos() {
        return meusAnuncios.stream()
                .filter(a -> "Ativo".equals(a.getStatus()))
                .count();
    }

    @Override
    public String getTipoUsuario() {
        return "Anunciante";
    }
}
