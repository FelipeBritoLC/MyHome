package observerAndstrategy;

public class NotificadorEmail implements CanalNotificacao {
    @Override
    public void enviar(String mensagem) {
        System.out.println("[E-MAIL] Enviando: " + mensagem);
    }
}
