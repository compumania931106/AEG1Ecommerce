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
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author victor
 */

@Stateless
public class EjbCompany {
    @PersistenceContext
    EntityManager entity;
    
    public String NewCompany(String companyname, String neighborhood, String zipcode,
            String city, String country, String state, String region, String street,
            String streetnumber, String phone, String rfc, String logo){
        
        Message m = new Message();
        Company c = new Company();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try {
            c.setCompanyname(companyname);
            c.setNeighborhood(neighborhood);
            c.setZipcode(zipcode);
            c.setCity(city);
            c.setCountry(country);
            c.setState(state);
            c.setRegion(region);
            c.setStreet(street);
            c.setStreetnumber(streetnumber);
            c.setPhone(phone);
            c.setRfc(rfc);
            c.setLogo(logo);
            

            entity.persist(c);
            entity.flush();

            m.setCode(200);
            m.setMsg("Ls compañia se ha registro correctamente");
            m.setDetail(c.getCompanyid().toString());

        } catch (IllegalArgumentException e) {
            m.setCode(503);
            m.setMsg("Error en la base de datos");
            m.setDetail(e.toString());
        } catch (EntityExistsException e) {
            m.setCode(400);
            m.setMsg("Hubo problemas con la base de datos");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String updateCompany(String companyid, String companyname, String neighborhood, String zipcode,
            String city, String country, String state, String region, String street,
            String streetnumber, String phone, String rfc, String logo){
        
        Message m = new Message();
        Company c = new Company();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try {
            Query q = entity.createNamedQuery("Company.updateCompany").
                    setParameter("companyname",companyname).
                    setParameter("neighborhood",neighborhood).
                    setParameter("zipcode",zipcode).
                    setParameter("city",city).
                    setParameter("country",country).
                    setParameter("state",state).
                    setParameter("region",region).
                    setParameter("street",street).
                    setParameter("streetnumber",streetnumber).
                    setParameter("phone",phone).
                    setParameter("rfc",rfc).
                    setParameter("logo",logo).
                    setParameter("companyid",Integer.parseInt(companyid));
                    

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
    
    public String getCompanys(){
        List<Company> listCompanys;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNamedQuery("Company.findAll");
            listCompanys = q.getResultList();

            m.setCode(200);
            m.setMsg(gson.toJson(listCompanys));
            m.setDetail("OK");
        } catch (IllegalArgumentException e) {
            m.setCode(501);
            m.setMsg("Error al consultar los registros");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getCompanyByID(String companyid){
        Message m = new Message();
        Company company = new Company();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
        try {

            Query q = entity.createNamedQuery("Company.findByCompanyid").setParameter("companyid", Integer.parseInt(companyid));
            company = (Company) q.getSingleResult();

            m.setCode(200);
            m.setMsg(gson.toJson(company));
            m.setDetail("OK");
        } catch (Exception e) {
            m.setCode(404);
            m.setMsg("No se encontro el registro");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }
    
    public String getCompanyByName(String companyname){
        List<Company> listCompanys;
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Query q = entity.createNativeQuery("Select * from Company where companyname like '" + companyname + "%'", Company.class);

        listCompanys = q.getResultList();

        m.setCode(200);
        m.setMsg(gson.toJson(listCompanys));
        m.setDetail("OK");

        return gson.toJson(m);
    }
    
    public String deleteCompany(String companyid){
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            Query q = entity.createNamedQuery("Company.deleteByID").
                    setParameter("companyid", Integer.parseInt(companyid));

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
