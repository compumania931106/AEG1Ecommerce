/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.entities;

/**
 *
 * @author VictorManuel
 */
public class ProductCart {
    private String code;
    private String productname;
    private int quantity;
    private Double salepricemay;
    private String image;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSalepricemay() {
        return salepricemay;
    }

    public void setSalepricemay(Double salepricemay) {
        this.salepricemay = salepricemay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}
