package model;

// interface prototype
public abstract class Imovel implements Cloneable {
    private String endereco;
    private double area;
    private int quartos;

    public Imovel(String endereco, double area, int quartos) {
        this.endereco = endereco;
        this.area = area;
        this.quartos = quartos;
    }

    public String getEndereco() { 
        return endereco; 
    }

    public void setEndereco(String e) { 
        this.endereco = e; 
    }

    public double getArea() { 
        return area; 
    }

    public int getQuartos() {
        return quartos;
    }

    public void setArea(double a) { 
        this.area = a; 
    }

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
