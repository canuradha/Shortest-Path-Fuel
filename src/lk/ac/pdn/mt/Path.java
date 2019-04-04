package lk.ac.pdn.mt;

import java.util.LinkedList;

public class Path implements Comparable<Path> {
	private LinkedList<PathWeight> route;
	private int weight;

	public Path(LinkedList<PathWeight> route, int weight) {

		this.route = route;
		this.weight = weight;
	}

	@Override
	public int compareTo(Path o) {

		return this.getWeight() == o.getWeight() ? 1
				: Integer.valueOf(this.getWeight()).compareTo(Integer.valueOf(o.getWeight()));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (PathWeight path : this.route) {
			sb.append("[ ");
			sb.append(path.printPath());
			sb.append("] ");
		}

		sb.append(" > Total path cost: " + this.getWeight());
		return sb.toString();
	}

	/**
	 * @return the route
	 */
	public LinkedList<PathWeight> getRoute() {
		return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(LinkedList<PathWeight> route) {
		this.route = route;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

}
