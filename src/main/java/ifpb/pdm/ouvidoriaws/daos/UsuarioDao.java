/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pdm.ouvidoriaws.daos;

import ifpb.pdm.ouvidoriaws.entities.Usuario;
import ifpb.pdm.ouvidoriaws.enums.TipoUsuario;
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
public class UsuarioDao {
    @PersistenceContext
    private EntityManager em;
    
    public Usuario salvar(Usuario usuario) {
        em.persist(usuario);
        return usuario;
    }
    
    public Usuario atualizar(Usuario usuario) {
        return em.merge(usuario);
    }
    
    public boolean exists(Usuario usuario) {
        TypedQuery<Usuario> query = em
                .createQuery("SELECT u"
                        + " FROM Usuario u"
                        + " WHERE u.email = :userEmail"
                        , Usuario.class)
                .setParameter("userEmail", usuario.getEmail());

        List<Usuario> result = query.getResultList();
        
        if (result.isEmpty()) 
            return false;
        return true;
        
    }
    
    public Usuario getAuditor() {
        TypedQuery<Usuario> query = em
                .createQuery("SELECT u"
                        + " FROM Usuario u"
                        + " WHERE u.tipoUsario = :tipo"
                        , Usuario.class)
                .setParameter("tipo", TipoUsuario.AUDITOR.getId());

        List<Usuario> result = query.getResultList();
        
        if (result.isEmpty()) 
            return null;
        return result.get(0);
        
    }

    public Usuario getById(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public Usuario getByEmail(String email) {
        TypedQuery<Usuario> query = em
                .createQuery("SELECT u"
                        + " FROM Usuario u"
                        + " WHERE u.email = :email"
                        , Usuario.class)
                .setParameter("email", email);

        List<Usuario> result = query.getResultList();
        
        if (result.isEmpty()) 
            return null;
        return result.get(0);
        
    }
    
}
