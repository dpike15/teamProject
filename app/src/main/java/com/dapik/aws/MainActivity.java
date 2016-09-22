package com.dapik.aws;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

public class MainActivity extends AppCompatActivity{

    private Car car;
    private Member member;
    private DynamoDBMapper mapper;
    private DynamoDBMapper mapperMembers;
    private PaginatedScanList<Car> result;
    private String vin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the Amazon Cognito credentials provider for members Table
        CognitoCachingCredentialsProvider credentialsProviderMembers = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:d203cc02-4b6f-475d-84b1-93687e673058", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClientMembers = new AmazonDynamoDBClient(credentialsProviderMembers);
        mapperMembers = new DynamoDBMapper(ddbClientMembers);


        // Initialize the Amazon Cognito credentials provider for Cars table
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:d471f9f6-bda2-4a1f-85c5-4cb99127c6d1", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        //DB client and JSON mapper-Cars Table
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(ddbClient);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, vin , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button checkIn = (Button) findViewById(R.id.checkIn);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        EditText editText = (EditText) findViewById(R.id.editText);
                        String input = editText.getText().toString();
                        //Loading in matching record
                        car = mapper.load(Car.class,input);
                        member = mapperMembers.load(Member.class,car.getReservationId());

                        if(car == null || member == null){
                            vin="No records found!";
                        }else{
                            if(car.getStatus().equals("true")){
                                vin = car.getVin() + " " + car.getMake() + " " +
                                        car.getModel() + " is currently already checked out!";

                            }else if(!car.getStatus().equals("true") &&
                                    car.getVin().equals(member.getReservationVin())){

                                vin = member.getFirst_name() + " " + member.getLast_name() +
                                        " checked in successfully with an " + car.getVin() + " " +
                                        car.getMake() + " " +
                                        car.getModel();
                                car.setStatus("true");
                                mapper.save(car);
                            }else{

                            }
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();


                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(vin);
            }
        });

        //Checking Out process -take input from editText box
        Button checkOut = (Button) findViewById(R.id.checkOut);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable(){
                    @Override
                    public void run(){
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String input = editText.getText().toString();
                        car = mapper.load(Car.class,input);
                        if(car.getStatus().equals("false")){
                            vin = car.getVin() + " " + car.getMake() + " " +
                                    car.getModel() + " is already checked out!";
                        }else{
                            car.setStatus("false");
                            mapper.save(car);
                            vin = car.getVin() + " " + car.getMake() + " " +
                                    car.getModel() + " checked out successfully!";
                        }
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();


                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(vin);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
