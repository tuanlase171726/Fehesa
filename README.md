# Fehese

### Team members:
  - Le Anh Tuan
  - Phan Huu Duc
  - Pham Huynh Xuan Dang

## 1. Introduction

- Project purpose: The vision of Fehese is to provide a convenient and user-friendly platform for book lovers to browse, buy and review books online. Fehese aims to offer a wide range of books from various genres, authors, and publishers, as well as personalized recommendations based on the user’s preferences and reading history. The web application also enables users to interact with other readers, share their opinions and feedback, and join online book clubs and events. The web application strives to create a vibrant and engaging community of book enthusiasts who can enjoy reading and learning from books anytime and anywhere.

- Project actors:
  - Admin(ad)
  - Customer(cus)

- Functions:
  - Admin(role):
    - Login: allows admin to access their personal account on Fehese database
    - Add, delete products:  allows the web application’s administrators to upload, remove books from the web application’s database
    - Manage orders: allows admin to see the order detail
    - Sort category: allows admin to organize the books displayed on the web application according to different criteria
    - Customize profile: enables admin to edit their profile information, such as their name, email, address, phone number, password, and more
    - Search products: allows admin to find specific books or books related to a certain keyword on the web application
  - Customer(role):
    - Login: allows customer to access their personal account on Fehese database
    - Add to cart: allows customer to select and save the books they want to buy on the web application, it also allows users to view or remove the books in their shopping cart
    - Sort category: allows customers to organize the books displayed on the web application according to different criteria
    - Customize profile: enables customers to edit their profile information, such as their name, email, address, phone number, password, and more
    - Search products: allows customers to find specific books or books related to a certain keyword on the web application
      
-------------------------------------------------------------------------------------------------------------------------------------------------
## 2.GUI/SITEMAPS
## - SITEMAPS
<img src='img/sitemap2.png' width='500'>

## - GUI/LOGIN
<img src='img/login.jpg' width='500'>

## - GUI/REGISTER
<img src='img/register.jpg' width='500'>

## - GUI/HOMEPAGE
<img src='img/mainpage.jpg' width='500'>

## - GUI/DETAIL
<img src='img/detail.jpg' width='500'>

## - GUI/CART
<img src='img/cart.jpg' width='500'>

## - GUI/CHECK OUT
<img src='img/CheckOut.png' width='500'>

## - GUI/PROFILE DETAIL
<img src='img/Profile-detail.png' width='500'>

## - GUI/PROFILE SECURITY
<img src='img/Profile-security.png' width='500'>

## - GUI/MANAGE PRODUCT
<img src='img/Manage-product.png' width='500'>

## - GUI/EDIT PRODUCT
<img src='img/Edit-product.png' width='500'>

## - GUI/MANAGE ORDER
<img src='img/Manage-order.png' width='500'>

## - GUI/ORDER DETAIL
<img src='img/Order-detail.png' width='500'>

-------------------------------------------------------------------------------------------------------------------------------------------------

## 3.ERD
<img src='img/erd.jpg' width='500'>

-------------------------------------------------------------------------------------------------------------------------------------------------

## 4. APP STRUCTURE
Website uses the MVC2 architecture, and this is the Model website
## Model structure
<img src='img/model.png' width='500'>

Alls of the functions of the Model will be listed below, All DTO file don't necessarily include constructor, set and get

- Author Model
  - This model's purpose is to get and save data from table Author from the server.   
    
This is the all data of AuthorDTO:
```
    [private int authorID;
    private String authorName;
    private String cescription;]
```
AuthorDAO's functions are:
- List data from table Author:
```
 public List<AuthorDTO> listAuthor(){
        String sql = "SELECT AuthorID, AuthorName, Description FROM Author";
        ArrayList<AuthorDTO> list = new ArrayList<AuthorDTO>();
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                list.add(new AuthorDTO(
                        rs.getInt("AuthorID"),
                        rs.getString("AuthorName"),
                        rs.getString("Description")
                ));
            }
            return list;
        }catch(SQLException e){
            System.out.println("Query Category error " + e.getMessage());
        }
        return null;
    }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
- Category Model
  - This model's purpose is to get and save data from table Category from the server.   
 
This is the all the data of CategoryDTO:
```
    private int cateID;
    private String cateName, description;
