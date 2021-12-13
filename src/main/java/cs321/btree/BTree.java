package cs321.btree;

import java.util.ArrayList;

public class BTree<E extends Comparable<E>> implements BTreeInterface<E>
{
	private TreeObject<E> root;
	//private int height;
	private int degree; // used for inserting new elements into tree
	// maximum number of keys in a full node.

	/**
	 * Creates a new empty B Tree
	 * Degree is set to 2 by default
	 **/
	public BTree() {
		root = new TreeObject<E>();

		degree = 2;
	}

	/**
	 * Creates a new empty B Tree with specified root
	 * Degree is set to size of root by default
	 * @param root
	 **/
	public BTree(TreeObject<E> root) {
		this.root = root;

		degree = root.getAllKeys().size();
	}

	/**
	 * @returns the root for this B Tree
	 **/
	public TreeObject<E> getRoot() {
		return root;
	}

	/**
	 * @returns the height for this B tree
	 **/
	public int getHeight() {
		int h = 0;
		TreeObject<E> t = root;
		// keep traversing until hit last child (leaf node)
		while (!t.isLeaf())
		{
			t = t.getChild(0);
			h++;
		}
		return h;
	}

	/**
	 * Sets the root for this B tree
	 * @param root
	 **/
	public void setRoot(TreeObject<E> root) {
		this.root = root;
	}

	/**
	 * Inserts the element into the B Tree
	 * @param element
	 **/
	public void insert(E element) {
		TreeObject<E> node = root;
		ArrayList<Integer> path = contains(element);
		if (node.isEmpty())
		{
			node.insertNewKey(0, element);
		}
		else if (!path.isEmpty())
		{
			for (int i = 0; i < path.size()-1; i++)
			{
				node = node.getChild(path.get(i));
			}
			node.incrementFreq(path.get(path.size()-1));
		}
		else
		{
			// find a leaf node where element can be inserted
			int index = 0;
			while (!node.isLeaf())
			{
				index = 0;
				int psize = node.size();
				while (index < psize && 
						element.compareTo(node.getKey(index)) > 0)
				{
					index++;
				}
				
				TreeObject<E> temp = node;;
				node = node.getChild(index);
				node.setParent(temp);
			}
			
			// insert key into leaf node
			// check if the node is full (size <= degree)
			// if so, split node into two nodes
			// about median (middle) key
			// promote middle key up one level (parent)
			// median key = floor(degree / 2);
			index = 0;
			while (index < node.size() && 
					node.getKey(index).compareTo(element) < 0)
			{
				index++;
			}
			
			node.insertNewKey(index, element);
			
			while (node.size() > degree)
			{
				node = splitPromote(node);
			}
			
			if (node.getParent()==null)
			{
				root = node;
			}
		}
	}

	/**
	 * Returns the degree for this B tree 
	 * Number of nodes in a full node
	 * @param height
	 **/
	public int getDegree() {
		return degree;
	}

	/**
	 * Sets the degree of this B Tree
	 * @param deg
	 **/
	public void setDegree(int deg) {
		degree = deg;
	}
	
	/**
	 * Returns a String representation of a B Tree
	 * in breadth first order traversal
	 * 
	 * (e.g. a tree with parent node 5 and child nodes
	 * 3 and 8 would be [5, 3, 8])
	 * 
	 * @return String representation of B Tree
	 **/
	public String BTreeString(TreeObject<E> node)
	{
		String s = "";
		if (node != null && !node.isEmpty())
		{
			s = s + node.toString() + ", ";
			ArrayList<TreeObject<E>> childNodes = node.getAllChildren();
			int size = childNodes.size();
			for (int i = 0; i < size; i++)
			{
				s = s + BTreeString(childNodes.get(i));
			}			
		}
		return s;
	}
	
	/**
	 * Returns a String representation of a B Tree
	 * in depth first order traversal
	 * 
	 * 
	 * @return String representation of B Tree
	 **/
	public String toString() {
		return BTreeString(root);
	}
	
	/**
	 * Splits the node into two sub nodes, promoting
	 * the middle key in the process
	 * @return String representation of B Tree
	 **/
	public TreeObject<E> splitPromote(TreeObject<E> t)
	{
		// should only be called if needed
		// i.e. node is full and needs to be split
		int median = degree / 2;
		TreeObject<E> p;
		TreeObject<E> lower = t.getSubNode(0, median);
		TreeObject<E> upper = t.getSubNode(median+1, t.size());
				
		if (t.getParent()==null)
		{
			// create a new root
			p = new TreeObject<E>(t.getKey(median), t.getFreq(median));
			p.setChild(0, lower);
			p.setChild(1, upper);
			t.setParent(p);
		}
		else
		{
			// insert node into existing parent node
			p = t.getParent();
			int index = 0;
			while (index < p.size() && 
					p.getKey(index).compareTo(t.getKey(median)) < 0)
			{
				index++;
			}
			p.insertNewKey(index, t.getKey(median), t.getFreq(median));
			p.setChild(index, lower);
			p.setChild(index+1, upper);
			t.setParent(p);
		}
		return p;
	}
	
	/**
	 * Check whether or not element is in the BTree
	 * If so, find a path of nodes
	 * (ordered by depth), to that element in the tree.
	 * If no such element is in the tree, the ArrayList is empty
	 * @param element to be searched
	 * @returns an Integer ArrayList containing the path to element,
	 **/
	public ArrayList<Integer> contains(E element)
	{
		ArrayList<Integer> path = new ArrayList<Integer>();
		TreeObject<E> node = root;
		boolean found = false;
		
		while (!found && node!=null)
		{
			ArrayList<E> keys = node.getAllKeys();
			int index = 0;
			while (index < keys.size() && element.compareTo(keys.get(index)) >= 0)
			{
				if (keys.get(index).equals(element))
				{
					found = true;
				}
				index++;
			}
			path.add(index);
			node = node.getChild(index);
		}
		if (found)
		{
			path.set(path.size()-1, path.get(path.size()-1)-1);
		}
		else
		{
			path.clear();
		}
		
		return path;
	}
	
}
