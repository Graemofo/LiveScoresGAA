package com.nci.graeme.livescoregaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ListView listview;
  //  TextView textView;
  //  TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  textView = findViewById(R.id.textview);
      //  textView2 = findViewById(R.id.textview2);
        listview = findViewById(R.id.listview);

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
//            ArrayAdapter<Match> itemsAdapter =
//                    new ArrayAdapter<Match>(MainActivity.this, android.R.layout.simple_list_item_1, matches);
//            listview.setAdapter(itemsAdapter);
            UsersAdapter adapter = new UsersAdapter(MainActivity.this, matches);
             // Attach the adapter to a ListView
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);

//            for(Match m : matches){
//                textView2.setText(m.getLeague());
//                textView.setText(m.getTeam1()+" "+m.getScore1()+"  -  "+m.getScore2()+ " "+m.getTeam2()+" "+m.getStatus()+" "+m.getTime());
//            }
        }
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
        // Populate the data into the template view using the data object
        team1.setText(match.getTeam1());
        team2.setText(match.getTeam2());
        score1.setText(match.getScore1());
        score2.setText(match.getScore2());
        status.setText(match.getStatus());
        time.setText(match.getTime());
        league.setText(match.getLeague());
        // Return the completed view to render on screen
        return convertView;
    }
}