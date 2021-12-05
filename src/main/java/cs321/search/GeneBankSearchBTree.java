package cs321.search;

import cs321.btree.BTree;
import cs321.btree.TreeObject;
import cs321.common.ParseArgumentException;
import cs321.common.ParseArgumentUtils;

import java.io.File;
import java.util.function.LongBinaryOperator;

public class GeneBankSearchBTree
{
    public static void main(String[] args) throws Exception
    {
        boolean cacheImplemented = false;

        boolean useCache;
        String btreeFilepath;
        String queryFilepath;
        int cacheSize;
        int debugLevel;
        TreeObject<Long> rootNode;
        String[] querySeqs;

        if (args.length > 5 || args.length < 3){
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }

        if (Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > 1){
            if (Integer.parseInt(args[0]) == 1) {
                useCache = cacheImplemented ;
            } else {
                useCache = false;
            }
        } else {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }

        if (args.length == 5 && useCache){
            cacheSize = Integer.parseInt(args[3]);
            debugLevel = Integer.parseInt(args[4]);
        } else if (args.length == 4) {
            debugLevel = Integer.parseInt(args[3]);
        } else if (args.length == 3) {
            debugLevel = 0;
        } else {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }

        btreeFilepath = args[1];
        queryFilepath = args[2];

        File btreeFile = new File(btreeFilepath);
        File queryFile = new File(queryFilepath);
    }

    public TreeObject<Long> ParseRootNode(File btreeFile){
        TreeObject<Long> node = new TreeObject<Long>();
        return node;
    }

    public TreeObject<Long> ParseNode(File btreeFile, int index){
        TreeObject<Long> node = new TreeObject<Long>();
        return node;
    }

}

