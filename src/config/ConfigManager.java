package config;

import java.io.FileInputStream;
import java.util.Properties;


public class ConfigManager {
    private static ConfigManager instance;
    private Properties props = new Properties();
    private boolean carregadoComSucesso = false;

    private ConfigManager() {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
            carregadoComSucesso = true;
        } catch (Exception e) {
            props.setProperty("preco.minimo", "1000.0");
            props.setProperty("termos.proibidos", "grátis,oferta");
            carregadoComSucesso = false;
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getConfig(String chave) { 
        return props.getProperty(chave); 
    }

    public boolean isCarregadoComSucesso() {
        return carregadoComSucesso;
    }
}