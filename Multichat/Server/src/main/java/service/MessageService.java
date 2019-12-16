package service;

import context.Component;
import dao.MessageDao;
import dao.MessageDaoImpl;
import model.Message;
import protocol.Request;

public class MessageService implements Component {
    private MessageDao messageDao;

    public Message sendMessage(Request request) {
        Message message = new Message(Integer.parseInt(request.getParameter("idUser")), request.getParameter("dateTime"), request.getParameter("message"));
        messageDao.save(message);
        return message;
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


//        try {
//            for (MultiClientServer.ClientHandler client :
//                    clients) {
//                PrintWriter out = new PrintWriter(client.getClientSocket().getOutputStream(), true);
//
//
//                out.println("(" + message.get("dateTime") + ")" + "<" + user.getName() + ">: " + message.get("message"));
//            }
//            db(user.getId(), (String) message.get("dateTime"), (String) message.get("message"));
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
