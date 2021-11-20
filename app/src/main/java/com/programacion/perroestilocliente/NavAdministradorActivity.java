package com.programacion.perroestilocliente;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.programacion.perroestilocliente.ExpandableListAdapter;
import com.programacion.perroestilocliente.MenuModel;
import com.programacion.perroestilocliente.R;
import androidx.appcompat.widget.Toolbar;

public class NavAdministradorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private FragmentManager mFraFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_administrador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_home) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Inicio", true, false, "",""); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Productos", true, true, "",""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Agrega producto", false, false, "Agrega producto","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Modifica producto", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Busca producto", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Elimina producto", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Consulta producto", false, false, "","");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Modelos", true, true, "",""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Crear modelo", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Modificar modelo", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar modelos", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Eliminar modelo", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar modelos", false, false, "","");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Kits", true, true, "",""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Crear KIT", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Modificar KIT", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar KIT", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Eliminar KIT", false, false, "","");
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar KITS", false, false, "","");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel("Talllas", true, false, "",""); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel("Diseño", true, false, "",""); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        String dir = headerList.get(groupPosition).menuName.toString();
                        switch (dir){
                            case "Inicio":
                                com.programacion.perroestilocliente.ui.administrador.modelos.crearModelo.CrearModeloFragment newFragment= new com.programacion.perroestilocliente.ui.administrador.modelos.crearModelo.CrearModeloFragment();
                                Bundle args = new Bundle();
                                newFragment.setArguments(args);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case "Talllas":
                                break;
                            case "Diseño":
                                com.programacion.perroestilocliente.ui.administrador.catalogo.crearCatergoria.CrearCategoriasFragment frag= new com.programacion.perroestilocliente.ui.administrador.catalogo.crearCatergoria.CrearCategoriasFragment();
                                args = new Bundle();
                                frag.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, frag);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                        }
                        onBackPressed();
                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    String dir = childList.get(headerList.get(groupPosition)).get(childPosition).menuName.toString();
                    switch (dir){
                        case "Agrega producto":
                            com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment newFragment= new com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment();
                            Bundle args = new Bundle();
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            break;
                        case "Modifica producto":
                            break;
                        case "Busca producto":
                            break;
                        case "Elimina producto":
                            break;
                        case "Consulta producto":
                            break;
                        case "Crear modelo":
                            break;
                        case "Modificar modelo":
                            break;
                        case "Buscar modelos":
                            break;
                        case "Eliminar modelo":
                            break;
                        case "Consultar modelos":
                            break;
                        case "Crear KIT":
                            break;
                        case "Modificar KIT":
                            break;
                        case "Buscar KIT":
                            break;
                        case "Eliminar KIT":
                            break;
                        case "Consultar KIT":
                            break;
                    }
                    onBackPressed();
                }
                return false;
            }
        });
    }
}