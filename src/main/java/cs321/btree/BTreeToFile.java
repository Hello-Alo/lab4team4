package cs321.btree;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Reads and writes B Trees to files
 * Comparable elements are Integers (or Longs)
 **/
public class BTreeToFile {
	private File f;
	private ArrayList<Integer> arrayInts;
	private ArrayList<Long> arrayLongs;
	private BTree<Integer> bInt;
	private BTree<Long> bLong;
	private int type;
	
	/**
	 * Reads comma-separated values from a file and 
	 * converts them into an ArrayList of Integers
	 * type = 0 for 32-bit int
	 * type = 1 for 64-bit int (long)
	 **/
	public BTreeToFile(String filename, int type) throws FileNotFoundException
	{
		this.type = type;
	    try {
	    	String data = "";
	    	f = new File(filename);
	        Scanner s = new Scanner(f);
	        while (s.hasNextLine()) {
	        	// read data in file
	        	// line by line
	          data = data + s.nextLine();
	        }
	        if (type==0)
	        {
	        	arrayInts = DataToInteger(data);
	        	bInt = new BTree<Integer>();
	        }
	        else
	        {
	        	arrayLongs = DataToLong(data);
	        	bLong = new BTree<Long>();
	        }
	        s.close();
	      }
	    catch (FileNotFoundException e) {
	        System.out.println("Could not open file.");
	        e.printStackTrace();
	      }
	}
	
	/**
	 * Returns underlying array list for B Tree File
	 * @return array list for B Tree File
	 **/
	public ArrayList getArrayList()
	{
		if (type==0)
		{
			return arrayInts;
		}
		else
		{
			return arrayLongs;
		}
	}
	
	/**
	 * Sets underlying array list for B Tree File
	 * @param array list
	 **/
	public void setArrayList(ArrayList a)
	{
		if (type==0)
		{
			arrayInts = a;
		}
		else
		{
			arrayLongs = a;
		}
	}
	
	/**
	 * Returns underlying B Tree for B Tree File
	 * @return B Tree structure
	 **/
	public BTree getBTree()
	{
		if (type==0)
		{
			return bInt;
		}
		else
		{
			return bLong;
		}
	}
	
	/**
	 * Sets underlying B Tree for B Tree File
	 * @param B Tree structure
	 **/
	public void setBTree(BTree b)
	{
		if (type==0)
		{
			bInt = b;
		}
		else
		{
			bLong = b;
		}
	}
	
	/**
	 * Creates a BTree using Array List
	 * (either Integer or Long depending on
	 * mode initialized in constructor)
	 **/
	public void CreateBTree()
	{
		// construct a new BTree using values
		// in ArrayList
		if (type==0)
		{
			bInt = new BTree<Integer>();
			for (int i = 0; i < arrayInts.size(); i++)
			{
				bInt.insert(arrayInts.get(i));
			}
		}
		else
		{
			bLong = new BTree<Long>();
			for (int i = 0; i < arrayLongs.size(); i++)
			{
				bLong.insert(arrayLongs.get(i));
			}
		}
	}
	
	
	/**
	 * Converts a comma separated value string
	 * into an Array List of Integers
	 * @param data
	 * @throws NumberFormatException if 
	 * String parameter is not an integer
	 * @returns an Integer Array List containing data from string
	 **/
	public ArrayList<Integer> DataToInteger(String data)
	{
		// format string to csv (without whitespace)
		data = data.replaceAll("\\s+","");
		ArrayList<String> strInt = new ArrayList<String>(Arrays.asList(data.split(",")));
		ArrayList<Integer> dataInt = new ArrayList<Integer>();
		try 
		{
			for (int i = 0; i < strInt.size(); i++)
			{
				dataInt.add(Integer.parseInt(strInt.get(i)));
			}
		}
		catch (NumberFormatException e)
		{
	        System.out.println("List Must be integers.");
	        e.printStackTrace();
		}
		return dataInt;
	}
	
