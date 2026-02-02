package state;

import builder.Anuncio;

public class EstadoAtivo implements EstadoAnuncio {

    @Override
    public String publicar(Anuncio contexto) { 
        return "Aviso: Este anúncio já se encontra no estado Ativo."; 
    }

    @Override
    public String cancelar(Anuncio contexto) {
        contexto.setEstado(new EstadoRascunho());
        
        return "Sucesso: O anúncio foi finalizado/removido e retornou ao estado de Rascunho.";
    }
    
    @Override
    public String getNomeEstado() { 
        return "Ativo"; 
    }
}