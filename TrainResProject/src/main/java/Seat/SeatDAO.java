package Seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class SeatDAO {

	private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "system";
    private final String PASSWORD = "1234";

    // DB 연결하고 연결된 커넥션 객체를 반환
    public Connection dbcon() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
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

        String sql = "SELECT seat_id, drive_id, car_no, seat_no, reserved, res_id " +
                    "FROM (" +
                   "SELECT s.*, ROW_NUMBER() OVER (" +
                   "PARTITION BY drive_id, car_no, seat_no " +
                   "ORDER BY CASE WHEN reserved='Y' THEN 1 ELSE 2 END, seat_id DESC" +
                   ") rn " +
                   "FROM seat s " +
                   "WHERE drive_id=? AND car_no=? " +
                   ") " +
                   "WHERE rn=1 " +
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
 /*   public boolean reserveSeat(int drive_id, int car_no, String seat_no, String cust_id ) {

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
    }*/
    
 // 1) RES에 한 줄 만들고 RES_ID 반환 (동일 커넥션에서 호출)
    private int insertReservation(Connection con, String custId, int driveId) throws SQLException {
        // Oracle: 시퀀스에서 번호 먼저 받고 INSERT
        int resId;
        try (PreparedStatement ps1 = con.prepareStatement("SELECT SEQ_RES_ID.NEXTVAL FROM DUAL");
             ResultSet rs = ps1.executeQuery()) {
            rs.next();
            resId = rs.getInt(1);
        }

        try (PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO RES (RES_ID, CUST_ID, DRIVE_ID) VALUES (?, ?, ?)")) {
            ps2.setInt(1, resId);
            ps2.setString(2, custId);
            ps2.setInt(3, driveId);
            ps2.executeUpdate();
        }
        return resId;
    }
    
 // 2) 결제 승인 시 좌석 확정(트랜잭션)
//  - RES INSERT → 좌석 reserved='Y' + res_id 세팅
    public boolean confirmReserveSeatTransactional(int drive_id, int car_no, String seat_no, String cust_id) {
        try (Connection con = dbcon()) {
            con.setAutoCommit(false);

            // 1) RES INSERT
            int resId = insertReservation(con, cust_id, drive_id);

            // 2) 좌석 UPSERT (없으면 INSERT Y, 있으면 N일 때 UPDATE Y)
            String sql =
                "MERGE INTO seat s " +
                "USING (SELECT ? AS drive_id, ? AS car_no, ? AS seat_no FROM dual) d " +
                "ON (s.drive_id=d.drive_id AND s.car_no=d.car_no AND s.seat_no=d.seat_no) " +
                "WHEN MATCHED THEN " +
                "  UPDATE SET s.reserved='Y', s.res_id=? " +
                "  WHERE s.reserved='N' " +
                "WHEN NOT MATCHED THEN " +
                "  INSERT (seat_id, drive_id, car_no, seat_no, reserved, res_id) " +
                "  VALUES (SEQ_SEAT_ID.NEXTVAL, d.drive_id, d.car_no, d.seat_no, 'Y', ?)";
            
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, drive_id);
                pst.setInt(2, car_no);
                pst.setString(3, seat_no);
                pst.setInt(4, resId);
                pst.setInt(5, resId);

                int affected = pst.executeUpdate();

                if (affected == 1) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}