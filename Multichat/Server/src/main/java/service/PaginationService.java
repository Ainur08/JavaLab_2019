package service;

import context.Component;
import dao.MessageDao;
import dao.MessageDaoImpl;
import model.Message;
import model.Pagination;
import protocol.Request;
import java.util.LinkedList;

public class PaginationService implements Component {
    private MessageDao messageDao;

    public Pagination getMessages(Request request) {
        LinkedList<Message> messages = messageDao.pagination(Integer.parseInt(request.getParameter("size")), Integer.parseInt(request.getParameter("page")));
        Pagination pagination = new Pagination();
        pagination.setMessages(messages);
        return pagination;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDaoImpl messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public String getComponentName() {
        return null;
    }
}

