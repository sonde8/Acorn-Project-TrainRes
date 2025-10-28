package customer;

import java.sql.*;
import java.sql.Date;

public class CustomerDAO {

    // ✅ Oracle 11g XE 버전 환경
	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

    // ✅ DB 연결 메서드
    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ 드라이버 로드 실패: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ DB 연결 실패: " + e.getMessage());
        }
        return conn;
    }

    // ✅ 회원가입
    public int join(Customer newUser) {
        int result = 0;
        String sql = "INSERT INTO CUST (CUST_ID, PASSWORD, NAME, BIRTH) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newUser.getCustId());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getName());
            pstmt.setDate(4, new java.sql.Date(newUser.getBirth().getTime()));

            result = pstmt.executeUpdate();
            System.out.println("✅ 회원가입 성공: " + newUser.getCustId());
        } catch (SQLException e) {
            System.out.println("❌ 회원가입 중 오류 발생: " + e.getMessage());
        }
        return result;
    }

    // ✅ 로그인
    public Customer login(String id, String pw) {
        Customer cust = null;
        String sql = "SELECT * FROM CUST WHERE CUST_ID = ? AND PASSWORD = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cust = new Customer();
                cust.setCustId(rs.getString("CUST_ID"));
                cust.setPassword(rs.getString("PASSWORD"));
                cust.setName(rs.getString("NAME"));
                cust.setBirth(rs.getDate("BIRTH"));
                System.out.println("✅ 로그인 성공: " + id);
            } else {
                System.out.println("⚠ 로그인 실패: 아이디 또는 비밀번호 불일치");
            }

        } catch (SQLException e) {
            System.out.println("❌ 로그인 중 오류: " + e.getMessage());
        }
        return cust;
    }

    // ✅ ID로 회원 조회
    public Customer findById(String custId) {
        Customer c = null;
        String sql = "SELECT * FROM CUST WHERE CUST_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, custId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                c = new Customer(
                    rs.getString("CUST_ID"),
                    rs.getString("NAME"),
                    rs.getDate("BIRTH"),
                    rs.getString("PASSWORD")
                );
                System.out.println("✅ 회원 조회 성공: " + custId);
            } else {
                System.out.println("⚠ 조회 결과 없음: " + custId);
            }
        } catch (SQLException e) {
            System.out.println("❌ 회원 조회 중 오류: " + e.getMessage());
        }
        return c;
    }

    // ✅ 회원 정보 수정
    public int update(Customer c) {
        int result = 0;
        String sql = "UPDATE CUST SET NAME = ?, PASSWORD = ? WHERE CUST_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getPassword());
            pstmt.setString(3, c.getCustId());
            result = pstmt.executeUpdate();
            System.out.println("✅ 회원 정보 수정 성공: " + c.getCustId());
        } catch (SQLException e) {
            System.out.println("❌ 회원 정보 수정 중 오류: " + e.getMessage());
        }
        return result;
    }

    // ✅ 단독 테스트 (JVM 실행용)
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        Date birth = Date.valueOf("2000-01-01");

        // (1) 회원가입 테스트
        int result = dao.join(new Customer("k1", "홍길동", birth, "1234"));
        System.out.println("회원가입 결과: " + result);

        // (2) 로그인 테스트
        Customer c = dao.login("k1", "1234");
        System.out.println("로그인 결과: " + (c != null ? c.getName() : "실패"));

        // (3) 회원 조회 테스트
        Customer c2 = dao.findById("k1");
        System.out.println("조회 결과: " + (c2 != null ? c2.getName() : "없음"));
    }
}
