package payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url    = "jdbc:oracle:thin:@localhost:1521:testdb"; // XE 환경이면 :XE 로 변경
    private final String user   = "scott";
    private final String pass   = "tiger";

    public PaymentDAO() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * ✅ [1] 운행(driveId)별 결제 전 정보 조회
     * 결제 직전 요금 표시용
     */
    public PaymentView findDriveForPay(long driveId) {
        String sql = """
            SELECT d.drive_id,
                   s1.station_name AS dept_name,
                   s2.station_name AS arri_name,
                   t.train_no,
                   TO_CHAR(d.dept_time, 'HH24:MI') AS dept_hm,
                   TO_CHAR(d.arri_time, 'HH24:MI') AS arri_hm,
                   r.price AS price_int
            FROM driveinfo d
            JOIN route r ON d.route_id = r.route_id
            JOIN station s1 ON r.dept_station = s1.station_id
            JOIN station s2 ON r.arri_station = s2.station_id
            JOIN train t ON d.train_id = t.train_id
            WHERE d.drive_id = ?
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, driveId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PaymentView v = new PaymentView();
                v.setDriveId(rs.getLong("drive_id"));
                v.setDeptStationName(rs.getString("dept_name"));
                v.setArriStationName(rs.getString("arri_name"));
                v.setTrainNo(rs.getString("train_no"));
                v.setDeptTime(rs.getString("dept_hm"));
                v.setArriTime(rs.getString("arri_hm"));
                v.setPrice(rs.getInt("price_int"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ✅ [2] 마이페이지용 결제내역 조회
     * (결제 테이블 없이 RES + ROUTE.PRICE 조인)
     */
    public List<PaymentView> findByCustomerId(String custId) {
        List<PaymentView> list = new ArrayList<>();

        String sql = """
            SELECT R.RES_ID,
                   T.TRAIN_NO,
                   S1.STATION_NAME AS DEPT_STATION,
                   S2.STATION_NAME AS ARRI_STATION,
                   TO_CHAR(D.DEPT_TIME, 'HH24:MI') AS DEPT_TIME,
                   TO_CHAR(D.ARRI_TIME, 'HH24:MI') AS ARRI_TIME,
                   RT.PRICE AS PRICE
            FROM RES R
            JOIN DRIVEINFO D ON R.DRIVE_ID = D.DRIVE_ID
            JOIN ROUTE RT ON D.ROUTE_ID = RT.ROUTE_ID
            JOIN TRAIN T ON D.TRAIN_ID = T.TRAIN_ID
            JOIN STATION S1 ON RT.DEPT_STATION = S1.STATION_ID
            JOIN STATION S2 ON RT.ARRI_STATION = S2.STATION_ID
            WHERE R.CUST_ID = ?
            ORDER BY R.RES_DATE DESC
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, custId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PaymentView p = new PaymentView();
                p.setDriveId(rs.getInt("RES_ID"));
                p.setTrainNo(rs.getString("TRAIN_NO"));
                p.setDeptStationName(rs.getString("DEPT_STATION"));
                p.setArriStationName(rs.getString("ARRI_STATION"));
                p.setDeptTime(rs.getString("DEPT_TIME"));
                p.setArriTime(rs.getString("ARRI_TIME"));
                p.setPrice(rs.getInt("PRICE"));
                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ 결제 내역 조회 실패");
            e.printStackTrace();
        }

        return list;
    }
}
