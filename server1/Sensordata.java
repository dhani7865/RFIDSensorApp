// sensor data class
public class Sensordata {
	
	String sensorname; // strings 
	String sensorvalue;
	String userid;
	String sensordate;

	
	public Sensordata(String sensorname, String sensorvalue, String userid,String sensordate) {
		super(); // public Sensordata super, includes the following listed below
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		this.userid = userid;
		this.sensordate = sensordate;
	}

	public Sensordata(String sensorname, String sensorvalue, String userid) {
		super();
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		this.userid = userid;
		this.sensordate = "unknown";
	} // constructors being implemented
	// Default if unknown
	public Sensordata(String sensorname, String sensorvalue) {
		super();
		this.sensorname = sensorname;
		this.sensorvalue = sensorvalue;
		this.userid = "unknown";
		this.sensordate = "unknown";
	}// Default if unknown

	public String getSensorname() {
		return sensorname;
	} // public string getSensorname
	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	} // public void setSensorname
	public String getSensorvalue() {
		return sensorvalue;
	} // public string getSensorvalue
	public void setSensorvalue(String sensorvalue) {
		this.sensorvalue = sensorvalue;
	} // public string setSensorvalue
	public String getUserid() {
		return userid;
	} // public string getUserid
	public void setUserid(String userid) {
		this.userid = userid;
	} // public string setUserid

	public String getSensordate() {
		return sensordate;
	} // public string getSensordate
	public void setSensordate(String sensorvalue) {
		this.sensordate = sensorvalue;
	} // public string setSensordate

	@Override
	public String toString() {
		return "SensorData [sensorname=" + sensorname + ", sensorvalue=" + sensorvalue + ", userid=" + userid
				+ "sensordate=" + sensordate + "]";
	} // override method
	
	
	
} //close Sensordata class 
