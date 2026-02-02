package state;
import builder.Anuncio;

public class EstadoModerando implements EstadoAnuncio {
    public String publicar(Anuncio anuncio) {
        anuncio.setEstado(new EstadoAtivo());
        return "Transição: Moderação -> Ativo (Publicado).";
    }
    public String cancelar(Anuncio anuncio) {
        anuncio.setEstado(new EstadoRascunho());
        return "Transição: Moderação -> Rascunho (Reprovado).";
    }
    public String getNomeEstado() { return "Em Moderação"; }
}