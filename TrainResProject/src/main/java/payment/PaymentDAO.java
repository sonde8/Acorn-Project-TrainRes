package payment;

import java.sql.*;
 

 
public class PaymentDAO {

    // 순수 JDBC 접속 정보
    private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url    = "jdbc:oracle:thin:@localhost:1521:testdb"; // 예시
    private final String user   = "scott";
    private final String pass   = "tiger";

    public PaymentDAO() {
        try {
            Class.forName(driver); // 드라이버 로드(신버전은 생략 가능하지만 안전하게)
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public PaymentView findDriveForPay(long driveId) {
        String sql =
            "SELECT d.drive_id, " +
            "       s1.station_name AS dept_name, " +
            "       s2.station_name AS arri_name, " +
            "       t.train_no, " +
            "       TO_CHAR(d.dept_time, 'HH24:MI') AS dept_hm, " +
            "       TO_CHAR(d.arri_time, 'HH24:MI') AS arri_hm, " +
            "       FLOOR(r.price) AS price_int " + // total_amount int 변환용
            "  FROM driveinfo d " +
            "  JOIN route r ON d.route_id = r.route_id " +
            "  JOIN station s1 ON r.dept_station = s1.station_id " +
            "  JOIN station s2 ON r.arri_station = s2.station_id " +
            "  JOIN train t ON d.train_id = t.train_id " +
            " WHERE d.drive_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, driveId);

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
