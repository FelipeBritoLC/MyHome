package observerAndstrategy;

import config.ConfigManager;
import util.ConsoleLogger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class NotificadorEmail implements CanalNotificacao {

    @Override
    public void enviar(String mensagem) {
        ConfigManager config = ConfigManager.getInstance();
        String apiKey = config.getConfig("email.api.key");
        String remetente = config.getConfig("email.remetente");

        // Construindo o corpo do e-mail (JSON simples para a API)
        // Nota: Em um sistema real, o destinatário viria do perfil do usuário
        String json = String.format(
            "{\"personalizations\": [{\"to\": [{\"email\": \"felipe_destinatario@teste.com\"}]}], " +
            "\"from\": {\"email\": \"%s\"}, \"subject\": \"Aviso MyHome\", " +
            "\"content\": [{\"type\": \"text/plain\", \"value\": \"%s\"}]}",
            remetente, mensagem
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sendgrid.com/v3/mail/send"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        // Envio assíncrono para não travar o sistema principal
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenAccept(status -> {
                    if (status == 202) {
                        ConsoleLogger.log("[API EMAIL] E-mail real enviado com sucesso via SendGrid!");
                    } else {
                        ConsoleLogger.erro("[API EMAIL] Falha ao enviar e-mail. Status: " + status);
                    }
                });
    }
}