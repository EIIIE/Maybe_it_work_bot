package ru.lizovski;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.*;


public class SugarCloudBot extends TelegramLongPollingBot {

    private static String botToken = System.getenv("botToken");
    private static String botUsername= System.getenv("botUsername");

    public static void main(String[] args) /*throws IOException*/ {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new SugarCloudBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return botUsername;
    }


    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId());
            if (update.getMessage().getText().equals("/start")) {
                message.setText("Hello, world! This is simple bot!");
            }
            if (update.getMessage().getText().equals("/me")) {
                message.setText("Just a nice try in bots world...");
            }
            try {
                System.out.println(message);
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
        //Токен бота
    }
}
