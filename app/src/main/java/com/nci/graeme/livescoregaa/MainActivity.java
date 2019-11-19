package com.nci.graeme.livescoregaa;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textview);

        new getData().execute();

    }//end of on create

    public class getData extends AsyncTask<Void, Void, Void>{
        String data = "";
        ArrayList<Match> matches = new ArrayList<Match>();
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://www.clubscores.ie/embed/957/trackers";
            try {
                Document doc = Jsoup.connect(url).get();
                data = doc.text();
                Element table = doc.select("table").get(0); //select the first table.
                Elements rows = table.select("tr");


                String league = null;
                for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    String classAttr = row.attr("class");
                    boolean leagueRow = classAttr != null && classAttr.equals("info");
                    Elements cols = row.select("td");

                    if(leagueRow && (cols.size() > 0)){
                        league = cols.get(0).text();
                    }
                    else{
                        Match m = new Match();
                        m.setLeague(league);
                        for(int j=0; j<cols.size(); j++){
                            switch(j){
                                case 0: //
                                    m.setTeam1(cols.get(0).text());
                                    break;
                                case 1: //
                                    m.setScore1(cols.get(1).text());
                                    break;
                                case 2: //
                                    m.setScore2(cols.get(2).text());
                                    break;
                                case 3: //
                                    m.setTeam2(cols.get(3).text());
                                    break;
                                case 4: //
                                    m.setTime(cols.get(4).text());
                                    break;
                                case 5: //
                                    m.setStatus(cols.get(5).text());
                                    break;
                                default:
                            }
                        }
                        matches.add(m);
                    }
                }// end of for





            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           // textView.setText(data);
            textView.setText(matches.toString());
        }
    }




}//end of MainActivity
