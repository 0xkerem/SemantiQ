package com.semantiq.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semantiq.server.entity.BotData;
import com.semantiq.server.entity.Chat;
import com.semantiq.server.entity.ChatBot;
import com.semantiq.server.repository.BotDataRepo;
import com.semantiq.server.repository.ChatBotRepo;
import com.semantiq.server.repository.ChatRepo;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatBotService {
    private final ChatBotRepo chatBotRepo;
    private final BotDataRepo botDataRepo;
    private final ChatRepo chatRepo;


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatBotService(ChatBotRepo chatBotRepo, BotDataRepo botDataRepo, ChatRepo chatRepo) {
        this.chatBotRepo = chatBotRepo;
        this.botDataRepo = botDataRepo;
        this.chatRepo = chatRepo;
    }

    // Save or update Chatbot in database
    public void saveChatBot(ChatBot chatBot) {
        chatBotRepo.save(chatBot);
    }

    // Find ChatBot by id
    public ChatBot findChatBotById(int id) {
        return chatBotRepo.findById(id);
    }

    // Method to convert string content to PDF and upload to external API
    public ChatBot setBotData(ChatBot chatBot, String formData, int id) {
        BotData botData = new BotData();

        // Save form data in JSON format to specified location
        String formDataPath = saveFormDataToJson(formData, id);
        if (formDataPath != null) {
            botData.setFormData(formDataPath);
        } else {
            System.err.println("Failed to save form data as JSON");
            return null;
        }

        String filePath = "src/main/resources/Files/" + String.valueOf(id) + ".pdf";
        boolean pdfConversionSuccess = convertStringToPDF(formData, filePath);

        if (pdfConversionSuccess) {
            chatBot.setData(botData);

            // Call the method to get the sourceId
            String sourceId = uploadPDF(filePath);

            if (sourceId != null && !sourceId.isEmpty()) {
                botData.setSourceId(sourceId);
                botDataRepo.save(botData);
            } else {
                System.err.println("Failed to retrieve sourceId from API");
            }
        } else {
            System.err.println("PDF conversion failed");
        }

        return chatBot;
    }

    private String saveFormDataToJson(String formData, int id) {
        try {
            String filePath = "src/main/resources/Files/FormData/" + id + ".json";
            Path path = Paths.get(filePath);
            FileWriter fileWriter = new FileWriter(path.toFile());
            fileWriter.write(formData);
            fileWriter.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method for String PDF conversation
    private static boolean convertStringToPDF(String content, String filePath) {
        try {
            // Create a new Document
            Document document = new Document();

            // Initialize PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Open the document
            document.open();

            // Add content to the document
            document.add(new Paragraph(content));

            // Close the document
            document.close();

            return true; // Return true if the method is successful
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    // Method to add for making API call and retrieving sourceId
    private String getSourceId(String urlToSubmit) {
        String apiUrl = "https://api.chatpdf.com/v1/sources/add-file";
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

    // Upload PDF file to API and retrieve sourceId
    private String uploadPDF(String filePath) {
        String apiUrl = "https://api.chatpdf.com/v1/sources/add-file";
        String sourceId = "";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-api-key", "sec_QEql2uXiD7sW0Nq6wbtK1fRuP3yZ5FHm");
            conn.setDoOutput(true);

            File pdfFile = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(pdfFile);
            OutputStream outputStream = conn.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            fileInputStream.close();

            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();
                sourceId = parseSourceId(jsonResponse);
            } else {
                System.out.println("Status: " + conn.getResponseCode());
                System.out.println("Error: " + conn.getResponseMessage());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sourceId;
    }

    private static String parseSourceId(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getString("sourceId");
    }


    // Chat service
    public String askQuestion(int botId, int chatId, String question) {
        ChatBot bot = findChatBotById(botId);
        String sourceId = bot.getData().getSourceId();

        Chat chat;
        String chatHistoryPath;

        if (chatRepo.findById(chatId) == null || chatId == -1) {
            chat = new Chat(bot);
            chat = chatRepo.save(chat);
            chatHistoryPath = "src/main/resources/Files/Chat/" + chat.getId() + ".json";
            chat.setChatHistory(chatHistoryPath);
            chatRepo.save(chat);
        } else {
            chat = chatRepo.findById(chatId);
            chatHistoryPath = chat.getChatHistory();
        }

        List<Message> chatHistory = getChatHistory(chatHistoryPath);

        // Construct message in the required format
        Message userMessage = new Message();
        userMessage.setRole("user");
        userMessage.setContent(question);

        // Check if chat history exists and append the new message
        List<Message> messages = new ArrayList<>();
        if (chatHistory != null) {
            messages.addAll(chatHistory);
        }
        messages.add(userMessage);

        // Prepare JSON payload
        JSONObject payload = new JSONObject();
        payload.put("sourceId", sourceId);

        JSONArray messagesArray = new JSONArray();
        for (Message msg : messages) {
            JSONObject msgObj = new JSONObject();
            msgObj.put("role", msg.getRole());
            msgObj.put("content", msg.getContent());
            messagesArray.put(msgObj);
        }
        payload.put("messages", messagesArray);

        // Make an actual HTTP POST request to the external API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", "sec_QEql2uXiD7sW0Nq6wbtK1fRuP3yZ5FHm"); // Add API key to the header

        HttpEntity<String> entity = new HttpEntity<>(payload.toString(), headers);

        String apiUrl = "https://api.chatpdf.com/v1/chats/message";
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        // Get the response body
        String responseBody = response.getBody();

        // Construct message for API response
        Message apiResponseMessage = new Message();
        apiResponseMessage.setRole("assistant");
        apiResponseMessage.setContent(responseBody);

        // Add API response message to chat history
        messages.add(apiResponseMessage);

        // Save updated chat history to file
        try {
            String chatHistoryJson = objectMapper.writeValueAsString(messages);
            Files.write(Paths.get(chatHistoryPath), chatHistoryJson.getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chatRepo.save(chat);

        return responseBody;
    }

    private List<Message> getChatHistory(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                return new ArrayList<>(); // Return an empty list for now
            }

            String content = new String(Files.readAllBytes(path));
            Message[] messages = objectMapper.readValue(content, Message[].class);
            return new ArrayList<>(Arrays.asList(messages));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
