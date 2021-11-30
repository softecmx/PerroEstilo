package com.programacion.perroestilocliente;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.programacion.perroestilocliente.ExpandableListAdapter;
import com.programacion.perroestilocliente.MenuModel;
import com.programacion.perroestilocliente.R;
import androidx.appcompat.widget.Toolbar;

public class NavAdministradorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    private int lastExpandedPosition = -1;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private FragmentManager mFraFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_administrador);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Inicio");
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment newFragment= new com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment();
        Bundle args = new Bundle();
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        toolbar.setTitle("Inicio");

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

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

        MenuModel menuModel = new MenuModel("Inicio", true, false, "",R.drawable.ic_home_black_24dp); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Categorías", true, true, "",R.drawable.outline_category_24); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Agregar categoría", false, false, "",R.drawable.ic_add_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Modifica categoría", false, false, "",R.drawable.ic_edit_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar categoría", false, false, "",R.drawable.ic_search_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar categoría", false, false, "",R.drawable.ic_list_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Eliminar categoría", false, false, "",R.drawable.ic_delete_forever_black_24dp);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Diseños", true, false, "",R.drawable.ic_baseline_style_24); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }

        menuModel = new MenuModel("Tallas", true, false, "",R.drawable.ic_baseline_straighten_24); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Productos", true, true, "",R.drawable.ic_baseline_shopify_24); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Agregar productos", false, false, "",R.drawable.ic_add_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Modificar productos", false, false, "",R.drawable.ic_edit_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar productos", false, false, "",R.drawable.ic_search_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar productos", false, false, "",R.drawable.ic_list_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Eliminar productos", false, false, "",R.drawable.ic_delete_forever_black_24dp);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Kits", true, true, "",0); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Crear kits", false, false, "",R.drawable.ic_add_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Modificar kits", false, false, "",R.drawable.ic_edit_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar kits", false, false, "",R.drawable.ic_search_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar kits", false, false, "",R.drawable.ic_list_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Eliminar kits", false, false, "",R.drawable.ic_delete_forever_black_24dp);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Inventario", true, false, "",R.drawable.outline_inventory_24); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Pagos", true, true, "",R.drawable.ic_payment_black_24dp); //Menu of Python Tutorials
        headerList.add(menuModel);

        childModel = new MenuModel("Consultar pagos", false, false, "",R.drawable.ic_info_outline_black_24dp);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Pedidos", true, true, "",R.drawable.ic_loyalty_black_24dp); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Crear pedidos", false, false, "",R.drawable.ic_add_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Modificar pedidos", false, false, "",R.drawable.ic_edit_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Buscar pedidos", false, false, "",R.drawable.ic_search_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Consultar pedidos", false, false, "",R.drawable.ic_list_black_24dp);
        childModelsList.add(childModel);

        childModel = new MenuModel("Cancelar pedidos", false, false, "",R.drawable.ic_cancel_black_24dp);
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Envios", true, true, "",R.drawable.ic_local_shipping_black_24dp); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Ver envios", false, false, "",R.drawable.ic_visibility_black_24dp);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel("Ventas", true, false, "",R.drawable.ic_equalizer_black_24dp); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Clientes", true, true, "",R.drawable.ic_people_black_24dp); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Ver clientes", false, false, "",R.drawable.ic_favorite_black_24dp);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Cuenta", true, false, "",R.drawable.ic_person_black_24dp); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }
        menuModel = new MenuModel("Ajustes", true, false, "",R.drawable.ic_settings_black_24dp); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(childModel, null);
        }
        menuModel = new MenuModel("Salir", true, false, "",R.drawable.ic_exit_to_app_black_24dp); //Menu of Android Tutorial. No sub menus
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
                                com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment newFragment= new com.programacion.perroestilocliente.ui.administrador.inicio.HomeAdminFragment();
                                Bundle args = new Bundle();
                                newFragment.setArguments(args);
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Inicio");
                                break;
                            case "Tallas":
                                com.programacion.perroestilocliente.ui.administrador.tallas.TallasFragment newFragment1= new com.programacion.perroestilocliente.ui.administrador.tallas.TallasFragment();
                                 args = new Bundle();
                                newFragment1.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, newFragment1);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Tallas");
                                break;
                            case "Inventario":
                                com.programacion.perroestilocliente.ui.administrador.inventario.InventarioFragment newFragment2= new com.programacion.perroestilocliente.ui.administrador.inventario.InventarioFragment();
                                args = new Bundle();
                                newFragment2.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, newFragment2);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Inventario");
                                break;
                            case "Ventas":
                                com.programacion.perroestilocliente.ui.administrador.ventas.consultarVentas.ConsultarVentasFragment frag= new com.programacion.perroestilocliente.ui.administrador.ventas.consultarVentas.ConsultarVentasFragment();
                                args = new Bundle();
                                frag.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, frag);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Ventas");
                                break;
                            case "Cuenta":
                                com.programacion.perroestilocliente.ui.administrador.cuenta.CuentaFragment frag1= new com.programacion.perroestilocliente.ui.administrador.cuenta.CuentaFragment();
                                args = new Bundle();
                                frag1.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, frag1);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Cuenta");
                                break;
                            case "Ajustes":
                                com.programacion.perroestilocliente.ui.ajustes.AjustesFragment frag2= new com.programacion.perroestilocliente.ui.ajustes.AjustesFragment();
                                args = new Bundle();
                                frag2.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, frag2);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Ajustes");
                                break;

                            case "Diseños":
                                com.programacion.perroestilocliente.ui.administrador.disenios.consularDisenios.ConsultarDiseniosFragment frag3= new com.programacion.perroestilocliente.ui.administrador.disenios.consularDisenios.ConsultarDiseniosFragment();
                                args = new Bundle();
                                frag3.setArguments(args);
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, frag3);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                toolbar.setTitle("Diseños");
                                break;
                            case "Salir":
                                FirebaseUser userj=FirebaseAuth.getInstance().getCurrentUser();
                                mostarToast("usuario: "+userj,1,false);
                                FirebaseAuth.getInstance().signOut();
                                mostarToast("Sessión terminada exitosamente",1,true);
                                startActivity(new Intent(NavAdministradorActivity.this, LogginActivity.class));
                                finish();
                                FirebaseUser userk=FirebaseAuth.getInstance().getCurrentUser();
                                mostarToast("usuario: "+userk,5,false);

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
                        case "Agregar categoría":
                            com.programacion.perroestilocliente.ui.administrador.catalogo.crearCatergoria.CrearCategoriasFragment newFragment= new com.programacion.perroestilocliente.ui.administrador.catalogo.crearCatergoria.CrearCategoriasFragment();
                            Bundle args = new Bundle();
                            newFragment.setArguments(args);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Agregar categoría");
                            break;
                        case "Modifica categoría":
                            com.programacion.perroestilocliente.ui.administrador.catalogo.editarCategoria.EditarCategoriaFragment newFragment1= new com.programacion.perroestilocliente.ui.administrador.catalogo.editarCategoria.EditarCategoriaFragment();
                            args = new Bundle();
                            newFragment1.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment1);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Modificar categoría");
                            break;
                        case "Buscar categoría":
                            com.programacion.perroestilocliente.ui.administrador.catalogo.buscarCategoria.BuscarCategoriaFragment newFragment2= new com.programacion.perroestilocliente.ui.administrador.catalogo.buscarCategoria.BuscarCategoriaFragment();
                            args = new Bundle();
                            newFragment2.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment2);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Buscar categoría");
                            break;
                        case "Consultar categoría":
                            com.programacion.perroestilocliente.ui.administrador.catalogo.consularCategorias.ConsultarCategoriasFragment newFragment3= new com.programacion.perroestilocliente.ui.administrador.catalogo.consularCategorias.ConsultarCategoriasFragment();
                            args = new Bundle();
                            newFragment3.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment3);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Consultar categorías");
                            break;
                        case "Eliminar categoría":
                            com.programacion.perroestilocliente.ui.administrador.catalogo.eliminarCategoria.EliminarCategoriaFragment newFragment4= new com.programacion.perroestilocliente.ui.administrador.catalogo.eliminarCategoria.EliminarCategoriaFragment();
                            args = new Bundle();
                            newFragment4.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment4);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Eliminar categoría");
                            break;
                        case "Agregar productos":
                            com.programacion.perroestilocliente.ui.administrador.productos.agregarProductos.AgregarProductosFragment newFragment5= new com.programacion.perroestilocliente.ui.administrador.productos.agregarProductos.AgregarProductosFragment();
                            args = new Bundle();
                            newFragment5.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment5);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Agregar productos");
                            break;
                        case "Modificar productos":
                            com.programacion.perroestilocliente.ui.administrador.productos.editarProductos.EditarProductosFragment newFragment6= new com.programacion.perroestilocliente.ui.administrador.productos.editarProductos.EditarProductosFragment();
                            args = new Bundle();
                            newFragment6.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment6);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Modificar productos");
                            break;
                        case "Buscar productos":
                            com.programacion.perroestilocliente.ui.administrador.productos.buscarProductos.BuscarProductosFragment newFragment7= new com.programacion.perroestilocliente.ui.administrador.productos.buscarProductos.BuscarProductosFragment();
                            args = new Bundle();
                            newFragment7.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment7);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Buscar productos");
                            break;
                        case "Consultar productos":
                            com.programacion.perroestilocliente.ui.administrador.productos.consultarProductos.ConsultarProductosFragment newFragment8= new com.programacion.perroestilocliente.ui.administrador.productos.consultarProductos.ConsultarProductosFragment();
                            args = new Bundle();
                            newFragment8.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment8);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Consultar productos");
                            break;
                        case "Eliminar productos":
                            com.programacion.perroestilocliente.ui.administrador.productos.eliminarProductos.EliminarProductosFragment newFragment9= new com.programacion.perroestilocliente.ui.administrador.productos.eliminarProductos.EliminarProductosFragment();
                            args = new Bundle();
                            newFragment9.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment9);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Eliminar productos");
                            break;
                        case "Crear kits":
                            com.programacion.perroestilocliente.ui.administrador.matchs.crearMatch.CrearMath1Fragment newFragment10= new com.programacion.perroestilocliente.ui.administrador.matchs.crearMatch.CrearMath1Fragment();
                            args = new Bundle();
                            newFragment10.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment10);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Crear kit");
                            break;
                        case "Modificar kits":
                            com.programacion.perroestilocliente.ui.administrador.matchs.editarMatch.EditarMatchFragment newFragment11= new com.programacion.perroestilocliente.ui.administrador.matchs.editarMatch.EditarMatchFragment();
                            args = new Bundle();
                            newFragment11.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment11);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Modificar kit");
                            break;
                        case "Buscar kits":
                            com.programacion.perroestilocliente.ui.administrador.matchs.buscarMatch.BuscarMatchFragment newFragment12= new com.programacion.perroestilocliente.ui.administrador.matchs.buscarMatch.BuscarMatchFragment();
                            args = new Bundle();
                            newFragment12.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment12);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Buscar kit");
                            break;
                        case "Consultar kits":
                            com.programacion.perroestilocliente.ui.administrador.matchs.consultarMatchs.ConsultarMatchsFragment newFragment13= new com.programacion.perroestilocliente.ui.administrador.matchs.consultarMatchs.ConsultarMatchsFragment();
                            args = new Bundle();
                            newFragment13.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment13);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Consultar kit");
                            break;
                        case "Eliminar kits":
                            com.programacion.perroestilocliente.ui.administrador.matchs.eliminarMatch.EliminarMatchFragment newFragment14= new com.programacion.perroestilocliente.ui.administrador.matchs.eliminarMatch.EliminarMatchFragment();
                            args = new Bundle();
                            newFragment14.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment14);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Eliminar kit");
                            break;
                        case "Inventario":
                            com.programacion.perroestilocliente.ui.administrador.inventario.InventarioFragment newFragment15= new com.programacion.perroestilocliente.ui.administrador.inventario.InventarioFragment();
                            args = new Bundle();
                            newFragment15.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment15);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Inventario");
                            break;
                        case "Crear pedidos":
                            com.programacion.perroestilocliente.ui.administrador.pedidos.crearPedido.CrearPedidoFragment newFragment16= new com.programacion.perroestilocliente.ui.administrador.pedidos.crearPedido.CrearPedidoFragment();
                            args = new Bundle();
                            newFragment16.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment16);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Crear pedido");
                            break;
                        case "Modificar pedidos":
                            com.programacion.perroestilocliente.ui.administrador.pedidos.editarPedido.EditarPedidoFragment newFragment17= new com.programacion.perroestilocliente.ui.administrador.pedidos.editarPedido.EditarPedidoFragment();
                            args = new Bundle();
                            newFragment17.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment17);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Modificar pedido");
                            break;
                        case "Buscar pedidos":
                            com.programacion.perroestilocliente.ui.administrador.pedidos.buscarPedido.BuscarPedidoFragment newFragment18= new com.programacion.perroestilocliente.ui.administrador.pedidos.buscarPedido.BuscarPedidoFragment();
                            args = new Bundle();
                            newFragment18.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment18);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Buscar pedido");
                            break;
                        case "Consultar pedidos":
                            com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ConsultarPedidosFragment newFragment19= new com.programacion.perroestilocliente.ui.administrador.pedidos.consultarPedidos.ConsultarPedidosFragment();
                            args = new Bundle();
                            newFragment19.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment19);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Consultar pedidos");
                            break;
                        case "Cancelar pedidos":
                            com.programacion.perroestilocliente.ui.administrador.pedidos.eliminarPedido.EliminarPedidoFragment newFragment20= new com.programacion.perroestilocliente.ui.administrador.pedidos.eliminarPedido.EliminarPedidoFragment();
                            args = new Bundle();
                            newFragment20.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment20);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Cancelar pedidos");
                            break;
                        case "Ver envios":
                            com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.VerEnviosFragment newFragment21= new com.programacion.perroestilocliente.ui.administrador.envios.verEnvios.VerEnviosFragment();
                            args = new Bundle();
                            newFragment21.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment21);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Ver envios");
                            break;
                        case "Ver clientes":
                            com.programacion.perroestilocliente.ui.administrador.clientes.VerClientesFragment newFragment22= new com.programacion.perroestilocliente.ui.administrador.clientes.VerClientesFragment();
                            args = new Bundle();
                            newFragment22.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment22);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Ver clientes");
                            break;
                        case "Consultar pagos":
                            com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes.PagosPendientesFragment newFragment23= new com.programacion.perroestilocliente.ui.administrador.pagos.consultarPagosPendientes.PagosPendientesFragment();
                            args = new Bundle();
                            newFragment23.setArguments(args);
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment23);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            toolbar.setTitle("Consultar pagos");
                            break;
                    }
                    onBackPressed();
                }


                return false;
            }
        });
    }
    public void mostarToast(String txt, int estatus, boolean corto) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = null;

        if(estatus==0){
            //Default
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==1){
            //Success
            layout = inflater.inflate(R.layout.custom_toast_success,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==2) {
            //Warning
            layout = inflater.inflate(R.layout.custom_toast_warning,
                    (ViewGroup) findViewById(R.id.layout_base));

        }else if(estatus==3){
            //Error
            layout = inflater.inflate(R.layout.custom_toast_error,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else if(estatus==4){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_red,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else if(estatus==5){
            //Falla de red
            layout = inflater.inflate(R.layout.custom_toast_sin_data,
                    (ViewGroup) findViewById(R.id.layout_base));


        }else{
            //Informacion
            layout = inflater.inflate(R.layout.custom_toast_info,
                    (ViewGroup) findViewById(R.id.layout_base));
        }

        TextView textView = layout.findViewById(R.id.txtToast);
        textView.setText(txt);
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 50);
        if(corto){
            toast.setDuration(Toast.LENGTH_SHORT);}else{
            toast.setDuration(Toast.LENGTH_LONG);}
        toast.setView(layout);
        toast.show();
    }
}