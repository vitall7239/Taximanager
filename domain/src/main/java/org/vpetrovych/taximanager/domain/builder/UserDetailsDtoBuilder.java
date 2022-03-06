package org.vpetrovych.taximanager.domain.builder;

import org.vpetrovych.taximanager.domain.dto.UserDetailsDto;
import org.vpetrovych.taximanager.domain.entities.User;

public class UserDetailsDtoBuilder {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phone;
    private int age;

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

    public UserDetailsDtoBuilder withUser(User user) {
        email = user.getEmail();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        phone = user.getPhone();
        fatherName = user.getFatherName();
        age = user.getAge();
        return this;
    }

    public UserDetailsDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDetailsDtoBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserDetailsDtoBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDetailsDtoBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDetailsDtoBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDetailsDtoBuilder withFatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public UserDetailsDtoBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public UserDetailsDto build() {
        return new UserDetailsDto(getPhone(), getPassword(), getFirstName(), getLastName(), getFatherName(), getEmail(), getAge());
    }
}
