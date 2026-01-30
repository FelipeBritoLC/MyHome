package state;

import builder.Anuncio;

public class EstadoAtivo implements EstadoAnuncio {
    public void publicar(Anuncio contexto) { System.out.println("O anúncio já está ativo!"); }
    public void cancelar(Anuncio contexto) {
        System.out.println("Anúncio finalizado/removido pelo usuário.");
        contexto.setEstado(new EstadoRascunho());
    }
    public String getNomeEstado() { return "Ativo"; }
}