package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
 * readable and maintainable in the long run!
 */
public class MessageService {
    public MessageDAO MessageDAO;
    public AccountDAO AccountDAO;

    
    public MessageService(){
        MessageDAO = new MessageDAO();
        AccountDAO = new AccountDAO();
    }
    
   // public MessageService(MessageDAO MessageDAO){
  //      this.MessageDAO = MessageDAO;
  //  }
    /**
     * TODO: Use the MessageDAO to retrieve all Messages.
     * @return all Messages.
     */
    public List<Message> getMessages() {
        return MessageDAO.getMessages();
    }
    /**
     * TODO: Use the MessageDAO to persist a Message to the database.
     * An ID will be provided in Message. Method should check if the Message ID already exists before it attempts to
     * persist it.
     * @param Message a Message object.
     * @return Message if it was successfully persisted, null if it was not successfully persisted (eg if the Message primary
     * key was already in use.)
     */
    public Message addMessage(Message Message) {
        if(Message.getMessage_text() == "" || Message.getMessage_text().length() > 255 ||
        AccountDAO.getAccountById(Message.getPosted_by()) == null){
            return null;
        }
        return MessageDAO.insertMessage(Message);
    }
    /**
     * TODO: Use the MessageDAO to delete a Message
     * @return all account Messages (MessageCount over zero)
     */
    public Message deleteMessage(int id) {
        return MessageDAO.deleteMessageFromId(id);
    }

    public Message getMessage(int id) {
        return MessageDAO.getMessageFromId(id);
    }

    public Message editMessage(int id, String newMessage) {
        return MessageDAO.editMessageFromId(id, newMessage);
    }

}
