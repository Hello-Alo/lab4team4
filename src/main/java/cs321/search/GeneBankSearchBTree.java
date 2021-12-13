package cs321.search;

import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
import cs321.common.SequenceUtils;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws Exception
    {
        boolean useCache;
        String btreeFile = "";
        String queryFile = "";
        String outFileName = "";
        int cacheSize;
        int debugLevel;
        TreeObject<Long> rootNode;
        String currentQuery;
        long queryLong;
        int freq;


        //Validate all given inputs
        try{
            GeneBankSearchBTreeArguments.checkNumArguments(args);
            useCache = GeneBankSearchBTreeArguments.useCache(args);
            debugLevel = GeneBankSearchBTreeArguments.debugLevel(args);
            if (useCache)
                cacheSize = GeneBankSearchBTreeArguments.cacheSize(args);
            btreeFile = GeneBankSearchBTreeArguments.openBTreeFile(args);
            queryFile = GeneBankSearchBTreeArguments.openQueryFile(args);
        } catch (ParseArgumentException e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        //Name the output file based on the btree file being queried, and the query file used
        outFileName = String.format("%s.%s", btreeFile, queryFile.substring(queryFile.lastIndexOf("/") + 1));

        //Load the root node, scanner for query, and the file to be written to
        rootNode = parseNode(btreeFile, 1);
        Scanner queryScan = new Scanner(new File(queryFile));
        FileWriter searchWriter = new FileWriter(outFileName);

        //For each line in the query, search for the given sequence, then write that sequence 
        //and its frequency in the btree (0 if not found) to the output file
        while (queryScan.hasNextLine()){
            currentQuery = queryScan.nextLine();
            queryLong = SequenceUtils.StringToLong(currentQuery);
            freq = Search(queryLong, rootNode, btreeFile);
            searchWriter.write(String.format("%s : %d\n", currentQuery, freq));
        }

        //cleanup
        queryScan.close();
        searchWriter.close();
    }

    /**
     * Search out a particular gene sequence in a btree, fed in as a long int and specially formatted txt file
     * @param seq the gene sequence to be searched for, as a long
     * @param node the node that is to be searched
     * @param btreeFile the file that the node originates from
     * @return freq the frequency the gene sequence is found in the gene file the btree was constructed from,
     *  or 0 if the gene sequence is not found.
     */
    public static int Search(long seq, TreeObject<Long> node, String btreeFile) {
        int i;
        //Un-comment the below line to show tree-traversal
        //System.out.println(node.toString());

        //if the given sequence is less than the left-most key, Search the left-most child
        //if the given sequence is greater than the right-most key, Search the right-most child
        //if the given sequence is greater than its left key and less than its right key, 
        //Search the child between those two values
        if (seq < node.getKey(0).longValue() && node.getChildLineNum(0) != -1 )
            return Search(seq, parseNode(btreeFile, node.getChildLineNum(0)), btreeFile);
        for (i = 0; i+1 < node.getAllKeys().size(); i++){
            if (seq == node.getKey(i).longValue()) 
                return node.getFreq(i); 
            if (seq > node.getKey(i).longValue() && seq < node.getKey(i+1).longValue() && node.getChildLineNum(i+1) != -1)
                return Search(seq, parseNode(btreeFile, node.getChildLineNum(i+1)), btreeFile);
            } 
        if (seq == node.getKey(i).longValue()) 
                return node.getFreq(i);
        if (seq > node.getKey(i).longValue() && node.getChildLineNum(i) != -1)
            return Search(seq, parseNode(btreeFile, node.getChildLineNum(i+1)), btreeFile);   

        return 0;
    }
    /**
     * Create a TreeObject from a line in a .btree file
     * @param btreeFile the .btree file from which to create the node
     * @param index the line # of the node
     * @return node a TreeObject for use in the Search method above.
     */
    public static TreeObject<Long> parseNode(String btreeFile, int index){
        TreeObject<Long> node = new TreeObject<Long>();
        String str = new String();
        String[] strArray;
        String[] smallArray; 

        //Check that the line was successfully retrieved
        try (Stream<String> lines = Files.lines(Paths.get(btreeFile))) {
            str = lines.skip(index).findFirst().get();
        } catch (IOException e) {
            System.out.println("Failed to retrieve node. Please ensure file is formatted correctly");
            System.exit(2);
        } finally {
            node.setParent(null);     
        }
        //Strip the line of all spaces, remove extraneous info, and break it into substrings around commas
        str = str.replace(" ", "");
        str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        strArray = str.split(",");

        //For each datum in the substring, check for a colon, which indicates data, 
        // if it is there, prcoss the data as a key and frequency, otherwise, 
        // set the given number as the line number of that child
        int keyPos = 0;
        for (int i = 0; i < strArray.length; i++){
            if (strArray[i].contains(":")){
                smallArray = strArray[i].split(":");
                node.setKey(keyPos, Long.parseLong(smallArray[0]));
                node.setFreq(keyPos,Integer.parseInt(smallArray[1]));
                keyPos++;
            } else {
                node.setChildLineNum(keyPos, Integer.parseInt(strArray[i]));
            }
        }
        return node;
    }
}

