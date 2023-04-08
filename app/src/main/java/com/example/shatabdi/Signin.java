package com.example.shatabdi;

import static android.Manifest.permission.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {
    AppCompatButton login;
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        }
        login= findViewById(R.id.login);
        email=findViewById(R.id.mail);
        pass=findViewById(R.id.password);


        if(!checkPer()){
            ActivityCompat.requestPermissions(Signin.this, new String[]{CAMERA, ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            //login.setEnabled(false);
            //login.setBackgroundDrawable(ContextCompat.getDrawable(Signin.this,R.drawable.disabled_button_bg));
            //login.setTextColor(Color.DKGRAY);

        }
        /*else {
            login.setEnabled(true);
            login.setBackgroundDrawable(ContextCompat.getDrawable(Signin.this,R.drawable.button_bg));
            login.setTextColor(Color.WHITE);
        }*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString();
                String password=pass.getText().toString();
                /*
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Signin.this, "Logged in", Toast.LENGTH_SHORT).show();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Signin.this,task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        */
                AlertDialog dialog;
                AlertDialog.Builder builder= new AlertDialog.Builder(Signin.this);
                View view1 = LayoutInflater.from(Signin.this).inflate(R.layout.loadingdialog,null);
                builder.setView(view1);
                dialog=builder.create();
                dialog.getWindow().getAttributes().windowAnimations=R.style.animation;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
                }
                dialog.setCancelable(false);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                dialog.getWindow().setLayout(600,400);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Intent i= new Intent(Signin.this,SalesmanDashboard.class);
                        startActivity(i);
                    }
                },3000);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        }
        alert.getWindow().setLayout(600,400);
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();
                //login.setEnabled(true);
                //login.setBackgroundDrawable(ContextCompat.getDrawable(Signin.this,R.drawable.button_bg));
                //login.setTextColor(Color.WHITE);

            }
            else{
                Toast.makeText(this, "Permissions Required", Toast.LENGTH_SHORT).show();
                //login.setEnabled(false);
                //login.setBackgroundDrawable(ContextCompat.getDrawable(Signin.this,R.drawable.disabled_button_bg));
                //login.setTextColor(Color.DKGRAY);
            }
        }
    }

    public boolean checkPer(){
        int resultCam= ActivityCompat.checkSelfPermission(this,CAMERA);
        int resultLoc= ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION);
        return resultCam==PackageManager.PERMISSION_GRANTED && resultLoc==PackageManager.PERMISSION_GRANTED;
    }
}