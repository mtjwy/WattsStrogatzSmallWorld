package com.lamaryw.adv;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lamaryw.util.StdDraw;


public class GraphDrawer {
	
	private Map<Integer, Point> points = new HashMap<Integer, Point>();
	private double xScale = 2.1;
	private double yScale = 2.1;
	private double offSet = 0.1;
	
	public double getxScale() {
		return xScale;
	}
	
	public double getyScale() {
		return yScale;
	}
	
	public double getOffSet() {
		return offSet;
	}
	
	/**
	 * Create a GraphDrawer for a Graph G
	 * @param G
	 */
	public GraphDrawer(Graph G) {
		int n = G.V();
		
		double step = (2 * PI) / n;//divide a circle's degree 360 to n parts, then use this degree info to create points
		double x = 0;

		for (int i = 0; i < n; i++) {
			points.put(i, new Point(cos(x) - xScale / 2, sin(x) + yScale / 2));//create Point (x, y) to draw on board, set i to be this point's ID
													//at a board scale (-1 ~ 1)
			//StdDraw.point(cos(x), sin(x));//point ID is i
			x += step;
		}
	}
	
	/**
	 * Draw the Graph G
	 * @param G
	 */
	public  void drawGraph(Graph G) {
		// set the scale of the coordinate system
		
        StdDraw.setXscale(-xScale, xScale);
        StdDraw.setYscale(-yScale, yScale);
        
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.01);
        
        int n = points.size();
		
		for (int i = 0; i < n; i++) {
			StdDraw.point(points.get(i).getX(), points.get(i).getY());//point ID is i
		}
		
		StdDraw.setPenColor();
        StdDraw.setPenRadius(0.001);
		for(Integer v : G.getNodes()) {
			for(Integer w : G.getNodes()) {
				if(G.hasEdge(v, w)) {
					StdDraw.line(points.get(v).getX(), points.get(v).getY(), 
							points.get(w).getX(), points.get(w).getY());
				}
			}
		}
		
		StdDraw.show();
	}
	
	public void drawText(int n, int k, double p, double avgPathLen, double clusterCoeff, double nAPL, double nCC) {
		DecimalFormat df = new DecimalFormat("0.00");
		
		StdDraw.textLeft(xScale / 6, yScale - 0.05, "n = " + n + "   k = " + k);
		StdDraw.textLeft(xScale / 6, yScale - 0.4, "p = " + df.format(p));
		StdDraw.textLeft(xScale / 6, yScale - 0.6, "clusterCoeff = " + df.format(clusterCoeff));
		StdDraw.textLeft(xScale / 6, yScale - 0.8, "avgPathLen = " + df.format(avgPathLen));
		
		 
		StdDraw.textLeft(-xScale , -yScale + 1.2, "normalized CC = " + df.format(nCC));
		StdDraw.textLeft(-xScale , -yScale + 1.0, "normalized APL = " + df.format(nAPL));
		
	}
	
	/**
	 * draw Normalized Average Path Length or Plot
	 * @param list
	 */
	public void drawNormalizedAPL_Plot(List<Point> list) {
		if (list == null) {
			return;
		}
		StdDraw.setPenColor();
		drawAxis();
		StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.008);
        StdDraw.text(xScale - 0.2, - offSet * 2 - 0.15, "APL");
		for(Point p : list) {
			StdDraw.point(p.getX(), p.getY() - (yScale - offSet));
		}
	}
	
	/**
	 * draw Normalized Clustering Coefficient Plot
	 * @param list
	 */
	public void drawNormalizedCC_Plot(List<Point> list) {
		if (list == null) {
			return;
		}
		//drawAxis();
		StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.008);
        StdDraw.text(xScale - 0.2, - offSet * 2, "CC");
		for(Point p : list) {
			StdDraw.point(p.getX(), p.getY() - (yScale - offSet));
		}
	}
	
	/**
	 * Draw axis for the Average Path Length and Clustering Coefficient plot
	 */
	public void drawAxis() {
		StdDraw.setPenColor();
		StdDraw.text(xScale / 2, 0.12, "Average Path Length and");//set plot title
		StdDraw.text(xScale / 2, 0, "Clustering Coefficient plot");//set plot title
		StdDraw.text(-offSet, - yScale / 2, "Normalized APL and CC", 90);//set y axis label
		StdDraw.text(xScale / 2, - yScale, "Reconnecting Probability");//set x axis label
		StdDraw.text(-offSet, -offSet, "1");
		StdDraw.text(-offSet, -yScale, "0");
		StdDraw.text(xScale, -yScale, "1");
		StdDraw.setPenRadius(0.003);
		StdDraw.line(0, 0, 0, -(yScale - offSet));//plot y axis
		StdDraw.line(0, -(yScale - offSet), xScale, -(yScale - offSet));//plot x axis
		StdDraw.setPenColor();
		StdDraw.setPenRadius();
		
	}
	
	
	/*
	private class Edge {
		private int v;
		private int w;
		
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	*/
}
