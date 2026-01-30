package state;

import builder.Anuncio;
import util.ConsoleLogger;

public class EstadoRascunho implements EstadoAnuncio {
    
    public void publicar(Anuncio contexto) {
        ConsoleLogger.log("Enviando anúncio '" + contexto.getTitulo() + "' para moderação...");
        contexto.setEstado(new EstadoModerando());
    }

    public void cancelar(Anuncio contexto) {
        ConsoleLogger.log("Anúncio descartado.");
    }
    
    public String getNomeEstado() { 
        return "Rascunho"; 
    }
}

