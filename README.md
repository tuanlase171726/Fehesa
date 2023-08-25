# Fehese

## 1.Introduction

- Project purpose: The vision of Fehese is to provide a convenient and user-friendly platform for book lovers to browse, buy and review books online. Fehese aims to offer a wide range of books from various genres, authors and publishers, as well as personalized recommendations based on the user’s preferences and reading history. The web application also enables users to interact with other readers, share their opinions and feedback, and join online book clubs and events. The web application strives to create a vibrant and engaging community of book enthusiasts who can enjoy reading and learning from books anytime and anywhere.

- Project actors:
  - Admin(ad)
  - Customer(cus)

- Functions:

| Actor | Functions | Description |
| --- | --- | --- |
| ad, cus | login | allows user to access their personal account on Fehese database |
| ad, cus | sort category | allows users to organize the books displayed on the web application according to different criteria |
| ad, cus | search book | allows users to find specific books or books related to a certain keyword on the web application |
| ad, cus | customize profile | enables users to edit their profile information, such as their name, email, address, phone number, password, and more |
| ad | add books | allows the web application’s administrators to upload new books to the web application’s database |
| ad | delete books | allows the web application’s administrators to remove books from the web application’s database | 
| ad | manage orders | allows admin to process, track, view order's details and update order's status such as confirmed, delivered |
| cus | add to cart | allows users to select and save the books they want to buy on the web application, it also allows users to view, edit, or remove the books in their shopping cart |

## 2.GUI/SITEMAPS
## - SITEMAPS
<img src='img/sitemap.jpg' width='500' height='300'>

## - GUI/LOGIN
<img src='img/login.jpg' width='500' height='600'>

## - GUI/REGISTER
<img src='img/register.jpg' width='500' height='600'>

## - GUI/HOMEPAGE
<img src='img/mainpage.jpg' width='500' height='600'>

## - GUI/DETAIL
<img src='img/detail.jpg' width='500' height='600'>

## - GUI/CART
<img src='img/cart.jpg' width='500' height='600'>

## 3.ERD
<img src='img/erd.jpg' width='500' height='200'>

-Trong BookDTO sẽ chứa những dữ liệu
```
    private int id;
    private String name,author,category;
    private int authorID, categoryID;
    private int price;
    private String status;
    private String description;
    private String img_url;
```
-Trong bookDAO sẽ chứa các chức năng

--Tìm kiếm tên sách và tên tác giả sau đó list ra dữ liệu bảng Books
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
--Thêm 1 dữ liệu Books
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
--Sửa 1 dữ liệu bảng Books thông qua id của nó
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
--Lấy 1 dữ liệu của Books thông qua id của nó
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
--Xóa 1 dữ liệu 
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

Controller sẽ thực hiện các hành động đã được đề ra theo tên của nó
- CusController sẽ quản lý các hành động liên quan tới tương tác của khách hàng

-Hàm này sẽ list các sản phẩm ra khi được gọi rồi truyền dữ liệu đến trang chủ
```
if (action == null || action.equals("list")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("homepage.jsp");
                rd.forward(request, response);
```
-Hàm này sẽ list các sản phẩm ra khi được gọi rồi truyền dữ liệu đến trang quản lý sách (customer feature)
```
if (action.equals("management")) {
                List<BookDTO> list = bookDAO.list(keyword, author);

                request.setAttribute("list", list);
                RequestDispatcher rd = request.getRequestDispatcher("cusbooklist.jsp");
                rd.forward(request, response);
            }
```

-Hàm này sẽ lấy detail của 1 dữ liệu Books thông qua id của nó
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

-Hàm này sẽ lấy dữ liệu của 1 books và thêm vào giỏ
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
-Hàm này sẽ lấy dữ liệu của user và hiện ra
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
-Hàm này cho phép người dùng edit lại profile của mình
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
-Hàm này sẽ lưu lại dữ liệu người dùng đã nhập qua bằng edit và sửa lại profile
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

Controller sẽ thực hiện các hành động đã được đề ra theo tên của nó
- LoginController sẽ quản lý các hành động liên quan tới login của khách hàng lẫn admin