```

CategoryDAO's functions are:
- List data from table Category
```
public List<CategoryDTO> listCategory(){
        String sql = "SELECT CateID, CateName, Description FROM Category";
        ArrayList<CategoryDTO> list = new ArrayList<CategoryDTO>();
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                list.add(new CategoryDTO(
                        rs.getInt("CateID"),
                        rs.getString("CateName"),
                        rs.getString("Description")
                ));
            }
            return list;
        }catch(SQLException e){
            System.out.println("Query Category error " + e.getMessage());
        }
        return null;
    }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
- Cart Model
  - This model's purpose is to get a product detail and customer profile then create a list of them.
 
This is the data of Item:
```
    private BookDTO book;
    private int quantity;
```
This is the data of CartDTO:
```
    List<Item> list;
```

CartDTO's functions are:
- Get product and product id:
```
private Item getItemById(int id){
        for(Item item : list){
            if(item.getBook().getId() == id )
                return item;
        }
        return null;
    }
```
- Get quantity using product id
```
public int getQuantityById(int id){
        return getItemById(id).getQuantity();
    }
```
- Check whether the product is in the cart or not:
```
private Item checkItem(int id){
        for(Item item : list){
            if(item.getBook().getId()==id)
                return item;
        }
        return null;
    }
```
- This function will add the product quantity to the cart if said product is in the cart already if not then add it to cart
```
public void addItem (Item item){
        if(getItemById(item.getBook().getId()) != null && checkItem(item.getBook().getId()) != null){
            Item sum = getItemById(item.getBook().getId());
            sum.setQuantity(sum.getQuantity() + item.getQuantity());
        }else{
            list.add(item);
        }
    }
```
- Delete product from Cart
```
public void removeItem(int id){
        if(getItemById(id) != null){
            list.remove(getItemById(id));
        }
    }
```
- Get cart total price
```
public double getTotalMoney(){
        double sum = 0;
        for(Item item : list){
            sum += item.getBook().getPrice() * item.getQuantity();
        }
        return sum;
    }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
- Book Model
  - This model's purpose is to get and store data of the table Books from server
-In BookDTO will store these data
```
    private int id;
    private String name,author,category;
    private int authorID, categoryID;
    private int price;
    private String status;
    private String description;
    private String img_url;
```
-bookDAO's functions are:

--Search book name and book author then list down all of the data from table book
```
public List<BookDTO> list(String keyword, String author) {
        ArrayList<BookDTO> list = new ArrayList<BookDTO>();

        String sql = "SELECT b.BookID, Bookname, a.AuthorName, c.CateName, b.AuthorID, b.CateID, Price,Status, b.Description, i.url\n"
                + " FROM Books AS b\n"
                + " left join Author AS a\n"
                + " ON b.AuthorID = a.AuthorID\n"
                + " left join Category AS c\n"
                + " ON b.CateID = c.CateID\n"
                + " left join Image AS i\n"
                + " ON b.BookID = i.BookID\n";

        String where = "";
        String whereJoinWord = " WHERE ";

        if (keyword != null && !keyword.trim().isEmpty()) {
            where += whereJoinWord;
            where += " (Bookname LIKE ? OR Bookname LIKE ? OR AuthorName Like ? OR AuthorName Like ?)";
            whereJoinWord = " AND ";
            sql += where;
        }
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);

            int index = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                stm.setString(index, "%"+keyword+"%");
                index++;
                stm.setString(index, "%"+keyword+"%");
                index++;
                stm.setString(index, "%"+keyword+"%");
                index++;
                stm.setString(index, "%"+keyword+"%");
                index++;
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new BookDTO(
                        rs.getInt("BookID"),
                        rs.getString("Bookname"),
                        rs.getString("AuthorName"),
                        rs.getString("CateName"),
                        rs.getInt("AuthorID"),
                        rs.getInt("CateID"),
                        rs.getInt("Price"),
                        rs.getString("Status"),
                        rs.getString("Description"),
                        rs.getString("url")
                ));

            }
            return list;
        } catch (Exception e) {
            System.out.println("Query book error. " + e.getMessage());
        }
        return null;
    }
