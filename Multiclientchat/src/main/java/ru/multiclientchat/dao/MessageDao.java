package ru.multiclientchat.dao;

import ru.multiclientchat.model.Message;

import java.util.LinkedList;

public interface MessageDao extends CrudDao<Message> {
    LinkedList pagination(int limit, int offset);
}
