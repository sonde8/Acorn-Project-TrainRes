package Seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class SeatDAO {

	private final String driver = "oracle.jdbc.driver.OracleDriver";
    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String user = "system";
    private final String password = "1234";

    // DB 연결하고 연결된 커넥션 객체를 반환
    public Connection dbcon() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // 특정 운행(drive_id), 특정 호차(car_no)의 좌석 목록 조회
    public ArrayList<Seat> SeatList(int drive_id, int car_no){

        Connection con = dbcon();
        ArrayList<Seat> SeatList  = new ArrayList<>();

        String sql = "SELECT * " +
                     "FROM SEAT " +
                     "WHERE drive_id = ? AND car_no = ? " +
                     "ORDER BY seat_no";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, drive_id);
            pst.setInt(2, car_no);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Seat s = new Seat();
                s.setSeat_id(rs.getInt("seat_id"));
                s.setDrive_id(rs.getInt("drive_id"));
                s.setCar_no(rs.getInt("car_no"));
                s.setSeat_no(rs.getString("seat_no"));
                s.setReserved(rs.getString("reserved"));
                s.setRes_id(rs.getString("res_id"));
                SeatList.add(s);
            }

            rs.close();
            pst.close();
            con.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return SeatList;
    }

    // 결제 확정 후 좌석 확정(해당 좌석을 reserved = 'Y')
    public boolean reserveSeat(int drive_id, int car_no, String seat_no, String cust_id ) {

        String sql =
            "UPDATE SEAT " +
            "SET reserved = 'Y', res_id = ? " +
            "WHERE drive_id = ? " +
            "  AND car_no = ? " +
            "  AND seat_no = ? " +
            "  AND reserved = 'N'";

        try (Connection con = dbcon();
             PreparedStatement pst = con.prepareStatement(sql)){

            if(cust_id == null) {
                pst.setNull(1, Types.VARCHAR);
            } else {
                pst.setString(1,  cust_id);
            }

            pst.setInt(2, drive_id);
            pst.setInt(3, car_no);
            pst.setString(4, seat_no);

            int update = pst.executeUpdate();
            return update > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
