package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper {

    private String filename;      // Dumping destination
    PhoneCall call ;

    /**
     * Creates a new text dumper that dumps to a file of a given name.
     * If the file does not exist, it is created.
     */
    public TextDumper(String fileName,PhoneCall call ) {
        this.filename = fileName;
        this.call = call;
    }


    /**
     * Dumps a phone bill to some destination.
        *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

     Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
     List<String> args_method = new ArrayList<String>();
     Integer size_bill = bill.getPhoneCalls().size();

     args_method.add(call.Caller_number);
     args_method.add(call.Callee_number);
     args_method.add(call.getStartTime);
     args_method.add(call.getEndTime);

     bill.addPhoneCall(call);
     map.put(size_bill+1, args_method);
     BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));

     for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
        Integer key = entry.getKey();
        List<String> values = entry.getValue();
        writer.write(String.join(",", values)+"\n");
        }
        writer.close();
    }
}

