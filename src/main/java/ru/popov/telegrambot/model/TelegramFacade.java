package ru.popov.telegrambot.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.popov.telegrambot.cash.BotStateCash;
import ru.popov.telegrambot.model.handler.CallbackQueryHandler;
import ru.popov.telegrambot.model.handler.MessageHandler;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    final MessageHandler messageHandler;
    final CallbackQueryHandler callbackQueryHandler;
    final BotStateCash botStateCash;

    @Value("${telegrambot.adminId}")
    int adminId;


    public TelegramFacade(MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler, BotStateCash botStateCash) {
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.botStateCash = botStateCash;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {

            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                return handleInputMessage(message);
            }
        }
        return null;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {
        BotState botState;
        String inputMsg = message.getText();
        //we process messages of the main menu and any other messages
        //set state
        switch (inputMsg) {
            case "/start":
                botState = BotState.START;
                break;
            case "Мои напоминания":
                botState = BotState.MYEVENTS;
                break;
            case "Создать напоминание":
                botState = BotState.CREATE;
                break;
            case "Отключить напоминания":
            case "Включить напоминания":
                botState = BotState.ONEVENT;
                break;
            case "All users":
                if (message.getFrom().getId() == adminId)
                botState = BotState.ALLUSERS;
                else botState = BotState.START;
                break;
            case "All events":
                if (message.getFrom().getId() == adminId)
                botState = BotState.ALLEVENTS;
                else botState = BotState.START;
                break;
            default:
                botState = botStateCash.getBotStateMap().get(message.getFrom().getId()) == null?
                        BotState.START: botStateCash.getBotStateMap().get(message.getFrom().getId());
        }
        //we pass the corresponding state to the handler
        //the corresponding method will be called
        return messageHandler.handle(message, botState);

    }
}
