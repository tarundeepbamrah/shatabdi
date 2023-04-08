package com.example.shatabdi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Conversation extends AppCompatActivity {
    AppCompatButton sendreport,cancel,confirm;
    CheckBox attendence;
    Boolean checkboxvalue=false;
    int id;
    String city,area,lattitude,longitude,date,conversation;
    Date currentdate= Calendar.getInstance().getTime();
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String finaldate,locationlink;
    TextView logout,confirmconversation;
    EditText conversationsummary;
    FusedLocationProviderClient mFusedLocationClient;
    ApiInterface apiInterface;
    AlertDialog dialog2;
    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_conversation);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        attendence=findViewById(R.id.attendence);
        sendreport= findViewById(R.id.sendreport);
        conversationsummary=findViewById(R.id.conversation);
        sendreport.setEnabled(false);
        sendreport.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.disabled_button_bg));
        sendreport.setTextColor(Color.DKGRAY);

        id=getIntent().getExtras().getInt("id");
        city=getIntent().getExtras().getString("city");
        area=getIntent().getExtras().getString("area");
        finaldate=df.format(currentdate);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Conversation.this);
                builder.setCancelable(false);
                builder.setMessage("Do you want to Logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dialog1;
                        AlertDialog.Builder builder1= new AlertDialog.Builder(Conversation.this);
                        View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.loadingdialog,null);
                        builder1.setView(view1);
                        dialog1=builder1.create();
                        dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                        }
                        dialog1.setCancelable(false);
                        dialog1.getWindow().setGravity(Gravity.CENTER);
                        dialog1.show();
                        dialog1.getWindow().setLayout(600,400);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog1.dismiss();
                                Intent i= new Intent(Conversation.this,Signin.class);
                                startActivity(i);
                            }
                        },3000);
                    }
                });
                builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert=builder.create();
                alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                alert.getWindow().setLayout(600,400);
                alert.show();
            }
        });

        attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkboxvalue) {
                    checkboxvalue = true;
                    sendreport.setEnabled(true);
                    sendreport.setBackgroundDrawable(ContextCompat.getDrawable(Conversation.this,R.drawable.button_bg));
                    sendreport.setTextColor(Color.WHITE);
                }
                else {
                    checkboxvalue = false;
                    sendreport.setEnabled(false);
                    sendreport.setBackgroundDrawable(ContextCompat.getDrawable(Conversation.this,R.drawable.disabled_button_bg));
                    sendreport.setTextColor(Color.DKGRAY);
                }
            }
        });


        sendreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(attendence.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Conversation.this);
                    View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.conversationdialog, null);
                    cancel = view1.findViewById(R.id.cancel);
                    confirm = view1.findViewById(R.id.confirm);
                    confirmconversation = view1.findViewById(R.id.confirmconversation);
                    confirmconversation.setText(conversationsummary.getText().toString());
                    builder.setView(view1);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));

                    dialog.setCancelable(false);
                    dialog.show();
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            initialization();
                            conversation=conversationsummary.getText().toString();
                            AlertDialog.Builder builder2= new AlertDialog.Builder(Conversation.this);
                            View view1 = LayoutInflater.from(Conversation.this).inflate(R.layout.loadingdialog,null);
                            builder2.setView(view1);
                            dialog2=builder2.create();
                            dialog2.getWindow().getAttributes().windowAnimations=R.style.animation;
                            dialog2.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));

                            dialog2.setCancelable(false);
                            dialog2.getWindow().setGravity(Gravity.CENTER);
                            dialog2.show();
                            dialog2.getWindow().setLayout(600,400);
                            Handler handler2=new Handler();
                            handler2.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog2.dismiss();
                                }
                            },10000);


                            getresult(id,"Deepak",conversation,"1245",lattitude,longitude,locationlink,finaldate);

                            AlertDialog dialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(Conversation.this);
                            View view2 = LayoutInflater.from(Conversation.this).inflate(R.layout.successdialog, null);
                            builder.setView(view2);
                            dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                            //}
                            dialog.setCancelable(true);
                            dialog.getWindow().setGravity(Gravity.CENTER);
                            dialog.show();
                            dialog.getWindow().setLayout(600, 400);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent i = new Intent(Conversation.this, Dealers.class);
                                    i.putExtra("city",city);
                                    i.putExtra("area",area);
                                    startActivity(i);
                                }
                            }, 2000);
                        }
                    });
                }
                else {
                    Toast.makeText(Conversation.this, "Please mark the CheckBox", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {

            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            //Toast.makeText(Conversation.this, "Latitude: " + location.getLatitude() + " "+"Longitude: "+ location.getLongitude()+" ", Toast.LENGTH_SHORT).show();
                            lattitude=String.valueOf(location.getLatitude());
                            longitude=String.valueOf(location.getLongitude());
                            locationlink="https://www.google.com/maps/search/?api=1&query="+lattitude+", "+longitude;
                            //latitudeTextView.setText(location.getLatitude() + "");
                            //longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //Toast.makeText(Conversation.this, "Latitude: " + mLastLocation.getLatitude() + " "+"Longitude: "+ mLastLocation.getLongitude()+" ", Toast.LENGTH_SHORT).show();
            lattitude=String.valueOf(mLastLocation.getLatitude());
            longitude=String.valueOf(mLastLocation.getLongitude());
            locationlink="https://www.google.com/maps/search/?api=1&query="+lattitude+", "+longitude;
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Conversation.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    private void initialization() {
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);
    }

    private void getresult(int id,String salesman_name,String conversation,String photo_loc,String lattitude,String longitude,String location,String date)
    {
        apiInterface.insertConversation(id,salesman_name,conversation,photo_loc,lattitude,longitude,location,date).enqueue(new Callback<InsertResponse>() {
            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                try{
                    if(response!=null){

                        if(response.body().getStatus().equals("1")){
                            dialog2.dismiss();
                        }
                        else{
                            Toast.makeText(Conversation.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent(Conversation.this,Dealers.class);
        i.putExtra("city",city);
        i.putExtra("area",area);
        startActivity(i);
    }
}