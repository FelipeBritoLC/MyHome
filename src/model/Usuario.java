package model;

/**
 * Classe abstrata base para todos os tipos de usuários do sistema.
 * Contém os dados comuns a Anunciantes e Compradores.
 */
public abstract class Usuario {
    private static int contadorId = 1;

    private final int id;
    private String nome;
    private String email;
    private String telefone;

    public Usuario(String nome, String email) {
        this.id = contadorId++;
        this.nome = nome;
        this.email = email;
    }

    public Usuario(String nome, String email, String telefone) {
        this(nome, email);
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o tipo do usuário (para identificação).
     */
    public abstract String getTipoUsuario();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", getTipoUsuario(), nome, email);
    }
}
