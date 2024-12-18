package bookmall.dao;

import bookmall.vo.CartVo;
import bookmall.vo.UserVo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    // Create
    public boolean insert(CartVo cartVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO cart (user_id, book_id, qty) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, cartVo.getUserNo());
            stmt.setLong(2, cartVo.getBookNo());
            stmt.setInt(3, cartVo.getQuantity());

            result = stmt.executeUpdate() > 0;

            if (result) {
                rs = stmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    cartVo.setNo(generatedId); // UserVo 객체에 설정
                    System.out.println("Generated ID: " + generatedId); // 로그 출력
                }
            }

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

    public List<CartVo> findByUserNo(Long userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CartVo> carts = new ArrayList<>();

        try {
            conn = getConnection();

            String sql = "SELECT c.cart_id,c.user_id,c.book_id, c.qty ,b.title " +
                    "FROM `cart` c " +
                    "JOIN `book` b ON c.book_id = b.book_id " +
                    "WHERE c.user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                CartVo cartVo = new CartVo(
                        rs.getLong("cart_id"),
                        rs.getLong("user_id"),
                        rs.getLong("book_id"),
                        rs.getInt("qty"),
                        rs.getString("title")
                );

                carts.add(cartVo);
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

        return carts;
    }

    public boolean updateCart(int cartId, int qty) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "UPDATE cart SET qty = ? WHERE cart_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qty);
            stmt.setInt(2, cartId);

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

    public boolean deleteByUserNoAndBookNo(Long userId,Long bookId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM cart WHERE cart_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);

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


    public boolean deleteCart(int cartId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM cart WHERE cart_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cartId);

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

