package observerAndstrategy;

import util.ConsoleLogger;

public class NotificadorWhatsApp implements CanalNotificacao {
    @Override
    public void enviar(String mensagem) {
        ConsoleLogger.log("[WHATSAPP] Enviando: " + mensagem);
    }
}
