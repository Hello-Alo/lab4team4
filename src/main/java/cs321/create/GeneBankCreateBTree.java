package cs321.create;

import cs321.common.ParseArgumentException;

public class GeneBankCreateBTree
{

    public static void main(String[] args) throws Exception
    {
        GeneBankCreateBTreeArguments geneBankArgs = parseArgumentsAndHandleExceptions(args);
        BTreeToFile br = new BTreeToFile(geneBankArgs);

        String btreeFilename = String.format("%s.btree.%d.%d", 
                                                geneBankArgs.gbkFileName(),
                                                geneBankArgs.degree(),
                                                geneBankArgs.subsequenceLength());
        br.WriteBTreeToFile(btreeFilename);

        if(geneBankArgs.debugLevel() == 1) {
            String btreeDumpFilename = String.format("%s.btree.dump.%d.%d", 
                                                        geneBankArgs.gbkFileName(), 
                                                        geneBankArgs.degree(),
                                                        geneBankArgs.subsequenceLength());
            br.WriteBTreeDumpToFile(btreeDumpFilename);
        }


    }

    private static GeneBankCreateBTreeArguments parseArgumentsAndHandleExceptions(String[] args)
    {
        GeneBankCreateBTreeArguments geneBankArgs = null;
        try
        {
            geneBankArgs = GeneBankCreateBTreeArguments.parseArguments(args);
        }
        catch (ParseArgumentException e)
        {
            printUsageAndExit(e.getMessage());
        }
        return geneBankArgs;
    }

    private static void printUsageAndExit(String errorMessage)
    {

        System.out.println(errorMessage);
        System.out.println("Usage: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> "
       + "[<cache size>] [<debug level>]");
        System.exit(1);
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException
    {
        return null;
    }

}
