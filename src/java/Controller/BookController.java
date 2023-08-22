/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Book.BookDAO;
import Book.BookDTO;
import User.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author xuand
 */
public class BookController extends HttpServlet {

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
            String action = request.getParameter("action");
            String keyword = request.getParameter("keyword");
            String author = request.getParameter("author");
            
            HttpSession session = request.getSession(false);
            UserDTO currentUser = null;
            
            if(session != null){
                currentUser = (UserDTO) session.getAttribute("usersession");
            }
            
            log("Debug: " + currentUser);
            if(currentUser == null){
                log("Debug: redirect to login page" + currentUser);
                response.sendRedirect(request.getContextPath() + "/.login");
                return;
            }
            BookDAO bookDAO = new BookDAO();
            
            if(action == null || action.equals("list")){
                try{
                    
                }catch(Exception e){
                
                }
                List<BookDTO> list = bookDAO.list(keyword, author);
                
                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("booklist.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("details")){
                int id = 0;
                
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    System.out.println("error " + ex.getMessage());
                }
                BookDTO book = null;
                if(id != 0){
                    book = bookDAO.load(id);
                }
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("edit")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    
                }
                BookDTO book = null;
                if(id != 0){
                    book = bookDAO.load(id);
                }
                request.setAttribute("object", book);
                request.setAttribute("nextaction", "update");
                RequestDispatcher rd = request.getRequestDispatcher("bookedit.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("add")){
                BookDTO book = new BookDTO();
                request.setAttribute("object", book);
                request.setAttribute("nextaction", "insert");
                RequestDispatcher rd = request.getRequestDispatcher("bookedit.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("update")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    
                }
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                BookDTO book = new BookDTO();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setDescription(description);
                bookDAO.update(book);
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("insert")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    
                }
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                BookDTO book = new BookDTO();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setDescription(description);
                bookDAO.insert(book);
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);
            }
            else if(action.equals("delete")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    
                }
                if(id != 0){
                    bookDAO.delete(id);
                }
                
                List<BookDTO> list = bookDAO.list(keyword, author);
                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("booklist.jsp");
                rd.forward(request, response);
            }
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
        processRequest(request, response);
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
