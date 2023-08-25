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
