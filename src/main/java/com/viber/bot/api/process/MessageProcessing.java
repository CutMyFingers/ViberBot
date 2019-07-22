package com.viber.bot.api.process;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.Futures;
import com.viber.bot.Response;
import com.viber.bot.api.ViberBot;
import com.viber.bot.data.model.Carousel;
import com.viber.bot.data.model.KeyboardButtons;
import com.viber.bot.data.model.Stickers;
import com.viber.bot.event.incoming.IncomingMessageEvent;
import com.viber.bot.message.KeyboardMessage;
import com.viber.bot.message.Message;
import com.viber.bot.message.MessageKeyboard;
import com.viber.bot.message.RichMediaMessage;
import com.viber.bot.message.RichMediaObject;
import com.viber.bot.message.StickerMessage;
import com.viber.bot.message.TextMessage;

@Component
public class MessageProcessing implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ViberBot bot;

    @Value("${application.viber-bot.webhook-url}")
    private String webhookUrl;

    private Random rand = new Random();
    private static final Logger LOGGER = Logger.getLogger(MessageProcessing.class.getName());

    @Override
    public void onApplicationEvent(ApplicationReadyEvent appReadyEvent) {
        try {
            bot.setWebhook(webhookUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.onMessageReceived((event, message, response) -> processUserText(event, message, response));

        bot.onConversationStarted(event -> Futures.immediateFuture(Optional
            .of(new TextMessage("Привіт, мій солоденький " + event.getUser().getName() + "."))));
    }

    private void processUserText(IncomingMessageEvent event, Message message, Response response) {
        switch (substringUserText(message.toString())) {
            case "Хелп":
                displayHelpButtons(response);
                break;
            case "Привіт":
                response.send("Вітання тобі, " + event.getSender().getName() + ".");
                displayHelpButtons(response);
                break;
            case "Каруселі":
                displayCarousel(response);
                displayHelpButtons(response);
                break;
            case "Стикер":
                sendSticker(response);
                displayHelpButtons(response);
                break;
            default:
                response.send("Щось незрозуміле :с");
                break;
        }
    }

    private String substringUserText(String textMessage) {
        return textMessage.substring(textMessage.indexOf("text=") + 5, textMessage.indexOf("messageStr") - 2);
    }

    private void displayHelpButtons(Response response) {
        try {
            MessageKeyboard messageKeyboard = new MessageKeyboard(
                    new ObjectMapper().readValue(KeyboardButtons.HELP_BUTTON, new TypeReference<Map<String, Object>>() {
                    }));
            response.send(new KeyboardMessage(messageKeyboard, null, null));
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "HelpButtons", ex);
        }
    }

    private void displayCarousel(Response response) {
        try {
            RichMediaObject richMediaObject = new RichMediaObject(
                    new ObjectMapper().readValue(Carousel.CAROUSELS, new TypeReference<Map<String, Object>>() {
                    }));
            RichMediaMessage rm = new RichMediaMessage(richMediaObject, "загублені каруселі", null, null, null, null);
            response.send(rm);
        } catch (Exception ex) {
            LOGGER.log(Level.FINE, "Carousel", ex);
        }
    }

    private void sendSticker(Response response) {
        response.send(new StickerMessage(Stickers.STICKER_ID[rand.nextInt(10)], null, null, null));
    }
}
