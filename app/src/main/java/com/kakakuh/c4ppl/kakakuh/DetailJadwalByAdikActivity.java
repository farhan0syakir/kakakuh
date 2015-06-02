package com.kakakuh.c4ppl.kakakuh;

import android.nfc.TagLostException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class DetailJadwalByAdikActivity extends KakakuhBaseActivity {

    TextView colorField,placeField,timeField,titleField,descriptionField,bookerField=null;
    int id,color=0;
    String title,place,startDateStr,endDateStr,descriptionStr,bookerId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal_by_adik);

        colorField = (TextView) findViewById(R.id.color);
        placeField = (TextView) findViewById(R.id.tempat);
        timeField = (TextView) findViewById(R.id.waktu_tanggal);
        titleField = (TextView) findViewById(R.id.status);
        descriptionField = (TextView) findViewById(R.id.detail);
        bookerField = (TextView) findViewById(R.id.booker);
        try {
            retrieveExtras(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveExtras(Bundle bundle){
        Bundle extras = getIntent().getExtras();
        this.id = Integer.parseInt(String.valueOf(extras.getLong("id")));
        this.title = extras.getString("judul");
        this.place = extras.getString("place");
        this.startDateStr = extras.getString("start");
        this.endDateStr = extras.getString("end");
        this.color = extras.getInt("color");
        placeField.setText(place);
        colorField.setBackgroundColor(color);
        titleField.setText(title);
        timeField.setText("" + startDateStr + " - " + endDateStr);

//        new detailJSON(DetailJadwalActivity.this,myUrl+"?id="+id).execute().get();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_jadwal_by_adik, menu);
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
}
