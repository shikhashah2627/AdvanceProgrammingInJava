package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextParser implements edu.pdx.cs410J.PhoneBillParser {

    String filename  = "";
    String customer_name = "";
    Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

    /**
     * Parametrized Constructor <code>TextParser</code>
     * assigns the file name and customer name
     * that is obtained from the main function.
     * @param filename
     * @param customer_name
     */
    public TextParser(String filename,String customer_name) {
        this.filename = filename;
        this.customer_name = customer_name;

    }

    /**
     * Parses the existing bill which is the source and returns a phone bill after the validation is completed.
     *
     * @throws ParserException If the source cannot be parsed
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {

        List<String> args_method;
        int line_number = 1;
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while((line = reader.readLine()) != null) {
                String [] items = line.split(",");
                args_method = Arrays.asList(items);
                map.put(line_number,args_method);
                line_number++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<PhoneCall> calls = new ArrayList<>();

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer key = entry.getKey();
            List<String> values = entry.getValue();
            if (values.get(0).matches(this.customer_name)) {
                String[] start_date = values.get(3).split(" ");
                String[] end_date   = values.get(4).split(" ");
                // the values of the file are passed to the validation class.
                // passing cusotmer name as an argument from file.
                Validation val  = new Validation(values.get(0), values.get(1), values.get(2), start_date[0], start_date[1], end_date[0], end_date[1],start_date[2], end_date[2]);
                PhoneCall  call = new PhoneCall(val);
                ((ArrayList<PhoneCall>) calls).add(call);
            } else {
                System.out.println("Passed customer name and the file name doesn't correlate.");
                System.exit(0);
            }
        }

        // create the phone bill by adding the existing call which are read from the file.
        PhoneBill bill = new PhoneBill(customer_name);
        for (PhoneCall c: calls) {
            bill.addPhoneCall(c);
        }

        return bill;
    }

    public AbstractPhoneBill sorted() throws ParserException {
        List<ArrayList<String>> sortedbill = new ArrayList<ArrayList<String>>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            Integer      key    = entry.getKey();
            List<String> values = entry.getValue();
            sortedbill.add(new ArrayList<String>(values));
        }
        Collections.sort(sortedbill, Comparator.comparing(o -> o.get(3)));
        Collections.sort(sortedbill, Comparator.comparing(o -> o.get(1)));

        Collection<PhoneCall> calls = new ArrayList<>();

        for (int i = 0; i < sortedbill.size(); i++) {
            String[] start_date = sortedbill.get(i).get(3).split(" ");
            String[] end_date   = sortedbill.get(i).get(4).split(" ");
            Validation val  = new Validation(sortedbill.get(i).get(0), sortedbill.get(i).get(1),sortedbill.get(i).get(2),start_date[0], start_date[1], end_date[0], end_date[1],start_date[2], end_date[2]);
            PhoneCall  call = new PhoneCall(val);
            ((ArrayList<PhoneCall>) calls).add(call);
        }

        PhoneBill bill = new PhoneBill(customer_name);
        for (PhoneCall c: calls) {
            bill.addPhoneCall(c);
        }

        return bill;
    }
}
