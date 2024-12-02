package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {


    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Write SQL logic here
            String sql = "select * from account where account.account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            //Write SQL logic here
            String sql = "select * from account where account.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAccountMessages(Account account){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> Messages = new ArrayList<Message>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccount_id());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message Message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                Messages.add(Message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Messages;
    }

    /**
     * TODO: insert an Account into the Account table.
     * The Account_id should be automatically generated by the sql database if it is not provided because it was
     * set to auto_increment.
     */
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {

            if(getAccountByUsername(account.getUsername()) != null ||
            account.getUsername().equals("")|| account.getPassword().length() < 4){
                return null;
            }

            String sql = "insert into account (username, password) values (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //remember this

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_Account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_Account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

