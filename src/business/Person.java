package business;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 3665880920647848288L;
    private String firstName;
    private String lastName;
    private String telephone;
    private Address address;

    public Person(String f, String l, String t, Address a) {
        firstName = f;
        lastName = l;
        telephone = t;
        address = a;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
