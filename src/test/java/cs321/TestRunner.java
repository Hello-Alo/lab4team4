package cs321;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import cs321.create.SequenceUtilsTest;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(SequenceUtilsTest.class);
		
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.printf("Number of successful tests: %d\n", result.getRunCount() - result.getFailureCount());
      System.out.println("All tests passed: " + result.wasSuccessful());
   }
} 