package bookmall.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bookmall.vo.UserVo;

public class UserDao {

    // Create
    public boolean insert(UserVo userVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO user (name, phone_num, email, password) VALUES (?, ?, ?, ?)";

//            stmt = conn.prepareStatement(sql);
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, userVo.getName());
            stmt.setString(2, userVo.getPhoneNum());
            stmt.setString(3, userVo.getEmail());
            stmt.setString(4, userVo.getPassword());

            result = stmt.executeUpdate() > 0;

            if (result) {
                rs = stmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    userVo.setNo(generatedId); // UserVo 객체에 설정
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

    public List<UserVo> findAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UserVo> users = new ArrayList<>();

        try {
            conn = getConnection();

            String sql = "SELECT * FROM user";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UserVo userVo = new UserVo(
                        rs.getString("name"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(userVo);
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

        return users;
    }

    public UserVo getUserById(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserVo userVo = null;

        try {
            conn = getConnection();

            String sql = "SELECT * FROM user WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                userVo = new UserVo(
                        rs.getString("name"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("password")
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

        return userVo;
    }

    public boolean updateUser(int userId, UserVo userVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "UPDATE user SET name = ?, phone_num = ?, email = ?, password = ? WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userVo.getName());
            stmt.setString(2, userVo.getPhoneNum());
            stmt.setString(3, userVo.getEmail());
            stmt.setString(4, userVo.getPassword());
            stmt.setInt(5, userId);

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

    public boolean deleteByNo(Long userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM user WHERE user_id = ?";
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


    private Connection getConnection() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.0.22:3306/bookmall";
            conn = DriverManager.getConnection(url, "bookmall", "bookmall");

//            String sql = "show tables";
//            stmt = conn.prepareStatement(sql);
//            rs = stmt.executeQuery();
//
//            while (rs.next()){
//                System.out.println(rs.getString(1));
//            }


        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }
}
