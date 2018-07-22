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
      assertThat(result.getTextWrittenToStandardError(), containsString("Missing argument"));
  }

    @Test
    public void checkMissingCallerNumber() {
        MainMethodResult result = invokeMain(Project1.class, "xyz");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing caller_number"));
    }

    @Test
    public void invokeMainWithAllCallerInformation() {
        MainMethodResult result = invokeMain(Project1.class, "shikha shah", "503-473-4347", "123-456-7890", "1/2/3123", "1:2", "1/2/3123", "1:2");
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

    @Test
    public void invokeMainWithWrongTime() {
        MainMethodResult result = invokeMain(Project1.class, "shikha shah", "503-473-4347", "123-456-7890", "1/2/3123", "1:2", "1/2/3123", "1:");
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

    @Test
    public void dashReadmeOptionPrintsOnlyReadme() {
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(Project1.README + "\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
    }

    @Test
    public void dashPrintOptionsPrintsNewlyCreatedPhoneCall() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";
        String name = "My Customer";
        MainMethodResult result =
                invokeMain(Project1.class, "-print", name, caller, callee, startDate, startTime, endDate, endTime);

        assertThat(result.getExitCode(), equalTo(0));
        String phoneCallToString = String.format("Phone call from %s to %s from %s %s to %s %s",
                caller, callee, startDate, startTime, endDate, endTime);
        assertThat(result.getTextWrittenToStandardOut(), containsString(phoneCallToString));
    }

    @Test
    public void validCommandLineWithNoDashPrintOptionPrintsNothingToStandardOut() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, endDate, endTime);

        assertThat(result.getExitCode(), equalTo(0));

        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

    }

    @Ignore
    @Test
    public void inProject2checkREADME() {
        MainMethodResult result =
                invokeMain(Project2.class,"-README");

        assertThat(result.getExitCode(), equalTo(0));
        //assertThat(result.getTextWrittenToStandardOut(), equalTo(Project2.README + "\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

    }

    @Ignore
    @Test
    public void Project2AppendtoFile() {
        String file_path = "/u/shikha2/Downloads/APJ/phonebill/xyz.txt";
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "07/04/2018";
        String startTime = "6:24";
        String endDate = "07/04/2018";
        String endTime = "6:48";

        MainMethodResult result =
                invokeMain(Project2.class,"-textFile" ,file_path, "-print","My Customer", caller, callee, startDate, startTime, endDate, endTime);

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(
                "Phone call from 123-456-7890 to 234-567-8901 from 07/04/2018 6:24 to 07/04/2018 6:48\\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

    }
}
