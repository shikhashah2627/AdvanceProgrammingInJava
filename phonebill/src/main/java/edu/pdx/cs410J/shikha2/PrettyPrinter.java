package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrettyPrinter implements PhoneBillDumper {

    String file_name,customer_name;

    public PrettyPrinter(String customer_name) {
        this.file_name = "";
        this.customer_name = customer_name;
    }

    public PrettyPrinter(String file_path, String customer_name) {
        this.file_name = file_path;
        this.customer_name = customer_name;
    }

    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

        Collection<PhoneCall> phoneCall  = bill.getPhoneCalls();
        int                   call_count = 1;


        if (this.file_name == "") {
            for (PhoneCall c : phoneCall) {
                long time_consumed = c.getEndTime1.getTime() - c.getStartTime1.getTime();
                time_consumed = time_consumed / (1000 * 60);
                System.out.println(c + " for " + time_consumed + " minutes.");
            }
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, true));
            for (PhoneCall c : phoneCall) {
                List<String> args_method = new ArrayList<String>();
                args_method.add(bill.getCustomer()); // adding customer name
                args_method.add(c.Caller_number); // adding caller number
                args_method.add(c.Callee_number); // adding callee number
                args_method.add(c.formatted_start_time); // adding start time
                args_method.add(c.formatted_end_time); // adding end time
                long time_consumed = c.getEndTime1.getTime() - c.getStartTime1.getTime();
                time_consumed = time_consumed / (1000 * 60);
                args_method.add(Long.toString(time_consumed));
                map.put(call_count, args_method);
                call_count++;

            }
            for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
                Integer      key    = entry.getKey();
                List<String> values = entry.getValue();
                //writer.write(String.join(",", values) + "\n");
                writer.write("Phone call from " + values.get(1) + " to " + values.get(2) + " from " + values.get(3) + " to " +
                        values.get(4) + " lasted for " + values.get(5) + " minutes." + "\n");
            }
            writer.close();
        }
    }
}
