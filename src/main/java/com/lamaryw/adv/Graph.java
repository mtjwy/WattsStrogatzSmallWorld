package com.lamaryw.adv;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Undirected graph data type.
 * 
 * Implemented using a map,
 * 		keys are nodes (Integer) and 
 * 		values are sets of neighbors (set of Integers)
 * 
 */
public class Graph {
	private Map<Integer, Set<Integer>> graphMap; //map: key = integer node, value = set of neighboring nodes
	private int E;
	
	/**
	 * Create an empty graph, has no nodes and edges
	 */
	public Graph() {
		graphMap = new HashMap<Integer, Set<Integer>>();
	}
	
	/**
	 * get number of nodes
	 * @return
	 */
	public int V() {
		return graphMap.size();
	}
	
	/**
	 * get number of edges
	 * @return
	 */
	public int E() {
		return E;
	}
	
	/**
	 * Check if v is a node in graph
	 * @param v
	 * @return
	 */
	public boolean hasNode(Integer v) {
		return graphMap.containsKey(v);
	}
	
	/**
	 * Get degree of node v
	 * @param v
	 * @return
	 */
	public int degree(Integer v) {
		validateNode(v);
		return graphMap.get(v).size();
	}
	
	//throw an exception if v is not a node in graph
	private void validateNode(Integer v) {
		if (!hasNode(v)) {
			throw new IllegalArgumentException(v + " is not a node in the graph");
		}
	}
	
	/**
	 * Check if v-w an edge in graph
	 * @param v
	 * @param w
	 * @return
	 */
	public boolean hasEdge(Integer v, Integer w) {
		validateNode(v);
		validateNode(w);
		return graphMap.get(v).contains(w);
	}
	
	/**
	 * Add node v to graph (if v is not already a node)
	 * @param v
	 */
	public void addNode(Integer v) {
		if (!hasNode(v)) {
			graphMap.put(v, new HashSet<Integer>());
		}
	}
	
	/**
	 * Add edge v-w to graph (if v-w is not already an edge)
	 * @param v
	 * @param w
	 */
	public void addEdge(Integer v, Integer w) {
		if (!hasNode(v)) {
			addNode(v);
		}
		if (!hasNode(w)) {
			addNode(w);
		}
		if (!hasEdge(v, w)) {
			E++;
			graphMap.get(v).add(w);
			graphMap.get(w).add(v);
		}
	}
	
	/**
	 * Delete edge v-w from graph (if v-w is an edge in graph)
	 * @param v
	 * @param w
	 */
	public void deleteEdge(Integer v, Integer w) {
		if(hasEdge(v, w)) {
			graphMap.get(v).remove(w);
			graphMap.get(w).remove(v);
			E--;
		} else {
			throw new RuntimeException("No edge " + v + "-" + w + "to delete");
		}
	}
	
	/**
	 * Return the set of neighbors of v as Iterable.
	 * @param v
	 * @return
	 */
	public Iterable<Integer> adjacentTo(Integer v) {
		validateNode(v);
		return graphMap.get(v);
	}
	
	/**
	 * Return the set of vertices as an Iterable.
	 * @return
	 */
	public Iterable<Integer> getNodes() {
		return graphMap.keySet();
	}
	
	/**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Integer v : graphMap.keySet()) {
            s.append(v + ": ");
            for (Integer w : graphMap.get(v)) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
	
    /*
    //test
    public static void main(String[] args) {
    	Graph g = new Graph();
    	g.addEdge(0, 1);
    	g.addEdge(1, 2);
    	g.addEdge(2, 3);
    	g.addEdge(3, 4);
    	g.addEdge(4, 5);
    	g.addEdge(5, 0);
    	
    	System.out.print(g);
    }
    */
}
