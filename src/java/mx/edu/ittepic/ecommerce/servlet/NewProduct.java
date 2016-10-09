/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ecommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.edu.ittepic.ecommerce.ejbs.EjbEcommerce;

/**
 *
 * @author VictorManuel
 */
@WebServlet(name = "NewProduct", urlPatterns = {"/NewProduct"})
public class NewProduct extends HttpServlet {

    @EJB
    private EjbEcommerce ejb;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewProduct</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-control", "no-store");
        
        PrintWriter p = response.getWriter();
        
            /*p.setCode(code);
            p.setProductname(productname);
            p.setBrand(brand);
            p.setPurchprice(purchprice);
            p.setStock(stock);
            p.setSalepricemin(salepricemin);
            p.setReorderpoint(reorderpoint);
            p.setCurrency(currency);
            p.setSalepricemay(salepricemay);
            p.setCategoryid(cat);*/
            
        String code = request.getParameter("code");
        String productname = request.getParameter("productname");
        String brand = request.getParameter("brand");
        String purchprice = request.getParameter("purchprice");
        String stock = request.getParameter("stock");
        String salepricemin = request.getParameter("salepricemin");
        String reorderpoint = request.getParameter("reorderpoint");
        String currency = request.getParameter("currency");
        String salepricemay = request.getParameter("salepricemay");
        String cat = request.getParameter("cat");
        
        p.println(ejb.newProduct(code, productname, brand, purchprice, stock, salepricemin, reorderpoint, currency, salepricemay, cat));
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}