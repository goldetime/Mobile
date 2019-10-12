package mn.num.edu.myapplication;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
	private String fname;
	private String lname;
	private String age;
	private String sex;
	private String phone;
	private Date bday;

	public User() {
		this.fname = "unknown";
		this.lname = "unknown";
		this.age = "20";
		setbDay(3,3,3);
	}

	public User(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	public String getfName() {
		return this.fname;
	}

	public void setfName(String fname) {
		this.fname = fname;
	}

	public String getlName() {
		return this.lname;
	}

	public void setlName(String lname) {
		this.lname = lname;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String  getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getbDay() {
		return this.bday;
	}
	
	public void setbDay(int y, int m, int d) {
		if (y > 0 && m > 0 && d > 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(y, m - 1, d, 0, 0, 0);

			this.bday = cal.getTime();
		}
	}
}
