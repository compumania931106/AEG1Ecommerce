/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;


import javax.ejb.Remote;

/**
 *
 * @author VictorManuel
 */
@Remote
public interface EjbCartBeanRemote {
    public String addProduct(String code, String productname, int quantity, Double salepricemay);
    public String removeProduct(int productid);
    public void remove();
    public void initialize();
}
