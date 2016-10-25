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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.ecommerce.entities.Category;
import mx.edu.ittepic.ecommerce.entities.Role;
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author Oscar_Arenas
 */

@Stateless
public class EjbCategory {
    
    @PersistenceContext
    EntityManager entity;
  
    
    public String getCategory(){
     
        List <Category> listCategory;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNamedQuery("Category.findAll");
            listCategory = q.getResultList();

            m.setCode(200);
            m.setMsg(gson.toJson(listCategory));
            m.setDetail("OK");
        } catch (IllegalArgumentException e) {
            m.setCode(50);
            m.setMsg("Error al consultar los registros");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String newCategory(String categoryname){
    
     Message m = new Message();
        Category r = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            r.setCategoryname(categoryname);
            entity.persist(r);
            entity.flush();

            m.setCode(200);
            m.setMsg("El rol se registro correctamente");
            m.setDetail(r.getCategoryid().toString());

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

    public String deleteCategory(String categoryid){
    Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
                Query q = entity.createNamedQuery("Category.deleteCategory").
                setParameter("categoryid", Integer.parseInt(categoryid));
                
                
            if(q.executeUpdate() == 1){
                m.setCode(200);
                m.setMsg("Se elimino correctamente.");
                m.setDetail("OK");
            }else{
                m.setCode(404);
                m.setMsg("No se realizo la eliminacion");
                m.setDetail("");
            }
        
        
        }catch(IllegalStateException e){
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        }catch(TransactionRequiredException e){
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        }catch(QueryTimeoutException e){
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        }catch(PersistenceException e){
            m.setCode(404);
            m.setMsg("No se realizo la actualizacion");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);

        
    }

 public String updateCategory(String categoryid, String categoryname){
 Message m = new Message();
        Category r = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            Query q = entity.createNamedQuery("Category.updateCategory").
                    setParameter("categoryname", categoryname).
                    setParameter("categoryid", Integer.parseInt(categoryid));
            if (q.executeUpdate() == 1) {
                m.setCode(200);
                m.setMsg("Se actualizo correctamente.");
                m.setDetail("OK");
            } else {
                m.setCode(404);
                m.setMsg("No se realizo la actualizacion");
                m.setDetail("");
            }

        } catch (TransactionRequiredException e) {

        }
        return gson.toJson(m);
 
 
 }
 
 public String getCategoryByID(String categoryid){
     Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            Category category;
            Query q = entity.createNamedQuery("Category.findByCategoryid").setParameter("categoryid", Integer.parseInt(categoryid));

            category = (Category) q.getSingleResult();

            

            m.setCode(200);
            m.setMsg(gson.toJson(category));
            m.setDetail("OK");
        } catch (NoResultException e) {
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
 }
 
    
}
