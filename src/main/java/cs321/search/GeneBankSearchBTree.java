package cs321.search;

import cs321.btree.BTree;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
import cs321.common.ParseArgumentUtils;
import cs321.create.SequenceUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileWriter;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.function.LongBinaryOperator;

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

       // PrintStream o = new PrintStream(new File("querytest1.txt"));
        // System.setOut(o);

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

        outFileName = String.format("%s.%s", btreeFile, queryFile.substring(queryFile.lastIndexOf("/") + 1));

        rootNode = parseNode(btreeFile, 1);
        Scanner queryScan = new Scanner(new File(queryFile));
        FileWriter searchWriter = new FileWriter(outFileName);

        while (queryScan.hasNextLine()){
            currentQuery = queryScan.nextLine();
            queryLong = SequenceUtils.StringToLong(currentQuery);
            freq = Search(queryLong, rootNode, btreeFile);
            searchWriter.write(String.format("%s : %d\n", currentQuery, freq));
        }
        queryScan.close();
        searchWriter.close();
    }

    /**
     * Search out a particular gene sequence in a btree, fed in as a long int and specially formatted txt file
     * @param seq the gene sequence to be searched for, as a long
     * @param node the node that is to be searched
     * @param btreeFile the file that the node originates from
     * @return freq the frequency the gene sequence is found in the gene file the btree was constructed from.
     */
    public static int Search(long seq, TreeObject<Long> node, String btreeFile) {
        int i;
        //Un-comment the below line to show tree-traversal
        //System.out.println(node.toString());
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

        try (Stream<String> lines = Files.lines(Paths.get(btreeFile))) {
            str = lines.skip(index).findFirst().get();
        } catch (IOException e) {
            System.out.println("Failed to retrieve node. Please ensure file is formatted correctly");
            System.exit(2);
        } finally {
            node.setParent(null);     
        }
        str = str.replace(" ", "");
        str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        strArray = str.split(",");

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

