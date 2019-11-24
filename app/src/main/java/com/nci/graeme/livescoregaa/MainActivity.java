package com.nci.graeme.livescoregaa;
//Bugs
// https://stackoverflow.com/questions/28741645/how-to-implement-onscrolllistener-to-a-listview

// Splash Screen Gif Tutorial https://www.youtube.com/watch?v=0F_-ckbWv9c
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListView.OnCreateContextMenuListener{
    private Button feedButton;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        feedButton = findViewById(R.id.buttonFeed);
        listview = findViewById(R.id.listview);

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLiveFeed();
            }
        });





        Thread thread = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try {
                        Thread.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {              // ------------------------------------
                                new getData().execute();
                            }                               // -------------------------------------
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        new getData().execute();
        thread.start();

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

//https://stackoverflow.com/questions/37432209/maintain-the-scroll-position-of-a-listview-after-getting-new-data
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            UsersAdapter adapter = new UsersAdapter(MainActivity.this, matches);
             // Attach the adapter to a ListView
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);
          //  adapter.notifyDataSetChanged();
            adapter.remove(matches.get(0)); //removes the empty view in the ListView




        }
    }
    //Open Live Feed
    public void openLiveFeed(){
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }


    }//end of MainActivity
//add public
// https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
class UsersAdapter extends ArrayAdapter<Match> {
    public UsersAdapter(Context context, ArrayList<Match> users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Match match = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.match, parent, false);
        }
        // Lookup view for data population
        TextView team1 =  convertView.findViewById(R.id.team1);
        TextView team2 =  convertView.findViewById(R.id.team2);
        TextView score1 = convertView.findViewById(R.id.score1);
        TextView score2 = convertView.findViewById(R.id.score2);
        TextView status = convertView.findViewById(R.id.status);
        TextView time = convertView.findViewById(R.id.time);
        TextView league = convertView.findViewById(R.id.league);
        TextView sidebar = convertView.findViewById(R.id.sidebar);
        // Populate the data into the template view using the data object
        long millis = System.currentTimeMillis();     //for testing Thread
        String word = Long.toString(millis);          //for testing Thread
        team1.setText(match.getTeam1());
        team2.setText(match.getTeam2());
        score1.setText(match.getScore1());
        score2.setText(match.getScore2());
        status.setText(match.getStatus());
        time.setText(match.getTime());
        league.setText(match.getLeague());
        if(!match.getStatus().equals("FT")){
            sidebar.setBackgroundColor(Color.parseColor("#2196F3"));
        }
        //Change this
        // Return the completed view to render on screen
        return convertView;


    }
}