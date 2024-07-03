package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.VilleAeroport;

public class VilleAeroportDaoSql implements VilleAeroportDao
{

    public VilleAeroportDaoSql()
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

    @Override
    public List<VilleAeroport> findAll()
    {
        List<VilleAeroport> villeAeroports = new ArrayList<VilleAeroport>();
        AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
        VilleDaoSQL villeDAO = new VilleDaoSQL();
        try
        {
            

            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM ville_aeroport");
           
            ResultSet tuple = ps.executeQuery();
           
            while (tuple.next())
            {
             
                VilleAeroport villeAeroport = new VilleAeroport(
                        tuple.getInt("id"));
                villeAeroport
                        .setVille(villeDAO.findById(tuple.getInt("idVille")));
                villeAeroport.setAeroport(
                        aeroportDAO.findById(tuple.getInt("idAeroport")));
               
                villeAeroports.add(villeAeroport);
            }

          
            villeDAO.fermetureConnexion();
            aeroportDAO.fermetureConnexion();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
      
        return villeAeroports;
    }

    @Override
    public VilleAeroport findById(Integer id)
    {
    
        VilleAeroport villeAeroport = null;
        AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
        VilleDaoSQL villeDAO = new VilleDaoSQL();

        try
        {

            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM ville_aeroport where id=?");
           
            ps.setInt(1, id);

            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                villeAeroport = new VilleAeroport(tuple.getInt("id"));
                villeAeroport
                        .setVille(villeDAO.findById(tuple.getInt("idVille")));
                villeDAO.fermetureConnexion();
                villeAeroport.setAeroport(
                        aeroportDAO.findById(tuple.getInt("idAeroport")));
                aeroportDAO.fermetureConnexion();

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return villeAeroport;
    }

    @Override
    public void create(VilleAeroport villeAeroport)
    {

        try
        {
         
            PreparedStatement requete;
            requete = connexion.prepareStatement(
                    "insert into ville_aeroport (id,idVille,idAeroport) VALUES(?,?,?)");
         
            requete.setInt(1, villeAeroport.getId());
           
            requete.setInt(2, villeAeroport.getVille().getIdVil());
            requete.setInt(3, villeAeroport.getAeroport().getIdAer());

        
            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public VilleAeroport update(VilleAeroport villeAeroport)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE ville_aeroport SET idVille=?, idAeroport=? WHERE id = ?");

            ps.setInt(1, villeAeroport.getVille().getIdVil());
            ps.setInt(2, villeAeroport.getAeroport().getIdAer());
            ps.setInt(3, villeAeroport.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return villeAeroport;
    }

    @Override
    public void delete(VilleAeroport villeAeroport)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "DELETE FROM ville_aeroport WHERE id = ?");
            ps.setLong(1, villeAeroport.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
