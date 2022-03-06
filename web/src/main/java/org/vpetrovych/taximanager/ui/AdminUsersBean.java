package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.criteria.UserCriteria;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;
import java.util.List;

@Component("adminUsersBean")
@Scope(value="flow")
@FlowScoped(value="admin")
public class AdminUsersBean {

    private String searchText;

    private String searchSelect;

    private String sort;

    private String role;

    private List<User> users;

    private UserCriteria userCriteria;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchSelect() {
        return searchSelect;
    }

    public void setSearchSelect(String searchSelect) {
        this.searchSelect = searchSelect;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void init(){
        searchText = "";
        searchSelect = "firstName";
        sort = "name.asc";
        role = "all";
        userCriteria = criteriaBuild();
        users = userService.findAll(userCriteria);
    }

    public void refresh(){
        userCriteria = criteriaBuild();
        users = userService.findAll(userCriteria);
    }

    public void search(){
        userCriteria = criteriaBuild();
        users = userService.findAll(userCriteria);
    }

    private UserCriteria criteriaBuild() {
        UserCriteria criteria = new UserCriteria();
        if (!searchText.equals("")){
            switch (searchSelect) {
                case "firstName": {
                    criteria.setFirstName(searchText);
                    break;
                }
                case "lastName": {
                    criteria.setLastName(searchText);
                    break;
                }
                case "fatherName": {
                    criteria.setFatherName(searchText);
                    break;
                }
                case "age": {
                    criteria.setAge(Integer.valueOf(searchText));
                    break;
                }
                case "email": {
                    criteria.setEmail(searchText);
                    break;
                }
                case "phone": {
                    criteria.setPhone(searchText);
                    break;
                }
                case "mark": {
                    criteria.setMark(searchText);
                    break;
                }
                case "model": {
                    criteria.setModel(searchText);
                    break;
                }
                case "year": {
                    criteria.setYear(Integer.valueOf(searchText));
                    break;
                }
                case "color": {
                    criteria.setColor(searchText);
                    break;
                }
                case "number": {
                    criteria.setNumber(searchText);
                    break;
                }
                case "driverNumber": {
                    criteria.setDriverNumber(searchText);
                    break;
                }
            }
        }
        switch(role){
            case "all" : { criteria.setRole(null); break; }
            case "DRIVER" : { criteria.setRole("DRIVER"); break; }
            case "MANAGER" : { criteria.setRole("MANAGER"); break; }
            case "ADMIN" : { criteria.setRole("ADMIN"); break; }
        }
        criteria.setSort(resolveSort(sort));
        return criteria;
    }

    private String resolveSort(String sort){
        switch (sort){
            case "name.desc": {
                return " lastName DESC ";
            }
            case "name.asc": {
                return " lastName ASC ";
            }
            default: return null;
        }
    }
}
