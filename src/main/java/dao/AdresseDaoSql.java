package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Adresse;

public class AdresseDaoSql implements AdresseDao
{

    private Connection connexion;

    public AdresseDaoSql()
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
    public List<Adresse> findAll()
    {
        
        List<Adresse> ListAdresse = new ArrayList<Adresse>();

        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM adresse");

           
            ResultSet tuple = ps.executeQuery();
            
            while (tuple.next())
            {
                
                Adresse objAdresse = new Adresse(tuple.getInt("idAdd"));

                objAdresse.setAdresse(tuple.getString("adresse"));
                objAdresse.setCodePostal(tuple.getString("codePostal"));
                objAdresse.setVille(tuple.getString("ville"));
                objAdresse.setPays(tuple.getString("pays"));

                
                ListAdresse.add(objAdresse);
            } 

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return ListAdresse;
    }

    @Override
    public Adresse findById(Integer idAdd)
    {
        
        Adresse objAdresse = null;

        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM adresse WHERE idAdd=?");
           
            ps.setInt(1, idAdd);

            
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                objAdresse = new Adresse(tuple.getInt("idAdd"));
                objAdresse.setAdresse(tuple.getString("adresse"));
                objAdresse.setCodePostal(tuple.getString("codePostal"));
                objAdresse.setVille(tuple.getString("ville"));
                objAdresse.setPays(tuple.getString("pays"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return objAdresse;
    }

    public void create(Adresse adresse)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO adresse (idAdd, adresse, codePostal, ville, pays) VALUES(?,?,?,?,?)");

            requete.setLong(1, adresse.getIdAdd());
            requete.setString(2, adresse.getAdresse());
            requete.setString(3, adresse.getCodePostal());
            requete.setString(4, adresse.getVille());
            requete.setString(5, adresse.getPays());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Adresse update(Adresse adresse)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE adresse SET adresse=?,codePostal=?,ville=?,pays=? WHERE idAdd = ?");

            ps.setString(1, adresse.getAdresse());
            ps.setString(2, adresse.getCodePostal());
            ps.setString(3, adresse.getVille());
            ps.setString(4, adresse.getPays());
            ps.setLong(5, adresse.getIdAdd());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return adresse;
    }

    public void delete(Adresse adresse)
    {

        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("delete from adresse where idAdd = ?");
            ps.setLong(1, adresse.getIdAdd());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
