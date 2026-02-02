package observerAndstrategy;

import config.ConfigManager;
import model.Usuario;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class NotificadorTelegram implements CanalNotificacao {
    @Override
    public String enviar(Usuario destinatario, String mensagem) {
        try {
            ConfigManager config = ConfigManager.getInstance();
            String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                config.getConfig("telegram.token"), 
                config.getConfig("telegram.chatid"), 
                URLEncoder.encode(mensagem, StandardCharsets.UTF_8)
            );

            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return (response.statusCode() == 200) 
                ? "[TELEGRAM] Sucesso: " + destinatario.getNome() 
                : "[TELEGRAM] Erro HTTP: " + response.statusCode();
        } catch (Exception e) {
            return "[TELEGRAM] Falha técnica: " + e.getMessage();
        }
    }
}