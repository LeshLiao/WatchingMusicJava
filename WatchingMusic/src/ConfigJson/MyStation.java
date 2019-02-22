package ConfigJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyStation {

	@SerializedName("StationID")
	@Expose
	private Integer stationID;
	@SerializedName("Name")
	@Expose
	private String name;
	@SerializedName("IP")
	@Expose
	private String iP;
	@SerializedName("Port")
	@Expose
	private Integer port;
	@SerializedName("Titles")
	@Expose
	private String titles;
	@SerializedName("Rules")
	@Expose
	private List<Rule> rules = null;

	public Integer getStationID() {
	return stationID;
	}

	public void setStationID(Integer stationID) {
	this.stationID = stationID;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getIP() {
	return iP;
	}

	public void setIP(String iP) {
	this.iP = iP;
	}

	public Integer getPort() {
	return port;
	}

	public void setPort(Integer port) {
	this.port = port;
	}

	public String getTitles() {
	return titles;
	}

	public void setTitles(String titles) {
	this.titles = titles;
	}

	public List<Rule> getRules() {
	return rules;
	}

	public void setRules(List<Rule> rules) {
	this.rules = rules;
	}
}
