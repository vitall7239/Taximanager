package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.criteria.OrderCriteria;
import org.vpetrovych.taximanager.domain.entities.OrderEntity;
import org.vpetrovych.taximanager.domain.entities.OrderStatus;
import org.vpetrovych.taximanager.service.interfaces.OrderService;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("driverOrderBean")
@Scope(value="flow")
@FlowScoped(value="manager")
public class DriverOrderBean {
    private String sort;

    private List<OrderEntity> orders;

    private OrderEntity orderEntity;

    private boolean showPopup;

    private boolean showDoPopup;

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

    public DriverOrderBean(){
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

    public boolean isShowDoPopup() {
        return showDoPopup;
    }

    public void setShowDoPopup(boolean showDoPopup) {
        this.showDoPopup = showDoPopup;
    }

    public void cancelOrder(){
        showPopup = false;
        showDoPopup = false;
        orderEntity = null;
    }

    public void acceptOrder(){
        showPopup = false;
        orderEntity.setStatus(OrderStatus.ACCEPTED.toString());
        orderService.setDriver(orderEntity, BeanUtils.getCurrentUser());
        showDoPopup = true;
        refresh();
    }

    public void doneOrder(){
        showDoPopup = false;
        orderEntity.setStatus(OrderStatus.DONE.toString());
        orderService.update(orderEntity);
        refresh();
    }

    public void returnOrder(){
        showDoPopup = false;
        orderEntity.setStatus(OrderStatus.ACTIVE.toString());
        orderEntity.setDriver(null);
        orderService.update(orderEntity);
        refresh();
    }

    public void doCancelOrder(){
        showDoPopup = false;
        orderEntity.setStatus(OrderStatus.CANCELED.toString());
        orderService.update(orderEntity);
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
        criteria = new OrderCriteria();
        criteria.setStatuses(Collections.singletonList(OrderStatus.ACTIVE.name()));
        criteria.setOrder(" orderTime DESC ");
        orders = orderService.findOrderByCriteria(criteria);
    }

    public void refresh(){
        String orderByText = resolveSort(sort);
        OrderCriteria criteria = new OrderCriteria();
        criteria.setStatuses(Collections.singletonList(OrderStatus.ACTIVE.name()));
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
