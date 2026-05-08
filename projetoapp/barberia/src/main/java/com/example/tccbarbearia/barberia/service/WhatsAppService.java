package com.example.tccbarbearia.barberia.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;

    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String INSTANCE = "instance173994";
    private final String TOKEN = "hksk4wbix5r0galw";

    public void enviarMensagem(String telefone, String mensagem) {

        String numero = telefone.replaceAll("[^0-9]", "");

        if (!numero.startsWith("55")) {
            numero = "55" + numero;
        }

        String url = "https://api.ultramsg.com/" +
                INSTANCE +
                "/messages/chat";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("token", TOKEN);
        body.add("to", numero);
        body.add("body", mensagem);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class);

        System.out.println(response.getBody());
    }
}