package bookmall.dao;

import bookmall.vo.CategoryVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    // Create
    public boolean insert(CategoryVo categoryVo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO category (category_name) VALUES (?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, categoryVo.getCategory());
//            stmt.setInt(2, bookId);

            result = stmt.executeUpdate() > 0;
            if (result) {
                rs = stmt.getGeneratedKeys(); // 데이터베이스에서 생성된 키 가져오기
                if (rs.next()) {
                    long generatedId = rs.getLong(1); // AUTO_INCREMENT 값
                    categoryVo.setNo(generatedId); // UserVo 객체에 설정
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

    public List<CategoryVo> findAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CategoryVo> categories = new ArrayList<>();

        try {
            conn = getConnection();

            String sql = "SELECT c.category_name FROM category c ";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new CategoryVo(rs.getString("category_name")));
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

        return categories;
    }

    public CategoryVo getCategoryById(int categoryId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CategoryVo category = null;

        try {
            conn = getConnection();

            String sql = "SELECT c.category_name FROM category c WHERE c.category_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                category = new CategoryVo(rs.getString("category_name"));
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

        return category;
    }

    public boolean updateCategory(int categoryId, String categoryName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoryName);
            stmt.setInt(2, categoryId);

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

    public boolean deleteByNo(Long categoryId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            conn = getConnection();

            String sql = "DELETE FROM category WHERE category_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, categoryId);

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

