package com.example.skyscraper.zz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    String mData[] = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String s = getJSONFile();
        listView = (ListView) findViewById(R.id.myList);

        try{
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray jsonArray1 = jsonObject1.getJSONArray("colors");

            int colorsLength = jsonArray1.length();
            mData = new String[colorsLength];
            for(int i=0;i<colorsLength;i++)
            {
                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                String col = jsonObject2.getString("color");
                Log.i("Val ", " : " + col);
                mData[i] = col;
            }

        }catch (JSONException e){
            e.printStackTrace();

            Log.i("MyError" ,": some error" );
            Toast.makeText(getApplicationContext(),"Json error",Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,mData);

        if(listView != null)
        {
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"This is "+ mData[position],Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Just a simple helper method to read the file,
    //no need to read this or understand it for this exercise
    public String getJSONFile(){
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.colors);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
