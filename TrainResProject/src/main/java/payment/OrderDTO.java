package payment;

public class OrderDTO {

    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private String quantity;
    private String total_amount;

    private int driveId;
    private String custId;

    private String carNo;
    private String seatNo;

    public String getPartner_order_id() { return partner_order_id; }
    public void setPartner_order_id(String partner_order_id) { this.partner_order_id = partner_order_id; }

    public String getPartner_user_id() { return partner_user_id; }
    public void setPartner_user_id(String partner_user_id) { this.partner_user_id = partner_user_id; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getTotal_amount() { return total_amount; }
    public void setTotal_amount(String total_amount) { this.total_amount = total_amount; }

    public int getDriveId() { return driveId; }
    public void setDriveId(int driveId) { this.driveId = driveId; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getCarNo() { return carNo; }
    public void setCarNo(String carNo) { this.carNo = carNo; }

    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String seatNo) { this.seatNo = seatNo; }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "partner_order_id='" + partner_order_id + '\'' +
                ", partner_user_id='" + partner_user_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", driveId=" + driveId +
                ", custId='" + custId + '\'' +
                ", carNo='" + carNo + '\'' +
                ", seatNo='" + seatNo + '\'' +
                '}';
    }
}
