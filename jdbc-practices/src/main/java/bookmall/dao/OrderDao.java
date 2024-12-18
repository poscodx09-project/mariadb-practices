package bookmall.dao;

import bookmall.vo.BookVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;
import bookshop.example.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {


    public boolean insert(OrderVo orderVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO `order` (user_id, order_number, order_price, order_place, order_status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setLong(1, orderVo.getUserNo());
            stmt.setString(2, orderVo.getNumber());
            stmt.setInt(3, orderVo.getPayment());
            stmt.setString(4, orderVo.getShipping());
            stmt.setString(5, orderVo.getStatus());

            result = stmt.executeUpdate() > 0;

            if (result) {
                rs = stmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    orderVo.setNo(generatedId);
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

    public boolean insertBook(OrderBookVo orderBookVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO `orders_book` (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setLong(1, orderBookVo.getOrderNo());
            stmt.setLong(2, orderBookVo.getBookNo());
            stmt.setInt(3, orderBookVo.getQuantity());
            stmt.setInt(4, orderBookVo.getPrice());

            result = stmt.executeUpdate() > 0;

            if (result) {
                rs = stmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    orderBookVo.setOrderNo(generatedId);
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


    public OrderVo findByNoAndUserNo(Long orderId,Long userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        OrderVo order = null;

        try {
            conn = getConnection();

            String sql = "SELECT o.order_id, u.user_id AS user_id, o.order_number, o.order_status, o.order_price, o.order_place " +
                    "FROM `order` o " +
                    "JOIN user u ON o.user_id = u.user_id " +
                    "WHERE o.order_id = ? AND o.user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, orderId);
            stmt.setLong(2, userId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                order = new OrderVo(
                        rs.getLong("order_id"),
                        rs.getLong("user_id"),
                        rs.getString("order_number"),
                        rs.getString("order_status"),
                        rs.getInt("order_price"),
                        rs.getString("order_place")
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

        return order;
    }

    public List<OrderBookVo> findBooksByNoAndUserNo(Long orderId , Long userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        OrderBookVo orderBookVo = null;
        List<OrderBookVo> orderBookVoList = new ArrayList<>();

        try {
            conn = getConnection();

            String sql = "SELECT ob.order_id, ob.quantity, ob.price, ob.book_id " +
                    "FROM `orders_book` ob " +
                    "JOIN `book` b ON b.book_id = ob.book_id " +
                    "WHERE ob.order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, orderId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                orderBookVo = new OrderBookVo(
                        rs.getLong("order_id"),
                        rs.getInt("quantity"),
                        rs.getInt("price"),
                        rs.getLong("book_id")
                );
                orderBookVoList.add(orderBookVo);
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

        return orderBookVoList;
    }

//    public boolean updateOrder(int orderId, String orderStatus, int orderPrice, String orderPlace) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        boolean result = false;
//
//        try {
//            conn = getConnection();
//
//            String sql = "UPDATE `order` SET order_status = ?, order_price = ?, order_place = ? WHERE order_id = ?";
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, orderStatus);
//            stmt.setInt(2, orderPrice);
//            stmt.setString(3, orderPlace);
//            stmt.setInt(4, orderId);
//
//            result = stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return result;
//    }

    public boolean deleteByNo(Long orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM `order` WHERE order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, orderId);

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

    public boolean deleteBooksByNo(Long orderId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM `order` WHERE order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, orderId);

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

