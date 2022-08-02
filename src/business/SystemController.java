package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
    public static Auth currentAuth = null;

    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();

    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public void addMember(String id, String firstName, String lastName, String cell, String street, String city, String state, String zip) throws LibrarySystemException {
        if (id.length() == 0 || firstName.length() == 0 || lastName.length() == 0
                || cell.length() == 0 || street.length() == 0 || city.length() == 0
                || state.length() == 0 || zip.length() == 0) {
            throw new LibrarySystemException("All fields must be non-empty");
        }
        if (searchMember(id) != null) {
            throw new LibrarySystemException("Library Member with ID " + id + " already exists");
        }
        Address address = new Address(street, city, state, zip);
        DataAccess da = new DataAccessFacade();
        LibraryMember member = new LibraryMember(id, firstName, lastName, cell, address);
        da.saveMember(member);
    }

    @Override
    public LibraryMember searchMember(String id) {
        DataAccess da = new DataAccessFacade();
        return da.searchMember(id);
    }

    @Override
    public void addBook(String isbn, String title, int maxCheckoutLength,
                        List<Author> authors) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        Book storedBook = da.searchBook(isbn);
        if (storedBook != null) {
            throw new LibrarySystemException("Book with ISBN " + isbn + " already exists");
        }
        Book book = new Book(isbn, title, maxCheckoutLength, authors);
        da.saveNewBook(book);
    }

    @Override
    public Book getBookById(String isbn) {
        DataAccess da = new DataAccessFacade();
        return da.searchBook(isbn);
    }

    @Override
    public void updateBook(Book book) throws LibrarySystemException {
        DataAccess da = new DataAccessFacade();
        if (book == null) {
            throw new LibrarySystemException("Book does not exits!");
        }
        da.updateBook(book);
    }

    @Override
    public void checkoutBook(LibraryMember member, Book book) throws LibrarySystemException {
        DataAccess dataAccess = new DataAccessFacade();
        List<List<String>> records = getMemberCheckoutEntries(member.getMemberId());

        if (!book.isAvailable()) {
            throw new LibrarySystemException("There are no available copies for book with ID '" + book.getIsbn() + "' to checkout.");
        }

        for (List<String> record : records) {
            if (member.getMemberId().equals(record.get(0)) && book.getIsbn().equals(record.get(2)))
                throw new LibrarySystemException("Book ISBN " + record.get(2) + " has been carried with the Member ID " + record.get(0) + " before!");
        }
        CheckoutRecordEntry checkoutRecordEntry = new CheckoutRecordEntry(
                LocalDate.now(), LocalDate.now().plusDays(book.getMaxCheckoutLength()),
                book.getNextAvailableCopy()
        );
        member.getCheckoutRecord().addCheckOutEntry(checkoutRecordEntry);
        dataAccess.updateMember(member);
        updateBook(book);
    }

    @Override
    public List<List<String>> getMemberCheckoutEntries(String memberId) throws LibrarySystemException {
        LibraryMember member = searchMember(memberId);
        if (member == null) {
            throw new LibrarySystemException("Member with with id '" + memberId + "' does not exist");
        }
        List<CheckoutRecordEntry> checkoutBooks = member.getCheckoutRecord().getCheckoutRecordEntries();
        List<List<String>> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (CheckoutRecordEntry entry : checkoutBooks) {
            Book book = entry.getBookCopy().getBook();
            List<String> record = new ArrayList<>();
            record.add(memberId);
            record.add(member.getFirstName() + " " + member.getLastName());
            record.add(book.getIsbn());
            record.add(book.getTitle());
            record.add(Integer.toString(entry.getBookCopy().getCopyNum()));
            record.add(entry.getCheckoutDate().format(formatter));
            record.add(entry.getDueDate().format(formatter));
            records.add(record);
        }
        return records;
    }


}
