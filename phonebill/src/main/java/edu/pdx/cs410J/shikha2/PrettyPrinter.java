package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {

    //String file_name
    private final PrintWriter writer;
    Map<Integer, List<String>>   map  = new HashMap<Integer, List<String>>();
    /**
     * <code>PrettyPrinter</code> constructor with only one parameter.
     * If output is required as standard output and hence file name is set as null.
     *
     * @param writer
     */
    public PrettyPrinter(PrintWriter writer) {
        this.writer = writer;
    }
    @Override

    public void dump(PhoneBill bill) {


        //writer.println(bill.getCustomer());
      //  sorted().getPhoneCalls().forEach((call) -> writer.println(call.toString()));
    }
    //bill.getPhoneCalls().forEach((call) -> writer.println(call.toString()));

    public AbstractPhoneBill sorted(PhoneBill bill) {
        Integer               size_bill = 1;
        Collection<PhoneCall> call1     = bill.getPhoneCalls();

        for (PhoneCall c: call1) {
            List<String> args = new ArrayList<String>();

            args.add(bill.getCustomer());

            args.add(c.Caller_number);
            args.add(c.Callee_number);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            String start_time,end_time;
            start_time = sdf.format(c.getStartTime1).toLowerCase();
            end_time = sdf.format(c.getEndTime1).toLowerCase();
            args.add(start_time); // adding start time
            args.add(end_time); // adding end time

            map.put(size_bill,args);
            size_bill++;
        }

        List<ArrayList<String>> sortedbill = new ArrayList<ArrayList<String>>();

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            List<String> values = entry.getValue();
            sortedbill.add(new ArrayList<String>(values));
        }

        //sorting the bill first with start time and then phone numbers
        sortedbill.sort(Comparator.comparing((List<String> l) -> l.get(3)).thenComparing(l -> l.get(1)));

        Collection<PhoneCall> calls = new ArrayList<>();

        for (int i = 0; i < sortedbill.size(); i++) {
            String[]   start_date = sortedbill.get(i).get(3).split(" ");
            String[]   end_date   = sortedbill.get(i).get(4).split(" ");
            Validation val        = new Validation(sortedbill.get(i).get(0), sortedbill.get(i).get(1), sortedbill.get(i).get(2), start_date[0], start_date[1], end_date[0], end_date[1], start_date[2], end_date[2]);
            PhoneCall  call       = new PhoneCall(val);
            calls.add(call);
        }
        PhoneBill bill1 = new PhoneBill(sortedbill.get(0).get(0));
        for (PhoneCall c : calls) {
            bill1.addPhoneCall(c);
        }

        return bill1;
    }
}

