package reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

    public ReservationDAO() {
 
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection dbcon() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
 
   
    }

    public List<Reservation> findByCustomerId(String custId) {
        List<Reservation> list = new ArrayList<>();

        String sql =
            "SELECT R.RES_ID, T.TRAIN_NO, " +
            "       S1.STATION_NAME AS DEPT_STATION, " +
            "       S2.STATION_NAME AS ARRI_STATION, " +
            "       D.DEPT_TIME, D.ARRI_TIME, R.RES_DATE, " +
            "       RT.PRICE AS AMOUNT " +
            "  FROM RES R " +
            "  JOIN DRIVEINFO D ON R.DRIVE_ID = D.DRIVE_ID " +
            "  JOIN ROUTE RT    ON D.ROUTE_ID = RT.ROUTE_ID " +
            "  JOIN TRAIN T     ON D.TRAIN_ID = T.TRAIN_ID " +
            "  JOIN STATION S1  ON RT.DEPT_STATION = S1.STATION_ID " +
            "  JOIN STATION S2  ON RT.ARRI_STATION = S2.STATION_ID " +
            " WHERE R.CUST_ID = ? " +
            " ORDER BY R.RES_DATE DESC";

        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, custId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reservation r = new Reservation();
                    r.setResId(rs.getInt("RES_ID"));
                    r.setTrainNo(rs.getString("TRAIN_NO"));
                    r.setDeptStation(rs.getString("DEPT_STATION"));
                    r.setArriStation(rs.getString("ARRI_STATION"));
                    r.setDeptTime(rs.getTimestamp("DEPT_TIME"));
                    r.setArriTime(rs.getTimestamp("ARRI_TIME"));
                    r.setResDate(rs.getTimestamp("RES_DATE"));
                    r.setAmount(rs.getInt("AMOUNT"));
                    list.add(r);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    public Reservation findByResId(int resId) {
        Reservation r = null;

        String sql =
            "SELECT R.RES_ID, R.CUST_ID, T.TRAIN_NO, " +
            "       S1.STATION_NAME AS DEPT_STATION, " +
            "       S2.STATION_NAME AS ARRI_STATION, " +
            "       D.DEPT_TIME, D.ARRI_TIME, R.RES_DATE, " +
            "       RT.PRICE AS AMOUNT " +
            "  FROM RES R " +
            "  JOIN DRIVEINFO D ON R.DRIVE_ID = D.DRIVE_ID " +
            "  JOIN ROUTE RT    ON D.ROUTE_ID = RT.ROUTE_ID " +
            "  JOIN TRAIN T     ON D.TRAIN_ID = T.TRAIN_ID " +
            "  JOIN STATION S1  ON RT.DEPT_STATION = S1.STATION_ID " +
            "  JOIN STATION S2  ON RT.ARRI_STATION = S2.STATION_ID " +
            " WHERE R.RES_ID = ?";

        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, resId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Reservation();
                    r.setResId(rs.getInt("RES_ID"));
                    r.setCustId(rs.getString("CUST_ID"));
                    r.setTrainNo(rs.getString("TRAIN_NO"));
                    r.setDeptStation(rs.getString("DEPT_STATION"));
                    r.setArriStation(rs.getString("ARRI_STATION"));
                    r.setDeptTime(rs.getTimestamp("DEPT_TIME"));
                    r.setArriTime(rs.getTimestamp("ARRI_TIME"));
                    r.setResDate(rs.getTimestamp("RES_DATE"));
                    r.setAmount(rs.getInt("AMOUNT"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return r;
    }

    // (안 써도 되지만 놔둬도 됨)
    public int insertReservation(String custId, int driveId) {
        String sql = "INSERT INTO RES (RES_ID, CUST_ID, DRIVE_ID, RES_DATE) " +
                     "VALUES (RES_SEQ.NEXTVAL, ?, ?, SYSDATE)";
        try (Connection con = dbcon();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, custId);
            ps.setInt(2, driveId);
            return ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
