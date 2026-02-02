package model;

public class Anunciante extends Usuario {
    private String registroProfissional; // Ex: CRECI para corretores ou CPF para proprietários

    public Anunciante(String nome, String email, String telefone, String registro) {
        super(nome, email, telefone);
        this.registroProfissional = registro;
    }

    @Override
    public String getPerfil() {
        return "Anunciante (" + registroProfissional + ")";
    }
}