```
--Add 1 Books
```
public int insert(BookDTO book) {
        String sql = "INSERT INTO Books (Bookname, AuthorID, CateID, Price, Status, Description) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
stm.setString(1, book.getName());
            stm.setInt(2, book.getAuthorID());
            stm.setInt(3, book.getCategoryID());
            stm.setInt(4, book.getPrice());
            stm.setString(5, book.getStatus());
            stm.setString(6, book.getDescription());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert book error" + e.getMessage());
        }
        return 0;
    }
```
--Update 1 book data through its id
```
public boolean update(BookDTO book) {
        String sql = "UPDATE books SET Bookname = ?, AuthorID = ?, CateID = ?, Price = ?, Status = ?, Description = ? WHERE BookID = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, book.getName());
            stm.setInt(2, book.getAuthorID());
            stm.setInt(3, book.getCategoryID());
            stm.setInt(4, book.getPrice());
            stm.setString(5, book.getStatus());
            stm.setString(6, book.getDescription());
            stm.setInt(7, book.getId());
            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update book error " + e.getMessage());
        }
        return false;
    }
```
--Get 1 data of book through its id
```
public BookDTO load(int id) {
        String sql = "SELECT b.BookID, Bookname, a.AuthorName, c.CateName, b.AuthorID, b.CateID, Price,Status, b.Description, i.url\n"
                + " FROM Books AS b\n"
                + " left join Author AS a\n"
                + " ON b.AuthorID = a.AuthorID\n"
                + " left join Category AS c\n"
                + " ON b.CateID = c.CateID\n"
                + " left join Image AS i\n"
                + " ON b.BookID = i.BookID\n"
                + " WHERE b.BookID = ?";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new BookDTO(
                        rs.getInt("BookID"),
                        rs.getString("Bookname"),
                        rs.getString("AuthorName"),
                        rs.getString("CateName"),
                        rs.getInt("AuthorID"),
                        rs.getInt("CateID"),
                        rs.getInt("Price"),
                        rs.getString("Status"),
                        rs.getString("Description"),
                        rs.getString("url"));
            }
        } catch (SQLException e) {
            System.out.println("Query book error" + e.getMessage());
        }
        return null;
    }
```
--Delete book
```
public boolean delete(int id) {
        String sql = "DELETE FROM Image WHERE BookID = ?";
try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Delete book error" + e.getMessage());
        }
        return false;
    }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
- User Model
  - This model's purpose is to get and store data from table Users from database 
-In UserDTO store these data
```
    private int id;
    private String username;
    private String fullname;
    private String addr;
    private String phone;
    private String email;
    private String DOB;
    private String role;
```
-UserDAO's functions are:
 -Allow user to log into web
```
public UserDTO login(String user, String password){
        
        String sql = "select userID, username from Users "
                + " where username = ? and password = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user);
            ps.setString(2, password);
            
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                UserDTO userDTO =  new UserDTO();
                userDTO.setUsername(rs.getString("username"));
                userDTO.setId(rs.getInt("UserID"));
                
                return userDTO;
                
            }
        }
        catch (SQLException ex) {
            System.out.println("Query Book error!" + ex.getMessage());
        }
        return null;
    }
```
-Allow user to register/sign up
```
public void register(String username,  String password){
        String sql = "INSERT users(username, password, role)"
                + "VALUES (?,?,?)";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, "Customer");
            ps.executeUpdate();
        }catch(SQLException e){  
        };
    }
```
- Check to see the register account is in the database or not
```
public UserDTO checkAcc(String username){
        try{
            String sql="SELECT * FROM users WHERE username = ?";
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                UserDTO user = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                return user;
            }
        }catch(Exception e){
            
        }
        return null;
    }
```
- Update user data;
```
public void update(int id, String fullname, String address, String phone, String email, String dob){
        String sql="UPDATE users SET fullname = ?, Addr = ?, Phone= ?, Email = ?, DOB = ? WHERE userID = ?";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setString(1, fullname);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.setString(4, email);
ps.setString(5, dob);
            ps.setInt(6, id);
            ps.executeUpdate();
        }catch(SQLException e){
        }
    }
```

