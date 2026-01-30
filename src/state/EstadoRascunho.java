package state;

import builder.Anuncio;

public class EstadoRascunho implements EstadoAnuncio {
    public void publicar(Anuncio contexto) {
        System.out.println("Enviando anúncio '" + contexto.getTitulo() + "' para moderação...");
        contexto.setEstado(new EstadoModerando());
    }
    public void cancelar(Anuncio contexto) {
        System.out.println("Anúncio descartado.");
    }
    public String getNomeEstado() { return "Rascunho"; }
}

