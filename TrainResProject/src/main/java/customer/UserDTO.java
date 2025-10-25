package customer;

import java.sql.Date;

public class UserDTO {
	
	private String custId;
	private String name;
	private Date birth;
	private String password;

	
	public UserDTO() {
		
	}
	
	public UserDTO(String custId, String name, Date birth, String password) {
		this.custId = custId;
		this.name = name;
		this.birth =birth;
		this.password =password;
	}
	
	
	
	
	//아래는 getter/setter 임
	public String getCustId() { return custId; }
	public String getName() { return name; }
	public Date getBirth() { return birth; }
	public String getPassword() {return password; }
	
	public void setCustId(String custId) { this.custId =custId;}
	public void setName(String name) { this.name =name; }
	public void setBirth(Date birth) { this.birth = birth; }
	public void setPassword(String password) { this.password = password; }

	


}
