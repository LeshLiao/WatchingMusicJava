package ConfigJson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rule {
	@SerializedName("Input")
	@Expose
	private List<Integer> input = null;
	@SerializedName("OutputType")
	@Expose
	private String outputType;
	@SerializedName("OutputParam")
	@Expose
	private List<Integer> outputParam = null;

	public List<Integer> getInput() {
	return input;
	}

	public void setInput(List<Integer> input) {
	this.input = input;
	}

	public String getOutputType() {
	return outputType;
	}

	public void setOutputType(String outputType) {
	this.outputType = outputType;
	}

	public List<Integer> getOutputParam() {
	return outputParam;
	}

	public void setOutputParam(List<Integer> outputParam) {
	this.outputParam = outputParam;
	}
}
