<!DOCTYPE html>
<html>
<head>
        <title>PRJ301 WS2 - Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
</head>
    <body>
        <h1>Please login</h1>
        <jsp:include page="/menu.html" flush="true" />
        <%! String err; %>
        <% err = (String) request.getAttribute("error"); 
        if (err != null) {
            out.print("<h2>"+err+"</h2>"); 
        }%>
        <h2> hello </h2>
        <form action="./login" name="" method="POST">         
            <table border="0">
                    <tr>
                        <td>Username</td>
                        <td><input name="user" type="text"></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input name="password" type="password"></td>
                    </tr>
                    <tr>
                        <td><input value="Login" type="submit"></td>
                    </tr>
            </table>

        </form>
        
    </body>
</html>
