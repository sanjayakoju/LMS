package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
    private static final long serialVersionUID = -2222297306790745013L;
    private final List<CheckoutRecordEntry> checkoutRecordEntries = new ArrayList<>();

    CheckoutRecord() {
    }

    public void addCheckOutEntry(CheckoutRecordEntry checkoutRecordEntry) {
        checkoutRecordEntries.add(checkoutRecordEntry);
    }

    public List<CheckoutRecordEntry> getCheckoutRecordEntries() {
        return checkoutRecordEntries;
    }
}
