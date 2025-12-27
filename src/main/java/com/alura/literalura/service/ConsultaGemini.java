package com.alura.literalura.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GenerateContentConfig;

public class ConsultaGemini {

    public static String obtenerTraduccion(String texto) {

        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            return "ERROR: La variable de entorno GEMINI_API_KEY no está configurada.";
        }

        String modelo = "gemini-2.5-flash";
        String prompt = "Traduce el siguiente texto al español: " + texto;

        try (Client client = Client.builder().apiKey(apiKey).build()) {

            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.2f)
                    .build();

            GenerateContentResponse response =
                    client.models.generateContent(
                            modelo,
                            prompt,
                            config
                    );

            return response.text();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
}