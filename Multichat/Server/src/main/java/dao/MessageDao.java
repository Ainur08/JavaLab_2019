package dao;

import model.Message;
import java.util.LinkedList;

public interface MessageDao extends CrudDao<Message> {
    LinkedList<Message> pagination(int limit, int offset);
}
