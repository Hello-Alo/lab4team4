package cs321.create;

import cs321.Utils;
import cs321.common.SequenceUtils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SequenceUtilsTest
{
   /* @Test
    public void readGBKFileTest0AndGenerateDNASequencesTest() throws Exception
    {
        List<String> expectedListOfDNASequences = Utils.getLinesFromFile("data/files_gbk_expected_results/test0.gbk.sequences");

        //TODO: as soon as the getDNASequencesFromGBKGenomeFile method is implemented correctly, this unit test will pass without any modification
        List<String> actualListOfDNASequences = SequenceUtils.getDNASequencesFromGBKGenomeFile("data/files_gbk/test0.gbk");

        assertEquals(expectedListOfDNASequences.size(), actualListOfDNASequences.size());

        for (int i = 0; i < expectedListOfDNASequences.size(); i++)
        {
            assertEquals(expectedListOfDNASequences.get(i), actualListOfDNASequences.get(i));
        }
    }
    */
    //HINT: you can (manually) identify a few sequences in the other genome files
    //      and verify that the actualListOfDNASequences contains the identified sequences

    @Test
    public void testStringToLong() throws Exception
    {
        long targetLong = 255;
        String testString = "tttt";

        assertEquals(targetLong, (SequenceUtils.StringToLong(testString)));
    }

    @Test
    public void testLongToString1() throws Exception
    {
        String targetString = "aaaa";
        long testLong = 0;   

        assertEquals(targetString, (SequenceUtils.LongToString(testLong, 4)));
    }

    
    @Test
    public void testLongToString2() throws Exception
    {
        String targetString = "aaat";
        long testLong = 3;   

        assertEquals(targetString, (SequenceUtils.LongToString(testLong, 4)));
    }

    
    @Test
    public void testLongToString3() throws Exception
    {
        String targetString = "actt";
        long testLong = 31;   

        assertEquals(targetString, (SequenceUtils.LongToString(testLong, 4)));
    }
    
    @Test
    public void testLongToString4() throws Exception
    {
        String targetString = "tgcact";
        long testLong = 3655;   

        assertEquals(targetString, (SequenceUtils.LongToString(testLong, 6)));
    }
}
