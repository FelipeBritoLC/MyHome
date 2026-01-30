package model;

public class Terreno extends Imovel {
    private String zoneamento; // Ex: Residencial, Comercial, Industrial

    public Terreno(String endereco, double area, String zoneamento) {

        super(endereco, area, 0); 
        this.zoneamento = zoneamento;
    }

    public String getZoneamento() {
        return zoneamento;
    }

    public void setZoneamento(String zoneamento) {
        this.zoneamento = zoneamento;
    }

    @Override
    public String getTipo() {
        return "Terreno";
    }
}
