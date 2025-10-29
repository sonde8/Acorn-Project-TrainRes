package customer;

import java.sql.*;

public class CustomerDAO {

 
 
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url    = "jdbc:oracle:thin:@localhost:1521:testdb";
    private final String user   = "scott";
    private final String pass   = "tiger";
 

    public CustomerDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection dbcon() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // 회원가입
    public int join(Customer c) {
        String sql = "INSERT INTO CUST (CUST_ID, NAME, BIRTH, PASSWORD) VALUES (?, ?, ?, ?)";
        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCustId());
            ps.setString(2, c.getName());
            ps.setDate(3, c.getBirth());
            ps.setString(4, c.getPassword());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 로그인
    public Customer login(String id, String pw) {
        String sql = "SELECT CUST_ID, NAME, BIRTH, PASSWORD FROM CUST WHERE CUST_ID = ? AND PASSWORD = ?";
        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, pw);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustId(rs.getString("CUST_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setBirth(rs.getDate("BIRTH"));
                    c.setPassword(rs.getString("PASSWORD"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 마이페이지용: 단일 회원 조회
    public Customer findById(String custId) {
        String sql = "SELECT CUST_ID, NAME, BIRTH, PASSWORD FROM CUST WHERE CUST_ID = ?";
        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustId(rs.getString("CUST_ID"));
                    c.setName(rs.getString("NAME"));
                    c.setBirth(rs.getDate("BIRTH"));
                    c.setPassword(rs.getString("PASSWORD"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 마이페이지에서 이름/비번 수정
    public int update(Customer c) {
        String sql = "UPDATE CUST SET NAME = ?, PASSWORD = ? WHERE CUST_ID = ?";
        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getCustId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
