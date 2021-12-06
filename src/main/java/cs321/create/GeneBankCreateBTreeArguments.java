package cs321.create;

import java.io.File;
import java.io.FileNotFoundException;

import cs321.common.ParseArgumentException;

public class GeneBankCreateBTreeArguments
{
    private final boolean useCache;
    private final int degree;
    private final String gbkFileName;
    private final int subsequenceLength;
    private final int cacheSize;
    private final int debugLevel;

    public GeneBankCreateBTreeArguments(boolean useCache, int degree, String gbkFileName, int subsequenceLength, int cacheSize, int debugLevel)
    {
        this.useCache = useCache;
        this.degree = degree;
        this.gbkFileName = gbkFileName;
        this.subsequenceLength = subsequenceLength;
        this.cacheSize = cacheSize;
        this.debugLevel = debugLevel;
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args) throws ParseArgumentException{
        boolean useCache;
        int degree;
        String gbkFileName;
        int subsequenceLength;
        int cacheSize = 0;
        int debugLevel = 0;
        try{
            int i;

            if (args.length > 6 || args.length < 4)
                throw new ParseArgumentException("Invalid number of arguments");
            
            try {
                i = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("First argument must be 1 or 0");
            }
            if (i == 1)
                useCache = true;
            else if (i == 0)
                useCache = false;
            else
                throw new ParseArgumentException("First argument must be 1 or 0");

            try{
                degree = Integer.parseInt(args[1]);
                if (!(degree > 1))
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("Second argument must be an integer greater than 1");
            }

            gbkFileName = args[2];
            File testFile = new File(gbkFileName);
            try {
            if (!testFile.exists()){
                throw new FileNotFoundException("File not found, confirm file name");
            }
            } catch (FileNotFoundException e){
                throw new ParseArgumentException(e.getMessage());
            }

            try{
                subsequenceLength = Integer.parseInt(args[3]);
                if (subsequenceLength <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("Third argument must be an integer greater than 0");
            }
            
            try {
                if (useCache) {
                    if (args.length == 6){
                        cacheSize = Integer.parseInt(args[4]);
                        debugLevel = Integer.parseInt(args[5]);
                    } else if (args.length == 5){
                        cacheSize = Integer.parseInt(args[4]);
                    } else {
                        throw new ParseArgumentException("Incorrect number of arguments");
                    }
                } else {
                    if (args.length == 5) {
                        debugLevel = Integer.parseInt(args[4]);
                    } else if (args.length != 4) {
                        throw new ParseArgumentException("Incorrect number of arguments");
                    }
                }
            } catch (NumberFormatException e) {
                throw new ParseArgumentException("the provided [<cacheSize>] or [<debugLevel>] is not a number");
            } catch (ParseArgumentException e) {
                throw new ParseArgumentException(e.getMessage());
            }
        } catch (ParseArgumentException e) {
            throw e;
        }

        return new GeneBankCreateBTreeArguments(useCache, degree, gbkFileName, subsequenceLength, cacheSize, debugLevel);
    }

    public boolean useCache(){
        return this.useCache;
    }

    public int degree(){
        return this.degree;
    }

    public String gbkFileName(){
        return this.gbkFileName;
    }

    public int subsequenceLength(){
        return this.subsequenceLength;
    }

    public int cacheSize(){
        return this.cacheSize;
    }

    public int debugLevel() {
        return this.debugLevel;
    }

    @Override
    public String toString()
    {
        //this method was generated using an IDE
        return "GeneBankCreateBTreeArguments{" +
                "useCache=" + useCache +
                ", degree=" + degree +
                ", gbkFileName='" + gbkFileName + '\'' +
                ", subsequenceLength=" + subsequenceLength +
                ", cacheSize=" + cacheSize +
                ", debugLevel=" + debugLevel +
                '}';
    }
}
