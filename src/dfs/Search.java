package dfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Search {

	List<List<String>> fuelPaths = new ArrayList<>();
	private List<String> stations;

	private Graph graph;
	private List<Connection> connections;
	private Set<PathWeight> pathWeights = new TreeSet<PathWeight>();
	private Set<PathWeight> orderdPathWeights = new TreeSet<PathWeight>();

	private Set<Path> possiblePaths = new TreeSet<Path>();

	public Search(Graph g, List<Connection> c) {
		this.graph = g;
		this.connections = c;
	}

	public Set<PathWeight> getPathWeights() {
		return pathWeights;
	}

	public void setPossiblePaths(Set<Path> possiblePaths) {
		this.possiblePaths = possiblePaths;
	}

	public Set<Path> getPossiblePaths() {
		return possiblePaths;
	}

	public void setPathWeights(Set<PathWeight> pathWeights) {
		this.pathWeights = pathWeights;
	}

	public List<String> getStations() {
		return stations;
	}

	public void setStations(List<String> stations) {
		this.stations = stations;
	}

	/*
	 * public void execute() {
	 * 
	 * LinkedList<String> visited = new LinkedList<String>();
	 * visited.add(Configuration.START);
	 * 
	 * depthFirst(graph, visited, Configuration.END); for(List<String> p : allPaths)
	 * { pathWeights.add(new PathWeight(p, findPathCost(p))); } }
	 */

	public void createRSPN() {
		LinkedList<String> chargeMap = new LinkedList<String>();

		LinkedList<String> passedStops = new LinkedList<String>();
		LinkedList<String> allStops = new LinkedList<String>(Configuration.CENTERS);

		allStops.addLast(Configuration.END);
		allStops.addFirst(Configuration.START);

		passedStops.addLast(Configuration.START);

		for (int i = 0; i < allStops.size(); i++) {
			for (int j = i + 1; j < allStops.size(); j++) {
				chargeMap.clear();
				chargeMap.add(allStops.get(i));
				fuelDepthFirst(graph, chargeMap, passedStops, allStops.get(j), stations);

			}
			passedStops.addLast(allStops.get(i));
		}

		/*
		 * for (int i = allStops.size()-1 ; i >=0; i--) { for (int j = i - 1; j >= 0;
		 * j--) { chargeMap.clear(); chargeMap.add(allStops.get(i));
		 * fuelDepthFirst(graph, chargeMap, passedStops, allStops.get(j), stations);
		 * 
		 * } passedStops.addLast(allStops.get(i)); }
		 */
		boolean isNewPath;
		PathWeight temp;
		List<String> reverse;
		for (List<String> p : fuelPaths) {

			isNewPath = true;

			temp = new PathWeight(p, findPathCost(p));

			for (PathWeight existing : pathWeights) {
				if (temp.compareTo(existing) == 0) {
					isNewPath = false;
				}
			}
			if (isNewPath) {
				pathWeights.add(temp);
			}
			
			isNewPath = true;
			reverse =  new LinkedList<String>(p);
			Collections.reverse(reverse);
			
			temp = new PathWeight(reverse, findPathCost(reverse));
			
			for (PathWeight existing : pathWeights) {
				if (temp.compareTo(existing) == 0) {
					isNewPath = false;
				}
			}
			if (isNewPath) {
				pathWeights.add(temp);
			}
			
		}

		for (PathWeight temp1 : pathWeights) {
			orderdPathWeights.add(temp1);
		}

	}

	private void fuelDepthFirst(Graph graph, LinkedList<String> stationPaths, LinkedList<String> allStops, String end,
			List<String> stations) {

		
		if(!stationPaths.getLast().equals(Configuration.END)) {
			LinkedList<String> nodes = graph.adjacentNodes(stationPaths.getLast());
			for (String node : nodes) {
	
				if (stationPaths.contains(node) || Configuration.EXCLUDES.contains(node)) {
					// ------------ Skipping Places that are already in the path or is specifically
					// excluded by
					// EXCLUDES ----------------
					continue;
				}
				if (node.equals(end)) {
					stationPaths.add(node);
	
					if (findPathCost(stationPaths) <= Configuration.FUEL_LIMIT) {
						fuelPaths.add(new LinkedList<String>(stationPaths));
					}
	
					stationPaths.removeLast();
					break;
				}
				if (stations.contains(node) || allStops.contains(node)) {
					continue;
				}
			}
	
			for (String node : nodes) {
				if (stationPaths.contains(node) || node.equals(end) || stations.contains(node) || allStops.contains(node)
						|| Configuration.EXCLUDES.contains(node)) {
					continue;
				}
	
				stationPaths.addLast(node);
				fuelDepthFirst(graph, stationPaths, allStops, end, stations);
				stationPaths.removeLast();
			}
		}
	}

	/*
	 * private void depthFirst(Graph graph, LinkedList<String> visited, String end)
	 * { LinkedList<String> nodes = graph.adjacentNodes(visited.getLast()); //
	 * examine adjacent nodes for (String node : nodes) { if
	 * (visited.contains(node)) { continue; } if (node.equals(end)) {
	 * visited.add(node); allPaths.add(new LinkedList<String>(visited));
	 * visited.removeLast(); break; } }
	 * 
	 * for (String node : nodes) { if (visited.contains(node) ||
	 * node.equals(Configuration.END)) { continue; } visited.addLast(node);
	 * depthFirst(graph, visited, end); visited.removeLast(); } }
	 * 
	 * 
	 * private void printPath(List<String> visited) { for (String node : visited) {
	 * System.out.print(node); System.out.print(" "); } System.out.println(); }
	 * 
	 * 
	 */
	private int findPathCost(List<String> visited) {

		int totalWeight = 0;

		String start, end;
		for (int i = 1; i < visited.size(); i++) {
			start = visited.get(i - 1);
			end = visited.get(i);
			totalWeight += getDistance(start, end);
		}

		return totalWeight;
	}

	private int getDistance(String start, String end) {
		for (Connection c : connections) {
			if (c.getStart().equals(start) && c.getEnd().equals(end)) {
				return c.getWeight();
			}
		}

		return 0;
	}

	public void findShortestPath(LinkedList<PathWeight> newPath, int weightTotal) {

		for (PathWeight nextPath : orderdPathWeights) {
			if (newPath.isEmpty() && nextPath.getPath().get(0).equals(Configuration.START)) {

				newPath.add(nextPath);
				// weightTotal += nextPath.getWeight();
				findShortestPath(new LinkedList<PathWeight>(newPath), weightTotal + nextPath.getWeight());
				newPath.removeLast();

			} else if (newPath.isEmpty() || newPath.contains(nextPath) || nextPath.getPath().contains(Configuration.START)) {
				continue;
			} else {
				List<String> lastPath = newPath.getLast().getPath();

				if (lastPath.get(lastPath.size() - 1).equals(nextPath.getPath().get(0))) {
//					System.out.println(newPath.toString() + " " + nextPath.getPath().toString() );

					if (nextPath.getPath().get(nextPath.getPath().size() - 1).equals(Configuration.END)) {
						// --------------------- nextPath ends the journey ------------------------
						
						newPath.addLast(nextPath);
//						System.out.println(newPath.toString());

						// ----------- check for stops that should necessarily be included in the path
						// (according to INCLUDES) ---------
						int includePlaceCount = 0;
						if (!Configuration.INCLUDES.get(0).equals("")) {
							for (String node : Configuration.INCLUDES) {
								for (int i = 0; i < newPath.size(); i++) {
									if (newPath.get(i).getPath().contains(node)) {
										includePlaceCount++;
										break;
									}
								}
							}
						}
						// --------- if all the stops in the INCLUDES is present or if no INCLUDES are
						// given add the new path as a Possible Path --------------
						if ((!Configuration.INCLUDES.get(0).equals("")
								&& includePlaceCount == Configuration.INCLUDES.size())
								|| Configuration.INCLUDES.get(0).equals("")) {
							possiblePaths.add(
									new Path(new LinkedList<PathWeight>(newPath), weightTotal + nextPath.getWeight()));
						}

						newPath.removeLast();
					} else {
						newPath.addLast(nextPath);
						findShortestPath(new LinkedList<PathWeight>(newPath), weightTotal + nextPath.getWeight());
						newPath.removeLast();
					}

				}
			}

		}

	}

	public void getShortestPath() {
		if (this.possiblePaths.isEmpty()) {
			System.out.println("No Possible Paths exist according to the current Configurations");
		} else {
			System.out.println(this.possiblePaths.toArray()[0]);
		}

	}

	public void showAllPathsInOrder() {
		if (!this.possiblePaths.isEmpty()) {
			for (Path pw : this.possiblePaths) {
				System.out.println(pw);
			}
		}
	}

	public void showAllRoutesInOrder() {

		if (!this.orderdPathWeights.isEmpty()) {
			for (PathWeight pw : orderdPathWeights) {
				System.out.println(pw);
			}
		}
	}

	/*
	 * public Set<PathWeight> findNearestStation() {
	 * 
	 * for (PathWeight pw : pathWeights) {
	 * 
	 * List<String> path = pw.getPath(); int pathToStation = 0; for (String st :
	 * stations) { pathToStation = 0; if (path.contains(st)) { for (int i = 1; i <
	 * path.size(); i++) { pathToStation += getDistance(path.get(i - 1),
	 * path.get(i)); if (path.get(i).equals(st)) { break; } }
	 * 
	 * pw.setDistanceToNearestStation(pathToStation); } } } return pathWeights; }
	 * 
	 * 
	 * public void listShortestFuelStation() {
	 * 
	 * for (PathWeight pw : pathWeights) { if (pw.getDistanceToNearestStation() >= 0
	 * && pw.getDistanceToNearestStation() <= Configuration.FUEL_LIMIT) {
	 * System.out.println(pw); } } }
	 * 
	 * 
	 * public String findShortestCenter() {
	 * 
	 * int shortestCenterDistance = Integer.MAX_VALUE; String shortestCenter = null;
	 * PathWeight startToCenterPath = null;
	 * 
	 * for (String center : Configuration.CENTERS) {
	 * 
	 * if (Configuration.EXCLUDES.contains(center)) { continue; }
	 * 
	 * allPaths = new ArrayList<>();
	 * 
	 * LinkedList<String> visited = new LinkedList<String>();
	 * visited.add(Configuration.START);
	 * 
	 * depthFirst(graph, visited, center);
	 * 
	 * pathWeights = new TreeSet<>(); for (List<String> p : allPaths) {
	 * pathWeights.add(new PathWeight(p, findPathCost(p))); }
	 * 
	 * if (pathWeights.size() > 0) {
	 * 
	 * PathWeight shortestPathWeight = null;
	 * 
	 * boolean contain = false; for (PathWeight pw : pathWeights) { for (String s :
	 * Configuration.EXCLUDES) { if (pw.getPath().contains(s)) { contain = true;
	 * break; } }
	 * 
	 * if (contain == false) { shortestPathWeight = pw; break; } }
	 * 
	 * if (shortestPathWeight == null) {
	 * System.err.println("There is no path to a charging center with fuel limit:" +
	 * Configuration.FUEL_LIMIT + "\nand exclude junctions: " +
	 * Configuration.EXCLUDES); System.exit(0); }
	 * 
	 * int shortest = (new ArrayList<>(pathWeights).get(0).getWeight()); if
	 * (shortest < shortestCenterDistance) { shortestCenterDistance = shortest;
	 * shortestCenter = center; startToCenterPath = new
	 * ArrayList<>(pathWeights).get(0); } } }
	 * 
	 * if (shortestCenter != null) {
	 * 
	 * // System.out.print("Start to nearest center\n\t"); //
	 * System.out.print(startToCenterPath); allPaths = new ArrayList<>();
	 * 
	 * LinkedList<String> visited = new LinkedList<String>();
	 * visited.add(shortestCenter);
	 * 
	 * depthFirst(graph, visited, Configuration.END);
	 * 
	 * pathWeights = new TreeSet<>(); for (List<String> p : allPaths) {
	 * pathWeights.add(new PathWeight(p, findPathCost(p))); }
	 * 
	 * if (pathWeights.size() > 0) { PathWeight pw = (new
	 * ArrayList<>(pathWeights).get(0)); //
	 * System.out.print("\nCenter to destination\n\t"); // System.out.print(pw);
	 * 
	 * System.out.println("Path " + startToCenterPath.getPath() + " " +
	 * pw.getPath()); System.out.println("Total distance is " +
	 * (startToCenterPath.getWeight() + (pw.getWeight())));
	 * 
	 * }
	 * 
	 * } else {
	 * System.err.println("There is no path to a charging center with fuel limit:" +
	 * Configuration.FUEL_LIMIT + "\nand exclude junctions: " +
	 * Configuration.EXCLUDES); System.exit(0); }
	 * 
	 * return shortestCenter; }
	 */
}
