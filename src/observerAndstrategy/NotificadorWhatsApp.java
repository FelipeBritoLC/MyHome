package observerAndstrategy;
import model.Usuario;

public class NotificadorWhatsApp implements CanalNotificacao {
    @Override
    public String enviar(Usuario destinatario, String mensagem) {
        return "[MOCK WPP] Destinatário: " + destinatario.getTelefone() + " | Msg: " + mensagem;
    }
}