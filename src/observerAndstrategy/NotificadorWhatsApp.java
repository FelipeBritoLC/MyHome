package observerAndstrategy;

public class NotificadorWhatsApp implements CanalNotificacao {
    @Override
    public void enviar(String mensagem) {
        System.out.println("[WHATSAPP] Enviando: " + mensagem);
    }
}
