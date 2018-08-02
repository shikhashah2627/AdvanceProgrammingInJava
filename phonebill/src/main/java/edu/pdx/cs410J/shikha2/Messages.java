package edu.pdx.cs410J.shikha2;

import java.io.PrintWriter;
import java.text.ParseException;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String inAppropriateRequiredParameter(String parameterName, String errorMessage) {
        return String.format("The required parameter \"%s\" is inappropriate because of %s", parameterName, errorMessage);
    }

    /**
     * @param new_call_information - new call details
     * @return prints the new call information that has been added.
     */

    public static String new_call_information(String new_call_information)
    {
        return "New call information is " + new_call_information;
    }

    public static String allDictionaryEntriesDeleted() {
        return "All Phone Call entries have been deleted";
    }

    public static void printAllCallValues(PrintWriter writer, PhoneBill bill) {
        PrettyPrinter pretty = new PrettyPrinter(writer);
        PhoneBill     bill1  = (PhoneBill) pretty.sorted(bill);
        writer.println(bill.getCustomer());
        bill1.getPhoneCalls().forEach((call) -> writer.println(call.toString()));
    }

    public static void printSearchedCallValues(PrintWriter writer, PhoneBill bill, String stat_date_time, String end_date_time) throws ParseException {
        PrettyPrinter pretty = new PrettyPrinter(writer);
        PhoneBill     bill1  = (PhoneBill) pretty.search(bill, stat_date_time, end_date_time);
        writer.println("Required Bill for " + bill.getCustomer());
        bill1.getPhoneCalls().forEach((call) -> writer.println(call.toString()));
    }
}
