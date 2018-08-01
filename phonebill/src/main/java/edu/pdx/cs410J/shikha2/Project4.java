package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    static final String README = "Author for Project 4: PhoneCall Project is : Shikha Shah. " + "\n" +
            "This project takes the command line argument and also works on URL 'Localhost:8080/phonecall' which asks for the required argument. " +
            "You can also print the phone calls by giving only customer name and also extended search option by passing start and end date." + "\n" +
            "On client side for command line as the parameters are provided host name and port numbers are compulsory";

    public static void main(String... args) throws IOException {
        String hostName = null;
        String portString = null;
        String name, caller_number, callee_number, start_date, start_time, end_date, end_time, start_AMorPM, end_AMorPM;
        name = caller_number = callee_number = start_date = start_time = end_date = end_time = start_AMorPM = end_AMorPM = null;

        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        if (containsOption(args, "-README")) {
            System.out.println(README);
            System.exit(0);
        }

        boolean host_set        = false;
        boolean port_set        = false;
        boolean search_criteria = false;

        for (String arg : args) {

            // set host name
            if (arg.matches("-host")) {
                host_set = true;
                continue;
            }
            if (arg.matches("-host") && hostName == null && host_set) {
                hostName = arg;
            }

            // set port number
            if (arg.matches("-port")) {
                port_set = true;
                continue;
            }
            if (arg.matches("-port") && portString == null && port_set) {
                portString = arg;
            }

            if (hostName == null) {
                usage(MISSING_ARGS);

            } else if (portString == null) {
                usage("Missing port");
            }

            if (arg.matches("-search") || arg.matches("-print")) continue;

            if (name == null) {
                name = arg;
            } else if (caller_number == null) {
                if (arg.matches("-search")) {
                    continue;
                } else caller_number = arg;
            } else if (callee_number == null) {
                if (arg.matches("-search")) {
                    continue;
                } else
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

        if (containsOption(args, "-search") && (name != null) && (start_date != null) && (start_time != null)
                && (start_AMorPM != null) && (end_AMorPM != null) && (end_date != null) && (end_time != null)) {

        } else {
            System.out.println("Some parameters for search criteria are missing");
            System.exit(1);
        }

        //adding phone call
        if (name != null && callee_number != null) {
            Validation val      = new Validation(name, caller_number, callee_number, start_date, start_time, end_date, end_time, start_AMorPM, end_AMorPM);
            PhoneCall  new_call = new PhoneCall(val);
            bill.addPhoneCall(new_call);
            client.addPhoneCall(bill.getCustomer(), new_call);
            //message = client.getPrettyPhoneBill(name);
            //System.out.println(message);

        } else {
            if (name != null) {
                //message = client.(name);
                // System.out.println(message);
            }
        }

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
        err.println("usage: java Project4 host port [Bill] [Call]");
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

    /**
     * <code>containsOption</code>
     *
     * @param args
     * @param option
     * @return list of all the options passed in the argument.
     */
    private static boolean containsOption(String[] args, String option) {
        return Arrays.stream(args).anyMatch(s -> s.equals(option));
    }
}