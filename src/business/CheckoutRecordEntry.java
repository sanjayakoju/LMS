package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

final public class CheckoutRecordEntry implements Serializable {

    private static final long serialVersionUID = -2226197306790745013L;
    private final LocalDate dueDate;
    private final LocalDate checkoutDate;
    private final BookCopy bookCopy;

    public CheckoutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy) {
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "Check Out Date: " + checkoutDate.format(formatter) + "\n Due Date: " + dueDate.format(formatter);
    }

}
