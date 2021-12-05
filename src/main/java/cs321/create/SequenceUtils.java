package cs321.create;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract DNA Sequences and Generate DNA Subsequences
 */
public class SequenceUtils
{
    private static final String MARKER_START_DNA_SEQUENCE = "ORIGIN";
    private static final int MAX_SEQ_LEN = 31;

    public static List<String> getDNASequencesFromGBKGenomeFile(String gbkGenomeFileName) throws Exception
    {
        return new ArrayList<>();
    }

    public static String LongToString (long longDNA, int seqLength) {
        String stringDNA;
        String[] binArray = new String[MAX_SEQ_LEN];

        stringDNA = Long.toBinaryString(longDNA);
        
        for (int i = 1; i <= MAX_SEQ_LEN; i++){
            binArray[i-1] = stringDNA.substring(i*2, (i*2) + 1);
        } 
        stringDNA = "";
        for (int i = MAX_SEQ_LEN - seqLength ; i < MAX_SEQ_LEN; i++){
            switch(binArray[i]) {
                case "00":
                    stringDNA = stringDNA.concat("a");
                    break;
                case "11":
                    stringDNA = stringDNA.concat("t");
                    break;
                case "01":
                    stringDNA = stringDNA.concat("c");
                    break;
                case "10":
                    stringDNA = stringDNA.concat("g");
                    break;
            }
        }
        return stringDNA;
    }

    public static Long StringToLong (String stringDNA, int seqLength) {
        String binDNA = "00";
        char[] charArrayDNA = stringDNA.toCharArray();
        
        for (int i = 0; i < MAX_SEQ_LEN - seqLength; i++){
            binDNA = binDNA.concat("00");
        }

        for(int i = MAX_SEQ_LEN - seqLength; i < MAX_SEQ_LEN; i++){
            switch(charArrayDNA[i - (MAX_SEQ_LEN - seqLength)]){
                case 'a':
                    binDNA = binDNA.concat("00");
                    break;
                case 't':
                    binDNA = binDNA.concat("11");
                    break;
                case 'c':
                    binDNA = binDNA.concat("01");
                    break;
                case 'g':
                    binDNA = binDNA.concat("10");
                    break;
            }
        }
        return Long.parseLong(binDNA,2);    
    }
}
