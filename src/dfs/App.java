package dfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class App {

	private static final String NETWORK_FILE = "network/nt4.txt";
	private static final String CONFIG_FILE = "network/config3.properties";

	private static Graph graph;
	private static List<Connection> connections;
	private static LinkedList<PathWeight> allPaths = new LinkedList<PathWeight>();
	public static void main(String[] args) {

		buildGraph(NETWORK_FILE);
		buildNetworkProperties(CONFIG_FILE);

		Search srh = new Search(graph, connections);

		/*
		 * srh.execute(); System.out.println("---All Paths---");
		 * srh.showAllPathsInOrder(); System.out.println("--- " +
		 * srh.getPathWeights().size() + " ---");
		 */
		srh.setStations(Configuration.CENTERS);
		srh.createRSPN();
		srh.findShortestPath(allPaths, 0);
		System.out.println(allPaths);
		/*
		 * try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		System.out.println("---All Possible Routes---");
		srh.showAllRoutesInOrder();
		System.out.println("--- Total: " + srh.getPathWeights().size() + " ---");

		System.out.println("\n---All Possible Paths---");
		srh.showAllPathsInOrder();
		System.out.println("--- Total: " + srh.getPossiblePaths().size() + " ---");

		System.out.println("\n---Shortest Path---");
		srh.getShortestPath();

		/*
		 * srh.setPathWeights(srh.findNearestStation()); // update nearest stations
		 * 
		 * System.out.println("-- List Shortest Fuel Stations---");
		 * srh.listShortestFuelStation();
		 * 
		 * String shortestCenter = srh.findShortestCenter();
		 * 
		 * if(shortestCenter != null) { System.out.println(shortestCenter); } else {
		 * System.err.println("There are no centers within the limit Fuel Limit: " +
		 * Configuration.FUEL_LIMIT); }
		 * 
		 */
	}

	/*
	 * private static void buildRefNetwork(String start, String end, List<String>
	 * stations) {
	 * 
	 * RSPgraph = new Graph(); RSPconnections = new ArrayList<>();
	 * 
	 * 
	 * }
	 */

	private static Graph buildGraph(String networkInfoFilePath) { 

		graph = new Graph();
		connections = new ArrayList<>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(NETWORK_FILE)));

			String line, start, end, weight;
			String[] split;
			while ((line = br.readLine()) != null) {
				
				split = line.split(",");
				start = split[0].trim();
				end = split[1].trim();
				weight = split[2].trim();
				
				graph.addEdge(start, end);
				graph.addEdge(end, start);
				connections.add(new Connection(start, end, new Integer(weight)));
				connections.add(new Connection(end, start, new Integer(weight)));
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void buildNetworkProperties(String configFilePath) {

		Properties prop = new Properties();

		try {
			InputStream inputStream = new FileInputStream(configFilePath);

			if (inputStream != null) {
				prop.load(inputStream);

				new Configuration(prop);
				inputStream.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
