package observerAndstrategy;
import model.Usuario;

public class NotificadorEmail implements CanalNotificacao {
    @Override
    public String enviar(Usuario destinatario, String mensagem) {
        return "[MOCK E-MAIL] Destinatário: " + destinatario.getEmail() + " | Msg: " + mensagem;
    }
}