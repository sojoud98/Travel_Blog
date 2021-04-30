package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class SingleBlogActivity extends AppCompatActivity {
    private static final String EXTRAS_BLOG = "EXTRAS_BLOG";

    private ProgressBar progressBar;
    private TextView textTitle;
    private TextView textDate;
    private TextView textAuthor;
    private TextView textRating;
    private TextView textDescription;
    private TextView textViews;
    private RatingBar ratingBar;
    private ImageView imageAvatar;
    private ImageView imageMain, imageBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         imageMain = findViewById(R.id.mainImageView);
        imageAvatar = findViewById(R.id.vector_image);
        progressBar = findViewById(R.id.progressBar);
        textTitle = findViewById(R.id.textTitle);
        textDate = findViewById(R.id.textDate);

         textAuthor = findViewById(R.id.textAuthor);

         textRating = findViewById(R.id.textRating);

         textViews = findViewById(R.id.textViews);

         textDescription = findViewById(R.id.textDescription);

        ratingBar = findViewById(R.id.ratingBar);

        imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> finish());
        showData(getIntent().getExtras().getParcelable(EXTRAS_BLOG));

    }

    private void showData(Blog blog) {
        progressBar.setVisibility(View.GONE);

        textTitle.setText(blog.getTitle());
        textDate.setText(blog.getDate());
        textAuthor.setText(blog.getAuthor().getName());
        textRating.setText(String.valueOf(blog.getRating()));
        textViews.setText(String.format("(%d views)", blog.getViews()));
        textDescription.setText(blog.getDescription());
        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setRating(blog.getRating());
        Log.d("test", blog.getImage());
        Log.d("test", blog.getAuthor().getAvatar());
        Glide.with(this)
                .load(blog.getImageURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatarURL())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }
    public static void startBlogDetailsActivity(Activity activity, Blog blog) {
        Intent intent = new Intent(activity, SingleBlogActivity.class);
        intent.putExtra(EXTRAS_BLOG,  blog);
        activity.startActivity(intent);
    }
}