package com.czestkowski.quizz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
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

import com.czestkowski.quizz.models.QuizDetailModel;
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
    String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewQuiz);
        super.onCreate(savedInstanceState);


//        Json.getJson(url);
        //            URL url1 = new URL(url);
        new DownloadFilesTask().execute(url);
        //       return null;


    }

    public class DownloadFilesTask extends AsyncTask<String, Integer, List<QuizModel>> {

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////           dialog.show();
//        }

        protected List<QuizModel> doInBackground(String... parameters) {
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;
            try {
                url = new URL(parameters[0]);
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                if (parameters[0].equals("http://quiz.o2.pl/api/v1/quizzes/0/100")) {
                    //if entering for the first time

                    JSONArray parentArray = parentObject.getJSONArray("items");

//                StringBuffer finalBufferedData = new StringBuffer();
                    List<QuizModel> quizModelList = new ArrayList<>();
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        QuizModel quizModel = new QuizModel();
                        quizModel.setTitle(finalObject.getString("title"));
                        quizModel.setId(finalObject.getString("id"));
                        quizModel.setPhoto(finalObject.getJSONObject("mainPhoto").getString("url"));
                        quizModelList.add(quizModel);
                    }


                    return quizModelList;
                } else {
                    //entering with the link containg id
                    JSONArray parentArray = parentObject.getJSONArray("questions");

                    List<QuizDetailModel> quizDetailModelList = new ArrayList<>();
                    List<String> textList = new ArrayList<>();
                    List<String> answersForOneQ = new ArrayList<>();
                    List<List<String>> answerList = new ArrayList<>();
                    String x;
                    QuizDetailModel quizDetailModel = new QuizDetailModel();

                    for (int j = 0; j < parentArray.length(); j++) {
                        JSONObject finalObject = parentArray.getJSONObject(j);
                        x = finalObject.getString("text");
                        textList.add(x);
                        for (int k = 0; k < finalObject.getJSONArray("answers").length(); k++) {
                            x= finalObject.getJSONArray("answers").getJSONObject(k).getString("text");
                            answersForOneQ.add(x);
                        }
                        answerList.add(answersForOneQ);

//                        List<QuizDetailModel.>
//                        quizDetailModelList.add(quizDetailModel);
                    }

                    quizDetailModel.setAnswerList(answerList);
                    quizDetailModel.setTextList(textList);

                    Intent it = new Intent(MainActivity.this, QuizActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("value", quizDetailModel);
                    it.putExtras(bundle);
                    startActivity(it);
                }


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
//                        quizmodel.getId();
                        // Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), QuizActivity.class);
                        String x = quizmodel.getId();
//                        intent.putExtra("ID", x); // converting ID into string type and sending it via intent
//                        startActivity(intent);

                        String link = "http://quiz.o2.pl/api/v1/quiz/" + x + "/0";
                        new DownloadFilesTask().execute(link);
//                        doInBackground(x);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "server not responding, please check connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    Intent intent_name = new Intent();
//    intent_name.setClass(getApplicationContext(),DestinationClassName.class);
//    startActivity(intent_name);

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
//            ImageView imageView;
            title = (TextView) convertView.findViewById(R.id.title);
//            imageView= (ImageView) convertView.findViewById(R.id.imageView);
            title.setText(quizModelList.get(position).getTitle());
            return convertView;
        }
    }

}