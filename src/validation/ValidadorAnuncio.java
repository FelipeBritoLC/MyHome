package validation;

import builder.Anuncio;

public abstract class ValidadorAnuncio {
    protected ValidadorAnuncio proximo;

    public void setProximo(ValidadorAnuncio proximo) {
        this.proximo = proximo;
    }

    public abstract boolean validar(Anuncio anuncio);

    protected boolean verificarProximo(Anuncio anuncio) {
        if (proximo == null) return true;
        return proximo.validar(anuncio);
    }
}
