package ru.popov.telegrambot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.popov.telegrambot.DAO.EventCashDAO;
import ru.popov.telegrambot.config.ApplicationContextProvider;
import ru.popov.telegrambot.entity.EventCashEntity;
import ru.popov.telegrambot.model.TelegramBot;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

@Component
public class SendEventFromCache {

    private final EventCashDAO eventCashDAO;

    @Value("${telegrambot.adminId}")
    private int admin_id;

    @Autowired
    public SendEventFromCache(EventCashDAO eventCashDAO) {
        this.eventCashDAO = eventCashDAO;
    }

    @PostConstruct
    @SneakyThrows
    //after every restart app  - check unspent events
    private void afterStart() {
        List<EventCashEntity> list = eventCashDAO.findAllEventCash();
        TelegramBot telegramBot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(admin_id));
        sendMessage.setText("Произошла перезагрузка!");
        telegramBot.execute(sendMessage);

        if (!list.isEmpty()) {
            for (EventCashEntity eventCashEntity : list) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(eventCashEntity.getDate());
                SendEvent sendEvent = new SendEvent();
                sendEvent.setSendMessage(new SendMessage(String.valueOf(eventCashEntity.getUserId()), eventCashEntity.getDescription()));
                sendEvent.setEventCashId(eventCashEntity.getId());
                new Timer().schedule(new SimpleTask(sendEvent), calendar.getTime());
            }
        }
    }
}
