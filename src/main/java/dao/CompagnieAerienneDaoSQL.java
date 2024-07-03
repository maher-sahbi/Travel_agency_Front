package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CompagnieAerienne;

public class CompagnieAerienneDaoSQL implements CompagnieAerienneDao
{

    public CompagnieAerienneDaoSQL()
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
                    "jdbc:mysql://localhost:3306/vol", "user", "");
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

    public List<CompagnieAerienne> findAll()
    {
        
        List<CompagnieAerienne> compagniesAeriennes = new ArrayList<CompagnieAerienne>();
       
        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM compagnie_aerienne");
            
            ResultSet tuple = ps.executeQuery();
            
            while (tuple.next())
            {
              
                CompagnieAerienne compagnieAerienne = new CompagnieAerienne(
                        tuple.getInt("id"), tuple.getString("nom"));
                
                compagniesAeriennes.add(compagnieAerienne);
            } 

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return compagniesAeriennes;
    }

    public CompagnieAerienne findById(Integer id)
    {
        
        CompagnieAerienne compagnieAerienne = null;

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM compagnie_aerienne where id=?");
           
            ps.setInt(1, id);

           
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                compagnieAerienne = new CompagnieAerienne(tuple.getInt("id"),
                        tuple.getString("nom"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return compagnieAerienne;
    }

    @Override
    public void create(CompagnieAerienne compagnieAerienne)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO compagnie_aerienne (id, nom) VALUES(?,?)");

            requete.setInt(1, compagnieAerienne.getId());
            requete.setString(2, compagnieAerienne.getNom());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public CompagnieAerienne update(CompagnieAerienne compagnieAerienne)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE compagnie_aerienne SET nom=? WHERE id = ?");

            ps.setString(1, compagnieAerienne.getNom());
            ps.setInt(2, compagnieAerienne.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return compagnieAerienne;
    }

    public void delete(CompagnieAerienne compagnieAerienne)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "delete from compagnie_aerienne where id = ?");
            ps.setLong(1, compagnieAerienne.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
