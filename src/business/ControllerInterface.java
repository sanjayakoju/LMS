package business;

import java.util.List;

public interface ControllerInterface {
    void login(String id, String password) throws LoginException;

    List<String> allMemberIds();

    List<String> allBookIds();

    void addMember(String id, String firstName, String lastName, String cell, String street, String city, String state, String zip) throws LibrarySystemException;

    LibraryMember searchMember(String id);

    void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) throws LibrarySystemException;

    Book getBookById(String isbn);

    void updateBook(Book book) throws LibrarySystemException;

    void checkoutBook(LibraryMember member, Book book) throws LibrarySystemException;

    List<List<String>> getMemberCheckoutEntries(String memberId) throws LibrarySystemException;

}
