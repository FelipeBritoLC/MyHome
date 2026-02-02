package observerAndstrategy;
import model.Usuario;

public interface CanalNotificacao {
    String enviar(Usuario destinatario, String mensagem);
}