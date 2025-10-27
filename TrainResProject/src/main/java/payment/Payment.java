package payment;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private String custId;
    private int amount;
    private String method;
    private String status;
    private Timestamp paymentDate;

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Timestamp paymentDate) { this.paymentDate = paymentDate; }
}
