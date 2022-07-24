package com.manish.wallsapp;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    Context context;
     List<Model> postList;






String flagColor = "white";
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;

    public PostAdapter(Context context, List<Model> postList) {
        this.context = context;
        this.postList = postList;
     sharedPreferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.post_layout, parent,false);

        return  new PostHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Model model = postList.get(position);
        holder.setImageview(model.getImageUrl());
       holder.setTextview(model.getLikes());
       holder.setTextview2(model.getViews());
       holder.setTextview3(model.getDownloads());
       Paper.init(context);


        String first10DigitsOfUrl = (model.getImageUrl()).replace("/","").trim();


//       if(sharedPreferences.getString(model.getImageUrl(),"").equals(model.getDownloads()+""))
//       {
//
//           holder.textview.setBackgroundResource(R.color.red);
//
//       } else {
//
//           holder.textview.setBackgroundResource(R.color.white);
//
//
//       }
        if(Paper.book().contains(first10DigitsOfUrl)){


            holder.textview.setBackgroundResource(R.color.red);

        } else{

            holder.textview.setBackgroundResource(R.color.white);

        }









       holder.textview.setOnClickListener(new View.OnClickListener() {

           int mLikes = model.getLikes();
           @Override
           public void onClick(View v) {

               if(flagColor.equals("white")){
                   holder.textview.setBackgroundResource(R.color.red);
                   flagColor = "red";
                   mLikes++;
                  holder.textview.setText("Likes"+ "\n"+mLikes);

//                   editor.putString(model.getImageUrl(),model.getDownloads()+"");
//                   editor.apply();

                   Paper.book().write(first10DigitsOfUrl+"",model.getDownloads()+"");



               } else{

                  holder.textview.setBackgroundResource(R.color.white);
                   flagColor = "white";
                   mLikes--;
                   holder.textview.setText("Likes"+ "\n"+mLikes);
                   //sharedPreferences.edit().remove(model.getImageUrl()).apply();
                  Paper.book().delete(first10DigitsOfUrl);



               }



           }
       });

       holder. constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity2.class);

                intent.putExtra("likes",model.getLikes());
                intent.putExtra("views",model.getViews());
                intent.putExtra("downloads",model.getDownloads());
                intent.putExtra("imageUrl",model.getImageUrl());
                context.startActivity(intent);
            }
        });
       holder.btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dexter.withContext(context)
                       .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                       .withListener(new PermissionListener() {
                           @Override
                           public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                               DownloadManager downloadManager =
                                       (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                               Uri uri = Uri.parse(model.getImageUrl());
                               DownloadManager.Request request = new DownloadManager.Request(uri);
                               request.setNotificationVisibility
                                       (DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                               downloadManager.enqueue(request);
                               Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                           }

                           @Override
                           public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                             permissionToken.continuePermissionRequest();
                           }
                       })
                       .check();



           }

       });





    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView textview, textview2, textview3;
        ConstraintLayout constraint;
        View view;
        Button btn;



        public PostHolder(@NonNull View itemView) {

            super(itemView);
            view = itemView;


            textview = view.findViewById(R.id.textView);
            constraint = itemView.findViewById(R.id.constraint);
            btn = itemView.findViewById(R.id.btn);

            SharedPreferences preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
            String likecount = preferences.getString("likes","");
            textview.setText(likecount);


        }
        public void setImageview(String url){
            imageview = view.findViewById(R.id.imageview);
            Glide.with(context).load(url).into(imageview);


        }
        public void setTextview(int likes){
            textview = view.findViewById(R.id.textView);
            textview.setText("Likes"+ "\n"+likes);

//            textview.setOnClickListener(new View.OnClickListener() {
//
//                int mLikes = likes;
//                @Override
//                public void onClick(View v) {
//
//                    if(flagColor.equals("white")){
//                        textview.setBackgroundResource(R.color.red);
//                        flagColor = "red";
//                        mLikes++;
//                        textview.setText("Likes"+ "\n"+mLikes);
//
//
//                    } else{
//                        textview.setBackgroundResource(R.color.white);
//                        flagColor = "white";
//                        mLikes--;
//                        textview.setText("Likes"+ "\n"+mLikes);
//
//
//                    }
//
//
//
//
//                }
//
//
//            });


        }
        public void setTextview2(int views){
            textview2 = view.findViewById(R.id.textView2);
            textview2.setText("Views"+"\n"+views);
        }
        public void setTextview3(int downloads){
            textview3 = view.findViewById(R.id.textView3);
            textview3.setText("Downloads"+"\n "+downloads);
        }



    }
}
