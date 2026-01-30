package state;

import builder.Anuncio;
import util.ConsoleLogger;

public class EstadoSuspenso implements EstadoAnuncio {
    
    public void publicar(Anuncio c) {
        ConsoleLogger.log("Reenviando para rascunho para correção.");
        c.setEstado(new EstadoRascunho());
    }

    public void cancelar(Anuncio c) { 
        c.setEstado(new EstadoRascunho()); 
    }

    public String getNomeEstado() { 
        return "Suspenso"; 
    }
}
