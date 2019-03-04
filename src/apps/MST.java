package apps;

import structures.*;
import java.util.ArrayList;

public class MST {

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		/*
		 * How to initialize:
		 * 1. Create a List that will be appended to keep all the partial trees
		 * FOR EACH vertex of the graph, first make a partial tree.
		 * THEN store all of its edges in a min heap that is constantly sifted to return the highest priority arc.
		 * add this partial tree to the list of partial trees AKA append it 
		 *
		 */
	
		if (graph.vertices.length == 0) {
			return null;
		}
		
		PartialTreeList list = new PartialTreeList(); //step 1
		for (int i = 0; i < graph.vertices.length; i++) { //iterate through all of the vertices in the graph
			PartialTree current = new PartialTree(graph.vertices[i]); //create a new partial tree for each vertex
			MinHeap<PartialTree.Arc> heap = new MinHeap <PartialTree.Arc>();
			Vertex pointer = graph.vertices[i]; //this will go through all the neighbors of this specific vertex
			
			while (pointer.neighbors != null) { //iterate through all the neighbors
				PartialTree.Arc edge = new PartialTree.Arc(graph.vertices[i], pointer.neighbors.vertex, pointer.neighbors.weight); //the current edge
				heap.insert(edge);
				pointer.neighbors = pointer.neighbors.next;
			}
			current.getArcs().merge(heap);
			list.append(current);
			
		}
		return list;
	}
	

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		/*
		 * this is the main part of this assignment, so devote most of my time to this.
		 * returns the arrayList of ALL arcs that are in the MST.
		 * traverse through the first partial tree. find the lowest arc. ADD that to the arraylist of arcs
		 * next, look into the tree of the SECOND vertex of that arc IF it does not already belong to the current tree
		 * find the min and to the arraylist IF it's not already there. 
		 * in both cases, if it's already there, pick the second highest.
		 */

		ArrayList <PartialTree.Arc> toReturn = new ArrayList<PartialTree.Arc>();
		int size = ptlist.size();
		
		while (size > 1) {
			PartialTree x = ptlist.remove();
			PartialTree.Arc edge = x.getArcs().deleteMin();
			Vertex v2 = edge.v2;
			
			while ( x.getArcs().isEmpty() == false) {
				if ( x.getRoot().equals(v2.getRoot())) {
					edge = x.getArcs().deleteMin();
					v2 = edge.v2;
				} else {
					break;
				}
			}
			PartialTree y = ptlist.removeTreeContaining(v2);
			x.merge(y);
			ptlist.append(x);
			toReturn.add(edge);
		}
		
		return toReturn;
	
	}
}