-Get user data throught id
```
public UserDTO getuser(int id){
        String sql ="SELECT userID, username, fullname , Addr, Phone, Email, DOB, role FROM users WHERE userID = ?";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement ps = con.prepareCall(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new UserDTO(rs.getInt("userID"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("Addr"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("DOB"),
                        rs.getString("role")
                );
            }
        }catch(SQLException e){
            }
        return null;
    }
```
-------------------------------------------------------------------------------------------------------------------------------------------------

## Controller structure
<img src='img/controller.png' width='500'>
Controller 
- BookController will control the interacts with books

- Function will list all books and then point to home page
```
if (action == null || action.equals("list")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
                rd.forward(request, response);
```

- This function will list all of the books and then point to the admin manager tab

```
if (action.equals("management")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("bookmanager.jsp");
                rd.forward(request, response);
            }
```
- This function will list 1 book data through its id and then point to book detail page 
```
if (action.equals("details")) {
                String check = request.getParameter("check");
                int id = 0;

                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    System.out.println("error " + ex.getMessage());
                }
                BookDTO book = null;
                if (id != 0) {
                    book = bookDAO.load(id);
                }
                request.setAttribute("check", check);
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("bookdetails.jsp");
                rd.forward(request, response);
            }
```
- This function will get 1 book data through its id and then point it to the admin book detail page
```
if (action.equals("adbookdetail")) {
                String check = request.getParameter("check");
                int id = 0;

                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    System.out.println("error " + ex.getMessage());
                }
                BookDTO book = null;
                if (id != 0) {
                    book = bookDAO.load(id);
                }
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("adbookdetail.jsp");
                rd.forward(request, response);
            }
```
- This will get 1 book data to update and then point it to book edit with the updated data
```
if (action.equals("edit")) {
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    ex.getMessage();
                }
                BookDTO book = null;
                if (id != 0) {
                    book = bookDAO.load(id);
                }
                AuthorDAO authorDAO = new AuthorDAO();
                List<AuthorDTO> listAuthor = authorDAO.listAuthor();
                request.setAttribute("listAuthor", listAuthor);

                CategoryDAO cateDAO = new CategoryDAO();
                List<CategoryDTO> catelist = cateDAO.listCategory();
                request.setAttribute("catelist", catelist);

                request.setAttribute("object", book);
                request.setAttribute("nextaction", "update");
                RequestDispatcher rd = request.getRequestDispatcher("bookedit.jsp");
                rd.forward(request, response);
            }
```
- This function will get data from the user and then insert after that it will point to book edit page
```
if (action.equals("add")) {

                AuthorDAO authorDAO = new AuthorDAO();
                List<AuthorDTO> listAuthor = authorDAO.listAuthor();
                request.setAttribute("listAuthor", listAuthor);

                CategoryDAO cateDAO = new CategoryDAO();
                List<CategoryDTO> catelist = cateDAO.listCategory();
                request.setAttribute("catelist", catelist);

                BookDTO book = new BookDTO();
                request.setAttribute("object", book);
                request.setAttribute("nextaction", "insert");
                RequestDispatcher rd = request.getRequestDispatcher("bookedit.jsp");
                rd.forward(request, response);
            }
```
- This function will update the book data
```
if (action.equals("update")) {
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    ex.getMessage();
                }
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                String description = request.getParameter("description");
                String img = request.getParameter("img");
                try {
                    int authorID = Integer.parseInt(request.getParameter("authorID"));
                    int cateID = Integer.parseInt(request.getParameter("categoryID"));
                    int price = Integer.parseInt(request.getParameter("price"));

                    BookDTO book = new BookDTO();

                    book.setId(id);
                    book.setName(name);
                    book.setAuthorID(authorID);
                    book.setCategoryID(cateID);
                    book.setPrice(price);
                    book.setStatus(status);
                    book.setDescription(description);
                    book.setImg_url(img);
                    
                    bookDAO.update(book);
                    book = bookDAO.load(id);

                    request.setAttribute("object", book);

                } catch (NumberFormatException e) {
                    e.getMessage();
                }

                RequestDispatcher rd = request.getRequestDispatcher("adbookdetail.jsp");
                rd.forward(request, response);
```
- This function will add a book data and then return the result 
```
if (action.equals("insert")) {
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    ex.getMessage();
                }
                String name = request.getParameter("name");
                String status = request.getParameter("status");
                String description = request.getParameter("description");
                try {
                    int authorID = Integer.parseInt(request.getParameter("authorID"));
                    int cateID = Integer.parseInt(request.getParameter("categoryID"));
                    int price = Integer.parseInt(request.getParameter("price"));

                    BookDTO book = new BookDTO();

                    book.setId(id);
                    book.setName(name);
                    book.setAuthorID(authorID);
                    book.setCategoryID(cateID);
                    book.setPrice(price);
                    book.setStatus(status);
                    book.setDescription(description);

                    bookDAO.insert(book);
                    request.setAttribute("object", book);

                } catch (NumberFormatException e) {
                    e.getMessage();
                }
                List<BookDTO> list = bookDAO.list(keyword, author);
                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("bookmanager.jsp");
                rd.forward(request, response);
            }
  ```
