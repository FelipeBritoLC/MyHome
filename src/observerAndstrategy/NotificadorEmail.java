package observerAndstrategy;

import model.Usuario;
import util.ConsoleLogger;

/**
 * Implementação do padrão Strategy para notificações via E-mail.
 * Atualmente opera em modo de simulação (Mock), demonstrando a 
 * flexibilidade do sistema em alternar entre diferentes canais.
 */
public class NotificadorEmail implements CanalNotificacao {

    @Override
    public void enviar(Usuario destinatario, String mensagem) {
        // Simulação de envio de e-mail
        // Em um cenário de produção, aqui estaria a integração com JavaMail ou SendGrid
        ConsoleLogger.log(String.format(
            "[E-MAIL] Simulando envio para %s (%s): %s", 
            destinatario.getNome(), 
            destinatario.getEmail(), 
            mensagem
        ));
    }
}