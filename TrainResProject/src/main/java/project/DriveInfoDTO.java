package project;

import java.sql.Date;
import java.sql.Timestamp;

public class DriveInfoDTO {
	private int driveId;
    private Date driveDate; // 운행 날짜
    private Timestamp deptTime; // 출발 시각 (시간 포함)
    private Timestamp arriTime; // 도착 시각 (시간 포함)
    private int trainId;        // 열차 ID (FK)
    private int routeId;        // 노선 ID (FK)
    
    // 기본 생성자
    public DriveInfoDTO() {}

    // 전체 필드 생성자
    public DriveInfoDTO(int driveId, Date driveDate, Timestamp deptTime, Timestamp arriTime, int trainId, int routeId) {
        this.driveId = driveId;
        this.driveDate = driveDate;
        this.deptTime = deptTime;
        this.arriTime = arriTime;
        this.trainId = trainId;
        this.routeId = routeId;
    }

    // Getter & Setter
    public int getDriveId() { return driveId; }
    public void setDriveId(int driveId) { this.driveId = driveId; }
    public Date getDriveDate() { return driveDate; }
    public void setDriveDate(Date driveDate) { this.driveDate = driveDate; }
    public Timestamp getDeptTime() { return deptTime; }
    public void setDeptTime(Timestamp deptTime) { this.deptTime = deptTime; }
    public Timestamp getArriTime() { return arriTime; }
    public void setArriTime(Timestamp arriTime) { this.arriTime = arriTime; }
    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }

    // toString()
    @Override
    public String toString() {
        return "DriveInfoDTO [driveId=" + driveId + ", driveDate=" + driveDate + ", deptTime=" + deptTime + ", arriTime=" + arriTime + "]";
    }
}