	/**
	 * Converts a comma separated value string
	 * into an Array List of Longs
	 * @param data
	 * @throws NumberFormatException if 
	 * String parameter is not a (long) integer
	 * @returns a Long Array List containing data from string
	 **/
	public ArrayList<Long> DataToLong(String data)
	{
		// format string to csv (without whitespace)
		data = data.replaceAll("\\s+","");
		ArrayList<String> strLong = new ArrayList<String>(Arrays.asList(data.split(",")));
		ArrayList<Long> dataLong = new ArrayList<Long>();
		try 
		{
			for (int i = 0; i < strLong.size(); i++)
			{
				dataLong.add(Long.parseLong(strLong.get(i)));
			}
		}
		catch (NumberFormatException e)
		{
	        System.out.println("List Must be (long) integers.");
	        e.printStackTrace();
		}
		return dataLong;
	}
	
	/**
	 * Writes a B Tree to a File in specific format
	 * @param outFilename
	 **/
	public void WriteBTreeToFile(String outFilename)
	{
	    try {
	        FileWriter bTreeFile = new FileWriter(outFilename);
	        CreateBTree();
	        if (type==0)
	        {
	        	TreeObject<Integer> currNode = bInt.getRoot();
	        	bTreeFile.write(bInt.getDegree() + "");
	        	bTreeFile.write("\n");
	        	bTreeFile.write(BTreeOutputFormat(bInt.getRoot()));
	        }
	        else
	        {
	        	TreeObject<Long> currNode = bLong.getRoot();
	        	bTreeFile.write(bLong.getDegree() + "");
	        	bTreeFile.write("\n");
	        	bTreeFile.write(BTreeOutputFormat(bLong.getRoot()));
	        }
	        bTreeFile.close();
	      } catch (IOException e) {
	        System.out.println("An error occurred when writing to file.");
	        e.printStackTrace();
	      }
	}

	/**
	 * Returns a BTree Object formatted String given a root node
	 * by iterating through all of its nodes
	 * node, recursively.
	 * @param t, ref, lineno
	 * @returns String containing output format for Tree Object
	 **/
	public String BTreeOutputFormat(TreeObject t)
	{
		String s = "";
		if (t!=null && !t.isEmpty())
		{
			int linenum = 2;
			int ref = 0;
			TreeObject parentRef = new TreeObject();
			ArrayList<TreeObject> tQueue = new ArrayList<TreeObject>();
			if (t.getParent()==null)
			{
				t.setParent(parentRef);
			}
			t.setLineNum(1);
			tQueue.add(t);
			while (!tQueue.isEmpty())
			{
				TreeObject curr = tQueue.get(0);
				parentRef = curr.getParent();
				// append string to file formatted string
				ArrayList<String> strarr = addChildLineNum(curr.toString(), linenum);
				s = s + parentRef.getLineNum() + strarr.toString() + "\n";
				
				// update the queue and line number
				tQueue.remove(curr);
				linenum = linenum + curr.size() + 1;
				
				if (!curr.isLeaf())
				{
					ArrayList<TreeObject> currChild = curr.getAllChildren();
					for (int i = 0; i < currChild.size(); i++)
					{
						currChild.get(i).setParent(curr);
						currChild.get(i).setLineNum(Integer.parseInt(strarr.get(2*i)));
						tQueue.add(currChild.get(i));
					}
				}
			}
		}
		return s;
	}
	
	/**
	 * Appends the child line numbers inside an existing string
	 * containing elements of a Tree Object
	 * @param format, line
	 * @returns the new Tree Object format string with child
	 * line numbers included
	 **/
	public ArrayList<String> addChildLineNum(String format, int line)
	{
		ArrayList<String> strarr = new ArrayList<String>();
		if (format!=null && format.length()>2)
		{
			format = format.substring(1,format.length()-1);
			format = format.replaceAll("\\s+","");
			strarr = new ArrayList<String>(Arrays.asList(format.split(",")));
			int length = strarr.size();
			for (int i = 0; i < length+1; i++)
			{
				strarr.add(2*i, line + "");
				line++;
			}
		}
		return strarr;
	}
	
}
