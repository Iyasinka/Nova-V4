package com.karayalcinyasin.nova_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.karayalcinyasin.nova_v4.Fragment.HomeFragment;
import com.karayalcinyasin.nova_v4.Fragment.NotificationFragment;
import com.karayalcinyasin.nova_v4.Fragment.ProfileFragment;
import com.karayalcinyasin.nova_v4.Fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    if (id == R.id.home) {
                        // Home seçildi
                    } else if (id == R.id.nav_search) {
                        // Arama
                    } else if (id == R.id.nav_add) {
                        // Ekle
                    } else if (id == R.id.nav_heart) {
                        // Beğeni
                    } else if (id == R.id.nav_profile) {
                        // Profil
                    }

                    return true;
                }

            };
    }
    /*//Menü olarak çıkış yap butonu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        auth.signOut();
        if(item.getItemId()==R.id.signout){
            Intent intent = new Intent(MainActivity.this,a.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/




