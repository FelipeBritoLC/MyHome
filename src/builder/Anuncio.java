package builder;

import model.Imovel;

public class Anuncio {
    private String titulo;
    private String descricao;
    private double preco;
    private Imovel imovel;

    // construtor privado para forçar o uso do Builder
    private Anuncio() {}

    @Override
    public String toString() {
        return "ANÚNCIO: " + titulo + "\nImóvel: " + imovel.getTipo() + 
               "\nPreço: R$ " + preco + "\nEndereço: " + imovel.getEndereco();
    }

    public static class Builder {
        private Anuncio anuncio = new Anuncio();

        public Builder comTitulo(String titulo) {
            anuncio.titulo = titulo;
            return this;
        }

        public Builder comDescricao(String descricao) {
            anuncio.descricao = descricao;
            return this;
        }

        public Builder comPreco(double preco) {
            anuncio.preco = preco;
            return this;
        }

        public Builder paraImovel(Imovel imovel) {
            anuncio.imovel = imovel;
            return this;
        }

        public Anuncio build() {
            if (anuncio.titulo == null || anuncio.imovel == null || anuncio.preco <= 0) {
                throw new IllegalStateException("Anúncio incompleto! Verifique título, imóvel e preço.");
            }
            return anuncio;
        }
    }
}
