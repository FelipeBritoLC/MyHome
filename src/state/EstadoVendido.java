package state;

import builder.Anuncio;

public class EstadoVendido implements EstadoAnuncio {

    @Override
    public String publicar(Anuncio c) { 
        return "Operação negada: O imóvel já consta como Vendido/Arquivado."; 
    }

    @Override
    public String cancelar(Anuncio c) { 
        return "Operação negada: Não é possível cancelar um anúncio de um imóvel já vendido."; 
    }

    @Override
    public String getNomeEstado() { 
        return "Vendido/Arquivado"; 
    }
}