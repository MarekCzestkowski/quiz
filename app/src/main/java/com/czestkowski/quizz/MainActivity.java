package com.czestkowski.quizz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.czestkowski.quizz.models.QuizModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    TextView a = (TextView)findViewById(R.id.a);
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewQuiz);
        super.onCreate(savedInstanceState);


        String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";
//        Json.getJson(url);
        try {
            URL url1 = new URL(url);
            new DownloadFilesTask().execute(url1);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
 //       return null;



    }
    private class DownloadFilesTask extends AsyncTask<URL, Integer, List<QuizModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//           dialog.show();
        }

        protected List<QuizModel> doInBackground(URL... urls) {
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;
            try {
                url = new URL("http://quiz.o2.pl/api/v1/quizzes/0/100");
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("items");

//                StringBuffer finalBufferedData = new StringBuffer();
                List<QuizModel> quizModelList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    QuizModel quizModel = new QuizModel();
                    quizModel.setTitle(finalObject.getString("title"));
                    quizModel.setId(finalObject.getInt("id"));
                    quizModelList.add(quizModel);
                }


                return quizModelList;

                //MainActivity.tekst(finalBufferedData.toString());
//                List<MovieModel> movieModelList = new ArrayList<>();

            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }
            return null;
        }


        protected void onPostExecute(final List<QuizModel> result) {
            super.onPostExecute(result);
//        dialog.dismiss();
            if (result != null) {
                QuizAdapter adapter = new QuizAdapter(getApplicationContext(), R.layout.single_row, result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        QuizModel quizmodel = result.get(position); // getting the model
                        //                   Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        //                  intent.putExtra("movieModel", new Gson().toJson(movieModel)); // converting model json into string type and sending it via intent
                        //                  startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class QuizAdapter extends ArrayAdapter {

        private List<QuizModel> quizModelList;
        private int resource;
        private LayoutInflater inflater;

        public QuizAdapter(Context context, int resource, List<QuizModel> objects) {
            super(context, resource, objects);
            quizModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.single_row, null);
            }
            TextView title;
            title = (TextView) convertView.findViewById(R.id.title);
            title.setText(quizModelList.get(position).getTitle());
            return convertView;
        }
    }
}