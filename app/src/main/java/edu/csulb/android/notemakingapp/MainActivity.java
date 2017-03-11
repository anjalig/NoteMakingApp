package edu.csulb.android.notemakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
ListView theList;
    List<String> values;
    DatabaseHelper databaseHelper;
    FloatingActionButton fb;
    int a=56;
    private ArrayAdapter slist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theList=(ListView) findViewById(R.id.list);
        fb=(FloatingActionButton) findViewById(R.id.fab);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,AddPhotoActivity.class),a);


            }
        });

        databaseHelper=new DatabaseHelper(this);

        values=databaseHelper.getAll();
        slist = new ArrayAdapter(this,android.R.layout.simple_list_item_1,values);
        theList.setAdapter(slist);
        theList.setOnItemClickListener(this);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==a){
                Log.e("tag","imagesaved");
                values=databaseHelper.getAll();
                slist.clear();
                slist.addAll(values);

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
String caption=(String)slist.getItem(position);
        String path=databaseHelper.getImage(caption);
        Intent intent=new Intent(this,ViewPhotoActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("caption",caption);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_uninstall:
                Uri packageURI = Uri.parse("package:"+"edu.csulb.android.notemakingapp");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
