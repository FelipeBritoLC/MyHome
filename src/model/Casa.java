package model;

public class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double area, int quartos, boolean temQuintal) {
        super(endereco, area, quartos);
        this.temQuintal = temQuintal;
    }

    @Override
    public String getTipo() { 
        return "Casa"; 
    }

    public boolean isTemQuintal() {
        return temQuintal;
    }
}
