package com.example.shatabdi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoViewWithSalesman extends AppCompatActivity {
    ImageView photo;
    StorageReference mStorageRef;
    ApiInterface apiInterface;
    AlertDialog dialog1;
    String dealer,date,conversation,sdate,tdate,sname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_photo_view);
        photo = findViewById(R.id.photo);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        conversation=getIntent().getExtras().getString("conversation");
        dealer=getIntent().getExtras().getString("dealer");
        sname=getIntent().getExtras().getString("sname");
        date=getIntent().getExtras().getString("date");
        sdate=getIntent().getExtras().getString("sdate");
        tdate=getIntent().getExtras().getString("tdate");
        Retrofit retrofit= ApiClient.getclient();
        apiInterface =retrofit.create(ApiInterface.class);


        AlertDialog.Builder builder1= new AlertDialog.Builder(PhotoViewWithSalesman.this);
        View view1 = LayoutInflater.from(PhotoViewWithSalesman.this).inflate(R.layout.loadingdialog,null);
        builder1.setView(view1);
        dialog1=builder1.create();
        dialog1.getWindow().getAttributes().windowAnimations=R.style.animation;
        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogbackground));
        dialog1.setCancelable(false);
        dialog1.getWindow().setGravity(Gravity.CENTER);
        dialog1.show();
        //dialog1.getWindow().setLayout(600,400);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog1.dismiss();
            }
        },3000);

        getresult(sname,date,conversation);

    }
    private void getresult(String sname,String date,String conversation) {
        apiInterface.readSalesmanPhotoLoc(sname,date,conversation).enqueue(new Callback<GetPhotoResponse>() {
            @Override
            public void onResponse(Call<GetPhotoResponse> call, Response<GetPhotoResponse> response) {
                try{
                    if(response!=null){
                        if(response.body().getStatus().equals("1")){
                            String loc= response.body().getData().get(0).getPhoto_loc();
                            mStorageRef= FirebaseStorage.getInstance().getReference("images/"+loc);
                            try {
                                File localfile= File.createTempFile("tempfile",".jpg");
                                mStorageRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                        if(dialog1.isShowing()){
                                            dialog1.dismiss();
                                        }
                                        Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                        photo.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PhotoViewWithSalesman.this, "Failed to get Photo", Toast.LENGTH_SHORT).show();
                                        dialog1.dismiss();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        else{
                            Toast.makeText(PhotoViewWithSalesman.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog1.dismiss();
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Exception",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetPhotoResponse> call, Throwable t) {
                Log.e("Failure",t.getLocalizedMessage());

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(PhotoViewWithSalesman.this, ConversationWithSalesman.class);
        i.putExtra("sname",sname);
        i.putExtra("date",date);
        i.putExtra("sdate",sdate);
        i.putExtra("tdate",tdate);
        i.putExtra("dealer",dealer);
        startActivity(i);
    }
}