package driveinfo;

import java.sql.Timestamp;

public class ResDTO {
	private int resId;
    private Timestamp resDate; // 예약 일시 (자동 입력되지만 DTO에는 포함)
    private String custId;     // 고객 ID (FK)
    private int driveId;       // 운행 정보 ID (FK)
    
    // 기본 생성자
    public ResDTO() {}

    // 전체 필드 생성자
    public ResDTO(int resId, Timestamp resDate, String custId, int driveId) {
        this.resId = resId;
        this.resDate = resDate;
        this.custId = custId;
        this.driveId = driveId;
    }

    // Getter & Setter
    public int getResId() { return resId; }
    public void setResId(int resId) { this.resId = resId; }
    public Timestamp getResDate() { return resDate; }
    public void setResDate(Timestamp resDate) { this.resDate = resDate; }
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public int getDriveId() { return driveId; }
    public void setDriveId(int driveId) { this.driveId = driveId; }

    // toString()
    @Override
    public String toString() {
        return "ResDTO [resId=" + resId + ", resDate=" + resDate + ", custId=" + custId + ", driveId=" + driveId + "]";
    }
}
