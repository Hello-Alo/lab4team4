package cs321.search;

import cs321.common.*;
import java.io.File;
import java.io.FileNotFoundException;

public class GeneBankSearchBTreeArguments
{

    public static boolean checkNumArguments(String[] args) throws ParseArgumentException{
        if (args.length > 5 || args.length < 3)
            throw new ParseArgumentException("Check num Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        
        return true;
    }

    public static boolean useCache (String[] args) throws ParseArgumentException{
        try{
            if (Integer.parseInt(args[0]) == 1)
                return true;
            else if (Integer.parseInt(args[0]) == 0)
                return false;
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Cache yes/no Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
       }
       return false;
    }

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
            throw new ParseArgumentException("Debug level Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        } catch (IndexOutOfBoundsException e) {
            i = 0; 
            return i;
        }
    }

    public static int cacheSize(String[] args) throws ParseArgumentException{
        int i;
        try {
            i = Integer.valueOf(Integer.parseInt(args[4]));
            return i;
        } catch (NumberFormatException e) {
            throw new ParseArgumentException("Cache size Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }

    public static String openBTreeFile(String[] args) throws ParseArgumentException{
        try{
            File file = new File(args[1]);
            if (!file.exists())
                throw new FileNotFoundException();
                return args[1];
        } catch (FileNotFoundException e) {
            throw new ParseArgumentException("open btree Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }

    public static String openQueryFile(String[] args) throws ParseArgumentException{
        try{
            File file = new File(args[2]);
            if (!file.exists())
                throw new FileNotFoundException();
                return args[2];
        } catch (FileNotFoundException e) {
            throw new ParseArgumentException("open query Usage: java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
        }
    }
}
