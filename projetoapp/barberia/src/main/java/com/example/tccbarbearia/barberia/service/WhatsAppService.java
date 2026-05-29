package com.example.tccbarbearia.barberia.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;
    private final String WhatsApp = "Zap";

    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void enviarMensagem(String telefone, String mensagem) {

        String url = "http://localhost:8081/message/sendText/" + WhatsApp;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", "SUA_KEY");

        Map<String, Object> body = new HashMap<>();
        body.put("number", "5519997305610"); 
        body.put("text", mensagem);
       

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class);

            System.out.println("✅ Mensagem enviada: " + response.getBody());
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar Zap: " + e.getMessage());
        }
    }
}