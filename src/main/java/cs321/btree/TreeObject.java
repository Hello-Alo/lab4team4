package cs321.btree;

import java.util.ArrayList;

public class TreeObject<E extends Comparable<E>> implements TreeObjectInterface<E>
{
	private ArrayList<E> key;
	private ArrayList<Integer> freq;
	private ArrayList<TreeObject<E>> child;
	private ArrayList<Integer> childLineNum;
	private TreeObject<E> parent;
	// used for writing to files
	private int lineNum;

	/**
	 * Creates a new Tree Object with
	 * empty arrays for keys and frequencies
	 **/
	public TreeObject() {
		key = new ArrayList<E>();
		freq = new ArrayList<Integer>();
		parent = null;
		childLineNum = new ArrayList<Integer>();
		child = new ArrayList<TreeObject<E>>();
		child.add(0, null);
		lineNum = 0;
	}

	/**
	 * Creates a new Tree Object with
	 * specified key and frequency (starting at index=0) 
	 * @param key, freq
	 **/
	public TreeObject(E key, int freq) {
		this.key = new ArrayList<E>();
		this.freq = new ArrayList<Integer>();
		this.key.add(key);
		this.freq.add(freq);
		parent = null;
		childLineNum = new ArrayList<Integer>();
		child = new ArrayList<TreeObject<E>>();
		lineNum = 0;
		// set child key nodes to null
		// to allow setting child node to arbitrary index
		// instead of starting from 0
		for (int i = 0; i < this.key.size()+1; i++)
		{
			child.add(null);
		}
	}

	/**
	 * Creates a new Tree Object with given list
	 * of keys and frequencies
	 * If the key list is bigger than the frequency list,
	 * any keys at indices larger than that of frequency list
	 * will default to a frequency of 1.  
	 * @param keyList, freqList
	 * @throws IndexOutOfBoundsException if key size < frequency size
	 **/	
	public TreeObject(ArrayList<E> keyList, ArrayList<Integer> freqList) {
		if ( keyList.size() < freqList.size() )
		{
			throw new IndexOutOfBoundsException();
		}
		int numfreq = freqList.size();
		int numkeys = keyList.size();
		
		this.key = new ArrayList<E>();
		this.freq = new ArrayList<Integer>();
		
		for (int i = 0; i < numfreq; i++)
		{
			key.add(keyList.get(i));
			freq.add(freqList.get(i));
		}
		
		// if key list is bigger than frequency list
		// frequencies are set to 0
		for (int i = numfreq; i < numkeys; i++)
		{
			key.add(keyList.get(i));
			freq.add(1);
		}
		parent = null;
		
		// set child key nodes to null
		// to allow setting child node to arbitrary index
		// instead of starting from 0
		childLineNum = new ArrayList<Integer>();
		child = new ArrayList<TreeObject<E>>();
		for (int i = 0; i < numkeys+1; i++)
		{
			child.add(null);
		}
		lineNum = 0;
	}

	/**
	 * Finds the key at specified index in the key array list 
	 * @param index
	 * @throws IndexOutOfBoundsException if index >= key list size;
	 * @returns Value of key at index
	 **/
	public E getKey(int index) {
		if (index >= key.size())
		{
			throw new IndexOutOfBoundsException();
		}
		return key.get(index);
	}

	/**
	 * Finds the frequency at specified index in the frequency array list 
	 * @param index
	 * @throws IndexOutOfBoundsException if index >= frequency list size;
	 * @returns Value of frequency at index
	 **/
	public int getFreq(int index) {
		if (index >= freq.size())
		{
			throw new IndexOutOfBoundsException();
		}
		return freq.get(index);
	}

	/**
	 * Finds the child's line number in file at specified index in the child array list 
	 * @param index
	 * @throws IndexOutOfBoundsException if index >= child list size;
	 * @returns Child Line Number at index
	 **/
	public int getChildLineNum(int index) {
		if (index >= childLineNum.size())
		{
			return -1;
			//throw new IndexOutOfBoundsException();
		}
		return childLineNum.get(index);
	}
	

	/**
	 * Finds the child node at specified index in the child array list 
	 * @param index
	 * @throws IndexOutOfBoundsException if index >= child list size;
	 * @returns Child Node at index
	 **/
	public TreeObject<E> getChild(int index) {
		if (index >= child.size())
		{
			throw new IndexOutOfBoundsException();
		}
		return child.get(index);
	}

	/**
	 * @returns reference to the parent node of the TreeObject
	 **/
	public TreeObject<E> getParent() {
		return parent;
	}

	/**
	 * @returns true if the TreeObject is a leaf node, false otherwise
	 **/
	public boolean isLeaf() {
		boolean isLeafNode = true;
		int index = 0;
		// check if there are child nodes
		// that are not null
		while (isLeafNode && index < child.size())
		{
			if (child.get(index)!=null)
			{
				isLeafNode = false;
			}
			index++;
		}
		return isLeafNode;
	}

	/**
	 * @returns reference to array of keys in Node
	 **/
	public ArrayList<E> getAllKeys() {
		return key;
	}

	/**
	 * @returns reference to array of frequencies in Node
	 **/	
	public ArrayList<Integer> getAllFreqs() {
		return freq;
	}

	/**
	 * @returns reference to array of all child nodes in Node
	 **/
	public ArrayList<TreeObject<E>> getAllChildren() {
		return child;
	}

	/**
	 * Sets the key at index in key array list
	 * @throws IndexOutOfBoundsException if index >= list size
	 * @param index, key
	 **/
	public void setKey(int index, E key) {
		if (index >= this.key.size())
		{
			this.key.add(index, key);
		}
		this.key.set(index, key);
	}

