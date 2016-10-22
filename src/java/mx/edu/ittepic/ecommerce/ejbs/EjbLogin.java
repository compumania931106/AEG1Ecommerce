/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.edu.ittepic.ecommerce.entities.Users;
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author VictorManuel
 */
@Stateless
public class EjbLogin {
    @PersistenceContext
    EntityManager entity;
    
    public String getUser(String username, String password){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            Users users;
            Query q = entity.createNamedQuery("Users.getUser").setParameter("username", username).setParameter("password", password);
        
            users = (Users) q.getSingleResult();
         
                m.setCode(200);
                m.setMsg("Tiene acceso al sistema");
                m.setDetail("OK");
         
        }catch(NoResultException e){
            m.setCode(401);
            m.setMsg("No tiene acceso al sistema");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
}
