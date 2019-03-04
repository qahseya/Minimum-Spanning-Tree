package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {

	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;

		/**
		 * Next node in linked list
		 */
		public Node next;

		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;

	/**
	 * Number of nodes in the CLL
	 */
	private int size;

	/**
	 * Initializes this list to empty
	 */
	public PartialTreeList() {
		rear = null;
		size = 0;
	}

	/**
	 * Adds a new tree to the end of the list
	 * 
	 * @param tree Tree to be added to the end of the list
	 */
	public void append(PartialTree tree) {
		Node ptr = new Node(tree);
		if (rear == null) {
			ptr.next = ptr;
		} else {
			ptr.next = rear.next;
			rear.next = ptr;
		}
		rear = ptr;
		size++;
	}

	/**
	 * Removes the tree that is at the front of the list.
	 * 
	 * @return The tree that is removed from the front
	 * @throws NoSuchElementException If the list is empty
	 */
	public PartialTree remove() 
			throws NoSuchElementException {
		/*
		 * removing the front tree means just moving the pointer that is at the front to the next one
		 * code all these cases: when the list is empty, when there is only 1 tree in the list, and when there are multiple.
		 * since it is stored as a CLL, remember that the handle you have is on the rear pointer.
		 * 
		 */

		if ( rear == null ) { //empty list 
			throw new NoSuchElementException("List is empty");

		} 

		if ( rear == rear.next) { //there's only one element, in which the circle points to itself
			PartialTree removeThis = rear.tree;
			rear = null; //make the tree empty
			return removeThis;
		}

		if ( rear.next != null) { //WILL this work if i put it after first if statement? or should i test single node cond first?
			PartialTree removeThis = rear.next.tree; 
			rear.next = rear.next.next;
			size -=1;
			return removeThis;	
		}

		return null;


	}

	/**
	 * Removes the tree in this list that contains a given vertex.
	 * 
	 * @param vertex Vertex whose tree is to be removed
	 * @return The tree that is removed
	 * @throws NoSuchElementException If there is no matching tree
	 */
	public PartialTree removeTreeContaining(Vertex vertex) 
			throws NoSuchElementException {
		/*
		 * Through a while loop, continue through all of the vertices of every tree until
		 * you hit the vertice itself or the pointer hits null. 
		 * - if it's null, throw nosuchelementexception
		 * otherwise find what tree that verex belongs to, jump into the same code as removeTree.
		 * 
		 * helper method checks if the variable belongs to the tree. IF it does, it returns true else false.
		 * if TRUE=> remove that tree that the variable belongs to
		 */
		/* COMPLETE THIS METHOD */
		if (rear == null) {
			throw new NoSuchElementException ("Not found");
		}
		Node ptr = rear.next; Node prev = rear;

		do {
			if ( ptr.tree.getRoot().equals(vertex.getRoot())) {
				if (size == 1) {
					size --;
					rear = null;
					return ptr.tree;
				}
				if (vertex.getRoot().equals(rear.tree.getRoot())) {
					size --;
					prev.next = rear.next;
					rear = prev;
					return ptr.tree;
				}
				prev.next = ptr.next;
				size--;
				return ptr.tree;
			}
			ptr = ptr.next;
			prev = prev.next;

		} while ( ptr != rear.next); 
		throw new NoSuchElementException("Tree Not Found");
	}

	/**
	 * Gives the number of trees in this list
	 * 
	 * @return Number of trees
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns an Iterator that can be used to step through the trees in this list.
	 * The iterator does NOT support remove.
	 * 
	 * @return Iterator for this list
	 */
	public Iterator<PartialTree> iterator() {
		return new PartialTreeListIterator(this);
	}

	private class PartialTreeListIterator implements Iterator<PartialTree> {

		private PartialTreeList.Node ptr;
		private int rest;

		public PartialTreeListIterator(PartialTreeList target) {
			rest = target.size;
			ptr = rest > 0 ? target.rear.next : null;
		}

		public PartialTree next() 
				throws NoSuchElementException {
			if (rest <= 0) {
				throw new NoSuchElementException();
			}
			PartialTree ret = ptr.tree;
			ptr = ptr.next;
			rest--;
			return ret;
		}

		public boolean hasNext() {
			return rest != 0;
		}

		public void remove() 
				throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}
}


