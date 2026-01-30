package config;

import java.io.FileInputStream;
import java.util.Properties;
import util.ConsoleLogger;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties props = new Properties();

    private ConfigManager() {
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in); // Resolve o RF07: Carrega de arquivo real
        } catch (Exception e) {
            ConsoleLogger.erro("Não foi possível carregar config.properties, usando padrões.");
            // Fallback para não quebrar o sistema
            props.setProperty("preco.minimo", "1000.0");
            props.setProperty("termos.proibidos", "grátis,oferta");
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) instance = new ConfigManager();
        return instance;
    }

    public String getConfig(String chave) { return props.getProperty(chave); }
}