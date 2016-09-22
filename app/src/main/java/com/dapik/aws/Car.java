package com.dapik.aws;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by dapik on 9/20/2016.
 */
@DynamoDBTable(tableName = "Cars")
public class Car{
    private String vin;
    private String make;
    private String status;

    @DynamoDBAttribute(attributeName = "reservationId")
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    private int reservationId;

    @DynamoDBAttribute(attributeName = "status")
    public String getStatus(){ return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @DynamoDBAttribute(attributeName = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute(attributeName = "miles")
    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    private String model;
    private String color;
    private String miles;

    @DynamoDBHashKey(attributeName = "VIN")
    public String getVin(){
        return vin;
    }

    @DynamoDBIndexRangeKey(attributeName = "make")
    public String getMake(){
        return make;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setMake(String make){
        this.make = make;
    }
}