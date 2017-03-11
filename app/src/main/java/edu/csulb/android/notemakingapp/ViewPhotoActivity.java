package edu.csulb.android.notemakingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoActivity extends AppCompatActivity {
ImageView iv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        iv= (ImageView) findViewById(R.id.imageView);
        tv=(TextView) findViewById(R.id.textView);
        Intent intent=getIntent();
        if(intent!=null){
            String path=intent.getStringExtra("path");
            String caption=intent.getStringExtra("caption");
            tv.setText(caption);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            // Experiment with different sizes
            Bitmap b = BitmapFactory.decodeFile(path, options);
            iv.setImageBitmap(b);
        }
    }
}
