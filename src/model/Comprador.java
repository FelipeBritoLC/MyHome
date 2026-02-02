package model;

public class Comprador extends Usuario {
    public Comprador(String nome, String email, String telefone) {
        super(nome, email, telefone);
    }

    @Override
    public String getPerfil() {
        return "Interessado/Comprador";
    }
}