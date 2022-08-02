package dataaccess;

import business.Book;
import business.LibraryMember;

import java.util.HashMap;

public interface DataAccess {
    HashMap<String, Book> readBooksMap();

    HashMap<String, User> readUserMap();

    HashMap<String, LibraryMember> readMemberMap();

    void saveNewMember(LibraryMember member);

    void saveMember(LibraryMember member);

    LibraryMember searchMember(String id);

    Book searchBook(String isbn);

    void saveNewBook(Book book);

    void updateBook(Book book);

    void updateMember(LibraryMember member);
}
