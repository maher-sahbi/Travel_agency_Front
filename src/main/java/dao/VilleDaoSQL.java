package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ville;

public class VilleDaoSQL implements VilleDao
{

    public VilleDaoSQL()
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

    private Connection connexion;

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

    public List<Ville> findAll()
    {
       
        List<Ville> villes = new ArrayList<Ville>();
        
        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM ville");
            
            ResultSet tuple = ps.executeQuery();
          
            while (tuple.next())
            {
                
                Ville ville = new Ville(tuple.getInt("id"),
                        tuple.getString("nom"));
                
                villes.add(ville);
            } 

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return villes;
    }

    public Ville findById(Integer id)
    {
       
        Ville ville = null;

        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM ville where id=?");
           
            ps.setInt(1, id);

       
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                ville = new Ville(tuple.getInt("id"), tuple.getString("nom"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ville;
    }

    public void create(Ville ville)
    {

        try
        {

            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO ville (id, nom) VALUES(?,?)");

            requete.setLong(1, ville.getIdVil());
            requete.setString(2, ville.getNom());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Ville update(Ville ville)
    {

        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("UPDATE ville SET nom=? WHERE id = ?");

            ps.setString(1, ville.getNom());
            ps.setInt(2, ville.getIdVil());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ville;
    }

    public void delete(Ville ville)
    {

        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("delete from ville where id = ?");
            ps.setLong(1, ville.getIdVil());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
