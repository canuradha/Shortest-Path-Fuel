package dfs;

public class Connection {

	private String start;
	private String end;
	private int weight;

	public Connection(String start, String end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
