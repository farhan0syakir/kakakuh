package com.kakakuh.c4ppl.kakakuh;

/**
 * Created by Aldi Reinaldi on 12/04/2015.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ProfilActivity extends TabActivity {
    // TabSpec Names
    private static final String PROFIL_ABOUT_SPEC = "About";
    private static final String PROFIL_LOG_SPEC = "Log";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        TabHost tabHost = getTabHost();

        // About Tab
        TabSpec profilAboutSpec = tabHost.newTabSpec(PROFIL_ABOUT_SPEC);
        // Tab Icon
        profilAboutSpec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector_about));
        Intent aboutIntent = new Intent(this, ProfilAboutActivity.class);
        // Tab Content
        profilAboutSpec.setContent(aboutIntent);

        // Outbox Tab
        TabSpec profilLogSpec = tabHost.newTabSpec(PROFIL_LOG_SPEC);
        profilLogSpec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector_log));
        Intent logIntent = new Intent(this, ProfilLogActivity.class);
        profilLogSpec.setContent(logIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(profilAboutSpec); // Adding Profil About tab
        tabHost.addTab(profilLogSpec); // Adding Profil Log tab
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

        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                //Intent upIntent = new Intent(this, MainActivity.class);
                //upIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(upIntent);
                super.onBackPressed();
                finish();
                return true;
            case R.id.action_pesan:
                //Buah Pikiran. Gimana agar pesan activity jalan di background?
                Intent nextScreen;
                String roleSekarang = MainActivity.getRoleSekarang();
                switch (roleSekarang) {
                    case "Koordinator":
                        nextScreen = new Intent(getApplicationContext(), PesanKoordinatorActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "KakakAsuh":
                        nextScreen = new Intent(getApplicationContext(), PesanKakakActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "Adik Asuh":
                        nextScreen = new Intent(getApplicationContext(), PesanAdikActivity.class);
                        startActivity(nextScreen);
                        break;
                    default:
                        break;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}