package driveinfo;

import java.sql.Date;
import java.sql.Timestamp;

public class DriveInfoDTO {

    private int driveId;
    private Date driveDate;
    private Timestamp deptTime;
    private Timestamp arriTime;
    private int trainId;
    private int routeId;

    public DriveInfoDTO() {}

    public DriveInfoDTO(int driveId, Date driveDate, Timestamp deptTime, Timestamp arriTime, int trainId, int routeId) {
        this.driveId = driveId;
        this.driveDate = driveDate;
        this.deptTime = deptTime;
        this.arriTime = arriTime;
        this.trainId = trainId;
        this.routeId = routeId;
    }

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

    @Override
    public String toString() {
        return "DriveInfoDTO [driveId=" + driveId + ", driveDate=" + driveDate + ", deptTime=" + deptTime + ", arriTime=" + arriTime + "]";
    }
}
