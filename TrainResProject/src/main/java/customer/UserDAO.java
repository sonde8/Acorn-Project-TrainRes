package customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 


/*
  
CREATE TABLE CUST (
    CUST_ID VARCHAR2(50) PRIMARY KEY,
    NAME VARCHAR2(100) NOT NULL,
    BIRTH DATE,
    PASSWORD VARCHAR2(255) NOT NULL
);
 * 
 */
public class UserDAO {
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

    // DB 연결 정보
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:testdb";
    private static final String CUST = "scott";
    private static final String PASSWORD = "tiger";

 


	// DB 연결 메서드
    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, CUST, PASSWORD);
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
            pstmt.setDate(4,   new  java.sql.Date (newUser.getBirth().getTime()) ); 
            
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
         	int result  =dao.join(new UserDTO("k1" ,"홍길동", birth , "1234"));
         	
         	System.out.println( result);
		
	}
}