- This function will delete the book
```
if (action.equals("delete")) {
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    ex.getMessage();
                }
                if (id != 0) {
                    bookDAO.delete(id);
                }

                List<BookDTO> list = bookDAO.list(keyword, author);
                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("bookmanager.jsp");
                rd.forward(request, response);
            }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
Controller 
- CusController will controll all of the interaction with customer

-Hàm này sẽ list các sản phẩm ra khi được gọi rồi truyền dữ liệu đến trang chủ
```
if (action == null || action.equals("list")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
                rd.forward(request, response);
```
-This function will list all of the books when called and then point it to customer book list
```
if (action.equals("management")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("cusbooklist.jsp");
                rd.forward(request, response);
            }
```

-This function will get detail of books using its id
```
if(action.equals("details")){
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
            
```

-This function will get 1 data from book and add into cart 
```
if(action.equals("Add")){
                int id = 0;
                
                try{
                    id = Integer.parseInt(request.getParameter("id1"));
                }catch(NumberFormatException ex){
                    System.out.println("error " + ex.getMessage());
                }
                BookDTO book = null;
                if(id != 0){
                    book = bookDAO.load(id);
                }
                request.setAttribute("object", book);
                RequestDispatcher rd = request.getRequestDispatcher("Cart.jsp");
                rd.forward(request, response);
            }
```
-This function will get data from user and show it 
```
if (action.equals("profile")) {
                int id = 0;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                }
                UserDAO userDAO = new UserDAO();
                UserDTO user1 = null;
                if(id!=0){
                    user1 = userDAO.getuser(id);
                }
                request.setAttribute("object", user1);
RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            }
```
-This function will allow user to begin edit
```
if(action.equals("Edit")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                    
                }
                UserDTO user = null;
                UserDAO userDAO = new UserDAO();
                if(id != 0){
                    user = userDAO.getuser(id);
                }
                request.setAttribute("object", user);
                RequestDispatcher rd = request.getRequestDispatcher("editprofile.jsp");
                rd.forward(request, response);
            }
```
-This function get data from user and edit the profile
```
if (action.equals("updateprofile")){
                int id = 0;
                try{
                    id = Integer.parseInt(request.getParameter("id"));
                }catch(NumberFormatException ex){
                }
                String fullname = request.getParameter("fullname");
                String address = request.getParameter("address");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String day = request.getParameter("day");
                String month = request.getParameter("month");
                String year = request.getParameter("year");
                String dob = month+"/"+day+"/"+year;
                
                
                UserDAO userDAO = new UserDAO();
                UserDTO user = new UserDTO();
                user.setId(id);
                user.setFullname(fullname);
                user.setAddr(address);
                user.setPhone(phone);
                user.setEmail(email);
                user.setDOB(dob);
                user.setRole("Customer");
                
                
                userDAO.update(id, fullname, address, phone, email, dob);
                user=userDAO.getuser(id);
                request.setAttribute("object", user);
                RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                rd.forward(request, response);
            }
