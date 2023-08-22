<%-- 
    Document   : bookdetails
    Created on : Aug 20, 2023, 11:10:10 PM
    Author     : xuand
--%>
<%@page import="Book.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book details</title>
    </head>
    <body>
        <h1>Book details</h1>
        <jsp:include page="/menu.html" flush="true" />
        <p> Login user: ${sessionScope.usersession.username}</p>
        
        <table>
         
         <tr><td>Id</td><td>${requestScope.object.id}</td></tr>
         <tr><td>Book name</td><td>${requestScope.object.name }</td></tr>
         <tr><td>Author</td><td>${requestScope.object.author}</td></tr>
         <tr><td>Description</td><td>${requestScope.object.description}</td></tr>
         
         </table>
         
         <form action="book">
             <input type=hidden name="id" value="${requestScope.object.id}">
             <input type=hidden name="action" value="edit">
             <input type=submit value="Edit">
         </form>
    </body>
</html>
