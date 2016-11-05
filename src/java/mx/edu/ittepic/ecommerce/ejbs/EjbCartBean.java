/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.ejbs;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import mx.edu.ittepic.ecommerce.entities.ProductCart;
import mx.edu.ittepic.ecommerce.utils.Message;

/**
 *
 * @author VictorManuel
 */
@Stateful
@Remote(EjbCartBeanRemote.class)
@EJB(name = "ejb/EjbCartBean", beanInterface = EjbCartBeanRemote.class, beanName = "EjbCartBean")
public class EjbCartBean implements EjbCartBeanRemote {

    List<ProductCart> cart;
    int indexrepetido = -1;

    @Override
    public String addProduct(String code, String productname, int quantity, Double salepricemay) {

        /*p.setCode(code);
        p.setProductname(productname);
        p.setQuantity(quantity);
        p.setSalepricemay(salepricemay);
        p.setImage(image);*/
        Message m = new Message();
        boolean var = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getCode().equals(code)) {

                var = true;
                indexrepetido = i;
            }
        }
        if (!var) {
            ProductCart p = new ProductCart();
            p.setCode(code);
            p.setProductname(productname);
            p.setQuantity(quantity);
            p.setSalepricemay(salepricemay);
            
            cart.add(p);
        } else {
            cart.get(indexrepetido).setQuantity(cart.get(indexrepetido).getQuantity()+ quantity);
        }
        m.setCode(200);
        m.setMsg(new GsonBuilder().create().toJson(cart));
        m.setDetail("OK");
        return new GsonBuilder().create().toJson(m);

    }

    @Override
    public String removeProduct(int productid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Remove
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @PostConstruct
    public void initialize() {
        cart = new ArrayList<>();
    }

}
