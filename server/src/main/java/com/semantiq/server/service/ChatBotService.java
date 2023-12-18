package com.semantiq.server.service;

import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.repository.BotDataRepo;
import com.semantiq.server.repository.ChatBotRepo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ChatBotService {
    private final ChatBotRepo chatBotRepo;
    private final BotDataRepo botDataRepo;

    public ChatBotService(ChatBotRepo chatBotRepo, BotDataRepo botDataRepo) {
        this.chatBotRepo = chatBotRepo;
        this.botDataRepo = botDataRepo;
    }

    // Save or update Chatbot in database
    public void saveChatBot(ChatBot chatBot) {
        chatBotRepo.save(chatBot);
    }

    // Set Chatbot's data and upload created PDF to external API
    public ChatBot setBotData(ChatBot chatBot, String formData) {
        BotData botData = new BotData();
        botData.setFormData(formData);

        String filePath = "src/main/resources/Files/" + String.valueOf(chatBot.getId());
        boolean pdfConversionSuccess = convertStringToPDF(formData, filePath);

        if (pdfConversionSuccess) {
            chatBot.setData(botData);

            // Call the method to get the sourceId
            String sourceId = getSourceId(filePath);

            if (sourceId != null && !sourceId.isEmpty()) {
                botData.setApiId(sourceId);
                botDataRepo.save(botData);
            } else {
                System.err.println("Failed to retrieve sourceId from API");
            }
        } else {
            System.err.println("PDF conversion failed");
        }

        return chatBot;
    }


    // Method for String PDF conversation
    public static boolean convertStringToPDF(String content, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                contentStream.newLineAtOffset(50, 700); // Adjust the position as needed
                contentStream.showText(content);
                contentStream.endText();
            }

            document.save(filePath);
            System.out.println("PDF created successfully at: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error occurred while creating PDF: " + e.getMessage());
            return false;
        }
    }

    // Method to add for making API call and retrieving sourceId
    public String getSourceId(String urlToSubmit) {
        String apiUrl = "https://api.chatpdf.com/v1/sources/add-url";
        String sourceId = "";

        try {
            HttpURLConnection connection = getHttpURLConnection(urlToSubmit, apiUrl);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Process the response JSON to get the sourceId
                String jsonResponse = response.toString();
                sourceId = parseSourceId(jsonResponse);
            } else {
                System.out.println("Error - HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sourceId;
    }

    private static HttpURLConnection getHttpURLConnection(String urlToSubmit, String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String postData = "{ \"url\": \"" + urlToSubmit + "\" }";

        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(postData);
            outputStream.flush();
        }
        return connection;
    }

    private static String parseSourceId(String jsonResponse) {
        // Parse the JSON response to extract the sourceId
        int startIndex = jsonResponse.indexOf("\"sourceId\": \"");
        int endIndex = jsonResponse.indexOf("\"", startIndex + 13);
        return jsonResponse.substring(startIndex + 13, endIndex);
    }
}
