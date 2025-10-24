package project;

public class StationDTO {
	private int stationId;
    private String stationName;
    
    // 기본 생성자
    public StationDTO() {}

    // 전체 필드 생성자
    public StationDTO(int stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }

    // Getter & Setter
    public int getStationId() { return stationId; }
    public void setStationId(int stationId) { this.stationId = stationId; }
    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    // toString()
    @Override
    public String toString() {
        return "StationDTO [stationId=" + stationId + ", stationName=" + stationName + "]";
    }
}
