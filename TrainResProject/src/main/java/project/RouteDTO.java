package project;

public class RouteDTO {
	private int routeId;
    private int deptStation; // 출발역 ID (FK)
    private int arriStation; // 도착역 ID (FK)
    private double price;    // 가격
    
    // 기본 생성자
    public RouteDTO() {}

    // 전체 필드 생성자
    public RouteDTO(int routeId, int deptStation, int arriStation, double price) {
        this.routeId = routeId;
        this.deptStation = deptStation;
        this.arriStation = arriStation;
        this.price = price;
    }

    // Getter & Setter
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public int getDeptStation() { return deptStation; }
    public void setDeptStation(int deptStation) { this.deptStation = deptStation; }
    public int getArriStation() { return arriStation; }
    public void setArriStation(int arriStation) { this.arriStation = arriStation; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // toString()
    @Override
    public String toString() {
        return "RouteDTO [routeId=" + routeId + ", deptStation=" + deptStation + ", arriStation=" + arriStation + ", price=" + price + "]";
    }
}
