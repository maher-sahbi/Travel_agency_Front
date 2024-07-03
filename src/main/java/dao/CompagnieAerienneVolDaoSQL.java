package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CompagnieAerienneVol;

public class CompagnieAerienneVolDaoSQL implements CompagnieAerienneVolDao
{
    public CompagnieAerienneVolDaoSQL()
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

    public List<CompagnieAerienneVol> findAll()
    {
       
        List<CompagnieAerienneVol> compagnieaeriennevols = new ArrayList<CompagnieAerienneVol>();
        VolDaoSql volDAO = new VolDaoSql();
        CompagnieAerienneDaoSQL compagnieDAO = new CompagnieAerienneDaoSQL();
       
        try
        {
            
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM compagnie_aerienne_vol");
            
            ResultSet tuple = ps.executeQuery();
           
            while (tuple.next())
            {
               
                CompagnieAerienneVol compagnieaeriennevol = new CompagnieAerienneVol(
                        tuple.getString("numero"), tuple.getShort("ouvert"));
                compagnieaeriennevol.setId(tuple.getInt("id"));
                compagnieaeriennevol.setCompagnieAerienne(
                        compagnieDAO.findById(tuple.getInt("idCompagnie")));
                compagnieaeriennevol
                        .setVol(volDAO.findById(tuple.getInt("idVol")));
                
                compagnieaeriennevols.add(compagnieaeriennevol);
            } 

            volDAO.fermetureConnexion();
            compagnieDAO.fermetureConnexion();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
       
        return compagnieaeriennevols;
    }

    public CompagnieAerienneVol findById(Integer id)
    {
        CompagnieAerienneVol compagnieAerienneVol = null;
        VolDaoSql volDAO = new VolDaoSql();
        CompagnieAerienneDaoSQL compagnieDAO = new CompagnieAerienneDaoSQL();
        try
        {

            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM compagnie_aerienne_vol where id=?");
            
            ps.setInt(1, id);

           
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                compagnieAerienneVol = new CompagnieAerienneVol(
                        tuple.getString("numero"), tuple.getShort("ouvert"));
                compagnieAerienneVol.setId(tuple.getInt("id"));
                compagnieAerienneVol
                        .setVol(volDAO.findById(tuple.getInt("idVol")));
                compagnieAerienneVol.setCompagnieAerienne(
                        compagnieDAO.findById(tuple.getInt("idCompagnie")));
                volDAO.fermetureConnexion();
                compagnieDAO.fermetureConnexion();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return compagnieAerienneVol;
    }

    @Override
    public void create(CompagnieAerienneVol compagnieAerienneVol)
    {
        try
        {
            PreparedStatement requete;
            requete = connexion.prepareStatement(
                    "INSERT INTO compagnie_aerienne_vol (id, numero, idCompagnie, idVol, ouvert) VALUES(?,?,?,?,?)");
            
            requete.setInt(1, compagnieAerienneVol.getId());
            requete.setString(2, compagnieAerienneVol.getNumero());
            requete.setLong(3,
                    compagnieAerienneVol.getCompagnieAerienne().getId());
            requete.setInt(4, compagnieAerienneVol.getVol().getIdVol());
            requete.setShort(5, compagnieAerienneVol.getOuvert());

          
            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public CompagnieAerienneVol update(
            CompagnieAerienneVol compagnieAerienneVol)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "update compagnie_aerienne_vol set numero=?,idCompagnie=?,idVol=?,ouvert=? where id = ?");

            ps.setString(1, compagnieAerienneVol.getNumero());
            ps.setInt(2, compagnieAerienneVol.getCompagnieAerienne().getId());
            ps.setInt(3, compagnieAerienneVol.getVol().getIdVol());
            ps.setShort(4, compagnieAerienneVol.getOuvert());
            ps.setInt(5, compagnieAerienneVol.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return compagnieAerienneVol;
    }

    @Override
    public void delete(CompagnieAerienneVol compagnieAerienneVol)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "delete from compagnie_aerienne_vol where id = ?");
            ps.setLong(1, compagnieAerienneVol.getId());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
