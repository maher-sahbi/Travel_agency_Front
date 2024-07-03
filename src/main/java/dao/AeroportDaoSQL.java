package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Aeroport;

public class AeroportDaoSQL implements AeroportDao
{

    
    public AeroportDaoSQL()
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

    public List<Aeroport> findAll()
    {
       
        List<Aeroport> aeroports = new ArrayList<Aeroport>();
        try
        {
            

            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM aeroport");
            
            ResultSet tuple = ps.executeQuery();
            
            while (tuple.next())
            {
               
                Aeroport aeroport = new Aeroport(tuple.getInt("idAero"),
                        tuple.getString("nom"));
                
                aeroports.add(aeroport);
            } 
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return aeroports;
    }

    public Aeroport findById(Integer idAero)
    {
       
        Aeroport aeroport = null;
        try
        {

            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM aeroport where idAero=?");
            
            ps.setInt(1, idAero);

            
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                aeroport = new Aeroport(tuple.getInt("idAero"),
                        tuple.getString("nom"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return aeroport;
    }

    @Override
    public void create(Aeroport aeroport)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO aeroport (idAero, nom) VALUES(?,?)");

            requete.setInt(1, aeroport.getIdAer());
            requete.setString(2, aeroport.getNom());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Aeroport update(Aeroport aeroport)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE aeroport SET nom=? WHERE idAero = ?");

            ps.setString(1, aeroport.getNom());
            ps.setInt(2, aeroport.getIdAer());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return aeroport;
    }

    public void delete(Aeroport aeroport)
    {

        try
        {

            PreparedStatement ps = connexion
                    .prepareStatement("delete from aeroport where idAero = ?");
            ps.setLong(1, aeroport.getIdAer());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
