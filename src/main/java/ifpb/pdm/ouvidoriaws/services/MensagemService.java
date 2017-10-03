/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.services;

import ifpb.pdm.ouvidoriaws.daos.MensagemDao;
import ifpb.pdm.ouvidoriaws.daos.TicketDao;
import ifpb.pdm.ouvidoriaws.daos.UsuarioDao;
import ifpb.pdm.ouvidoriaws.entities.Mensagem;
import ifpb.pdm.ouvidoriaws.entities.Ticket;
import ifpb.pdm.ouvidoriaws.entities.Usuario;
import ifpb.pdm.ouvidoriaws.enums.StatusTicket;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author natarajan
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless
public class MensagemService {

    @EJB
    private TicketDao ticketDao;
    @EJB
    private MensagemDao mensagemDao;
    @EJB
    private UsuarioDao usuarioDao;
    
    
    /*
    pegar uma mensagem pelo di
    */
    public Mensagem getById(Long id) {
        return mensagemDao.getById(id);
    }

    /*
    salvar uma mensagem de cliente
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Mensagem salvarMensagemCliente(String msgText, Long idUser, Long idTicket) {
        Mensagem mensagemSalva = null;
        try {
            Usuario cliente = usuarioDao.getById(idUser);
            Ticket ticket = ticketDao.getById(idTicket);
            if (ticket.getStatus() == StatusTicket.REPLICATED) {
                Mensagem mensagem = new Mensagem();
                mensagem.setFrom(cliente);
                mensagem.setTicket(ticket);
                mensagem.setText(msgText);
                mensagemSalva = mensagemDao.salvar(mensagem);
                ticket.setUpdatedIn(LocalDateTime.now());
                ticketDao.atualizar(ticket);
                // aquimandar atualizar o pubnub - cliente
            } else {
                throw new IllegalArgumentException("O tickt não está replicado.");
            }
            
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
        return mensagemSalva;
    }
    
    /*
    salvar uma mensagem de auditor
    */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Mensagem salvarMensagemAuditor(String msgText, Long idAuditor, Long idTicket) {
        Mensagem mensagemSalva = null;
        try {
            Usuario cliente = usuarioDao.getById(idAuditor);
            Ticket ticket = ticketDao.getById(idTicket);
            if (ticket.getStatus() == StatusTicket.OPEN) {
                Mensagem mensagem = new Mensagem();
                mensagem.setFrom(cliente);
                mensagem.setTicket(ticket);
                mensagem.setText(msgText);
                
                mensagemSalva = mensagemDao.salvar(mensagem);
                ticket.setUpdatedIn(LocalDateTime.now());
                ticketDao.atualizar(ticket);
                // aquimandar atualizar o pubnub - auditor
            } else {
                throw new IllegalArgumentException("O tickt não está aberto.");
            }
            
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new EJBException(ex);
        }
        return mensagemSalva;
    }
    
    public Mensagem getLastMensagem(Long idTicket) {
        return mensagemDao.getLastMensagemByTicket(idTicket);
    }
    
}
