package cs321.btree;

import java.util.ArrayList;

public class BTree<E extends Comparable<E>> implements BTreeInterface<E>
{
	private TreeObject<E> root;
	private int height;
	private int degree; // used for inserting new elements into tree
	// maximum number of keys in a full node.

	/**
	 * Creates a new empty B Tree
	 * Degree is set to 2 by default
	 **/
	public BTree() {
		root = new TreeObject<E>();
		height = 0;
		degree = 2;
	}

	/**
	 * Creates a new empty B Tree with specified root
	 * Degree is set to size of root by default
	 * @param root
	 **/
	public BTree(TreeObject<E> root) {
		this.root = root;
		height = getHeight();
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
	 * Sets the height for this B tree
	 * @param height
	 **/
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Inserts the element into the B Tree
	 * @param element
	 **/
	public void insert(E element) {
		TreeObject<E> node = root;

		// traverse to leaf node
		// where key can be inserted
		int index = 0;
		while (!node.isLeaf())
		{
			index = 0;
			while (index < node.getAllKeys().size() && 
					node.getKey(index).compareTo(element) < 0)
			{
				index++;
			}
			node = node.getChild(index);
		}
		
		index = 0;
		while (index < node.getAllKeys().size() && 
				node.getKey(index).compareTo(element) < 0)
		{
			index++;
		}
		
		if (node.getKey(index).equals(element))
		{
				node.incrementFreq(index);
		}
		// need to insert a new key
		else
		{
				node.insertNewKey(index, element);
				// node was full, need to split and promote element
				int currsize = node.getAllKeys().size();
				while (currsize > degree)
				{
					// extract median key
					int median = currsize / 2;
					E medkey = node.getAllKeys().get(currsize);
					int medfreq = node.getAllFreqs().get(currsize);
					TreeObject<E> med = new TreeObject(medkey, medfreq);
					
					ArrayList<E> s1key = new ArrayList<E>();
					ArrayList<Integer> s1freq = new ArrayList<Integer>();
					ArrayList<E> s2key = new ArrayList<E>();
					ArrayList<Integer> s2freq = new ArrayList<Integer>();
					
					ArrayList<Integer> freqs = node.getAllFreqs();
					ArrayList<E> keys = node.getAllKeys();
					// splitting node into two nodes
					for (int i = 0; i < median; i++)
					{
						s1key.add(keys.get(i));
						s1freq.add(freqs.get(i));
					}
					for (int i = median+1; i < node.getAllKeys().size(); i++)
					{
						s2key.add(keys.get(i));
						s2freq.add(freqs.get(i));
					}
					// create children for median key
					TreeObject<E> lower = new TreeObject(s1key, s1freq);
					TreeObject<E> upper = new TreeObject(s2key, s2freq);
					
					// insert node into existing parent node
					// re split and promote if necessary
					if (node.getParent()!=null)
					{
						node = node.getParent();
						index = 0;
						while (index < node.getAllKeys().size() && 
								node.getKey(index).compareTo(medkey) < 0)
						{
							index++;
						}
						node.insertNewKey(index, medkey, medfreq);
						node.setChild(index, lower);
						node.setChild(index+1, upper);
						currsize = node.getAllKeys().size();
					}
					// reached the root, node is full
					else
					{
						// create a new root node
						TreeObject<E> newRoot = new TreeObject(node.getKey(median), node.getFreq(median));
						newRoot.setChild(0, lower);
						newRoot.setChild(1, upper);
						node = newRoot;
						setRoot(node);
					}
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

}