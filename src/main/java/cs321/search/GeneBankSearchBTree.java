package cs321.search;

import cs321.btree.BTree;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
import cs321.common.ParseArgumentUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.function.LongBinaryOperator;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws Exception
    {
        boolean cacheImplemented = false;

        boolean useCache;
        String btreeFile;
        String queryFile;
        int cacheSize;
        int debugLevel;
        int degree;
        TreeObject<Long> rootNode;
        TreeObject<Long> currentNode;
        long currentQuery;

        try{
        GeneBankSearchBTreeArguments.checkNumArguments(args);
        useCache = GeneBankSearchBTreeArguments.useCache(args);
        debugLevel = GeneBankSearchBTreeArguments.debugLevel(args);
        if (useCache)
            cacheSize = GeneBankSearchBTreeArguments.cacheSize(args);
        btreeFile = GeneBankSearchBTreeArguments.openBTreeFile(args);
        queryFile = GeneBankSearchBTreeArguments.openQueryFile(args);
        } catch (ParseArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

//  public int ParseDegree(String btreeFile) {
//  }

    public TreeObject<Long> ParseRootNode(String btreeFile){
        TreeObject<Long> node = new TreeObject<Long>();
        String str = new String();
        String[] strArray;
        String[] smallArray; 

        try (Stream<String> lines = Files.lines(Paths.get(btreeFile))) {
            str = lines.skip(1).findFirst().get();
        } catch (IOException e) {
            System.out.println("Failed to retrieve root node. Please ensure file is formatted correctly");
            System.exit(2);
        } finally {
            node.setParent(null);       
        }
        str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
        strArray = str.split(",");

        int keyPos = 0;
        for (int i = 0; i < strArray.length; i++){
            if (strArray[i].contains(":")){
                strArray[i] = strArray[i].substring(1,strArray[i].length());
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

    public TreeObject<Long> ParseNode(File btreeFile, int index){
        TreeObject<Long> node = new TreeObject<Long>();
        return node;
    }

}

