package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper {

    private String filename;      // Dumping destination
    PhoneCall call;

    /**
     * <code>TextDumper</code>
     * The parameters are passed from the main class and thus provides filename and customer name,
     *
     * @param fileName
     * @param call
     */
    public TextDumper(String fileName, PhoneCall call) {
        this.filename = fileName;
        this.call = call;
    }

    /** <code>Check File</code> checks if the file exists and if not creates it.
     * @param file_path
     */

    public static void check_file(String file_path) {
        try {
            File   f                  = new File(file_path);
            String file_relative_path = f.getCanonicalPath();
            f = new File(file_relative_path);
            if (f.getName().toLowerCase().endsWith(".txt")) {
                if (f.exists() && f.length() == 0.0 && f.isFile()) {
                    System.out.println("It highly inappropriate, since either file does not exist or size is 0.");
                    System.exit(0);
                } else {
                    f.createNewFile();
                }
            } else {
                System.out.println("File name not appropriate.");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            System.exit(0);
        }
    }

    /**
     * <code>Dump</code>
     * Dumps a phone bill to the mentioned file name.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        List<String>  args_method = new ArrayList<String>();
        Integer  size_bill   = bill.getPhoneCalls().size();

        args_method.add(bill.getCustomer()); // adding customer name
        args_method.add(call.Caller_number); // adding caller number
        args_method.add(call.Callee_number); // adding callee number
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String start_time,end_time;
        start_time = sdf.format(call.getStartTime1).toLowerCase();
        end_time = sdf.format(call.getEndTime1).toLowerCase();
        args_method.add(start_time); // adding start time
        args_method.add(end_time); // adding end time

        bill.addPhoneCall(call);
        map.put(size_bill + 1, args_method);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer      key    = entry.getKey();
            List<String> values = entry.getValue();
            writer.write(String.join(",", values) + "\n");
        }
        writer.close();
    }
}

