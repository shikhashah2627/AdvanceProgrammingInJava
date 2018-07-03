package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

    @Test
    public void checkMissingCallerNumber() {
        MainMethodResult result = invokeMain(Project1.class, "xyz");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing caller_number"));
    }

    @Ignore
    @Test
    public void invokeMainWithAllCallerInformation() {
        MainMethodResult result = invokeMain(Project1.class, "shikha shah", "503-473-4347", "123-456-7890", "1/2/3123 1:2", "1/2/3123 1:2");
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

}