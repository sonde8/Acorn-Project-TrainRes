package driveinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriveInfoDAO {

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

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

    // 페이징 + 조건 검색
    public ArrayList<DriveInfoResultDTO> findAllByRoutePaging(
            String deptName,
            String arriName,
            String startTimeFilter,
            int offset,
            int limit
    ) {

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
            "        WHERE S1.STATION_NAME = ? AND S2.STATION_NAME = ? ";

        if (startTimeFilter != null && !startTimeFilter.isEmpty()) {
            sql += " AND TO_CHAR(D.DEPT_TIME, 'HH24') >= ? ";
        }

        sql +=
            " ORDER BY DEPT_TIME_STR " +
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

            pst.setString(1, deptName);
            pst.setString(2, arriName);

            if (startTimeFilter != null && !startTimeFilter.isEmpty()) {
                pst.setString(3, startTimeFilter);
                pst.setInt(4, offset + limit);
                pst.setInt(5, offset);
            } else {
                pst.setInt(3, offset + limit);
                pst.setInt(4, offset);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DriveInfoResultDTO dto = new DriveInfoResultDTO();

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

                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            System.err.println("운행 정보 필터링 조회 중 SQL 오류 발생: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        }
        return list;
    }

    // 단일 운행 상세
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
            "WHERE D.DRIVE_ID = ?";

        try (Connection con = dbcon();
             PreparedStatement pst = con.prepareStatement(sql)) {

            if (con == null) {
                System.err.println("데이터베이스 연결 실패로 조회 작업을 수행할 수 없습니다.");
                return null;
            }

            pst.setInt(1, driveId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    dto = new DriveInfoResultDTO();

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
