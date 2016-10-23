/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.ecommerce.entities.Company;
import mx.edu.ittepic.ecommerce.entities.Role;
import mx.edu.ittepic.ecommerce.entities.Users;
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author victor
 */
@Stateless
public class EjbUsers {

    @PersistenceContext
    EntityManager entity;

    public String newUser(String username, String password, String phone,
            String neigborhood, String zipcode, String city, String country,
            String state, String region, String street, String email, String streetnumber,
            String photo, String cellphone, String companyid, String roleid, String gender) {

        Message m = new Message();
        Users u = new Users();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Company com = entity.find(Company.class, Integer.parseInt(companyid));
        Role rol = entity.find(Role.class, Integer.parseInt(roleid));

        try {
            u.setUsername(username);
            u.setPassword(password);
            u.setPhone(phone);
            u.setNeigborhood(neigborhood);
            u.setZipcode(zipcode);
            u.setCity(city);
            u.setCountry(country);
            u.setState(state);
            u.setRegion(region);
            u.setStreet(street);
            u.setEmail(email);
            u.setStreetnumber(streetnumber);
            u.setPhoto(photo);
            u.setCellphone(cellphone);
            u.setCompanyid(com);
            u.setRoleid(rol);
            u.setGender(gender.charAt(0));

            entity.persist(u);
            entity.flush();

            m.setCode(200);
            m.setMsg("El usuario se ha registro correctamente");
            m.setDetail(u.getRoleid().toString());

        } catch (IllegalArgumentException e) {
            m.setCode(503);
            m.setMsg("Error en la base de datos");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(503);
            m.setMsg("Error en la transaccion con la base de datos");
            m.setDetail(e.toString());
        } catch (EntityExistsException e) {
            m.setCode(400);
            m.setMsg("Hubo problemas con la base de datos");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String updateUser(String userid, String username, String password, String phone,
            String neigborhood, String zipcode, String city, String country,
            String state, String region, String street, String email, String streetnumber,
            String photo, String cellphone, String companyid, String roleid, String gender){
    
        Message m = new Message();
        Users r = new Users();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        Company com = entity.find(Company.class, Integer.parseInt(companyid));
        Role rol = entity.find(Role.class, Integer.parseInt(roleid));
        
        try {
            Query q = entity.createNamedQuery("Users.updateUser").
                    setParameter("username", username).
                    setParameter("password", password).
                    setParameter("phone", phone).
                    setParameter("neigborhood", neigborhood).
                    setParameter("zipcode", zipcode).
                    setParameter("city", city).
                    setParameter("country", country).
                    setParameter("state", state).
                    setParameter("region", region).
                    setParameter("street", street).
                    setParameter("email", email).
                    setParameter("streetnumber", streetnumber).
                    setParameter("photo", photo).
                    setParameter("cellphone", cellphone).
                    setParameter("companyid", com).
                    setParameter("roleid", rol).
                    setParameter("gender", gender.charAt(0)).
                    setParameter("userid", Integer.parseInt(userid));

            if (q.executeUpdate() == 1) {
                m.setCode(200);
                m.setMsg("Se actualizo correctamente.");
                m.setDetail("OK");
            } else {
                m.setCode(404);
                m.setMsg("No se realizo la actualizacion");
                m.setDetail("");
            }

        } catch (IllegalStateException e) {
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        } catch (QueryTimeoutException e) {
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String GetUsers(){
        List<Users> listUsers;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNamedQuery("Users.findAll");
            listUsers = q.getResultList();

            m.setCode(200);
            m.setMsg(gson.toJson(listUsers));
            m.setDetail("OK");
        } catch (IllegalArgumentException e) {
            m.setCode(501);
            m.setMsg("Error al consultar los registros");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getUserByID(String userid) {
        Message m = new Message();
        Users users = new Users();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {

            Query q = entity.createNamedQuery("Users.findByUserid").setParameter("userid", Integer.parseInt(userid));
            users = (Users) q.getSingleResult();

            m.setCode(200);
            m.setMsg(gson.toJson(users));
            m.setDetail("OK");
        } catch (Exception e) {
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getUsersByName(String username) {
        List<Users> listUserts;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Query q = entity.createNativeQuery("Select * from users where username like '" + username + "%'", Users.class);

        listUserts = q.getResultList();

        m.setCode(200);
        m.setMsg(gson.toJson(listUserts));
        m.setDetail("OK");

        return gson.toJson(m);
    }
    
    public String deleteUser(String userid){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            Query q = entity.createNamedQuery("Users.deleteUser").
                    setParameter("userid", Integer.parseInt(userid));

            if (q.executeUpdate() == 1) {
                m.setCode(200);
                m.setMsg("Se elimino correctamente.");
                m.setDetail("OK");
            } else {
                m.setCode(404);
                m.setMsg("No se realizo la eliminacion");
                m.setDetail("");
            }

        } catch (IllegalStateException e) {
            m.setCode(404);
            m.setMsg("No se realizo la eliminacion");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(404);
            m.setMsg("No se realizo la eliminacion");
            m.setDetail(e.toString());
        } catch (QueryTimeoutException e) {
            m.setCode(404);
            m.setMsg("No se realizo la eliminacion");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(404);
            m.setMsg("No se realizo la eliminacion");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
}
