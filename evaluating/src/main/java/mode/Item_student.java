package mode;

import java.util.Date;

public class Item_student {
	private String student_number;
	
	private String student_name;
	
	private String student_class;
	
	private String student_age_class;
	
	private int student_sum_mark;
	
	private int access;
	
	private int examin;
	
	private Date time;

	public int getExamin() {
		return examin;
	}

	public void setExamin(int examin) {
		this.examin = examin;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date date) {
		this.time = date;
	}

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_class() {
		return student_class;
	}

	public void setStudent_class(String student_class) {
		this.student_class = student_class;
	}

	public String getStudent_age_class() {
		return student_age_class;
	}

	public void setStudent_age_class(String student_age_class) {
		this.student_age_class = student_age_class;
	}

	public int getStudent_sum_mark() {
		return student_sum_mark;
	}

	public void setStudent_sum_mark(int student_sum_mark) {
		this.student_sum_mark = student_sum_mark;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}
	
	

}
