package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Vol;

public class VolDaoSql implements VolDao
{

    private Connection connexion;

    public VolDaoSql()
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

    public List<Vol> findAll()
    {
       
        List<Vol> vols = new ArrayList<Vol>();
       
        AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();
       
        try
        {

            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM vol");
        
            ResultSet tuple = ps.executeQuery();
            
            while (tuple.next())
            {
               
                Vol vol = new Vol(tuple.getInt("idVol"));
                vol.setDateArrivee(tuple.getDate("dateArrivee"));
                vol.setDateDepart(tuple.getDate("dateDepart"));
                vol.setHeureArrivee(tuple.getTime("heureArrivee"));
                vol.setHeureDepart(tuple.getTime("heureDepart"));
                vol.setAeroportArrivee(aeroportDAO
                        .findById(tuple.getInt("idAeroportArrivee")));
                vol.setAeroportDepart(
                        aeroportDAO.findById(tuple.getInt("idAeroportDepart")));
             
                vols.add(vol);
            } 

          
            aeroportDAO.fermetureConnexion();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

       
        return vols;
    }

    public Vol findById(Integer idVol)
    {
        
        Vol vol = null;
       
        AeroportDaoSQL aeroportDAO = new AeroportDaoSQL();

        try
        {
            PreparedStatement requete = connexion
                    .prepareStatement("SELECT * FROM vol where idVol=?");
           
            requete.setInt(1, idVol);

          
            ResultSet tuple = requete.executeQuery();

            if (tuple.next())
            {
                vol = new Vol(tuple.getInt("idVol"));
                vol.setDateArrivee(tuple.getDate("dateArrivee"));
                vol.setDateDepart(tuple.getDate("dateDepart"));
                vol.setHeureArrivee(tuple.getTime("heureArrivee"));
                vol.setHeureDepart(tuple.getTime("heureDepart"));
                vol.setAeroportArrivee(aeroportDAO
                        .findById(tuple.getInt("idAeroportArrivee")));
                vol.setAeroportDepart(
                        aeroportDAO.findById(tuple.getInt("idAeroportDepart")));
                aeroportDAO.fermetureConnexion();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return vol;
    }

    @Override
    public void create(Vol vol)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO vol (idVol, dateDepart, dateArrivee, heureDepart, heureArrivee, idAeroportDepart, idAeroportArrivee) VALUES(?,?,?,?,?,?,?)");

            requete.setLong(1, vol.getIdVol());
            requete.setDate(2,
                    new java.sql.Date(vol.getDateDepart().getTime()));
            requete.setDate(3,
                    new java.sql.Date(vol.getDateArrivee().getTime()));
            requete.setTime(4,
                    new java.sql.Time(vol.getHeureDepart().getTime()));
            requete.setTime(5,
                    new java.sql.Time(vol.getHeureArrivee().getTime()));
            requete.setLong(6, vol.getAeroportDepart().getIdAer());
            requete.setLong(7, vol.getAeroportArrivee().getIdAer());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Vol update(Vol vol)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "UPDATE vol SET dateDepart=?,dateArrivee=?,heureDepart=?,heureArrivee=?,idAeroportDepart=?,idAeroportArrivee=? WHERE idVol = ?");

            ps.setDate(1, new java.sql.Date(vol.getDateDepart().getTime()));
            ps.setDate(2, new java.sql.Date(vol.getDateArrivee().getTime()));
            ps.setTime(3, new java.sql.Time(vol.getHeureDepart().getTime()));
            ps.setTime(4, new java.sql.Time(vol.getHeureArrivee().getTime()));
            ps.setLong(5, vol.getAeroportDepart().getIdAer());
            ps.setLong(6, vol.getAeroportArrivee().getIdAer());
            ps.setLong(7, vol.getIdVol());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return vol;
    }

    public void delete(Vol vol)
    {

        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("delete from vol where idVol = ?");
            ps.setLong(1, vol.getIdVol());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
