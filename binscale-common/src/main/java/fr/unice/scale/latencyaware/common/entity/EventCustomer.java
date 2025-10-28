package fr.unice.scale.latencyaware.common.entity;

public class EventCustomer {

    private int customerID;
    private String customerName;
    public EventCustomer(int ID, String name) {
        this.customerID = ID;
        this.customerName = name;
    }
    public int getID() {
        return customerID;
    }
    public String getName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                '}';
    }

}