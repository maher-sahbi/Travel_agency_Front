package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Passager;

public class PassagerDaoSQL implements PassagerDao
{

    private Connection connexion;

    public PassagerDaoSQL()
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

    public List<Passager> findAll()
    {
      
        List<Passager> passagers = new ArrayList<Passager>();
        AdresseDaoSql adresseDAO = new AdresseDaoSql();
        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM Passager");
            
            ResultSet tuple = ps.executeQuery();

            while (tuple.next())
            {
                
                Passager passager = new Passager(tuple.getInt("idPassager"));
                
                passager.setNom(tuple.getString("nom"));
                passager.setPrenom(tuple.getString("prenom"));
                passager.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
               
                passagers.add(passager);
            } 
            adresseDAO.fermetureConnexion();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
       
        return passagers;
    }

    public Passager findById(Integer idPas)
    {
        
        Passager passager = null;
        AdresseDaoSql adresseDAO = new AdresseDaoSql();

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM passager where idPassager=?");
            
            ps.setInt(1, idPas);

            
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                passager = new Passager(tuple.getInt("idPassager"));
                passager.setNom(tuple.getString("nom"));
                passager.setPrenom(tuple.getString("prenom"));
                passager.setAdresse(adresseDAO.findById(tuple.getInt("idAdd")));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return passager;
    }

    public void create(Passager passager)
    {

        try
        {

            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO passager (idPassager, nom, prenom, IdAdd) VALUES(?,?,?,?)");

            requete.setLong(1, passager.getIdPas());
            requete.setString(2, passager.getNom());
            requete.setString(3, passager.getPrenom());
            requete.setLong(4, passager.getAdresse().getIdAdd());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Passager update(Passager passager)
    {

        try
        {

            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE passager SET nom=?,prenom=?,idAdd=? WHERE idPassager = ?");

            ps.setString(1, passager.getNom());
            ps.setString(2, passager.getPrenom());
            ps.setLong(3, passager.getAdresse().getIdAdd());
            ps.setLong(4, passager.getIdPas());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return passager;
    }

    public void delete(Passager passager)
    {

        try
        {

            PreparedStatement ps = connexion.prepareStatement(
                    "delete from passager where idPassager = ?");
            ps.setLong(1, passager.getIdPas());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
