package bookmall.dao;

import bookmall.vo.BookVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    // Create
    public boolean insert(BookVo bookVo) {

        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, bookVo.getTitle());
            pstmt.setInt(2, bookVo.getPrice());

            result = pstmt.executeUpdate() > 0;

            if (result) {
                rs = pstmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    bookVo.setNo(generatedId);
                    System.out.println("Generated ID: " + generatedId); // 로그 출력
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if(pstmt != null) {
                    pstmt.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Read (All Books)
    public List<BookVo> findAll() {
        List<BookVo> books = new ArrayList<>();
        boolean result = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();

            String sql = "SELECT * FROM book";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookVo bookVo = new BookVo(
                        rs.getString("title"),
                        rs.getInt("price")
                );
                books.add(bookVo);
            }

        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    // Read (Single Book by ID)
    public BookVo getBookById(int bookId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        BookVo bookVo = null;

        try {
            conn = getConnection();

            String sql = "SELECT * FROM book WHERE book_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                bookVo = new BookVo(
                        rs.getString("title"),
                        rs.getInt("price")
                );
            }

        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return bookVo;
    }


    // Update
    public boolean updateBook(int bookId, BookVo bookVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "UPDATE book SET title = ?, price = ? WHERE book_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, bookVo.getTitle());
            stmt.setInt(2, bookVo.getPrice());
            stmt.setInt(3, bookId);

            result = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public boolean deleteByNo(Long bookId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM book WHERE book_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, bookId);

            result = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    private Connection getConnection() throws SQLException{
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.0.22:3306/bookmall";
            conn = DriverManager.getConnection(url, "bookmall", "bookmall");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }
}
