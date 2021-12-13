package cs321.common;


/**
 * Extract DNA Sequences and Generate DNA Subsequences
 */
public class SequenceUtils
{
    private static final int MAX_SEQ_LEN = 31;

    /**
     * Converts a long int into a sequence of letters a, t, c, g, representing a DNA sequence
     * @param longDNA the long to be converted 
     * @param seqLength the length of the desired sequence
     * @return stringDNA a string representing a sequence of DNA
     */
    public static String LongToString (long longDNA, int seqLength) {
        String stringDNA;
        String[] binArray = new String[MAX_SEQ_LEN];

        stringDNA = Long.toBinaryString(longDNA);
        
        //Right-justify if there is a leading c, or c after leading a's
        if(stringDNA.length() %2 != 0)
            stringDNA = "0".concat(stringDNA);

        //Right-justify if there are any leading a's
        while(stringDNA.length() < seqLength * 2)
            stringDNA = "00".concat(stringDNA);

        //instantiate binary array
        for (int i = 0; i < MAX_SEQ_LEN; i++)
            binArray[i] = "00";

        //split the string into an array of 2-bit binary strings
        for (int i = 0; 2* i < stringDNA.length(); i++)
            binArray[MAX_SEQ_LEN - seqLength + i] = stringDNA.substring(i*2, (i*2) + 2);

        //empty stringDNA to use it to store the final return value
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

    /**
     * Converts a string sequence of DNA into a long for machine usability and storage optimization
     * @param stringDNA a string DNA sequence consisting only of the characters a, t, c, and g
     * @return longDNA a long int equivalent to the given string sequence of DNA
     */
    public static long StringToLong (String stringDNA) {
        //binDNA instantiated as "00" to exclude the signed bit
        String binDNA = "00";
        char[] charArrayDNA = stringDNA.toLowerCase().toCharArray();
        
        //right-justify the binary string
        for (int i = 0; i < MAX_SEQ_LEN - stringDNA.length(); i++){
            binDNA = binDNA.concat("00");
        }

        //concatenate the binary string with the binary code for each letter in sequence
        for(int i = MAX_SEQ_LEN - stringDNA.length(); i < MAX_SEQ_LEN; i++){
            switch(charArrayDNA[i - (MAX_SEQ_LEN - stringDNA.length())]){
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
