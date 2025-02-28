package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.EtatReservation;
import model.Reservation;

public class ReservationDaoSQL implements ReservationDao
{

    private Connection connexion;

    public ReservationDaoSQL()
    {
       
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

    public List<Reservation> findAll()
    {
       
        List<Reservation> reservations = new ArrayList<Reservation>();
        PassagerDaoSQL passagerDAO = new PassagerDaoSQL();
        ClientDaoSql clientDAO = new ClientDaoSql();
        VolDaoSql volDAO = new VolDaoSql();
        try
        {
            PreparedStatement ps = connexion
                    .prepareStatement("SELECT * FROM Reservation");
           
            ResultSet tuple = ps.executeQuery();
            
            while (tuple.next())
            {
                
                Reservation reservation = new Reservation(
                        tuple.getInt("idResa"));
               
                reservation.setDate(tuple.getDate("dateReservation"));
                reservation.setNumero(tuple.getInt("numero"));
                reservation.setEtat(
                        EtatReservation.valueOf(tuple.getString("etat")));
                reservation.setVol(volDAO.findById(tuple.getInt("idVol")));
                reservation.setPassager(
                        passagerDAO.findById(tuple.getInt("idPassager")));
                reservation.setClient(
                        clientDAO.findById(tuple.getInt("idClient")));
                
                reservations.add(reservation);
            } 

            volDAO.fermetureConnexion();
            clientDAO.fermetureConnexion();
            passagerDAO.fermetureConnexion();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
      
        return reservations;
    }

    public Reservation findById(Integer idRes)
    {
        
        Reservation reservation = null;
        PassagerDaoSQL passagerDAO = new PassagerDaoSQL();
        ClientDaoSql clientDAO = new ClientDaoSql();
        VolDaoSql volDAO = new VolDaoSql();

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT * FROM reservation where idResa=?");
            
            ps.setInt(1, idRes);

            
            ResultSet tuple = ps.executeQuery();

            if (tuple.next())
            {
                reservation = new Reservation(tuple.getInt("idResa"));
                reservation.setDate(tuple.getDate("dateReservation"));
                reservation.setNumero(tuple.getInt("numero"));
                reservation.setEtat(
                        EtatReservation.valueOf(tuple.getString("etat")));
                reservation.setVol(volDAO.findById(tuple.getInt("idVol")));
                reservation.setPassager(
                        passagerDAO.findById(tuple.getInt("idPassager")));
                reservation.setClient(
                        clientDAO.findById(tuple.getInt("idClient")));
                volDAO.fermetureConnexion();
                clientDAO.fermetureConnexion();
                passagerDAO.fermetureConnexion();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public void create(Reservation reservation)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "INSERT INTO reservation (idResa, dateReservation, numero, etat, idVol, idPassager, idClient) VALUES(?,?,?,?,?,?,?)");

            requete.setLong(1, reservation.getIdRes());
            requete.setDate(2,
                    new java.sql.Date(reservation.getDate().getTime()));
            requete.setInt(3, reservation.getNumero());
            requete.setString(4, reservation.getEtat().getLabel());
            requete.setLong(5, reservation.getVol().getIdVol());
            requete.setLong(6, reservation.getPassager().getIdPas());
            requete.setLong(7, reservation.getClient().getIdCli());
            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation update(Reservation reservation)
    {

        try
        {
            PreparedStatement requete = connexion.prepareStatement(
                    "UPDATE reservation SET dateReservation=?,numero=?,etat=?,idVol=?,idPassager=?,idClient=? WHERE idResa = ?");

            requete.setDate(1,
                    new java.sql.Date(reservation.getDate().getTime()));
            requete.setInt(2, reservation.getNumero());
            requete.setString(3, reservation.getEtat().getLabel());
            requete.setLong(4, reservation.getVol().getIdVol());
            requete.setLong(5, reservation.getPassager().getIdPas());
            requete.setLong(6, reservation.getClient().getIdCli());
            requete.setLong(7, reservation.getIdRes());

            requete.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return reservation;
    }

    @Override
    public void delete(Reservation reservation)
    {

        try
        {
            PreparedStatement ps = connexion.prepareStatement(
                    "delete from reservation where idResa = ?");
            ps.setLong(1, reservation.getIdRes());

            ps.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
