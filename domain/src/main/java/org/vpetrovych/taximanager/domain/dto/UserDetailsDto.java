package org.vpetrovych.taximanager.domain.dto;

import java.util.Objects;

public class UserDetailsDto {

    private final String phone;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String fatherName;
    private final String email;
    private final int age;

    public UserDetailsDto(String phone, String password, String firstName, String lastName, String fatherName, String email, int age) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserDetailsDto)) {
            return false;
        }
        UserDetailsDto o = (UserDetailsDto) obj;
        return Objects.equals(this.getPhone(), o.getPhone());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int prime = 31;

        return result * prime + Objects.hashCode(getPhone());
    }
}
