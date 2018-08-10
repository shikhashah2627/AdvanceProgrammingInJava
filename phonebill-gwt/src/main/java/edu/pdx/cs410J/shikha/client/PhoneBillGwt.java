package edu.pdx.cs410J.shikha.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    private final Alerter               alerter;
    private final PhoneBillServiceAsync phoneBillService;
    private final Logger                logger;

    private final DeckPanel layoutPanel = new DeckPanel();
    String Customer_Name,Caller_Number,Callee_Number,Star_Date_Time,End_Date_Time;
    public PhoneBillGwt() {
        this(new Alerter() {
            @Override
            public void alert(String message) {
                Window.alert(message);
            }
        });
    }

    @VisibleForTesting
    PhoneBillGwt(Alerter alerter) {
        this.alerter = alerter;
        this.phoneBillService = GWT.create(PhoneBillService.class);
        this.logger = Logger.getLogger("phoneBill");
        Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
    }

    private void alertOnException(Throwable throwable) {
        Throwable     unwrapped = unwrapUmbrellaException(throwable);
        StringBuilder sb        = new StringBuilder();
        sb.append(unwrapped.toString());
        sb.append('\n');

        for (StackTraceElement element : unwrapped.getStackTrace()) {
            sb.append("  at ");
            sb.append(element.toString());
            sb.append('\n');
        }

        this.alerter.alert(sb.toString());
    }

    private Throwable unwrapUmbrellaException(Throwable throwable) {
        if (throwable instanceof UmbrellaException) {
            UmbrellaException umbrella = (UmbrellaException) throwable;
            if (umbrella.getCauses().size() == 1) {
                return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
            }

        }

        return throwable;
    }

