package observerAndstrategy;

import config.ConfigManager;
import model.Usuario;
import util.ConsoleLogger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NotificadorTelegram implements CanalNotificacao {
    @Override
    public void enviar(Usuario destinatario, String mensagem) {
        try {
            ConfigManager config = ConfigManager.getInstance();
            String token = config.getConfig("telegram.token");
            String chatId = config.getConfig("telegram.chatid");

            // Validação simples de segurança
            if (token == null || chatId == null) {
                ConsoleLogger.erro("[TELEGRAM] Token ou ChatID não encontrados no config.properties!");
                return;
            }

            String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                token, 
                chatId, 
                URLEncoder.encode(mensagem, StandardCharsets.UTF_8)
            );

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Mudamos para .send() (Síncrono) para garantir que o Java espere a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ConsoleLogger.log("[TELEGRAM] Notificação enviada com sucesso para " + destinatario.getNome());
            } else {
                ConsoleLogger.erro("[TELEGRAM] Erro na API. Código: " + response.statusCode() + " - Resposta: " + response.body());
            }

        } catch (Exception e) {
            ConsoleLogger.erro("[TELEGRAM] Falha ao conectar: " + e.getMessage());
        }
    }
}