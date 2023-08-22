<%-- 
    Document   : booklist
    Created on : Aug 20, 2023, 10:49:52 PM
    Author     : xuand
--%>

<%@page import="java.util.List"%>
<%@page import="Book.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book List</title>
    </head>
    <body>
        <h1>Book List</h1>
        <jsp:include page="/menu.html" flush="true" />
        <%! List<BookDTO> list; %>
        <%list = (List<BookDTO>)request.getAttribute("list");%>
        <form action="book">
            <input type="hidden" name="action" value="list" />
            <input type="text" name="keyword"/>
            <input type="hidden" name="author" value="" />
            <input type="submit" value="Search" />
        </form>
        <h1>Search result: ${param.keyword}</h1>
        <table border="0">
                <tr>
                    <th>ID</th>
                    <th>name</th>
                    <th>author</th>
                </tr>
                
                 
                   <%for(BookDTO book : list){
                       %>
                <tr>
                    <td><a href=book?action=details&id=<%=book.getId()%>><%=book.getId() %></a></td>
                    <td><%=book.getName() %></td>
                    <td><%=book.getAuthor() %></td>
                    <td><form action="book">
                        <input type=hidden name=action value=delete>
                        <input type=hidden name=id value="<%=book.getId()%>">
                        <input type=submit value=Delete>
                        </form></td>
                </tr>

                <%
                   }
                %>   
        </table>
        <form action="book">
            <input type=hidden name="action" value="add">
            <input type=submit value="Add new book">
        </form>
    </body>
</html>
