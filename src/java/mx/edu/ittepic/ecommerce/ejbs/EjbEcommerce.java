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
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.ecommerce.entities.Category;
import mx.edu.ittepic.ecommerce.entities.Product;
import mx.edu.ittepic.ecommerce.entities.Role;
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author VictorManuel
 */
@Stateless
public class EjbEcommerce {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext
    EntityManager entity;
    
    public String getRoles(){
        List<Role> listRoles;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        
        
        Query q = entity.createNamedQuery("Role.findAll");
        listRoles = q.getResultList();
        
        String roles = "[";
        for(Role listRole: listRoles){
            roles = roles + "{\"roleid\":"+listRole.getRoleid()+", \"rolename\":\""+listRole.getRolename() +"\"},";
        }
        
        roles = roles.substring(0, roles.length()-1);
        roles = roles + "]";
        
        m.setCode(200);
        m.setMsg(roles);
        m.setDetail("OK");
        
        return gson.toJson(m);
    }
    
    public String updateRole(String rolid, String name){
        Message m = new Message();
        Role r = new Role();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
                /*Query q = entity.createNamedQuery("Role.updateRole").
                setParameter("rolename", name).
                setParameter("roleid", Integer.parseInt(rolid));*/
                
                Query q = entity.createNativeQuery("UPDATE Role SET rolename = '"+ name +"' WHERE roleid = "+ rolid +";");
                
            if(q.executeUpdate() == 1){
                m.setCode(200);
                m.setMsg("Se actualizo correctamente.");
                m.setDetail("OK");
            }else{
                m.setCode(404);
                m.setMsg("No se realizo la actualizacion");
                m.setDetail("");
            }
        
        
        }catch(TransactionRequiredException e){
        
        }
        return gson.toJson(m);
    }
    
    /*public String updateRole(String rolid, String name){
        Message m = new Message();
        Role r = new Role();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try{
            r.setRoleid(Integer.parseInt(rolid));
            r.setRolename(name);
            //entity.merge(r);
            entity.refresh(entity.merge(r));
            m.setCode(200);
            m.setMsg("El rol se actualizo correctamente");
            m.setDetail("OK");
            
        }catch(NumberFormatException e){
            m.setCode(406);
            m.setMsg("Error de tipo de dato '" + rolid + "'");
            m.setDetail(e.toString());
        }catch(IllegalArgumentException e){
            m.setCode(503);
            m.setMsg("Error en la base de datos");
            m.setDetail(e.toString());
        }catch(TransactionRequiredException e){
            m.setCode(503);
            m.setMsg("Error en la transaccion con la base de datos");
            m.setDetail(e.toString());
        }catch(EntityNotFoundException e){
            m.setCode(406);
            m.setMsg("El registro no pudo ser actualizado, debido a que no existe en la BD");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }*/
    
    public String newRole(String name){
        Message m = new Message();
        Role r = new Role();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            r.setRolename(name);
            entity.persist(r);
            entity.flush();
           
            m.setCode(200);
            m.setMsg("El rol se registro correctamente");
            m.setDetail(r.getRoleid().toString());
            
            
        }catch(IllegalArgumentException e){
            m.setCode(503);
            m.setMsg("Error en la base de datos");
            m.setDetail(e.toString());
        }catch(TransactionRequiredException e){
            m.setCode(503);
            m.setMsg("Error en la transaccion con la base de datos");
            m.setDetail(e.toString());
        }catch(EntityExistsException e){
            m.setCode(400);
            m.setMsg("Hubo problemas con la base de datos");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getRole(String roleid){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            Role role;
            Query q = entity.createNamedQuery("Role.findByRoleid").setParameter("roleid", Integer.parseInt(roleid));
        
            role = (Role) q.getSingleResult();
        
            role.setUsersList(null);
        
            m.setCode(200);
            m.setMsg(gson.toJson(role));
            m.setDetail("OK");
        }catch(NoResultException e){
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String deleteRole(String roleid){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            String info = this.getRole(roleid);
            boolean resultado = info.contains("OK");
            if(resultado){
                Role r = entity.find(Role.class, Integer.parseInt(roleid));
                entity.remove(r);
                m.setCode(200);
                m.setMsg("Registro Eliminado correctamente");
                m.setDetail("OK");
            }else{
                m.setCode(404);
                m.setMsg("No se encontro el registro");
                m.setDetail("");
            }
        }catch(IllegalArgumentException e){
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }catch(TransactionRequiredException e){
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        
        
        return gson.toJson(m);
    }
    
    //Funcionando todo lo de la tabla products.
    public String newProduct(String code, String productname, String brand, 
           String purchprice, String stock, String salepricemin, 
           String reorderpoint, String currency, String salepricemay, 
           String categoryid){
        
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            Category cat = entity.find(Category.class, Integer.parseInt(categoryid));
            
            Product p = new Product();
            p.setCode(code);
            p.setProductname(productname);
            p.setBrand(brand);
            p.setPurchprice(Double.parseDouble(purchprice));
            p.setStock(Integer.parseInt(stock));
            p.setSalepricemin(Double.parseDouble(salepricemin));
            p.setReorderpoint(Integer.parseInt(reorderpoint));
            p.setCurrency(currency);
            p.setSalepricemay(Double.parseDouble(salepricemay));
            p.setCategoryid(cat);
            
            entity.persist(p);
            entity.flush();
            
            m.setCode(200);
            m.setMsg("Se inserto correctamente");
            m.setDetail(p.getProductid().toString());
          
        }catch(IllegalArgumentException e){
            m.setCode(503);
            m.setMsg("Error en la base de datos");
            m.setDetail(e.toString());
        }catch(TransactionRequiredException e){
            m.setCode(503);
            m.setMsg("Error en la transaccion con la base de datos");
            m.setDetail(e.toString());
        }catch(EntityExistsException e){
            m.setCode(400);
            m.setMsg("Hubo problemas con la base de datos");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getProducts(){
        List<Product> listProducts;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try{
            Query q = entity.createNamedQuery("Product.findAll");
            listProducts = q.getResultList();
        
            m.setCode(200);
            m.setMsg(gson.toJson(listProducts));
            m.setDetail("OK");
        }catch(IllegalArgumentException e){
            m.setCode(501);
            m.setMsg("Error al consultar los registros");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getProductsByReorder(){
        List<Product> listProducts;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try{
            Query q = entity.createNamedQuery("Product.findMinimalProducts");
            listProducts = q.getResultList();
        
            m.setCode(200);
            m.setMsg(gson.toJson(listProducts));
            m.setDetail("OK");
        }catch(IllegalArgumentException e){
            m.setCode(501);
            m.setMsg("Error al consultar los registros");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getProductByID(String productid){
        Message m = new Message();
        Product product = new Product();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
            
            Query q = entity.createNamedQuery("Product.findByProductid").setParameter("productid", Integer.parseInt(productid));
            product = (Product) q.getSingleResult();
        
            
        
            m.setCode(200);
            m.setMsg(gson.toJson(product));
            m.setDetail("OK");
        }catch(Exception e){
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    
    //GetProductByName
    
    public String getProductByName(String productname){ 
        List<Product>listProducts;
        Message m=new Message();
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        
        Query q=entity.createNativeQuery("Select * from product where productname like '"+productname+"%'",Product.class);
        
        listProducts = q.getResultList();
      
        m.setCode(200);
        m.setMsg(gson.toJson(listProducts));
        m.setDetail("OK");
        
        return gson.toJson(m);
    }
    
    
    public String updateProductOnlyName(String productid, String productname){
        Message m = new Message();
        Product r = new Product();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
                Query q = entity.createNamedQuery("Product.updateProduct").
                setParameter("productname", productname).
                setParameter("productid", Integer.parseInt(productid));
                
                
            if(q.executeUpdate() == 1){
                m.setCode(200);
                m.setMsg("Se actualizo correctamente.");
                m.setDetail("OK");
            }else{
                m.setCode(404);
                m.setMsg("No se realizo la actualizacion");
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
    
    public String deleteProduct(String productid){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try{
                Query q = entity.createNamedQuery("Product.deleteProduct").
                setParameter("productid", Integer.parseInt(productid));
                
                
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
    
}
