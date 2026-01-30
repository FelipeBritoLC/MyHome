package state;

import builder.Anuncio;

public class EstadoModerando implements EstadoAnuncio {
    
    public void publicar(Anuncio contexto) {
        System.out.println("Aprovação concluída! O anúncio agora está visível.");
        contexto.setEstado(new EstadoAtivo());
    }

    public void cancelar(Anuncio contexto) {
        System.out.println("Anúncio reprovado na moderação. Voltando para rascunho.");
        contexto.setEstado(new EstadoRascunho());
    }

    public String getNomeEstado() { return "Em Moderação"; }
}

