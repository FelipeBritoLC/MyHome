package model;

public class ImovelComercial extends Imovel {
    private boolean temRecepcao;

    public ImovelComercial(String endereco, double area, int salas, boolean temRecepcao) {
        // Usamos o campo quartos pra representar a quantidade de salas/ambientes
        super(endereco, area, salas);
        this.temRecepcao = temRecepcao;
    }

    public boolean isTemRecepcao() {
        return temRecepcao;
    }

    public void setTemRecepcao(boolean temRecepcao) {
        this.temRecepcao = temRecepcao;
    }

    @Override
    public String getTipo() {
        return "Imóvel Comercial";
    }
}