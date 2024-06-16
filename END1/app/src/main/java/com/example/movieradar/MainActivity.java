package com.example.movieradar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ez a metódus akkor hívódik meg, amikor az activity elindul. Itt történik az inicializálás.


        EdgeToEdge.enable(this);
        //Ez a sor lehetővé teszi az edge-to-edge megjelenítést, ami azt jelenti, hogy az alkalmazás teljes képernyőt használja,
        // beleértve azokat a területeket is, ahol általában a rendszer navigációs elemei láthatók.
        setContentView(R.layout.activity_main);
        //Ez a sor beállítja a felhasználói felületet az activity_main elrendezésre.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Ez a blokk beállít egy listener-t, amely az ablak insets-eket alkalmazza a nézetek padding-jára.
        // Ez biztosítja, hogy a tartalom ne kerüljön a rendszer sávok alá.

        // Helyes fragment inicializálás


        pager = findViewById(R.id.MoviePager);
        ViewPager2 pager = findViewById(R.id.MoviePager);
        TabLayout tabs = findViewById(R.id.TabMovies);

        ViewAdapter adapter = new ViewAdapter(this);
        //Ez a sor létrehoz egy új ViewAdapter objektumot, amely az oldalak tartalmát kezeli.
        pager.setAdapter(adapter);
        //Beállíjuk a pager adapterét (ami kezeli)
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabs.getTabAt(position).select();
            }
        });

    }
}

