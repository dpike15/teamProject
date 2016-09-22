package com.dapik.aws;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by dapik on 9/21/2016.
 */
@DynamoDBTable(tableName = "members")
public class Member {

    @DynamoDBHashKey(attributeName = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "first_name")
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @DynamoDBAttribute(attributeName = "last_name")
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @DynamoDBAttribute(attributeName = "reservationVin")
    public String getReservationVin() {
        return reservationVin;
    }

    public void setReservationVin(String reservationVin) {
        this.reservationVin = reservationVin;
    }

    private int id;
    private String first_name;
    private String last_name;
    private String  reservationVin;



}