-Hàm này sẽ thoát tài khoản của người dùng
```
public void setAdmin(int user_id, String isAdmin){
        String sql = "update users set isAdmin= ? where user_id = ?";
        try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setInt(2, user_id);
        ps.setString(1, isAdmin.toUpperCase());
        ps.executeUpdate();
        } catch (Exception e) {
        }
        
    }
```
-Hàm này sẽ check xem người dùng là admin hay là customer
```
if(action.equals("checklogin")){

                log("Debug user : " + user + " " + password);

                if (user == null && password == null) {

                    log("Debug user : Go to login " + user + " " + password);
                    RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                    rd.forward(request, response);
                } else {

                    log("Debug user : Go to here " + user + " " + password);
                    UserDAO userDAO = new UserDAO();
                    UserDTO userDTO = userDAO.login(user, password);
                    
                    if (userDTO != null) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("usersession", userDTO);
                        if (user.equals("admin")) {
                            response.sendRedirect("book?action=management");
                        } else {
                            response.sendRedirect("Cus");
                        }

                    } else {
                        request.setAttribute("error", "Wrong username or password");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                    }
                }
            }
```
-Hàm này sẽ cho phép người dùng đăng kí tài khoản
```
if (action.equals("Register")) {
                String name = request.getParameter("username");
                String pass = request.getParameter("password");
                String confpass = request.getParameter("confpassword");
                if (name.isEmpty() || pass.isEmpty() || confpass.isEmpty()) {
                    request.setAttribute("message", "Please fill all the box");
                    request.getRequestDispatcher("Register.jsp").forward(request, response);
                }
                if (!pass.equals(confpass)) {
                    request.setAttribute("message", "Password does not match!");
                    request.getRequestDispatcher("Register.jsp").forward(request, response);
                } else {
                    UserDAO userDAO = new UserDAO();
                    UserDTO user1 = userDAO.checkAcc(name);
                    if (user1 == null) {
                        userDAO.register(name, password);
                        request.setAttribute("message", "Register successfully");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {
                        request.setAttribute("message", "User name existed");
                        request.getRequestDispatcher("Register.jsp").forward(request, response);
                    }
                }
            }
```

-Trong UserDTO sẽ chứa những dữ liệu
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
-Trong UserDAO sẽ chứa các chức năng
 -Cho phép người dùng login vào web
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
-Cho phép người dùng đăng kí
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
- Kiểm tra xem tài khoản người dùng tạo ra đã có trong db hay chưa
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
- Chỉnh lai thông tin của người dùng
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

-Lấy thông tin của người dùng thông qua id
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
CONCLUSION
Phan Hữu Đức: sau khi làm project này em mới nhận ra là trình độ và sự sắp xếp thời gian của mình còn yếu kém. Ngoài ra sự thiếu giáo tiếp của em với nhừng người trong group đã khiến cho những việc đươc giao cho em trở nên khó khăn hơn. Em nhận ra là về phần tư duy logic của mình thuộc dạng trung bình nhưng về phần code thì còn yếu kém so với các bạn khác. Sau khi học xong môn này lần thứ 2 và làm project cùng với bạn trong nhóm thì em đã học được là nên giao tiếp với bạn và nên code nhiều hơn để tự nâng trình độ của bản thân và tránh làm gánh nặng cho nhóm.

- Phạm Huỳnh Xuân Đăng: là một leader, em cảm thấy mình còn quá yếu kém trong việc quản lý nhân sự. Khi mình hoặc các thành viên không xác định được điểm mạnh và yếu của bản thân thì ta nên giao task VÀ DEADLINE cho họ, nếu họ không làm được trong khoản thời gian đó thì nên chuyển task đó ngay lập tức cho người khác xử lý, tránh mất mát thêm thời gian vô ích. Giao tiếp nhiều và khiến mọi người có động lực là những gì em cố gắng làm với mọi người nhưng vẫn chưa thực sự hiệu quả. Ngày đầu tiên mà mọi người lên thuyết trình sản phẩm thực sự khiến em cảm thấy như bị khủng hoảng tâm lý, vì so với sản phẩm của nhóm mình thì của mọi người ai cũng làm rất là tốt, ít nhất là về phần nhìn của sản phẩm. Em mong là những kinh nghiệm này sẽ giúp ích cho bản thân ở những kỳ sau này và cho đường đời còn dài phía trước.

Lê Anh Tuấn: qua môn học PRJ301, em đã tiếp thu được thêm rất nhiều kiến thức hữu ích về cách xây dựng trang jsp, việc điều phối các chức năng qua các controller, ngoài ra cũng là 1 dịp đáng giá để em nhìn nhận lại kiến thức về css và html của chính bản thân mình. Về cá nhân em, em đồng thời nhận ra các mặt thiếu sót về kĩ năng code và làm việc nhóm của bản thân mình, khả năng tự học của em vẫn còn kém trong phạm vi trong lớp. Cuối cùng thì em xin cảm ơn thầy và các bạn đã luôn support và đóng góp thêm những kinh nghiệm đi làm cũng như kĩ năng code cho nhóm em. Em xin cảm ơn!

