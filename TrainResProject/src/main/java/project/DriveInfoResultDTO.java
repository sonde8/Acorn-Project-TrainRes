package project;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DriveInfoResultDTO {
	
	private int driveId;
	private String deptStation;
	private String arriStation;
	private String trainNo;
	private String trainType;
	
	// 화면 출력용
	private String formattedDeptTime;
	private String formattedArriTime;
	
	// 소요 시간 계산을 위한 Timestamp
	private Timestamp originalDeptTime;
	private Timestamp originalArriTime;
	private String durationStr;
	
	// 금액 정보 
	private BigDecimal price;
	
	// 운행편을 조회시 시간으로 조회할 때 사용할 hour만 저장하는 필드 추가
	private String deptHour;

	
	// 기본 생성자
	public DriveInfoResultDTO() {
	}
	
	public DriveInfoResultDTO(int driveId, String deptStation, String arriStation, String trainNo, String trainType,
			String formattedDeptTime, String formattedArriTime, Timestamp originalDeptTime, Timestamp originalArriTime,
			String durationStr, BigDecimal price) {
		super();
		this.driveId = driveId;
		this.deptStation = deptStation;
		this.arriStation = arriStation;
		this.trainNo = trainNo;
		this.trainType = trainType;
		this.formattedDeptTime = formattedDeptTime;
		this.formattedArriTime = formattedArriTime;
		this.originalDeptTime = originalDeptTime;
		this.originalArriTime = originalArriTime;
		this.durationStr = durationStr;
		this.price = price;
	}
	
	// Getter Setter
	public int getDriveId() {
		return driveId;
	}


	public void setDriveId(int driveId) {
		this.driveId = driveId;
	}


	public String getDeptStation() {
		return deptStation;
	}


	public void setDeptStation(String deptStation) {
		this.deptStation = deptStation;
	}


	public String getArriStation() {
		return arriStation;
	}


	public void setArriStation(String arriStation) {
		this.arriStation = arriStation;
	}


	public String getTrainNo() {
		return trainNo;
	}


	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}


	public String getTrainType() {
		return trainType;
	}


	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}


	public String getFormattedDeptTime() {
		return formattedDeptTime;
	}


	public void setFormattedDeptTime(String formattedDeptTime) {
		this.formattedDeptTime = formattedDeptTime;
	}


	public String getFormattedArriTime() {
		return formattedArriTime;
	}


	public void setFormattedArriTime(String formattedArriTime) {
		this.formattedArriTime = formattedArriTime;
	}


	public Timestamp getOriginalDeptTime() {
		return originalDeptTime;
	}


	public void setOriginalDeptTime(Timestamp originalDeptTime) {
		this.originalDeptTime = originalDeptTime;
	}


	public Timestamp getOriginalArriTime() {
		return originalArriTime;
	}


	public void setOriginalArriTime(Timestamp originalArriTime) {
		this.originalArriTime = originalArriTime;
	}


	public String getDurationStr() {
		return durationStr;
	}


	public void setDurationStr(String durationStr) {
		this.durationStr = durationStr;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	// '시' Getter Setter
	public String getDeptHour() {
		return deptHour;
	}
	
	public void setDeptHour(String deptHour) {
		this.deptHour = deptHour;
	}
	
	
	
	// toString
	@Override
	public String toString() {
		return "DriveInfoResultDTO [driveId=" + driveId + ", deptStation=" + deptStation + ", arriStation="
				+ arriStation + ", trainNo=" + trainNo + ", trainType=" + trainType + ", formattedDeptTime="
				+ formattedDeptTime + ", formattedArriTime=" + formattedArriTime + ", originalDeptTime="
				+ originalDeptTime + ", originalArriTime=" + originalArriTime + ", durationStr=" + durationStr
				+ ", price=" + price + "]";
	}
}
