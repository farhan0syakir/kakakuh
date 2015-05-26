package com.kakakuh.c4ppl.kakakuh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;


public class DetailJadwalActivity extends KakakuhBaseActivity {
    Intent nextScreen;
    int id;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);

        bundle = getIntent().getExtras();
        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_jadwal);

        Button ubahBtn = (Button) findViewById(R.id.btn_ubah);
        ubahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "farhan ganteng",Toast.LENGTH_SHORT ).show();

                nextScreen = new Intent(getApplicationContext(), UpdateJadwalActivity.class);
//                nextScreen.putExtra("id",bundle.getString("id"));
                startActivity(nextScreen);
            }
        });
        Button hapusBtn = (Button) findViewById(R.id.btn_hapus);
        hapusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHapusPressed();
            }
        });
    }
    public void onHapusPressed() {

        new AlertDialog.Builder(DetailJadwalActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Apakah anda yakin ingin Menghapus?")
                .setNegativeButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        logout();
                    }

                })
                .setPositiveButton("Tidak", null)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_jadwal, menu);
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
