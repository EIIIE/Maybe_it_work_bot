package ru.lizovski;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SugarCloudBot extends TelegramLongPollingBot {

    // Environment variables from Heroku
    private static String botToken = System.getenv("botToken");
    private static String botUsername = System.getenv("botUsername");

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            String answer ;

            // Commands from chat
            switch (update.getMessage().getText())
            {
                case "/start":
                    answer = EmojiParser.parseToUnicode("Hello, world! This is simple bot! :ghost:");
                    break;

                case "/me":
                    answer = EmojiParser.parseToUnicode(
                            ":hear_no_evil: First name: " + user_first_name + "\n"
                                    + ":see_no_evil: Last name: " + user_last_name + "\n"
                                    + ":speak_no_evil: Username: " + user_username + "\n"
                                    + ":cyclone: User id: " + update.getMessage().getChat().getId() + "\n"
                                    + ":snowflake: Chat id: " + update.getMessage().getChatId() + "\n"
                                    + ":umbrella: Description: " + update.getMessage().getChat().getDescription() + "\n");
                    break;

                case "/bots":
                    answer = EmojiParser.parseToUnicode(
                                ":ghost: Some examples from TelegramBots Api: \n"
                                    + ":iphone: Use custom keyboards: @weatherbot\n"
                                    + ":inbox_tray: Basic messages: @directionsbot\n"
                                    + ":email: Send files by file_id: @filesbot\n"
                                    + ":postbox: Another files: @TGlanguagesbot\n"
                                    + ":mag_right: Inline support: @RaeBot\n"
                                    + ":e-mail: Webhook support: @SnowcrashBot\n");
                    break;

                 default:
                     answer = "You wrote: " + update.getMessage().getText();
            }

            // Create a message object object
            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(answer);

            // Logging User's message
            log(user_username, user_first_name, user_last_name, Long.toString(user_id), message_text, answer);

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void log(String username, String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + username + " " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
