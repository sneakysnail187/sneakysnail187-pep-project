package Controller;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;

/* endpoints
GET localhost:8080/accounts/{account_id}/messages...        DAO implemented
PATCH localhost:8080/messages/{message_id}...               DAO implemented
DELETE localhost:8080/messages/{message_id}...              DAO implemented 
GET localhost:8080/messages/{message_id}...                 DAO implemented                    
GET localhost:8080/messages...                              DAO implemented
POST localhost:8080/messages...                             DAO implemented
POST localhost:8080/login...                                DAO implemented
POST localhost:8080/register...                             DAO implemented

*/





/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/books", this::getAllBooksHandler);
        app.post("/books", this::postBookHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.post("/authors", this::postAuthorHandler);
        app.get("/books/available", this::getAvailableBooksHandler);
        //app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void getAccountMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int accNum = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        //Author addedAuthor = authorService.addAuthor(author);
       // if(addedAuthor!=null){
       //     ctx.json(mapper.writeValueAsString(addedAuthor));
      //  }else{
       //     ctx.status(400);
      //  }
    }


}