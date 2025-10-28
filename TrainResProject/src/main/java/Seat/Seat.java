package Seat;

public class Seat {
	
	int seat_id;		// 좌석 ID
	int drive_id;		// 운행정보 ID
	int car_no;			// 호차 번호
	String seat_no;		// 좌석번호
	String reserved;	// 예약 여부
	String res_id;		// 예약 ID
	
	// 생성자
	
	public Seat() {}
	
	public Seat(int seat_id, int drive_id, int car_no, String seat_no, String reserved, String res_id) {

		this.seat_id = seat_id;
		this.drive_id = drive_id;
		this.car_no = car_no;
		this.seat_no = seat_no;
		this.reserved = reserved;
		this.res_id = res_id;
	}

	
	// toString
	
	@Override
	public String toString() {
		return "Seat [seat_id=" + seat_id + ", drive_id=" + drive_id + ", car_no=" + car_no + ", seat_no=" + seat_no
				+ ", reserved=" + reserved + ", res_id=" + res_id + "]";
	}
	
	
	// Getter/Setter

	public int getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(int seat_id) {
		this.seat_id = seat_id;
	}

	public int getDrive_id() {
		return drive_id;
	}

	public void setDrive_id(int drive_id) {
		this.drive_id = drive_id;
	}

	public int getCar_no() {
		return car_no;
	}

	public void setCar_no(int car_no) {
		this.car_no = car_no;
	}

	public String getSeat_no() {
		return seat_no;
	}

	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getRes_id() {
		return res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}
}
