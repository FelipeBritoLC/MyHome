package state;

import builder.Anuncio;
import util.ConsoleLogger;

public class EstadoAtivo implements EstadoAnuncio {
    public void publicar(Anuncio contexto) { 
        ConsoleLogger.log("O anúncio já está ativo!"); 
    }

    public void cancelar(Anuncio contexto) {
        ConsoleLogger.log("Anúncio finalizado/removido pelo usuário.");
        contexto.setEstado(new EstadoRascunho());
    }
    
    public String getNomeEstado() { 
        return "Ativo"; 
    }
}