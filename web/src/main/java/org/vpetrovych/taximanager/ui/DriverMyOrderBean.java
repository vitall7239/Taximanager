package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.service.interfaces.OrderService;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("driverMyOrderBean")
@Scope(value="flow")
@FlowScoped(value="manager")
public class DriverMyOrderBean {

    private String sort;

    private List<OrderEntity> orders;

    private OrderEntity orderEntity;

    private boolean showPopup;

    private OrderCriteria criteria;

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){
        this.orderService = orderService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public DriverMyOrderBean(){
        sort = "date.desc";
        orders = new ArrayList<>();
    };

    public String infoShow(){
        return null;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderInfo) {
        this.orderEntity = orderInfo;
    }

    public boolean isShowPopup() {
        return showPopup;
    }

    public void setShowPopup(boolean showPopup) {
        this.showPopup = showPopup;
    }

    public void cancelOrder(){
        showPopup = false;
        orderEntity = null;
    }

    public void saveOrder(){
        showPopup = false;
        UserDetails userDetails = BeanUtils.getCurrentUser();
        if(userDetails != null){
            orderService.saveOrder(orderEntity, userDetails);
        }
        refresh();
    }

    public void viewOrder(Long id){
        orderEntity = orders.stream()
                .filter(el-> id.equals(el.getId()) )
                .collect(Collectors.toList())
                .get(0);
        showPopup = true;
        //refresh();
    }

    public void deleteOrder(Long id){
        orderService.delete(orders.stream()
                .filter(el-> id.equals(el.getId()) )
                .collect(Collectors.toList())
                .get(0));
        refresh();
    }

    public void init(){
        sort = "date.desc";
        OrderCriteria criteria = new OrderCriteria();
        criteria.setDriver(userService.findByPhone(BeanUtils.getCurrentUser().getUsername()));
        criteria.setOrder(" orderTime DESC ");
        orders = orderService.findOrderByCriteria(criteria);
    }

    public void refresh(){
        String orderByText = resolveSort(sort);
        OrderCriteria criteria = new OrderCriteria();
        criteria.setDriver(userService.findByPhone(BeanUtils.getCurrentUser().getUsername()));
        criteria.setOrder(orderByText);
        orders = orderService.findOrderByCriteria(criteria);
    }

    private String resolveSort(String sort){
        switch (sort){
            case "date.desc": {
                return " orderTime DESC ";
            }
            case "date.asc": {
                return " orderTime ASC ";
            }
            case "price.desc": {
                return " price DESC ";
            }
            case "price.asc": {
                return " price ASC ";
            }
            case "name.desc": {
                return " addressFrom DESC ";
            }
            case "name.asc": {
                return " addressFrom ASC ";
            }
            default: return null;
        }
    }

}
