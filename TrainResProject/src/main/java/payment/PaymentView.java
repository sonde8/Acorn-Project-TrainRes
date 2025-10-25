package payment;

 
 
public class PaymentView {
    private long driveId;
    private String deptStationName;
    private String arriStationName;
    private String trainNo;
    private String deptTime;   // "HH:mm"
    private String arriTime;   // "HH:mm"
    private int price;         // ��ȭ ���� (��: 20900)
	public long getDriveId() {
		return driveId;
	}
	public void setDriveId(long driveId) {
		this.driveId = driveId;
	}
	public String getDeptStationName() {
		return deptStationName;
	}
	public void setDeptStationName(String deptStationName) {
		this.deptStationName = deptStationName;
	}
	public String getArriStationName() {
		return arriStationName;
	}
	public void setArriStationName(String arriStationName) {
		this.arriStationName = arriStationName;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getDeptTime() {
		return deptTime;
	}
	public void setDeptTime(String deptTime) {
		this.deptTime = deptTime;
	}
	public String getArriTime() {
		return arriTime;
	}
	public void setArriTime(String arriTime) {
		this.arriTime = arriTime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public PaymentView(long driveId, String deptStationName, String arriStationName, String trainNo, String deptTime,
			String arriTime, int price) {
		super();
		this.driveId = driveId;
		this.deptStationName = deptStationName;
		this.arriStationName = arriStationName;
		this.trainNo = trainNo;
		this.deptTime = deptTime;
		this.arriTime = arriTime;
		this.price = price;
	}
	@Override
	public String toString() {
		return "PaymentView [driveId=" + driveId + ", deptStationName=" + deptStationName + ", arriStationName="
				+ arriStationName + ", trainNo=" + trainNo + ", deptTime=" + deptTime + ", arriTime=" + arriTime
				+ ", price=" + price + "]";
	}
    
    public PaymentView() {
		// TODO Auto-generated constructor stub
	}
    
    
    
}
