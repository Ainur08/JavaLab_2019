package ru.multiclientchat.service;

import ru.multiclientchat.dao.MessageDaoImpl;
import ru.multiclientchat.model.Message;

import java.util.LinkedList;

public class CommandService {
    public LinkedList getMessages(int limit, int offset){
        MessageDaoImpl messageDao = new MessageDaoImpl();
        LinkedList linkedList = messageDao.pagination(limit, offset);
        return linkedList;
    }
}