//  private void addWidgets(VerticalPanel panel) {
//    showPhoneBillButton = new Button("Show Phone Bill");
//    showPhoneBillButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent clickEvent) {
//        showPhoneBill();
//      }
//    });
//
//    showUndeclaredExceptionButton = new Button("Show undeclared exception");
//    showUndeclaredExceptionButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent clickEvent) {
//        showUndeclaredException();
//      }
//    });
//
//    showDeclaredExceptionButton = new Button("Show declared exception");
//    showDeclaredExceptionButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent clickEvent) {
//        showDeclaredException();
//      }
//    });
//
//    showClientSideExceptionButton= new Button("Show client-side exception");
//    showClientSideExceptionButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent clickEvent) {
//        throwClientSideException();
//      }
//    });
//
//    panel.add(showPhoneBillButton);
//    panel.add(showUndeclaredExceptionButton);
//    panel.add(showDeclaredExceptionButton);
//    panel.add(showClientSideExceptionButton);
//  }

    private void addMenuItems(MenuBar menu) {
        menu.setWidth("300px");
        MenuBar HelpMe = new MenuBar();
        HelpMe.addItem("About Us", new Command() {
            @Override
            public void execute() {
                showPhoneBill();
            }
        });
        menu.addItem(new MenuItem("Add Call", new Command() {
            @Override
            public void execute() {

            }
        }));
        menu.addSeparator();
        menu.addItem(new MenuItem("Search Call", new Command() {
            @Override
            public void execute() {

            }
        }));
        menu.addSeparator();
        MenuBar About_Us = new MenuBar();
        About_Us.setWidth("150px");
        About_Us.addItem(new MenuItem("README", new Command() {
            @Override
            public void execute() {
                showReadmeValue();
            }
        }));

        menu.addItem(new MenuItem("About Us", About_Us));
    }


    private void throwClientSideException() {
        logger.info("About to throw a client-side exception");
        throw new IllegalStateException("Expected exception on the client side");
    }

    private void showUndeclaredException() {
        logger.info("Calling throwUndeclaredException");
        phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    private void showDeclaredException() {
        logger.info("Calling throwDeclaredException");
        phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    private void showPhoneBill() {
        logger.info("Calling getPhoneBill");
        phoneBillService.getPhoneBill(new AsyncCallback<PhoneBill>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(PhoneBill phoneBill) {
                StringBuilder         sb    = new StringBuilder(phoneBill.toString());
                Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
                for (PhoneCall call : calls) {
                    sb.append(call);
                    sb.append("\n");
                }
                alerter.alert(sb.toString());
            }
        });
    }

    private void showReadmeValue() {
        logger.info("Showing README values");
        StringBuilder sb = new StringBuilder();
        sb.append("This is all about the last Project 5 haivng GWT into it which provide the features like : Add Call and Search Call either with Customer Name or by providing" +
                "Start Date and End Date.");
        alerter.alert(sb.toString());
    }

    @Override
    public void onModuleLoad() {
        setUpUncaughtExceptionHandler();

        // The UncaughtExceptionHandler won't catch exceptions during module load
        // So, you have to set up the UI after module load...
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                setupUI();
            }
        });
    }


    private void setupUI() {
        RootPanel rootPanel = RootPanel.get();
        rootPanel.setStyleName("bg-body");
        MenuBar   menuItems = new MenuBar();
        rootPanel.add(menuItems);

        rootPanel.add(layoutPanel);

        addMenuItems(menuItems);
        layoutPanel.add(addFormItems());
        layoutPanel.add(searchPhoneCalls());
        layoutPanel.add(showOutput());

        layoutPanel.showWidget(0);
    }

    private VerticalPanel addFormItems() {
        VerticalPanel layout_form = new VerticalPanel();

        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox customer_name = new TextBox();
        // get the entered customer name.
        customer_name.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Customer_Name = getInsertedValues(customer_name);
            }
        });
        layout_form.add(customer_name);

        layout_form.add(new Label("Enter the Caller Number in the format of XXX-XXX-XXXX: "));
        TextBox caller_number = new TextBox();
        caller_number.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Caller_Number = getInsertedValues(caller_number);
            }
        });
        layout_form.add(caller_number);

        layout_form.add(new Label("Enter the Callee Number in the format of XXX-XXX-XXXX: "));
        TextBox callee_number = new TextBox();
        callee_number.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Callee_Number = getInsertedValues(callee_number);
            }
        });
        layout_form.add(callee_number);

        layout_form.add(new Label("Enter the Start Date and Time: "));
        TextBox start_date_time = new TextBox();
        start_date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                Star_Date_Time = getInsertedValues(start_date_time);
            }
        });
        layout_form.add(start_date_time);

        layout_form.add(new Label("Enter the End Date and Time: "));
        TextBox end_Date_time = new TextBox();
        end_Date_time.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                End_Date_Time = getInsertedValues(end_Date_time);
            }
        });
        layout_form.add(end_Date_time);

        Button Submit = new Button("Submit");
        Submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Validation val = new Validation(Customer_Name,Caller_Number,Callee_Number);
                showOutputDeck();
            }
        });
        layout_form.add(Submit);

        return layout_form;
    }

    private void showOutputDeck() {
        this.layoutPanel.showWidget(2);
    }

    private String getInsertedValues(TextBox textBox) {
        try{
            return textBox.getValue();
        } catch(NumberFormatException ex){
            alerter.alert("Please pass the correct Input.");
            return null;
        }
    }

    // show the final output.

    /**
     * <code>showOutput</code> helps to show the final output
     */
    private VerticalPanel showOutput() {
        VerticalPanel layout = new VerticalPanel();
        layout.add(new Label("hi"));
        return layout;
    }

    // show the screen for searching functionality

    /**
     * <code>searchPhoneCalls</code> helps to ask the criteria for searching the phonecall from the bill.
     */
    private VerticalPanel searchPhoneCalls() {
        VerticalPanel layout_form = new VerticalPanel();
        layout_form.add(new Label("Enter the Customer Name: "));
        TextBox customer_name = new TextBox();
        layout_form.add(customer_name);

        Button Submit = new Button("Submit Customer Name");
        layout_form.add(Submit);

        layout_form.add(new Label("Enter the Start Date and Time: "));
        TextBox start_date_time = new TextBox();
        layout_form.add(start_date_time);

        layout_form.add(new Label("Enter the End Date and Time: "));
        TextBox end_Date_time = new TextBox();
        layout_form.add(end_Date_time);

        Button Submit_Search_Criteria = new Button("Submit Criteria");
        layout_form.add(Submit_Search_Criteria);

        return layout_form;
    }

    private void setUpUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                alertOnException(throwable);
            }
        });
    }

    @VisibleForTesting
    interface Alerter {
        void alert(String message);
    }

}