	/**
	 * Sets the frequency at index in frequency array list
	 * @throws IndexOutOfBoundsException if index >= list size
	 * @param index, freq
	 **/
	public void setFreq(int index, int freq) {
		if (index >= this.freq.size())
		{
			this.freq.add(index, freq);
		}
		this.freq.set(index, freq);
	}

	/**
	 * Sets the child at index in child array list
	 * @throws IndexOutOfBoundsException if index >= child list size
	 * @param index, child
	 **/
	public void setChildLineNum(int index, int lineNum) {
		if (index >= this.childLineNum.size())
		{
			this.childLineNum.add(index, lineNum);
		}
		this.childLineNum.set(index, lineNum);
	}

	/**
	 * Sets the child at index in child array list
	 * @throws IndexOutOfBoundsException if index >= child list size
	 * @param index, child
	 **/
	public void setChild(int index, TreeObject<E> child) {
		if (index >= this.child.size())
		{
			throw new IndexOutOfBoundsException();
		}
		this.child.set(index, child);
	}

	/**
	 * Sets the parent node to specified tree node
	 * @param parent
	 **/
	public void setParent(TreeObject<E> parent) {
		this.parent = parent;
	}

	/**
	 * Sets key array to list of keys
	 * @param keyList
	 **/
	public void setAllKeys(ArrayList<E> keyList) {
		key = keyList;
	}

	/**
	 * Sets frequency array to list of frequencies
	 * @param freqList
	 **/
	public void setAllFreqs(ArrayList<Integer> freqList) {
		freq = freqList;
	}

	/**
	 * Sets child array to list of children
	 * @param childList
	 **/
	public void setAllChildren(ArrayList<TreeObject<E>> childList) {
		child = childList;
	}

	/**
	 * Increments the frequency of the specified index
	 * in frequency list by one
	 * @throws IndexOutOfBoundsException if index >= list size
	 * @param index
	 **/
	public void incrementFreq(int index) {
		if (index >= this.size())
		{
			throw new IndexOutOfBoundsException();
		}
		int f = freq.get(index);
		f++;
		freq.set(index, f);
	}
	
	/**
	 * Compares to Tree Objects
	 * @param comparable
	 * @returns true if two Tree Objects are equal, false otherwise
	 **/	
	public boolean equals(TreeObject<E> comparable)
	{
		boolean eq = false;
		if (key==comparable.getAllKeys() && child==comparable.getAllChildren() && 
				parent==comparable.getParent() && freq==comparable.getAllFreqs())
		{
			eq = true;
		}
		return eq;
	}
	
	

	/**
	 * Returns the string representation of the Tree Node
	 * formatted by "[key:freq, key:freq, key:freq...]"
	 * This does not include parent nor child nodes.
	 * @returns String representation the of Tree Node
	 **/
	public String toString() {
		String s = "[";
		int size = key.size();
		for (int i = 0; i < size-1; i++)
		{
			s = s + key.get(i) + ":" + freq.get(i) + ", ";
		}
		s = s + key.get(size-1) + ":" + freq.get(size-1) + "]";
		return s;
	}

	/**
	 * Inserts the key in key list at specified index
	 * Inserts the frequency in frequency list at specified index
	 * If not specified, frequency defaults to 1
	 * @throws IndexOutOfBoundsException if index > list size
	 * @param index, element, frequency
	 **/
	public void insertNewKey(int index, E element, int frequency) {
		if (index > size())
		{
			throw new IndexOutOfBoundsException();
		}
		this.key.add(index, element);
		this.freq.add(index, frequency);
		this.child.add(index, null);
		//this.child.add(index+1, null);
	}
	
	/**
	 * Inserts the key in key list at specified index
	 * Inserts the frequency in frequency list at specified index
	 * If not specified, frequency defaults to 1
	 * @throws IndexOutOfBoundsException if index > list size
	 * @param index, element
	 **/
	public void insertNewKey(int index, E element) {
		if (index > size())
		{
			throw new IndexOutOfBoundsException();
		}
		this.key.add(index, element);
		// default frequency is 1
		this.freq.add(index, 1);
		this.child.add(index, null);
		//this.child.add(index+1, null);
	}

	/**
	 * Returns true if the Node is empty
	 * (i.e. has 0 elements)
	 * @return true if empty, false otherwise
	 **/
	public boolean isEmpty() {
		return key.size()==0;
	}
	
	/**
	 * Returns the size of the Tree Object (node)
	 * number of keys
	 * @return size of node
	 **/
	public int size()
	{
		return this.getAllKeys().size();
	}
	
	/**
	 * Returns the current line number of node
	 * in File Output Format
	 * @return line number
	 **/
	public int getLineNum()
	{
		return lineNum;
	}
	
	/**
	 * Sets the current line number of node
	 * in File Output Format
	 * @param line
	 **/
	public void setLineNum(int line)
	{
		lineNum = line;
	}
	
	/**
	 * Splits a tree object node from start to end position
	 * @throws IndexOutOfBoundsException if either start
	 * or end position is out of bounds < 0 or > size
	 * @return A sub node of the original Tree Node
	 **/
	public TreeObject<E> getSubNode(int start, int end)
	{
		if (start < 0 || end > size() || start > end)
		{
			throw new IndexOutOfBoundsException();
		}
		
		TreeObject<E> sub = new TreeObject<E>();
		ArrayList<TreeObject<E>> children = new ArrayList<TreeObject<E>>();
		for (int i = start; i < end; i++)
		{
			sub.insertNewKey(i-start, getKey(i), getFreq(i));
			children.add(this.getChild(i));
		}
		children.add(this.getChild(end));
		sub.setAllChildren(children);
		return sub;
	}

	public boolean hasChild(int index){
		if (getChild(index).isEmpty())
			return false;
		else
			return true;
	}
	
}
