package observerAndstrategy;

import model.Usuario;

public interface CanalNotificacao {
    void enviar(Usuario destinatario, String mensagem);
}