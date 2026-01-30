package state;

import builder.Anuncio;
import util.ConsoleLogger;

public class EstadoVendido implements EstadoAnuncio {
    public void publicar(Anuncio c) { 
        ConsoleLogger.log("Anúncio já vendido."); 
    }

    public void cancelar(Anuncio c) { 
        ConsoleLogger.log("Não pode cancelar um anúncio vendido."); 
    }

    public String getNomeEstado() { 
        return "Vendido/Arquivado"; 
    }
}
