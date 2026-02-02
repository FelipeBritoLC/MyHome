package state;

import builder.Anuncio;

public class EstadoSuspenso implements EstadoAnuncio {
    
    @Override
    public String publicar(Anuncio c) {
        c.setEstado(new EstadoRascunho());
        return "Ação: O anúncio estava suspenso e foi movido para Rascunho para correções.";
    }

    @Override
    public String cancelar(Anuncio c) { 
        c.setEstado(new EstadoRascunho()); 
        return "Ação: O anúncio suspenso foi cancelado e retornou ao Rascunho.";
    }

    @Override
    public String getNomeEstado() { 
        return "Suspenso"; 
    }
}