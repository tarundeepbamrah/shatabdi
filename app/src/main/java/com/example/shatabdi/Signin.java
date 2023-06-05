package com.example.shatabdi;

import static android.Manifest.permission.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {
    AppCompatButton login;
    EditText email,pass;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference dbref;
    String name,phone,position,checkphone;
    //FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_signin);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        login= findViewById(R.id.login);
        email=findViewById(R.id.mail);
        pass=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dbref=database.getReference();

        if(!checkPer()){
            ActivityCompat.requestPermissions(Signin.this, new String[]{CAMERA, ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            String tempstring=FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String userstring=tempstring.substring(0,10);
            AlertDialog dialog;
            AlertDialog.Builder builder= new AlertDialog.Builder(Signin.this);
            View view1 = LayoutInflater.from(Signin.this).inflate(R.layout.loadingdialog,null);
            builder.setView(view1);
            dialog=builder.create();
            dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();

            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if(dataSnapshot.child("phone").getValue().toString().equals(userstring)){
                            name = dataSnapshot.child("name").getValue().toString();
                            phone = dataSnapshot.child("phone").getValue().toString();
                            position = dataSnapshot.child("position").getValue().toString();
                        }
                    }
                    if (position.equals("Salesman")){
                        dialog.dismiss();
                        Intent i= new Intent(Signin.this,SalesmanDashboard.class);
                        i.putExtra("name",name);
                        i.putExtra("phone",phone);
                        i.putExtra("position",position);
                        startActivity(i);
                    } else if (position.equals("Manager")) {
                        dialog.dismiss();
                        Intent i= new Intent(Signin.this,ManagerDashboard.class);
                        i.putExtra("name",name);
                        i.putExtra("phone",phone);
                        i.putExtra("position",position);
                        startActivity(i);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString()+"@shatabdiply.com";
                String password=pass.getText().toString();
                checkphone=email.getText().toString();
                if(email.getText().toString().equals("")){
                    email.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(Signin.this,R.drawable.required),null);
                }
                if(pass.getText().toString().equals("")){
                    pass.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(Signin.this,R.drawable.required),null);
                }
                else{
                    AlertDialog dialog;
                    AlertDialog.Builder builder= new AlertDialog.Builder(Signin.this);
                    View view1 = LayoutInflater.from(Signin.this).inflate(R.layout.loadingdialog,null);
                    builder.setView(view1);
                    dialog=builder.create();
                    dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                    dialog.setCancelable(false);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.show();
                    //dialog.getWindow().setLayout(600,400);

                    mAuth.signInWithEmailAndPassword(mail, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dbref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                                    if(dataSnapshot.child("phone").getValue().toString().equals(checkphone)){
                                                        name = dataSnapshot.child("name").getValue().toString();
                                                        phone = dataSnapshot.child("phone").getValue().toString();
                                                        position = dataSnapshot.child("position").getValue().toString();
                                                    }
                                                }
                                                if (position.equals("Salesman")){
                                                    dialog.dismiss();
                                                    Intent i= new Intent(Signin.this,SalesmanDashboard.class);
                                                    i.putExtra("name",name);
                                                    i.putExtra("phone",phone);
                                                    i.putExtra("position",position);
                                                    startActivity(i);
                                                } else if (position.equals("Manager")) {
                                                    dialog.dismiss();
                                                    Intent i= new Intent(Signin.this,ManagerDashboard.class);
                                                    i.putExtra("name",name);
                                                    i.putExtra("phone",phone);
                                                    i.putExtra("position",position);
                                                    startActivity(i);
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    } else {
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        },3000);
                                        Toast.makeText(Signin.this,task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
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
        //alert.getWindow().setLayout(600,400);
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkPer(){
        int resultCam= ActivityCompat.checkSelfPermission(this,CAMERA);
        int resultLoc= ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION);
        return resultCam==PackageManager.PERMISSION_GRANTED && resultLoc==PackageManager.PERMISSION_GRANTED;
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mFirebaseUser=mAuth.getCurrentUser();
        if(mFirebaseUser!=null){
            Toast.makeText(this, mFirebaseUser.getPhoneNumber(), Toast.LENGTH_SHORT).show();
        }else{

        }
    }
}