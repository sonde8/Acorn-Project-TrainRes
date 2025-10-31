package payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

    public PaymentDAO() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 결제/예매 직전 보여줄 단일 운행 정보
    public PaymentView findDriveForPay(long driveId) {

        String sql =
            "SELECT d.drive_id, " +
            "       s1.station_name AS dept_name, " +
            "       s2.station_name AS arri_name, " +
            "       t.train_no, " +
            "       TO_CHAR(d.dept_time, 'YYYY-MM-DD HH24:MI') AS dept_hm_full, " +
            "       TO_CHAR(d.arri_time, 'YYYY-MM-DD HH24:MI') AS arri_hm_full, " +
            "       FLOOR(r.price) AS price_int " +
            "  FROM driveinfo d " +
            "  JOIN route r   ON d.route_id     = r.route_id " +
            "  JOIN station s1 ON r.dept_station = s1.station_id " +
            "  JOIN station s2 ON r.arri_station = s2.station_id " +
            "  JOIN train t   ON d.train_id     = t.train_id " +
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
                    v.setDeptTime(rs.getString("dept_hm_full"));
                    v.setArriTime(rs.getString("arri_hm_full"));
                    v.setPrice(rs.getInt("price_int"));

                    return v;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 마이페이지 '결제 내역' 조회용
    public List<PaymentView> findByCustomerId(String custId) {

        String sql =
            "SELECT D.DRIVE_ID, " +
            "       T.TRAIN_NO, " +
            "       S1.STATION_NAME AS DEPT_STATION_NAME, " +
            "       S2.STATION_NAME AS ARRI_STATION_NAME, " +
            "       TO_CHAR(D.DEPT_TIME, 'YYYY-MM-DD HH24:MI') AS DEPT_TIME_STR, " +
            "       TO_CHAR(D.ARRI_TIME, 'YYYY-MM-DD HH24:MI') AS ARRI_TIME_STR, " +
            "       FLOOR(RT.PRICE) AS PRICE_INT " +
            "  FROM RES R " +
            "  JOIN DRIVEINFO D ON R.DRIVE_ID = D.DRIVE_ID " +
            "  JOIN ROUTE RT    ON D.ROUTE_ID = RT.ROUTE_ID " +
            "  JOIN TRAIN T     ON D.TRAIN_ID = T.TRAIN_ID " +
            "  JOIN STATION S1  ON RT.DEPT_STATION = S1.STATION_ID " +
            "  JOIN STATION S2  ON RT.ARRI_STATION = S2.STATION_ID " +
            " WHERE R.CUST_ID = ? " +
            " ORDER BY R.RES_DATE DESC";

        List<PaymentView> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, custId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PaymentView v = new PaymentView();
                    v.setDriveId(rs.getLong("DRIVE_ID"));
                    v.setTrainNo(rs.getString("TRAIN_NO"));
                    v.setDeptStationName(rs.getString("DEPT_STATION_NAME"));
                    v.setArriStationName(rs.getString("ARRI_STATION_NAME"));
                    v.setDeptTime(rs.getString("DEPT_TIME_STR"));
                    v.setArriTime(rs.getString("ARRI_TIME_STR"));
                    v.setPrice(rs.getInt("PRICE_INT"));
                    list.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 결제 후 예약 insert
    public void insertReservation(OrderDTO order) throws SQLException {
        String sql =
            "INSERT INTO RES (RES_ID, CUST_ID, DRIVE_ID) " +
            "VALUES (SEQ_RES_ID.NEXTVAL, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, order.getCustId());
            ps.setInt(2, order.getDriveId());

            ps.executeUpdate();
        }
    }
}