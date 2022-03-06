package org.vpetrovych.taximanager.ui;

import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.dao.exception.ProcessException;
import org.vpetrovych.taximanager.domain.builder.UserDetailsDtoBuilder;
import org.vpetrovych.taximanager.domain.dto.UserDetailsDto;
import org.vpetrovych.taximanager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.faces.flow.FlowScoped;

@Component("registerBean")
@Scope(value="flow")
@FlowScoped(value="login")
public class RegisterBean {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private String firstName;
    private String lastName;
    private String fatherName;
    private String phone;
    private String email;
    private String password;
    private String confirmPassword;
    private int age = 18;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String register(){

        UserDetailsDto userDetailsDto = new UserDetailsDtoBuilder()
                .withPhone(phone)
                .withPassword(password)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withFatherName(fatherName)
                .withEmail(email)
                .withAge(age)
                .build();

        try{
            userService.register(userDetailsDto);
        } catch (ProcessException e){
            BeanUtils.addMessageToFacesContext( e.getErrorObjects() );
            return "";
        }
        return "back";
    }

    public void clean(){
        firstName = null;
        lastName = null;
        fatherName = null;
        phone = null;
        email = null;
        password = null;
        confirmPassword = null;
        age = 18;
    }
}
