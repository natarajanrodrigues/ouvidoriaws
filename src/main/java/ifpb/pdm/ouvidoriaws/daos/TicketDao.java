/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.daos;

import ifpb.pdm.ouvidoriaws.entities.Ticket;
import ifpb.pdm.ouvidoriaws.enums.StatusTicket;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author natarajan
 */

@DataSourceDefinition(
        name = "java:app/ouvidoria-data-source",
        className = "org.postgresql.Driver",
//        url = "jdbc:postgresql://banco-cliente:5432/cliente",        
        url = "jdbc:postgresql://localhost:5432/ouvidoria",
        user = "postgres",
        password = "12345"
)

@Stateless
public class TicketDao {
    @PersistenceContext
    private EntityManager em;
    
    public Ticket salvar(Ticket ticket) {
        em.persist(ticket);
        return ticket;
    }
    
    public Ticket atualizar(Ticket ticket) {
        return em.merge(ticket);
    }
    

    public Ticket getById(Long id) {
        return em.find(Ticket.class, id);
    }
    
    public List<Ticket> getAllOpen() {
        TypedQuery<Ticket> query = em
                .createQuery("SELECT t"
                        + " FROM Ticket t"
                        + " WHERE t.status = :ticketStatus"
                        , Ticket.class)
                .setParameter("ticketStatus", StatusTicket.OPEN.getId());

        return query.getResultList();        
    }
    
    public List<Ticket> getInactive() {
        
        LocalDateTime dateTime10daysBefore = LocalDateTime.now().minusDays(10);                            
        
        TypedQuery<Ticket> query = em
                .createQuery("SELECT t"
                        + " FROM Ticket t"
//                        + " WHERE t.status = :ticketStatus and date (t.updatedin) < now() - INTERVAL '10 DAY'"
                        + " WHERE t.status = :ticketStatus and t.updatedIn < :daysBefore"
                        , Ticket.class)
                .setParameter("ticketStatus", StatusTicket.OPEN.getId())
                .setParameter("daysBefore", dateTime10daysBefore);

        return query.getResultList();        
    }

    public List<Ticket> getOpensByUser(Long userId) {
        TypedQuery<Ticket> query = em
                .createQuery("SELECT t"
                        + " FROM Ticket t"
                        + " WHERE t.status = :ticketStatus AND t.from.id = :idUser"
                        , Ticket.class)
                .setParameter("ticketStatus", StatusTicket.OPEN.getId())
                .setParameter("idUser", userId);

        return query.getResultList();        
    }
    
}
