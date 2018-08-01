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
    Map<String, PhoneCall>       map1 = new HashMap<String, PhoneCall>();
    Map.Entry<String, PhoneCall> entry

    {
        String    key    = entry.getKey();
        PhoneCall values = entry.getValue();
        System.out.println(entry.getValue());

    }

        for(

    /**
     * <code>PrettyPrinter</code> constructor with only one parameter.
     * If output is required as standard output and hence file name is set as null.
     *
     * @param writer
     */
    public PrettyPrinter(PrintWriter writer) {
        this.writer = writer;
    } :map1.entrySet())

    sorted(bill.getCustomer()

    getPhoneCalls()).

    forEach((call).
    @Override
    public void dump(PhoneBill bill) {
        writer.println(bill.getCustomer());
        Integer               size_bill = bill.getPhoneCalls().size();
        Collection<PhoneCall> call1     = bill.getPhoneCalls();

        foreACH
        for (Map.Entry<String, PhoneCall> entry : map1.entrySet()) {
            String    key  = bill.getCustomer();
            PhoneCall call = bill.getPhoneCalls().;
            map1.put(key, call);
        }
    } ->writer.println(call.toString()))
    //bill.getPhoneCalls().forEach((call) -> writer.println(call.toString()));
    }

    public AbstractPhoneBill sorted(String Customer_name) {
        List<ArrayList<String>> sortedbill = new ArrayList<ArrayList<String>>();
        SimpleDateFormat        sdf        = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String                  start_time, end_time;

        for (Map.Entry<String, PhoneCall> entry : map1.entrySet()) {
            String    key    = entry.getKey();
            PhoneCall values = entry.getValue();
            start_time = sdf.format(values.getStartTime1).toLowerCase();
            end_time = sdf.format(values.getEndTime1).toLowerCase();
            List<String> values_required = new ArrayList<String>();
            values_required.add(values.Caller_number);
            values_required.add(values.Callee_number);
            values_required.add(start_time);
            values_required.add(end_time);
            sortedbill.add(new ArrayList<String>(values_required));

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

        PhoneBill bill = new PhoneBill(Customer_name);
        for (PhoneCall c : calls) {
            bill.addPhoneCall(c);
        }

        return bill;
    }
}

