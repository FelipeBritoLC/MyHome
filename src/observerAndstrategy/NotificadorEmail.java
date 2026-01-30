package observerAndstrategy;

import util.ConsoleLogger;

public class NotificadorEmail implements CanalNotificacao {
    @Override
    public void enviar(String mensagem) {
        ConsoleLogger.log("[E-MAIL] Enviando: " + mensagem);
    }
}
