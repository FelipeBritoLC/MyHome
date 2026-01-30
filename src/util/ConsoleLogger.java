package util;

// Resolve o E2: Centraliza a saída para facilitar reuso futuro
public class ConsoleLogger {
    public static void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
    public static void erro(String msg) {
        System.err.println("[ERRO] " + msg);
    }
}
