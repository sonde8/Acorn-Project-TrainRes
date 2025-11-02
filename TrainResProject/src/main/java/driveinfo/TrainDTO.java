package driveinfo;

public class TrainDTO {
	private int trainId;
    private String trainNo;
    private String trainType;
    
    // 기본 생성자
    public TrainDTO() {}

    // 전체 필드 생성자
    public TrainDTO(int trainId, String trainNo, String trainType) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.trainType = trainType;
    }

    // Getter & Setter
    public int getTrainId() { return trainId; }
    public void setTrainId(int trainId) { this.trainId = trainId; }
    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }
    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    // toString()
    @Override
    public String toString() {
        return "TrainDTO [trainId=" + trainId + ", trainNo=" + trainNo + ", trainType=" + trainType + "]";
    }
}
