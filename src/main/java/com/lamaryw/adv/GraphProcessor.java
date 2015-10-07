package com.lamaryw.adv;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * GraphProcessor contains methods to process a graph 
 * according to WattsStrogatz Small World model. 
 * It also contains methods to compute average path length 
 * and clustering coefficient.
 * 
 * 
 */
public class GraphProcessor {
	
	/**
	 * Greate a regular graph with n nodes and every node has k neighbors
	 * 
	 * @param n number of node
	 * @param k number of neighbors
	 * @return Graph G
	 */
	public static Graph createRegularGraph(int n, int k) {
		
		Graph G = new Graph();
		//double step = (2 * PI) / n;
		//double x = 0;

		for (int i = 0; i < n; i++) {
			//StdDraw.point(cos(x), sin(x));//point ID is i
			G.addNode(i);
			//x += step;
		}

		// Add the circle links.

		int kk = k / 2;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= kk; j++) {
				int jj = (i + j) % n;
				G.addEdge(i, jj);
				//addEdge(edgeId(i, jj), nodeId(i), nodeId(jj));
			}
		}
		
		return G;
	}
	
	/**
	 * Reconnect graph G with probability p
	 * @param G
	 * @param p: reconnecting probability
	 * @param k: number of neighbors
	 */
	public static void reconnectGraph(Graph G, double p, int k) {
		/*
		//first implementation, not correct
		for (Integer v : G.getNodes()) {
			for (Integer w : G.getNodes()) {
				if (G.hasEdge(v, w)) {
					if (Math.random() <= p) {
						//G.deleteEdge(v, w);
						int randomVertex = v;
						while (randomVertex == v) {//choose a random vertex other than v
							randomVertex = 0 + (int)(Math.random() * ((G.V() - 1 - 0) + 1));// vertex id is from 0 to G.V() - 1
						}
						G.addEdge(v, randomVertex);
					}
				}
			}
		}
		*/
		
		/*
		 * reconnecting algorithm: 
		 * 		For each node i and each edge (i, j) with i < j
		 * 			with probability p, replace (i,j) with (i, k) where k is
		 * 			chosen uniformly from vertices not equal to or adjacent to i 
		 */
		for (int i = 0; i < G.V(); i++) {
			for (int j = i + 1; j <= i + k / 2; j++) {//consider its k/2 neighbors on its larger id side
				int jj = j >= G.V() ? j % G.V() : j;//round up node id; if jj >= G.V(), restart jj from 0
				if (G.hasEdge(i, jj)) {
					if (Math.random() <= p) {//reconnecting with probability p
						int randomVertex = jj;
						while (randomVertex == i || randomVertex == jj || G.hasEdge(i, randomVertex)) {//chosen uniformly from vertices not equal to or adjacent to i
							randomVertex = 0 + (int)(Math.random() * ((G.V() - 1 - 0) + 1));// node id is from 0 to G.V() - 1
						}
						G.addEdge(i, randomVertex);
						G.deleteEdge(i, jj);
					}
				}
			}
		}
	}
	
	
	/**
	 * get the average path length of the graph
	 * @param G
	 * @return average path length
	 */
	public static double avgPathLen(Graph G) {
		double totalEdges = 0;
		double totalPairs = G.V() * (G.V() - 1);
		
		for (Integer v : G.getNodes()) {//iterate through all nodes in graph
			PathExplorer explorer = new PathExplorer(G, v);//Create a pathExplorer for v
			for (Integer w : G.getNodes()) {//treat v as source, for all other node, sum their path length up
				if (explorer.hasPathTo(w)) {
					totalEdges += explorer.pathLenTo(w);
				}
			}
		}
		return (double) totalEdges / totalPairs;
	}
	
	/**
	 * get the average path length of the graph for (nodes > 5000, k > 10)
	 * @param G
	 * @return average path length
	 */
	public static double avgPathLenBig(Graph G) {
		BigDecimal totalEdges = BigDecimal.valueOf(0);
		BigDecimal totalPairs = BigDecimal.valueOf(G.V() * (G.V() - 1));
		
		for (Integer v : G.getNodes()) {//iterate through all nodes in graph
			PathExplorer explorer = new PathExplorer(G, v);//Create a pathExplorer for v
			for (Integer w : G.getNodes()) {//treat v as source, for all other node, sum their path length up
				if (explorer.hasPathTo(w)) {
					totalEdges = totalEdges.add(BigDecimal.valueOf(explorer.pathLenTo(w)));
				}
			}
		}
		return totalEdges.divide(totalPairs, 2, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * get the average path length of sampleNum randomly choose nodes 
	 * @param G
	 * @return average path length of sampleNum randomly choose nodes
	 */
	public static double avgPathLen(Graph G, int sampleNum) {
		double totalEdges = 0;
		double totalPairs = sampleNum * (sampleNum - 1);
		
		
		for (int i = 0; i < sampleNum; i++) {
			
			int v = (int)(Math.random() * G.V());
			//System.out.println("v = " + v);
			PathExplorer explorer = new PathExplorer(G, v);//Create a pathExplorer for v
			for (int j = 0; j < sampleNum; j++) {
				int w = v;
				while (w == v) {
					w = (int)(Math.random() * G.V()); 
					//System.out.println("w = " + w);
				}
				
				if (explorer.hasPathTo(w)) {
					totalEdges += explorer.pathLenTo(w);
				}
			}
			
		}
		return (double) totalEdges / totalPairs;
	}
	
	
	/**
	 * Compute cluster coefficient
	 * @param G
	 * @return cluster coefficient
	 */
	public static double clusterCoeff(Graph G) {
		double possible = 0;
		double edges = 2 * G.E();
		for (Integer v : G.getNodes()) {
			//sum up local edge totals.
			for (Integer u : G.adjacentTo(v)) {
				for (Integer w : G.adjacentTo(v)) {
					if (G.hasEdge(u, w)) {
						edges++;
					}
				}
			}
			int t = G.degree(v);
			possible += (t + 1) * t;
		}
		return (double) edges / possible;
	}
	
	/**
	 * Compute cluster coefficient of the graph for (nodes > 5000, k > 10)
	 * @param G
	 * @return cluster coefficient
	 */
	public static double clusterCoeffBig(Graph G) {
		BigDecimal possible = BigDecimal.valueOf(0);
		BigDecimal edges = BigDecimal.valueOf(2 * G.E());
		for (Integer v : G.getNodes()) {
			//sum up local edge totals.
			for (Integer u : G.adjacentTo(v)) {
				for (Integer w : G.adjacentTo(v)) {
					if (G.hasEdge(u, w)) {
						edges = edges.add(BigDecimal.valueOf(1));
					}
				}
			}
			BigDecimal t = BigDecimal.valueOf(G.degree(v));
			possible = possible.add((t.add(BigDecimal.valueOf(1))).multiply(t));
		}
		return edges.divide(possible, 2, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * Compute cluster coefficient of sampleNum randomly choose nodes
	 * @param G
	 * @return cluster coefficient
	 */
	public static double clusterCoeff(Graph G, int sampleNum) {
		double possible = 0;
		double edges = 2 * G.E() * (sampleNum / G.V());
		for (int  i = 0; i < sampleNum; i++) {
			int v = (int)(Math.random() * G.V());
			//sum up local edge totals.
			for (Integer u : G.adjacentTo(v)) {
				for (Integer w : G.adjacentTo(v)) {
					if (G.hasEdge(u, w)) {
						edges++;
					}
				}
			}
			int t = G.degree(v);
			possible += (t + 1) * t;
		}
		return (double) edges / possible;
	}
	
	
}
