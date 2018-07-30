package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String name, caller_number, callee_number, start_date, start_time, end_date, end_time, file_path, start_AMorPM, end_AMorPM, pretty_file;
        name = caller_number = callee_number = start_date = start_time = end_date = end_time = file_path = start_AMorPM = end_AMorPM = pretty_file = null;

        for (String arg : args) {
            if (hostName == null) {
                hostName = arg;

            } else if ( portString == null) {
                portString = arg;

            } else if (name == null) {
                name = arg;
            } else if (caller_number == null) {
                caller_number = arg;
            } else if (callee_number == null) {
                callee_number = arg;
            } else if (start_date == null) {
                start_date = arg;
            } else if (start_time == null) {
                start_time = arg;
            } else if (start_AMorPM == null) {
                if (arg.toLowerCase().matches("am") || arg.toLowerCase().matches("pm")) {
                    start_AMorPM = arg;
                } else {
                    System.out.println("Your 12-hour format for start time is not specified properly!");
                    System.exit(0);
                }
            } else if (end_date == null) {
                end_date = arg;
            } else if (end_time == null) {
                end_time = arg;
            } else if (end_AMorPM == null) {
                if (arg.toLowerCase().matches("am") || arg.toLowerCase().matches("pm")) {
                    end_AMorPM = arg;
                } else {
                    System.out.println("Your 12-hour format is not specified properly!");
                    System.exit(0);
                }

            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);
        PhoneBill           bill   = new PhoneBill(name);

        String message;
        try {

            Validation val      = new Validation(name, caller_number, callee_number, start_date, start_time, end_date, end_time, start_AMorPM, end_AMorPM);
            PhoneCall  new_call = new PhoneCall(val);
            bill.addPhoneCall(new_call);
            message = client.getPrettyPhoneBill(name);

        } catch ( IOException ex ) {
            error("While contacting server: " + ex);
            return;
        }

        System.out.println(message);

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  call         call in bill");
        err.println("  bill   Definition of call");
        err.println();
        err.println("This simple program posts bill and their calls");
        err.println("to the server.");
        err.println("If specific search time is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no search time is specified, all entries are printed");
        err.println();

        System.exit(1);
    }
}