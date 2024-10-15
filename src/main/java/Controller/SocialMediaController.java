package Controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/* endpoints
GET localhost:8080/accounts/{account_id}/messages...        DAO and Service implemented
PATCH localhost:8080/messages/{message_id}...               DAO and Service implemented
DELETE localhost:8080/messages/{message_id}...              DAO and Service implemented
GET localhost:8080/messages/{message_id}...                 DAO and Service implemented                
GET localhost:8080/messages...                              DAO and Service implemented
POST localhost:8080/messages...                             DAO and Service implemented
POST localhost:8080/login...                                DAO and Service implemented
POST localhost:8080/register...                             DAO and Service implemented
*/

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages/{message_id}", this::getMessageHandler); //done
        app.patch("/messages/{message_id}", this::editMessageHandler); //done
        app.delete("/messages/{message_id}", this::deleteMessageHandler); //done
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.post("/login", this::loginHandler); //done
        app.post("/register", this::registerHandler); //done
        app.post("/messages", this::postMessageHandler); //done
        app.get("/messages", this::getAllMessagesHandler); //done
        //app.start(8080);
        return app;
    }

    private void getMessageHandler(Context ctx) {
        int m_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message fin = messageService.getMessage(m_id);
        ctx.json(fin);
    }
    private void editMessageHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int m_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        String message = mapper.readValue(ctx.body(), String.class);
        Message editedMessage = messageService.editMessage(m_id, message);

        if(editedMessage!=null){
            ctx.json(mapper.writeValueAsString(editedMessage));
        }else{
            ctx.status(400);
        }
    }
    private void deleteMessageHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int m_id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message deletedMessage = messageService.deleteMessage(m_id);
        ctx.json(mapper.writeValueAsString(deletedMessage));
        
    }
    private void loginHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account targetAccount = accountService.loginAccount(account);
        if(targetAccount!=null){
            ctx.json(mapper.writeValueAsString(targetAccount));
        }else{
            ctx.status(401);
        }
    }
    private void registerHandler(Context ctx)throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account targetAccount = accountService.registerAccount(account);
        if(targetAccount!=null){
            ctx.json(mapper.writeValueAsString(targetAccount));
        }else{
            ctx.status(400);
        }
    }
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getMessages();
        ctx.json(messages);
    }
    private void getAccountMessagesHandler(Context ctx) throws JsonProcessingException {
        int accNum = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        Account targetAccount = accountService.getAccountById(accNum);
        List<Message> accMessages = accountService.getAccountMessages(targetAccount);
        ctx.json(accMessages);
    }


}