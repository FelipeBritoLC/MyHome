package observerAndstrategy;

import model.Usuario;
import util.ConsoleLogger;

/**
 * Implementação do padrão Strategy para notificações via WhatsApp.
 * Atualmente opera em modo de simulação (Mock), demonstrando a 
 * flexibilidade do sistema em suportar múltiplos canais de comunicação.
 */
public class NotificadorWhatsApp implements CanalNotificacao {

    @Override
    public void enviar(Usuario destinatario, String mensagem) {
        // Simulação de envio de WhatsApp
        // Como o CallMeBot estava instável, mantemos esta classe como 
        // prova de conceito do padrão Strategy.
        ConsoleLogger.log(String.format(
            "[WHATSAPP - SIMULADO] Enviando para %s (%s): %s", 
            destinatario.getNome(), 
            destinatario.getTelefone(), 
            mensagem
        ));
    }
}