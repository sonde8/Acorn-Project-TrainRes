package driveinfo;

import java.sql.Date;

// 고객 테이블 DTO
public class CustDTO {
	private String custId;
    private String name;
    private Date birth;
    private String password;
    
 // 기본 생성자
    public CustDTO() {}

    // 전체 필드 생성자
    public CustDTO(String custId, String name, Date birth, String password) {
        this.custId = custId;
        this.name = name;
        this.birth = birth;
        this.password = password;
    }

    // Getter & Setter
    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // toString()
    @Override
    public String toString() {
        return "CustDTO [custId=" + custId + ", name=" + name + ", birth=" + birth + "]";
    }
}
