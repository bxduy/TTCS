package com.appbishoy.packyourbag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    ImageView imgYoutube,imgInstagram,imgTwitter;
    TextView tvEmail,tvLinkedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About us");

        imgYoutube = findViewById(R.id.img_youtube);
        imgInstagram = findViewById(R.id.img_instagram);
        imgTwitter = findViewById(R.id.img_twitter);
        tvEmail = findViewById(R.id.tv_email);
        tvLinkedIn = findViewById(R.id.tv_linkedin_link);


        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUri("https://www.youtube.com/channel/UCtFaJlm-G0JvtVeoA1xMg8A");
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUri("https://www.youtube.com/channel/UCtFaJlm-G0JvtVeoA1xMg8A");
            }
        });
        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUri("https://www.youtube.com/channel/UCtFaJlm-G0JvtVeoA1xMg8A");
            }
        });

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"beshoypauls810@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT,"From MyBag App");
                    startActivity(intent);
                }catch (ActivityNotFoundException ex){
                    System.out.println(ex);
                }
            }
        });

        tvLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUri("https://www.linkedin.com/feed/");
            }
        });
    }
    private void navigateToUri(String uri){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}