package com.kakakuh.c4ppl.kakakuh;

/**
 * Created by Aldi Reinaldi on 12/04/2015.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
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
}