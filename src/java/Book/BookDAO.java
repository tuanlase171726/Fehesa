/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author xuand
 */
public class BookDAO {
    public List<BookDTO> list (String keyword, String author){
        ArrayList<BookDTO> list = new ArrayList<BookDTO>();
        
        String sql = "SELECT id, name, author FROM books";
        
        String where = "";
        String whereJoinWord = " WHERE ";
        
        if(keyword != null && !keyword.trim().isEmpty()){
            where += whereJoinWord;
            where += " (name LIKE ? OR author LIKE ?)";
            whereJoinWord = " AND ";
            sql += where;
        }
        if(author != null && !author.trim().isEmpty()){
            where += whereJoinWord;
            where +=" author LIKE ? ";
            whereJoinWord = " AND ";
            sql += where;
        }
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            
            int index = 1;
            if(keyword != null && !keyword.trim().isEmpty()){
                stm.setString(index, keyword);
                index++;
                stm.setString(index, keyword);
                index++;
            }
            if(author != null && !author.trim().isEmpty()){
                stm.setString(index, author);
                index++;
            }
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                list.add(new BookDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author")               
                ));
            }
            return list;
        }catch(Exception e){
            System.out.println("Query book error. " + e.getMessage());
        }
        return null;
    }
    
    public BookDTO load(int id){
        String sql = "SELECT id, name, author, description FROM books WHERE id = ?";
        try{
            Connection con  = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return new BookDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("description"));
            }
        }catch(SQLException e){
            System.out.println("Query book error" + e.getMessage());
        }
        return null;
    }
    
    public int insert(BookDTO book){
        String sql =  "INSERT INTO books (id, name, author, description) "
                + "VALUES (?, ?, ?, ?)";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, book.getId());
            stm.setString(2, book.getName());
            stm.setString(3, book.getAuthor());
            stm.setString(4, book.getDescription());
            stm.executeUpdate();
    
        }catch(SQLException e){
            System.out.println("Insert book error" + e.getMessage());
        }
        return 0;
    }
    public boolean update(BookDTO book){
        String sql = "UPDATE books SET name = ?, author = ?, description = ? WHERE id = ?";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, book.getName());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getDescription());
            stm.setInt(4, book.getId());
            if(stm.executeUpdate() > 0)
                return true;
        }catch(SQLException e){
            System.out.println("Update book error" + e.getMessage());
        }
        return false;
    }
    public boolean delete(int id){
        String sql = "DELETE FROM books WHERE id = ?";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            if(stm.executeUpdate() > 0){
                return true;
            }
        }catch(SQLException e){
            System.out.println("Delete book error" + e.getMessage());
        }
        return false;
    }
}
