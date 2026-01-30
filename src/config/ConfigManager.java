package config;

import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties props;

    private ConfigManager() {
        props = new Properties();
        props.setProperty("moeda", "R$");
        props.setProperty("preco.minimo", "1000.0"); 
        props.setProperty("termos.proibidos", "grátis,oferta,imperdível,urgente");
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
}