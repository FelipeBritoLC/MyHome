package validation;
import builder.Anuncio;

public abstract class ValidadorAnuncio {
    protected ValidadorAnuncio proximo;
    public void setProximo(ValidadorAnuncio proximo) { this.proximo = proximo; }
    public abstract boolean validar(Anuncio anuncio);
}