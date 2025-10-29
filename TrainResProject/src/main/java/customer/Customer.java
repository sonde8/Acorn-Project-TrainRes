package customer;

import java.sql.Date;

public class Customer {
    private String custId;
    private String name;
    private Date birth;      // CUST.BIRTH (DATE)
    private String password;

    public Customer() {}

    public Customer(String custId, String name, Date birth, String password) {
        this.custId = custId;
        this.name = name;
        this.birth = birth;
        this.password = password;
    }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Customer[custId=" + custId +
               ", name=" + name +
               ", birth=" + birth +
               ", password=" + password + "]";
    }
}
