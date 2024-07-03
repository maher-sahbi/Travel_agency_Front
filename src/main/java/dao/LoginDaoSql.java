package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Login;

public class LoginDaoSql implements LoginDao
{

    private Connection connexion;

    public LoginDaoSql()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {

            e.printStackTrace();
        }
        
        try
        {
            connexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vol", "user", "password");
        }
        catch (SQLException e)
        {

            e.printStackTrace();
        }
    }

    public void fermetureConnexion()
    {
        try
        {
            connexion.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Login> findAll()
    {
        
        List<Login> ListLogin = new ArrayList<Login>();

        try
        {
           
            PreparedStatement requete = connexion
                    .prepareStatement("SELECT * FROM login");

            
            ResultSet tuple = requete.executeQuery();
          
            while (tuple.next())
            {
               
                Login objLogin = new Login(tuple.getInt("id"));

                objLogin.setLogin(tuple.getString("login"));
                objLogin.setMotDePasse(tuple.getString("motDePasse"));
                objLogin.setAdmin(tuple.getInt("admin"));

               
                ListLogin.add(objLogin);
            } 

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
      
        return ListLogin;
    }

    @Override
    public Login findById(Integer id)
    {
        
        Login objLogin = null;

        try
        {
           
            PreparedStatement requete = connexion
                    .prepareStatement("SELECT * FROM login WHERE id=?");
            
            requete.setInt(1, id);

            
            ResultSet tuple = requete.executeQuery();

            if (tuple.next())
            {
                objLogin = new Login(tuple.getInt("id"));
                objLogin.setLogin(tuple.getString("login"));
                objLogin.setMotDePasse(tuple.getString("motDePasse"));
                objLogin.setAdmin(tuple.getInt("admin"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return objLogin;
    }

    @Override
    public void create(Login login)
    {
        try
        {

            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO login (id, login, motDePasse, admin) VALUES(?,?,?,?)");

            requete.setLong(1, login.getIdLog());
            requete.setString(2, login.getLogin());
            requete.setString(3, login.getMotDePasse());
            requete.setLong(4, login.getAdmin());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Login update(Login login)
    {
        try
        {

            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE login SET login=?,motDePasse=?,admin=? WHERE id = ?");

            requete.setString(1, login.getLogin());
            requete.setString(2, login.getMotDePasse());
            requete.setLong(3, login.getAdmin());
            requete.setLong(4, login.getIdLog());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return login;
    }

    @Override
    public void delete(Login login)
    {
        try
        {

            PreparedStatement requete = connexion
                    .prepareStatement("delete from login where id = ?");
            requete.setLong(1, login.getIdLog());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
