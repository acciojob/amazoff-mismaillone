package com.driver;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;

    public DeliveryPartner(String id) {
        this.id = id;
        this.numberOfOrders = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
    public void incrementNumberOfOrders() {
        setNumberOfOrders(numberOfOrders + 1);
    }

    // Method to decrement the number of orders
    public void decrementNumberOfOrders() {
        setNumberOfOrders(numberOfOrders - 1);
    }

}