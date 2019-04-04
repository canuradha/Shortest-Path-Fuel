package lk.ac.pdn.mt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Graph {

	private Map<String, LinkedList<String>> map = new HashMap<String, LinkedList<String>>();

	public void addEdge(String node1, String node2) {
		LinkedList<String> adjacent = map.get(node1);
		if (adjacent == null) {
			adjacent = new LinkedList<String>();
			map.put(node1, adjacent);
		}
		adjacent.add(node2);
	}

	public LinkedList<String> adjacentNodes(String last) {
		LinkedList<String> adjacent = map.get(last);
		if (adjacent == null) {
			return new LinkedList<String>();
		}
		return new LinkedList<String>(adjacent);
	}
}
