package model;

// Interface Prototype
public abstract class Imovel implements Cloneable {
    private String endereco;
    private double area;
    private int quartos;

    public Imovel(String endereco, double area, int quartos) {
        this.endereco = endereco;
        this.area = area;
        this.quartos = quartos;
    }

    // Getters e Setters
    public String getEndereco() { return endereco; }
    public void setEndereco(String e) { this.endereco = e; }
    public double getArea() { return area; }
    public void setArea(double a) { this.area = a; }

    @Override
    public Imovel clone() {
        try {
            return (Imovel) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public abstract String getTipo();
}

// Subclasse Casa
class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double area, int quartos, boolean temQuintal) {
        super(endereco, area, quartos);
        this.temQuintal = temQuintal;
    }

    @Override
    public String getTipo() { return "Casa"; }
}

// Subclasse Apartamento
class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double area, int quartos, int andar) {
        super(endereco, area, quartos);
        this.andar = andar;
    }

    @Override
    public String getTipo() { return "Apartamento"; }
}