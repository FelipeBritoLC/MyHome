package state;
import builder.Anuncio;

public class EstadoRascunho implements EstadoAnuncio {
    public String publicar(Anuncio anuncio) {
        anuncio.setEstado(new EstadoModerando());
        return "Transição: Rascunho -> Em Moderação.";
    }
    public String cancelar(Anuncio anuncio) { return "Anúncio excluído."; }
    public String getNomeEstado() { return "Rascunho"; }
}