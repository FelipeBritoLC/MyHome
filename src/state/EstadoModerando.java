package state;

import builder.Anuncio;
import util.ConsoleLogger;

public class EstadoModerando implements EstadoAnuncio {
    
    public void publicar(Anuncio contexto) {
        ConsoleLogger.log("Aprovação concluída! O anúncio agora está visível.");
        contexto.setEstado(new EstadoAtivo());
    }

    public void cancelar(Anuncio contexto) {
        ConsoleLogger.log("Anúncio reprovado na moderação. Voltando para rascunho.");
        contexto.setEstado(new EstadoRascunho());
    }

    public String getNomeEstado() { 
        return "Em Moderação"; 
    }
}

