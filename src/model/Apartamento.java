package model;

public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double area, int quartos, int andar) {
        super(endereco, area, quartos);
        this.andar = andar;
    }

    @Override
    public String getTipo() { 
        return "Apartamento"; 
    }

    public int getAndar() {
        return andar;
    }
}
