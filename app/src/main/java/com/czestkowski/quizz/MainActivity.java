package com.czestkowski.quizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
//    int score;
    private ListView listView;
    String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewQuiz);
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("czestkowski.com.Quizz", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        score = sharedPreferences.getInt("BestScore", 0);
        new DownloadFilesTask().execute(url);
    }

    public class DownloadFilesTask extends AsyncTask<String, Integer, List<QuizModel>> {

        protected List<QuizModel> doInBackground(String... parameters) {
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;
            try {
                url = new URL(parameters[0]);
                is = url.openStream();
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
                    List<QuizModel> quizModelList = new ArrayList<>();

                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        QuizModel quizModel = new QuizModel();
                        quizModel.setTitle(finalObject.getString("title"));
                        quizModel.setId(finalObject.getString("id"));
                        quizModel.setPhoto(finalObject.getJSONObject("mainPhoto").getString("url"));
                        quizModel.setScore(sharedPreferences.getInt(finalObject.getString("id"), 0));
                        quizModelList.add(quizModel);
                    }


                    return quizModelList;
                } else {
                    //entering with the link containg id
                    JSONArray parentArray = parentObject.getJSONArray("questions");

                    List<String> textList = new ArrayList<>();
                    List<String> answersForOneQ;
                    List<String> abcd;
                    List<List<String>> answerList = new ArrayList<>();
                    List<List<String>> abcdList = new ArrayList<>();
                    List<Integer> correctAnswerList = new ArrayList<>();
                    String x;
                    String y;
                    List<String> flagTitle = new ArrayList<>();
                    List<String> flagContent = new ArrayList<>();
                    int correctAnswerNumber;
                    QuizDetailModel quizDetailModel = new QuizDetailModel();
                    if (parentObject.has("flagResults")) {
                        for (int n = 0; n < parentObject.getJSONArray("flagResults").length(); n++) {
                            JSONObject finalObject2 = parentObject.getJSONArray("flagResults").getJSONObject(n);
                            x = finalObject2.getString("content"); //getting the flag results
                            flagContent.add(x);
                            y = finalObject2.getString("title");
                            flagTitle.add(y);
                        }
                    }


                    for (int j = 0; j < parentArray.length(); j++) {
                        JSONObject finalObject = parentArray.getJSONObject(j);
                        x = finalObject.getString("text");
                        textList.add(x);
                        int l = 0;
                        answersForOneQ = new ArrayList<>();
                        abcd = new ArrayList<>();

                        for (int k = 0; k < finalObject.getJSONArray("answers").length(); k++) {
                            x = finalObject.getJSONArray("answers").getJSONObject(k).getString("text");
                            if (finalObject.getJSONArray("answers").getJSONObject(k).has("isCorrect")) {
                                correctAnswerNumber = l;
                                correctAnswerList.add(correctAnswerNumber);
                            } else {
                                l++;
                            }
                            if (finalObject.getJSONArray("answers").getJSONObject(k).has("flag_answer")) {

                                if (finalObject.getJSONArray("answers").getJSONObject(k).getJSONObject("flag_answer").has("A")) {
                                    abcd.add("A");
                                } else if (finalObject.getJSONArray("answers").getJSONObject(k).getJSONObject("flag_answer").has("B")) {
                                    abcd.add("B");
                                } else if (finalObject.getJSONArray("answers").getJSONObject(k).getJSONObject("flag_answer").has("C")) {
                                    abcd.add("C");
                                } else if (finalObject.getJSONArray("answers").getJSONObject(k).getJSONObject("flag_answer").has("D")) {
                                    abcd.add("D");
                                }
                            }
                            answersForOneQ.add(x);
                        }
                        abcdList.add(abcd);
                        answerList.add(answersForOneQ);
                    }

                    quizDetailModel.setListNumberOfCorrectAnswer(correctAnswerList);
                    quizDetailModel.setAnswerList(answerList);
                    quizDetailModel.setTextList(textList);
                    quizDetailModel.setUrlToPhoto(parentObject.getJSONObject("mainPhoto").getString("url"));
                    quizDetailModel.setAbcdList(abcdList);
                    quizDetailModel.setFlagContent(flagContent);
                    quizDetailModel.setFlagTitle(flagTitle);

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
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), QuizActivity.class);
                        String x = quizmodel.getId();

                        String link = "http://quiz.o2.pl/api/v1/quiz/" + x + "/0";
                        new DownloadFilesTask().execute(link);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "server not responding, please check the connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class QuizAdapter extends ArrayAdapter {

        private List<QuizModel> quizModelList;
        private LayoutInflater inflater;

        public QuizAdapter(Context context, int resource, List<QuizModel> objects) {
            super(context, resource, objects);
            quizModelList = objects;
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.single_row, null);
            }
            TextView title;
            TextView textViewBestScore;
            textViewBestScore = (TextView) convertView.findViewById(R.id.textViewBestScore);
            title = (TextView) convertView.findViewById(R.id.title);
            title.setText(quizModelList.get(position).getTitle());
            textViewBestScore.setText("Najlepszy Wynik:\n " + quizModelList.get(position).getScore());
            return convertView;
        }
    }
}