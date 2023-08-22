<%-- 
    Document   : bookedit
    Created on : Aug 20, 2023, 11:27:41 PM
    Author     : xuand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book edit</title>
    </head>
    <body>
        <h1>Book edit</h1>
        <jsp:include page="/menu.html" flush="true" />
        <p>Login user: ${sessionScope.usersession.username}</p>
        
        <form action="book">
             <table border="0">
                     <tr>
                         <td>ID</td>
                         <td><input type="text" name="id" value="${requestScope.object.id}"></td>
                     </tr>
                     <tr>
                         <td>Book Name</td>
                         <td><input type="text" name="name" value="${requestScope.object.name}"></td>
                     </tr>
                     <tr>
                         <td>Author</td>
                         <td><input type="text" name="author" value="${requestScope.object.author}"></td>
                     </tr>
                     <tr>
                         <td>Description</td>
                         <td><input type="text" name="description" value="${requestScope.object.description}"></td>
                     </tr>
                     <tr>
                         <td><input type="submit" value="save"></td>
                         <td><input type="hidden" name="action" value="${requestScope.nextaction}"></td>
                     </tr>
             </table>

         </form>
    </body>
</html>
