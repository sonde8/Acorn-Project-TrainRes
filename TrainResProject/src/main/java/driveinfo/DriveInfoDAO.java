package driveinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal; 

public class DriveInfoDAO {
    // DB 접속 정보 (모델)
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

    // DB에 연결 후 Connection 객체 반환
    public Connection dbcon() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
        } catch (SQLException e2) {
            System.err.println("DB 연결 실패 (URL, ID, PW 확인): " + e2.getMessage());
        }
        return con;
    }
    
    // Paging 쿼리 (공백 문제 해결)
    public ArrayList<DriveInfoResultDTO> findAllByRoutePaging(String deptName, String arriName, String startTimeFilter, int offset, int limit) {
        
        String sql = 
            "SELECT * FROM ( " +
            "    SELECT /*+ INDEX_ASC(D PK_DRIVEINFO) */ " +
            "        ROWNUM AS RN, " + 
            "        T1.* " +
            "    FROM ( " +
            "        SELECT " +
            "            D.DRIVE_ID, " +
            "            S1.STATION_NAME AS DEPT_STATION, " +
            "            S2.STATION_NAME AS ARRI_STATION, " +
            "            T.TRAIN_NO, " +
            "            T.TRAIN_TYPE, " +
            "            TO_CHAR(D.DEPT_TIME, 'HH24') AS DEPT_HOUR, " +
            "            TO_CHAR(D.DEPT_TIME, 'HH24:MI') AS DEPT_TIME_STR, " +
            "            TO_CHAR(D.ARRI_TIME, 'HH24:MI') AS ARRI_TIME_STR, " +
            "            D.DEPT_TIME AS ORIGINAL_DEPT_TIME, " +
            "            D.ARRI_TIME AS ORIGINAL_ARRI_TIME, " +
            "            R.PRICE " +
            "        FROM DRIVEINFO D " +
            "        JOIN ROUTE R ON D.ROUTE_ID = R.ROUTE_ID " +
            "        JOIN STATION S1 ON R.DEPT_STATION = S1.STATION_ID " +
            "        JOIN STATION S2 ON R.ARRI_STATION = S2.STATION_ID " +
            "        JOIN TRAIN T ON D.TRAIN_ID = T.TRAIN_ID " +
            "        WHERE S1.STATION_NAME = ? AND S2.STATION_NAME = ? "; // <-- WHERE 절 끝에는 공백 유지
        
        // 시간 필터 조건 추가
        if (startTimeFilter != null && !startTimeFilter.isEmpty()) {
            sql += " AND TO_CHAR(D.DEPT_TIME, 'HH24') >= ? "; // <-- 뒤에 공백이 있어 안전함
        }
        
        // ORDER BY 및 ROWNUM 구문 연결 (앞에 공백 명시적으로 추가)
        sql += 
            " ORDER BY DEPT_TIME_STR " + // <-- ORDER BY 앞에 공백을 명시적으로 추가
            "    ) T1 " +                  
            "    WHERE ROWNUM <= ? " +      
            ") WHERE RN > ?";
            
        ArrayList<DriveInfoResultDTO> list = new ArrayList<>();
        
        try (Connection con = dbcon();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            if (con == null) {
                System.err.println("데이터베이스 연결 실패로 조회 작업을 수행할 수 없습니다.");
                return list;
            }

            // 노선 파라미터 설정
            pst.setString(1, deptName); 
            pst.setString(2, arriName);
            
            // 시간 필터 유무에 따라 파라미터 인덱스를 조정
            if (startTimeFilter != null && !startTimeFilter.isEmpty()) {
                // 시간 필터가 있을 경우 (? 5개): 3, 4, 5번에 할당
                pst.setString(3, startTimeFilter);
                
                // 페이징 파라미터 설정
                pst.setInt(4, offset + limit); // ROWNUM <= ? (4번)
                pst.setInt(5, offset);        // RN > ? (5번)
            } else {
                // 시간 필터가 없을 경우 (? 4개): 3, 4번에 할당
                // 페이징 파라미터 설정
                pst.setInt(3, offset + limit); // ROWNUM <= ? (3번)
                pst.setInt(4, offset);        // RN > ? (4번)
            }
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DriveInfoResultDTO dto = new DriveInfoResultDTO();
                    
                    // 기본 ID 및 이름 정보
                    dto.setDriveId(rs.getInt("DRIVE_ID"));
                    dto.setDeptStation(rs.getString("DEPT_STATION"));
                    dto.setArriStation(rs.getString("ARRI_STATION"));
                    
                    // 열차 정보 및 타입
                    dto.setTrainNo(rs.getString("TRAIN_NO"));
                    dto.setTrainType(rs.getString("TRAIN_TYPE"));
                    
                    // 시간 정보 및 포맷된 시간
                    dto.setDeptHour(rs.getString("DEPT_HOUR"));
                    dto.setFormattedDeptTime(rs.getString("DEPT_TIME_STR")); 
                    dto.setFormattedArriTime(rs.getString("ARRI_TIME_STR"));
                    
                    // 원본 Timestamp
                    dto.setOriginalDeptTime(rs.getTimestamp("ORIGINAL_DEPT_TIME")); 
                    dto.setOriginalArriTime(rs.getTimestamp("ORIGINAL_ARRI_TIME"));
                    
                    // 금액
                    dto.setPrice(rs.getBigDecimal("PRICE")); 
                    
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            // SQL 오류 상세 정보를 콘솔에 출력하여 디버깅을 돕습니다.
            System.err.println("운행 정보 필터링 조회 중 SQL 오류 발생: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace(); // 상세 스택 추적을 통해 원인 파악
        }
        return list;
    }
    
    // DRIVE_ID로 단일 운행 정보 상세 조회
    public DriveInfoResultDTO findByDriveId(int driveId) {
        DriveInfoResultDTO dto = null;
        
        String sql = 
            "SELECT " +
            "    D.DRIVE_ID, " +
            "    S1.STATION_NAME AS DEPT_STATION, " +
            "    S2.STATION_NAME AS ARRI_STATION, " +
            "    T.TRAIN_NO, " +
            "    T.TRAIN_TYPE, " +
            "    TO_CHAR(D.DEPT_TIME, 'HH24') AS DEPT_HOUR, " +
            "    TO_CHAR(D.DEPT_TIME, 'HH24:MI') AS DEPT_TIME_STR, " +
            "    TO_CHAR(D.ARRI_TIME, 'HH24:MI') AS ARRI_TIME_STR, " +
            "    D.DEPT_TIME AS ORIGINAL_DEPT_TIME, " +
            "    D.ARRI_TIME AS ORIGINAL_ARRI_TIME, " +
            "    R.PRICE " +
            "FROM DRIVEINFO D " +
            "JOIN ROUTE R ON D.ROUTE_ID = R.ROUTE_ID " +
            "JOIN STATION S1 ON R.DEPT_STATION = S1.STATION_ID " +
            "JOIN STATION S2 ON R.ARRI_STATION = S2.STATION_ID " +
            "JOIN TRAIN T ON D.TRAIN_ID = T.TRAIN_ID " +
            "WHERE D.DRIVE_ID = ?"; // DRIVE_ID로 단일 조회
            
        try (Connection con = dbcon();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            if (con == null) {
                System.err.println("데이터베이스 연결 실패로 조회 작업을 수행할 수 없습니다.");
                return null;
            }

            // 파라미터 설정 (DRIVE_ID)
            pst.setInt(1, driveId);
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) { // 단일 결과만 필요하므로 if 사용
                    dto = new DriveInfoResultDTO();
                    
                    // 결과 매핑: DTO에 조회된 모든 값을 담습니다.
                    dto.setDriveId(rs.getInt("DRIVE_ID"));
                    dto.setDeptStation(rs.getString("DEPT_STATION"));
                    dto.setArriStation(rs.getString("ARRI_STATION"));
                    dto.setTrainNo(rs.getString("TRAIN_NO"));
                    dto.setTrainType(rs.getString("TRAIN_TYPE"));
                    dto.setDeptHour(rs.getString("DEPT_HOUR"));
                    dto.setFormattedDeptTime(rs.getString("DEPT_TIME_STR"));
                    dto.setFormattedArriTime(rs.getString("ARRI_TIME_STR"));
                    dto.setOriginalDeptTime(rs.getTimestamp("ORIGINAL_DEPT_TIME"));
                    dto.setOriginalArriTime(rs.getTimestamp("ORIGINAL_ARRI_TIME"));
                    dto.setPrice(rs.getBigDecimal("PRICE"));
                }
            }
        } catch (SQLException e) {
            System.err.println("DRIVE_ID 상세 조회 중 SQL 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return dto;
    }
}