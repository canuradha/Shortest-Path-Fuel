package lk.ac.pdn.mt;

import java.util.List;

public class PathWeight implements Comparable<PathWeight> {

	private List<String> path;
	private int weight;
	private int distanceToNearestStation = -1;

	public PathWeight(List<String> path, int weight) {
		this.path = path;
		this.weight = weight;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	// private static int count = 0;

	@Override
	public int compareTo(PathWeight o) {
		List<String> currentPath = o.getPath();
		List<String> newPath = this.getPath();
		if (currentPath.get(0).equals(newPath.get(0))
				&& currentPath.get(currentPath.size() - 1).equals(newPath.get(newPath.size() - 1))) {
			// for (String node : currentPath) {
			// System.out.print(node + " ");
			// }
			// System.out.print("\t");
			// for (String node : newPath) {
			// System.out.print(node + " ");
			// }
			// System.out.println("\t" + currentPath.get(0).equals(newPath.get(0)));
			// System.out.println(o.getWeight() + "\t" + this.getWeight() +"\n\n");
			
			if (o.getWeight() > this.getWeight()) {
				o.setPath(this.getPath());
				o.setWeight(this.getWeight());
			}else if(o.getWeight() == this.getWeight()) {
				if(currentPath.size() != newPath.size()) {
					return 1;
				}else {
					int count = 0;
					String newStop;
					for(String stop : currentPath) {
						newStop = newPath.get(count);
						if(!stop.equals(newStop)) {
//							System.out.println("Toggled");
							return 1;
						}
						count++;						
					}
//					System.out.println(currentPath + " " + newPath);
					return 0;
				}
				
			}
			return 0;
		}

		return Integer.valueOf(this.getWeight()).compareTo(Integer.valueOf(o.getWeight())) == 0 ? 1
				: Integer.valueOf(this.getWeight()).compareTo(Integer.valueOf(o.getWeight()));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (String node : this.path) {
			sb.append(node);
			sb.append(" ");
		}
		sb.append(" > Total Cost: " + this.getWeight());

		if (this.getDistanceToNearestStation() != -1) {
			sb.append(", Distance to the nearest filling station: " + this.getDistanceToNearestStation());
		}
		return sb.toString();
	}

	public String printPath() {
		StringBuffer sb = new StringBuffer();

		for (String node : this.path) {
			sb.append(node);
			sb.append(" ");
		}
		return sb.toString();
	}

	public int getDistanceToNearestStation() {
		return distanceToNearestStation;
	}

	public void setDistanceToNearestStation(int distanceToNearestStation) {

		if (this.distanceToNearestStation == -1 || this.distanceToNearestStation > distanceToNearestStation) {
			this.distanceToNearestStation = distanceToNearestStation;
		}
	}
}
