package com.lamaryw.adv;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.lamaryw.util.StdDraw;

/**
 * Simulation WattsStrogatzSmallWorld
 *
 */
public class WattsStrogatzSmallWorld {
	
	public static void main(String[] args) {
			
			Scanner in = new Scanner(System.in);

			System.out.println("Enter number of nodes(20~8000): ");
			int n = in.nextInt();// number of nodes
			
		
			System.out.println("Enter number of neighbors(2~40): ");
			int k = in.nextInt();// number of neighbors
			
		
			double p = 0;// reconnecting probability
			
			System.out.println("Enter steps for reconnecting probability (0.00~1.00): ");
			// int sampleNum = n / 10;
			double step = in.nextDouble();//step for p
			
			
			int showTime = (int) (1000 * 0.5);// display and pause for showTime ms
			
				
			List<Point> p_apl_list = new LinkedList<Point>();//points of (p, normalized average path length)
			List<Point> p_cc_list = new LinkedList<Point>();//points of (p, normalized clustering coefficient)
			
			Graph G = GraphProcessor.createRegularGraph(n, k);
			
			/*
			double avgPathLen0 = GraphProcessor.avgPathLen(G);
			double cc0 = GraphProcessor.clusterCoeff(G);
			*/
			
			
			double avgPathLen0 = (n >= 5000 && k > 10) ? GraphProcessor.avgPathLenBig(G) 
					: GraphProcessor.avgPathLen(G);
			double cc0 = (n >= 5000 && k > 10) ? GraphProcessor.clusterCoeffBig(G) 
					: GraphProcessor.clusterCoeff(G);
			
			
			/*
			double avgPathLen0 = (n <= 100) ? GraphProcessor.avgPathLen(G) 
					: GraphProcessor.avgPathLen(G, sampleNum);
			double cc0 = (n <= 100) ? GraphProcessor.clusterCoeff(G) 
					: GraphProcessor.clusterCoeff(G, sampleNum);
			*/
			
			GraphDrawer graphDrawer = new GraphDrawer(G);
			double offSet = graphDrawer.getOffSet(); // get offSet of this graphDrawer;
			double xScale = graphDrawer.getxScale(); 
			double yScale = graphDrawer.getyScale();
			graphDrawer.drawGraph(G);
			graphDrawer.drawText(n, k, p, avgPathLen0, cc0, 1.00, 1.00);
			
			p_apl_list.add(new Point(p * (xScale - offSet), 
					1 * (yScale - offSet)));//points of (p, normalized average path length)
			p_cc_list.add(new Point(p * (xScale - offSet), 
					1 * (yScale - offSet)));//points of (p, normalized clustering coefficient)
			
			graphDrawer.drawNormalizedAPL_Plot(p_apl_list);
			graphDrawer.drawNormalizedCC_Plot(p_cc_list);
			
			
			StdDraw.show(showTime); // display and pause for showTime ms
			
			System.out.println("p = " + p);
			System.out.println("avgPathLen: " + avgPathLen0);
			System.out.println("clusterCoeff: " + cc0);
			System.out.println();
			
			
			System.out.println("p = " + p);
			System.out.println("normalized avgPathLen: " + avgPathLen0 / avgPathLen0 );
			System.out.println("normalized clusterCoeff: " + cc0 / cc0);
			System.out.println();
			
			
			p = step;
			while(p <= 1) {
				StdDraw.clear();
				GraphProcessor.reconnectGraph(G, p, k);
				graphDrawer = new GraphDrawer(G);
				graphDrawer.drawGraph(G);
				
				/*
				double avgPL = GraphProcessor.avgPathLen(G);
				double cc = GraphProcessor.clusterCoeff(G);
				*/
				
				double avgPL = (n >= 5000 && k > 10) ? GraphProcessor.avgPathLenBig(G) 
						: GraphProcessor.avgPathLen(G);
				double cc = (n >= 5000 && k > 10) ? GraphProcessor.clusterCoeffBig(G) 
						: GraphProcessor.clusterCoeff(G);
				
				/*
				double avgPL = (n <= 100) ? GraphProcessor.avgPathLen(G) 
						: GraphProcessor.avgPathLen(G, sampleNum);
				double cc = (n <= 100) ? GraphProcessor.clusterCoeff(G) 
						: GraphProcessor.clusterCoeff(G, sampleNum);
				*/
				
				graphDrawer.drawText(n, k, p, avgPL, cc, avgPL / avgPathLen0, cc / cc0 );
				
				p_apl_list.add(new Point(p * (xScale - offSet), 
						avgPL / avgPathLen0 * (yScale - offSet)));//points of (p, normalized average path length)
				p_cc_list.add(new Point(p * (xScale - offSet), 
						cc / cc0 * (yScale - offSet)));//points of (p, normalized clustering coefficient)
				
				graphDrawer.drawNormalizedAPL_Plot(p_apl_list);
				graphDrawer.drawNormalizedCC_Plot(p_cc_list);
				
				StdDraw.show(showTime);
				
				System.out.println("p = " + p);
				System.out.println("avgPathLen: " + avgPL);
				System.out.println("clusterCoeff: " + cc);
				System.out.println();
				
				System.out.println("p = " + p);
				System.out.println("normalized avgPathLen: " + avgPL / avgPathLen0);
				System.out.println("normalized clusterCoeff: " + cc / cc0);
				System.out.println();
				
				
				p += step;
				G = GraphProcessor.createRegularGraph(n, k);
					
			}
			
			p = 1;
			StdDraw.clear();
			GraphProcessor.reconnectGraph(G, p, k);
			graphDrawer = new GraphDrawer(G);
			graphDrawer.drawGraph(G);
			
			/*
			double avgPL = GraphProcessor.avgPathLen(G);
			double cc = GraphProcessor.clusterCoeff(G);
			*/
			
			double avgPL = (n >= 5000 && k > 10) ? GraphProcessor.avgPathLenBig(G) 
					: GraphProcessor.avgPathLen(G);
			double cc = (n >= 5000 && k > 10) ? GraphProcessor.clusterCoeffBig(G) 
					: GraphProcessor.clusterCoeff(G);
			
			/*
			double avgPL = (n <= 100) ? GraphProcessor.avgPathLen(G) 
					: GraphProcessor.avgPathLen(G, sampleNum);
			double cc = (n <= 100) ? GraphProcessor.clusterCoeff(G) 
					: GraphProcessor.clusterCoeff(G, sampleNum);
			*/
			
			graphDrawer.drawText(n, k, p, avgPL, cc, avgPL / avgPathLen0, cc / cc0 );
			
			p_apl_list.add(new Point(p * (xScale - offSet), 
					avgPL / avgPathLen0 * (yScale - offSet)));
			p_cc_list.add(new Point(p * (xScale - offSet), 
					cc / cc0 * (yScale - offSet)));
			//p_apl_list.add(new Point(p, avgPL / avgPathLen0 ));
			//p_cc_list.add(new Point(p, cc / cc0 ));
			graphDrawer.drawNormalizedAPL_Plot(p_apl_list);
			graphDrawer.drawNormalizedCC_Plot(p_cc_list);
			
			StdDraw.show(showTime);
			
			System.out.println("p = " + p);
			System.out.println("avgPathLen: " + avgPL);
			System.out.println("clusterCoeff: " + cc);
			System.out.println();
			
			System.out.println("p = " + p);
			System.out.println("normalized avgPathLen: " + avgPL / avgPathLen0);
			System.out.println("normalized clusterCoeff: " + cc / cc0);
			System.out.println();
			
		
			
		}
}
