package Service;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

import java.util.List;

public class AccountService {
    public AccountDAO AccountDAO;

    /**
     * No-args constructor for AccountService which creates a AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        AccountDAO = new AccountDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param AccountDAO
     */
    public AccountService(AccountDAO AccountDAO){
        this.AccountDAO = AccountDAO;
    }

    /**
     * TODO: Use the AccountDAO to persist a Account to the database.
     * An ID will be provided in Account. Method should check if the Account ID already exists before it attempts to
     * persist it.
     * @param Account a Account object.
     * @return Account if it was successfully persisted, null if it was not successfully persisted (eg if the Account primary
     * key was already in use.)
     */
    public Account registerAccount(Account account) {
        return AccountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account) {
        if(account.getPassword() == AccountDAO.getAccountByUsername(account.getUsername()).getPassword()){
            return AccountDAO.getAccountByUsername(account.getUsername());
        }
        return null;
    }

    public Account getAccountById(int id) {
        return AccountDAO.getAccountById(id);
    }

    public List<Message> getAccountMessages(Account account) {
        return AccountDAO.getAccountMessages(account);
    }
}
