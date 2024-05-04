package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, partner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
                // Add order to the given partner's order list
                if (!partnerToOrderMap.containsKey(partnerId)) {
                    partnerToOrderMap.put(partnerId, new HashSet<>());
                }
                partnerToOrderMap.get(partnerId).add(orderId);

                // Increase order count of partner
                DeliveryPartner partner = partnerMap.get(partnerId);
                partner.incrementNumberOfOrders();

                // Assign partner to this order
                orderToPartnerMap.put(orderId, partnerId);

        }
    }

    public Order findOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        HashSet<String> orders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        return orders.size();

    }

    public List<String> findOrdersByPartnerId(String partnerId){
        HashSet<String> orders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        return new ArrayList<>(orders);

    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());


    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if (partnerMap.containsKey(partnerId)) {
            // Get the set of orders assigned to this partner
            HashSet<String> assignedOrders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());

            // Remove the partner from the partner map
            partnerMap.remove(partnerId);

            // Iterate over the assigned orders and remove the partner assignment
            for (String orderId : assignedOrders) {
                orderToPartnerMap.remove(orderId);
            }

            // Move all assigned orders to unassigned orders
            partnerToOrderMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId) {
        // your code here
        // delete order by ID
        if (orderMap.containsKey(orderId)) {
            // Get the partner ID assigned to this order
            String partnerId = orderToPartnerMap.get(orderId);

            // Remove the order from the partner's order list
            if (partnerId != null && partnerToOrderMap.containsKey(partnerId)) {
                partnerToOrderMap.get(partnerId).remove(orderId);
            }

            // Remove the order from the order map
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int unassignedCount = 0;
        for (Order order : orderMap.values()) {
            if (!orderToPartnerMap.containsKey(order.getId())) {
                unassignedCount++;
            }
        }
        return unassignedCount;

    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int count = 0;
        for (String orderId : partnerToOrderMap.getOrDefault(partnerId, new HashSet<>())) {
            Order order = orderMap.get(orderId);
            if (order != null && order.getDeliveryTime() > 0) {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        String lastDeliveryTime = "00:00";
        for (String orderId : partnerToOrderMap.getOrDefault(partnerId, new HashSet<>())) {
            Order order = orderMap.get(orderId);
            if (order != null && order.getDeliveryTime() > 0) {
                lastDeliveryTime = String.valueOf(order.getDeliveryTime());
            }
        }
        return lastDeliveryTime;
    }
}