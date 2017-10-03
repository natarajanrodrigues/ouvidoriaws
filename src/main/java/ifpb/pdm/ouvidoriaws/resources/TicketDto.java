/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.resources;

import ifpb.pdm.ouvidoriaws.entities.*;
import ifpb.pdm.ouvidoriaws.enums.StatusTicket;
import ifpb.pdm.ouvidoriaws.enums.TipoTicket;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author natarajan
 */

public class TicketDto implements Serializable {

    private String resume;
    private Long from;
    private int tipo;

    public TicketDto() {
    }
    
    public TicketDto(String resume, Long from, int tipo) {
        this.resume = resume;
        this.from = from;
        this.tipo = tipo;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    
    
}
