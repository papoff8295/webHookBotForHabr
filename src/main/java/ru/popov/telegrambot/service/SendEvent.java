package ru.popov.telegrambot.service;

import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.popov.telegrambot.DAO.EventCashDAO;
import ru.popov.telegrambot.config.ApplicationContextProvider;
import ru.popov.telegrambot.model.TelegramBot;

@Setter
//thread with event
public class SendEvent extends Thread {


    private long eventCashId;
    private SendMessage sendMessage;

    public SendEvent() {
    }

    @SneakyThrows
    @Override
    public void run() {
        TelegramBot telegramBot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        EventCashDAO eventCashDAO = ApplicationContextProvider.getApplicationContext().getBean(EventCashDAO.class);
        telegramBot.execute(sendMessage);
        //if event it worked, need to remove it from the database of unresolved events
        eventCashDAO.delete(eventCashId);
    }
}
