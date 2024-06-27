package com.example.footballmanager_cvl;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.footballmanager_cvl.ui.fecha.DialogoFecha;
import com.example.footballmanager_cvl.ui.save.SaveFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.footballmanager_cvl.databinding.ActivityDrawerBinding;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DrawerActivity extends AppCompatActivity implements
        DialogoFecha.onFechaSeleccionada{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(findViewById(R.id.toolbar));
        /*
        binding.appBarDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_fecha, R.id.nav_save, R.id.nav_historic, R.id.nav_signOut)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResultadoFecha(GregorianCalendar c) {
        //3.1. Mostraremos en el editText de fecha, la fecha seleccionada
        TextView textFecha = findViewById(R.id.textFechaSelected);
        textFecha.setText(c.get(Calendar.DAY_OF_MONTH) + "/" +
                (c.get(Calendar.MONTH)+1) + "/" +
                c.get(Calendar.YEAR));
        SaveFragment.fechaSeleccionada = c.get(Calendar.DAY_OF_MONTH) + "-" +
                (c.get(Calendar.MONTH)+1) + "-" +
                c.get(Calendar.YEAR);
        //NOTA: LOS MESES VAN NUMERADOS DEL 0 AL 11, POR ESO SUMANOS 1 AL MES

    }
}