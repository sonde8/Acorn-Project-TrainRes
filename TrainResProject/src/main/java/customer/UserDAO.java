package customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

    // DB 연결 정보
    // private static final String URL = "jdbc:oracle:thin:@localhost:1521:testdb";
    // private static final String CUST = "scott";
    // private static final String PASSWORD = "tiger";
    
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:testdb";
    private final String USER = "scott";
    private final String PASSWORD = "tiger";
    

	// DB 연결 메서드
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ DB 연결 성공");
        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패: " + e.getMessage());
        }
        return conn;
    }
    
    // 회원가입
    public int join(UserDTO newUser) {
    	System.out.println( "user insert" + newUser);
        int result = 0;
        String sql = "INSERT INTO CUST (CUST_ID, PASSWORD, NAME, BIRTH) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getCustId());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getName());
            pstmt.setDate(4, new java.sql.Date (newUser.getBirth().getTime()) ); 
            
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkId(String custId) {
        String sql = "SELECT COUNT(*) FROM CUST WHERE CUST_ID = ?";
        
        int count = 0; 
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, custId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1); 
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // DB 오류 발생 시, 가입 방지를 위해 중복으로 간주
            return true; 
        }
        
        return (count > 0); 
    }
    
    // 로그인 
    public UserDTO login(String id, String pw) {
        UserDTO cust = null;
        String sql = "SELECT * FROM CUST  WHERE CUST_ID =? AND PASSWORD =?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	cust = new UserDTO();
            	cust.setCustId(rs.getString(1));
            	cust.setName(rs.getString(2));
            	cust.setBirth(rs.getDate(3));
            	cust.setPassword(rs.getString(4));           		   
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cust;
    }
    
    
    public static void main(String[] args) {
    	
    	    UserDAO dao  =  new UserDAO();    	
    	    Date birth = Date.valueOf("2000-01-01"); 
    	    boolean isDuplicate = dao.checkId("k1");
            System.out.println("아이디 'k1' 중복 여부: " + isDuplicate);
            
         	//int result  =dao.join(new UserDTO("k1", "홍길동", birth, "1234"));
         	
         	//System.out.println(result);
		
	}
}


