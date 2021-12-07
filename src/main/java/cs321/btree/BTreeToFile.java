package cs321.btree;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import cs321.create.GeneBankCreateBTreeArguments;
import cs321.create.SequenceUtils;


/**
 * Reads and writes B Trees to files
 * Comparable elements are Integers (or Longs)
 **/
public class BTreeToFile {
	private GeneBankCreateBTreeArguments geneBankArgs;
	private BTree<Long> bLong;
	
	/**
	 * Reads comma-separated values from a file and 
	 * converts them into an ArrayList of Integers
	 * type = 0 for 32-bit int
	 * type = 1 for 64-bit int (long)
	 **/
	public BTreeToFile(GeneBankCreateBTreeArguments geneBankArgs) throws FileNotFoundException
	{
			this.geneBankArgs = geneBankArgs;
			bLong = CreateBTree( this.geneBankArgs);
	      
	}
	
	/**
	 * Returns underlying B Tree for B Tree File
	 * @return B Tree structure
	 **/
	public BTree<Long> getBTree()
	{
			return bLong;
	}
	
	/**
	 * Sets underlying B Tree for B Tree File
	 * @param B Tree structure
	 **/
	public void setBTree(BTree<Long> b)
	{
			bLong = b;
	}

	/**
	 * Converts a gbk file into a BTree of longs
	 * @param geneBankArgs
	 * @throws NumberFormatException if 
	 * String parameter is not a (long) integer
	 * @returns btree a BTree containing data from the argued gbk file
	 **/
	public BTree<Long> CreateBTree(GeneBankCreateBTreeArguments geneBankArgs)
	{
		Scanner scan = new Scanner("");
		try{
			scan = new Scanner(new File(geneBankArgs.gbkFileName()));
		} catch (FileNotFoundException e){
			System.out.printf("Something went very wrong.\n" + e.toString());
			System.exit(1);
		}
		String str = "";
		String line = "";
		while (!line.equals("ORIGIN")) {
			line = scan.nextLine().stripTrailing();
		}
		line =  scan.nextLine();
		line = line.replace(" ", "");
		line = line.substring(11);

		for (int i = 0; i < geneBankArgs.subsequenceLength(); i++){
			str += line.charAt(i);
		}

		BTree<Long> btree = new BTree<Long>();
		btree.setDegree(geneBankArgs.degree());

		btree.insert(SequenceUtils.StringToLong(str));
		while (!line.equals("//")) {
			for (int i = 0; i < line.length(); i++){
				str = str.substring(1);
				str += line.charAt(i);
				btree.insert(SequenceUtils.StringToLong(str));
			}
			line =  scan.nextLine();
			line = line.replace(" ", "");
			if (line.length() >= 11)
				line = line.substring(11);
		} 
		scan.close();
		return btree;
	}
	
	/**
	 * Writes a B Tree to a File in specific format
	 * @param outFilename
	 **/
	public void WriteBTreeToFile(String outFilename)
	{
	    try {
	        FileWriter bTreeFile = new FileWriter(outFilename);
	        CreateBTree(geneBankArgs);

			TreeObject<Long> currNode = bLong.getRoot();
			bTreeFile.write(bLong.getDegree() + "");
			bTreeFile.write("\n");
			bTreeFile.write(BTreeOutputFormat(bLong.getRoot()));
	        bTreeFile.close();

	      } catch (IOException e) {
	        System.out.println("An error occurred when writing to file.");
	        e.printStackTrace();
	      }
	}

	public void WriteBTreeDumpToFile(String outFilename)
	{
	    try {
	        FileWriter bTreeDumpFile = new FileWriter(outFilename);
	        CreateBTree(geneBankArgs);

			TreeObject<Long> currNode = bLong.getRoot();
			//bTreeDumpFile.write(bLong.getDegree() + "");
			//bTreeDumpFile.write("\n");
			bTreeDumpFile.write(BTreeDumpFormat(bLong.getRoot()));
	        bTreeDumpFile.close();

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
	public String BTreeOutputFormat(TreeObject<Long> t)
	{
		String s = "";
		if (t!=null && !t.isEmpty())
		{
			int linenum = 2;
			int ref = 0;
			TreeObject<Long> parentRef = new TreeObject<Long>();
			ArrayList<TreeObject<Long>> tQueue = new ArrayList<TreeObject<Long>>();
			if (t.getParent()==null)
			{
				t.setParent(parentRef);
			}
			t.setLineNum(1);
			tQueue.add(t);
			while (!tQueue.isEmpty())
			{
				TreeObject<Long> curr = tQueue.get(0);
				parentRef = curr.getParent();
				// append string to file formatted string
				ArrayList<String> strarr = addChildLineNum(curr.toString(), linenum, curr);
				s = s + parentRef.getLineNum() + strarr.toString() + "\n";
				
				// update the queue and line number
				tQueue.remove(curr);
				linenum = linenum + curr.size() + 1;
				
				if (!curr.isLeaf())
				{
					ArrayList<TreeObject<Long>> currChild = curr.getAllChildren();
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

	public String BTreeDumpFormat (TreeObject<Long> t) {
		String str = "";
		String token = "";
		if(t.isLeaf()){
			for(int i = 0; i<t.getAllKeys().size(); i++){
				token = String.format("%s: %d\n", SequenceUtils.LongToString(t.getKey(i), geneBankArgs.subsequenceLength()), t.getFreq(i));
				str = str.concat(token);
			}
		} else {
			int i;
			for (i = 0; i<t.getAllKeys().size(); i++){
				if(t.hasChild(i)){
					str = str.concat(BTreeDumpFormat(t.getChild(i)));
				} 
				token = String.format("%s: %d\n", SequenceUtils.LongToString(t.getKey(i), geneBankArgs.subsequenceLength()), t.getFreq(i));
				str = str.concat(token);
			}
			if (t.hasChild(i)){
				str = str.concat(BTreeDumpFormat(t.getChild(i)));
			}
		}
		return str;
	}
	
	/**
	 * Appends the child line numbers inside an existing string
	 * containing elements of a Tree Object
	 * @param format, line
	 * @returns the new Tree Object format string with child
	 * line numbers included
	 **/
	public ArrayList<String> addChildLineNum(String format, int line, TreeObject<Long> node)
	{
		ArrayList<String> strarr = new ArrayList<String>();
		if (format!=null && format.length()>2)
		{
			format = format.substring(1,format.length()-1);
			format = format.replaceAll(" ","");
			strarr = new ArrayList<String>(Arrays.asList(format.split(",")));
			int length = strarr.size();
			for (int i = 0; i < length+1; i++)
			{
				if(node.getChild(i) != null){
					strarr.add(2*i, line + "");
					line++;
				}
			}
		}
		return strarr;
	}
	
}
