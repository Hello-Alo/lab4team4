package cs321.search;

import cs321.common.*;
import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankSearchBTreeArguments
{

    /**
     * Checks that there are a valid number of arguments, throws ParseArgumentException if not
     * @param args a string array of 3 to 5 arguments
     * @return true if there are a valid number of arguments.
     * @throws ParseArgumentException
     */
    public static boolean checkNumArguments(String[] args) throws ParseArgumentException{
        if (args.length > 5 || args.length < 3)
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        
        return true;
    }

    /**
     * Retrieves the first argument from args, and converts it to a boolean.
     * @param args
     * @return true if the first argument is 1, false if it is 0
     * @throws ParseArgumentException
     */
    public static boolean useCache (String[] args) throws ParseArgumentException{
        try{
            if (Integer.parseInt(args[0]) == 1)
                return true;
            else if (Integer.parseInt(args[0]) == 0)
                return false;
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
       }
       return false;
    }

    /**
     * Checks if useCache is true or false, then checks if an argument that could be debug level exists. 
     * then converts the appropriate arg to an int.
     * @param args
     * @return debugLevel, the debug value of the program.
     * @throws ParseArgumentException
     */
    public static int debugLevel(String[] args) throws ParseArgumentException{
        int i;
        try {
            if (Integer.parseInt(args[0]) == 1){
                i = Integer.valueOf(Integer.parseInt(args[5]));
                return i;
            } else if (args.length == 4) {
                i = Integer.valueOf(Integer.parseInt(args[4]));       
                return i;         
            } else{
                i = 0;
                return i;
            }
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        } catch (IndexOutOfBoundsException e) {
            i = 0; 
            return i;
        }
    }

    /**
     * Checks if useCache is true, then converts the appropriate arg to an int
     * Cache use is not implemented.
     * @param args
     * @return cacheSize, the size of the cache, in bytes
     * @throws ParseArgumentException
     */
    public static int cacheSize(String[] args) throws ParseArgumentException{
        int i;
        try {
            i = Integer.valueOf(Integer.parseInt(args[4]));
            return i;
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }

    /**
     * Checks that the given file of argument 2 exists
     * @param args
     * @return btreeFile, the string name of the given file
     * @throws ParseArgumentException
     */
    public static String openBTreeFile(String[] args) throws ParseArgumentException{
        try{
            File file = new File(args[1]);
            if (!file.exists())
                throw new FileNotFoundException();
                return args[1];
        } catch (FileNotFoundException e) {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }

    /**
     * checks that the givn file of argument 3 exists
     * @param args
     * @return queryFile, the string name of the given file
     * @throws ParseArgumentException
     */
    public static String openQueryFile(String[] args) throws ParseArgumentException{
        try{
            File file = new File(args[2]);
            if (!file.exists())
                throw new FileNotFoundException();
                return args[2];
        } catch (FileNotFoundException e) {
            throw new ParseArgumentException("Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }
}