```
-------------------------------------------------------------------------------------------------------------------------------------------------
Controller 
- CartController will control the interactions with the cart

- This function will create cart and then add book, also check the book status before adding
```
HttpSession session = request.getSession(false);
        String action = request.getParameter("action");

        if (action == null)
        {
            CartDTO cart = null;
            Object object = session.getAttribute("cart");
            if (object != null)
            {
                cart = (CartDTO) object;
            } else
            {
                cart = new CartDTO();
            }
            try
            {
                int id = Integer.parseInt(request.getParameter("id"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                BookDAO bookDAO = new BookDAO();
                BookDTO book = bookDAO.load(id);
                
                if(book.getStatus().equals("Available")){
                    Item item = new Item(book, quantity);
                    cart.addItem(item);
                }else{
                    RequestDispatcher rd = request.getRequestDispatcher("book?action=details&check=false&id=" + id);
                    rd.forward(request, response);
                }

                session.setAttribute("cart", cart);
                session.setAttribute("total", cart.getTotalMoney());
                RequestDispatcher rd = request.getRequestDispatcher("book?action=details&check=true&id=" + id);
                rd.forward(request, response);

            } catch (NumberFormatException e)
            {
                e.getMessage();
            }
```
-This function will show the cart
```
if (action.equals("showcart"))
        {
            RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
            rd.forward(request, response);
        }
```
- This function will delete book from the cart 
```
if(action.equals("deletecart")){
            CartDTO cart = null;
            Object object = session.getAttribute("cart");
            if(object != null){
                cart = (CartDTO)object;
            }else{
                cart = new CartDTO();
            }
            try{
                int id = Integer.parseInt(request.getParameter("id"));
                cart.removeItem(id);
                List<Item> list = cart.getList();
                session.setAttribute("cart", cart);
                session.setAttribute("total", cart.getTotalMoney());
                response.sendRedirect("cart.jsp");
            }catch(NumberFormatException e){
                e.getMessage();
            }
        }
```
-------------------------------------------------------------------------------------------------------------------------------------------------



- View structure
<img src='img/view.png' width='500'> 

## 5. Conclustion

Sau đây sẽ là những lời tổng kết của các thành viên trong nhóm:
- Phan Hữu Đức: sau khi làm project này em mới nhận ra là trình độ và sự sắp xếp thời gian của mình còn yếu kém. Ngoài ra sự thiếu giao tiếp của em với những người trong group đã khiến cho những việc đươc giao cho em trở nên khó khăn hơn. Em nhận ra là về phần tư duy logic của mình thuộc dạng trung bình nhưng về phần code thì còn yếu kém so với các bạn khác. Sau khi học xong môn này lần thứ 2 và làm project cùng với bạn trong nhóm thì em đã học được là nên giao tiếp với bạn và nên code nhiều hơn để tự nâng trình độ của bản thân và tránh làm gánh nặng cho nhóm.

- Phạm Huỳnh Xuân Đăng: là một leader, em cảm thấy mình còn quá yếu kém trong việc quản lý nhân sự. Khi mình hoặc các thành viên không xác định được điểm mạnh và yếu của bản thân thì ta nên giao task VÀ DEADLINE cho họ, nếu họ không làm được trong khoản thời gian đó thì nên chuyển task đó ngay lập tức cho người khác xử lý, tránh mất mát thêm thời gian vô ích. Giao tiếp nhiều và khiến mọi người có động lực là những gì em cố gắng làm với mọi người nhưng vẫn chưa thực sự hiệu quả. Ngày đầu tiên mà mọi người lên thuyết trình sản phẩm thực sự khiến em cảm thấy như bị khủng hoảng tâm lý, vì so với sản phẩm của nhóm mình thì của mọi người ai cũng làm rất là tốt, ít nhất là về phần nhìn của sản phẩm. Em mong là những kinh nghiệm này sẽ giúp ích cho bản thân ở những kỳ sau này và cho đường đời còn dài phía trước.

Lê Anh Tuấn: qua môn học PRJ301, em đã tiếp thu được thêm rất nhiều kiến thức hữu ích về cách xây dựng trang jsp, việc điều phối các chức năng qua các controller, ngoài ra cũng là 1 dịp đáng giá để em nhìn nhận lại kiến thức về css và html của chính bản thân mình. Về cá nhân em, em đồng thời nhận ra các mặt thiếu sót về kĩ năng code và làm việc nhóm của bản thân mình, khả năng tự học của em vẫn còn kém trong phạm vi trong lớp. Cuối cùng thì em xin cảm ơn thầy và các bạn đã luôn support và đóng góp thêm những kinh nghiệm đi làm cũng như kĩ năng code cho nhóm em. Em xin cảm ơn!
