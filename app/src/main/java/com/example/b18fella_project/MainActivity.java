package com.example.b18fella_project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listData;
    private ArrayList<gamingGear> mountainArrayList = new ArrayList<>();
    private ArrayAdapter<gamingGear> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<gamingGear>(this, R.layout.list_item_textview, R.id.list_item_textview);

        new FetchData().execute();


        ListView my_listview=(ListView) findViewById(R.id.my_list_view);

        my_listview.setAdapter(adapter);

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String temp = adapter.getItem(i).info();
                Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String forecastJsonStr = null;

            Log.d("error fel här", "Fel här");

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/b18fella/lectures_duggor/programmering_av_mobila_applikationer/prejectjsondata.php");


                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            Log.d("laakso", o);
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.

            try {
                JSONArray jarray = new JSONArray(o);
                for (int i = 0; i < jarray.length(); i++){
                    Log.d("laakso", "element 1: " + jarray.get(i).toString());
                    JSONObject jArray1 = jarray.getJSONObject(i);

                    Log.d("laakso", jArray1.getString("name"));
                    Log.d("laakso", jArray1.getString("company"));
                    Log.d("laakso", "" + jArray1.getString("cost"));
                    Log.d("laakso", jArray1.getString("location"));
                    Log.d("laakso", jArray1.getString("category"));


                    gamingGear gearValues = new gamingGear(jArray1.getString("name"), jArray1.getString("company"), jArray1.getInt("cost"), jArray1.getString("location"), jArray1.getString("category"));
                    adapter.add(gearValues);
                }
            } catch (JSONException e) {
                Log.e("laakso","E:"+e.getMessage());
            }
        }
    }
}
