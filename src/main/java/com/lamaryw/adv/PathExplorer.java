package com.lamaryw.adv;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;



/**
 * Processing graph with Breath First Search.
 * Have method support query path and path length from
 * a source s to a destination d on a graph.
 * 
 * 
 */
public class PathExplorer {
	private Map<Integer, Integer> prev = new HashMap<Integer, Integer>();//key = node v; value = previous node of v on shortest path from s to v 
	private Map<Integer, Integer> pathLen = new HashMap<Integer, Integer>();//key = node v; value = length of shortest path from s to v
	
	/**
	 * Construct PathExplorer, run breath first search, and initialize instance
	 * variables prev and pathLen
	 * @param G
	 * @param s: source of path
	 */
	public PathExplorer(Graph G, Integer s) {
		
		//put source s in the queue
		Queue<Integer> q = new LinkedList<Integer>();
		q.offer(s);
		pathLen.put(s, 0);
		
		//run breath first search, and initialize prev and pathLen
		while (!q.isEmpty()) {
			Integer v = q.poll();
			for(Integer w: G.adjacentTo(v)) {//iterate all of v's neighbors
				if (!pathLen.containsKey(w)) {//if this neighbor w has not been visit yet
					q.offer(w);//put w in queue
					pathLen.put(w, 1 + pathLen.get(v));//path length of w to s, is 1 plus, the path length of w's previous neighbor v, to s
					prev.put(w, v);// v is w's previous node on the path from w to source s. 
				}
			}
		}
	}
	
	/**
	 * Check if v is reachable from source s
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(Integer v) {
		return pathLen.containsKey(v);
	}
	
	/**
	 * Get the shortest path length from s to v
	 * @param v
	 * @return
	 */
	public int pathLenTo(Integer v) {
		if (!hasPathTo(v)) {
			return Integer.MAX_VALUE;
		}
		return pathLen.get(v);
	}
	
}
