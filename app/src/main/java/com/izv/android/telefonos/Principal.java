package com.izv.android.telefonos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class Principal extends Activity {
    private ArrayList<Telefono> datos;
    private Adaptador ad;
    private ListView lv;
    private EditText etmarca, etmodelo, etprecio, etstock;
    String marca, modelo, precio, stock;
    boolean semaforo=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        iniciarComponentes();
    }

    /***********************************************************************/
    /*                                                                     */
    /*                              MENUS                                  */
    /*                                                                     */
    /***********************************************************************/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int index=info.position;
        Object o= info.targetView.getTag();
        Adaptador.ViewHolder vh;
        vh=(Adaptador.ViewHolder)o;
        if (id == R.id.m_editar) {
            editar(index);
        }else {
            if (id == R.id.m_borrar) {
                datos.remove(index);
                ad.notifyDataSetChanged();
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menucontextual,menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.m_anadir) {
            return anadir();
        }
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************************/
    /*                                                                     */
    /*                        METODOS AUXILIARES                           */
    /*                                                                     */
    /***********************************************************************/

    public boolean anadir(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.anadirmovil);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo, null);
        alert.setView(vista);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        etmarca = (EditText) vista.findViewById(R.id.etmarca);
                        etmodelo = (EditText) vista.findViewById(R.id.etmodelo);
                        etprecio=(EditText)vista.findViewById(R.id.etprecio);
                        etstock=(EditText)vista.findViewById(R.id.etstock);
                        marca=etmarca.getText().toString().trim();
                        modelo=etmodelo.getText().toString().trim();
                        precio=etprecio.getText().toString();
                        stock=etstock.getText().toString();
                        if (marca.length() > 0 && modelo.length() > 0 && precio.length() > 0 && stock.length() > 0 ) {
                            boolean comprobar=true;
                            Telefono tl= new Telefono(marca, modelo, precio, stock);
                            comprobar=comprobaranadir(tl);
                            if(comprobar==true){
                                datos.add(tl);
                                tostada("TELÉFONO REGISTRADO");
                            }else{
                                tostada("TELÉFONO REPETIDO");
                            }
                            Collections.sort(datos);
                            ad.notifyDataSetChanged();
                            semaforo=true;
                        }else{
                            semaforo=false;
                        }
                        if(semaforo==false) {
                            tostada("NO RELLENASTE TODOS LOS CAMPOS");
                        }
                    }
                }
        );
        alert.setNegativeButton(android.R.string.no,null);
        alert.show();
        return true;
    }

    public boolean comprobaranadir(Telefono tl){
        Telefono tl2;
        for (int i=0;i<datos.size();i++){
            tl2=datos.get(i);
            if (tl.getMarca().compareToIgnoreCase(tl2.getMarca())==0 ){
                if(tl.getModelo().compareToIgnoreCase(tl2.getModelo())==0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean compribareditar(Telefono tl2, int index){
        Telefono tl;
        for (int i=0;i<index;i++){
            tl=datos.get(i);
            if (tl.getMarca().compareToIgnoreCase(tl2.getMarca())==0 ){
                if(tl.getModelo().compareToIgnoreCase(tl2.getModelo())==0){
                    return false;
                }
            }
        }
        for (int i=index+1;i<datos.size();i++){
            tl=datos.get(i);
            if (tl.getMarca().compareToIgnoreCase(tl2.getMarca())==0 ){
                if(tl.getModelo().compareToIgnoreCase(tl2.getModelo())==0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean editar(final int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.editartelefono);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.dialogo, null);
        alert.setView(vista);
        etmarca = (EditText) vista.findViewById(R.id.etmarca);
        etmodelo = (EditText) vista.findViewById(R.id.etmodelo);
        etprecio=(EditText)vista.findViewById(R.id.etprecio);
        etstock=(EditText)vista.findViewById(R.id.etstock);
        Telefono tl=new Telefono();
        tl=datos.get(index);
        marca=tl.getMarca();
        modelo=tl.getModelo();
        precio=tl.getPrecio();
        stock=tl.getStock();
        etmarca.setText(marca);
        etmodelo.setText(modelo);
        etprecio.setText(precio);
        etstock.setText(stock);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        etmarca = (EditText) vista.findViewById(R.id.etmarca);
                        etmodelo = (EditText) vista.findViewById(R.id.etmodelo);
                        etprecio=(EditText)vista.findViewById(R.id.etprecio);
                        etstock=(EditText)vista.findViewById(R.id.etstock);
                        marca=etmarca.getText().toString().trim();
                        modelo=etmodelo.getText().toString().trim();
                        precio=etprecio.getText().toString();
                        stock=etstock.getText().toString();
                        if (marca.length() > 0 && modelo.length() > 0 && precio.length() > 0 && stock.length() > 0 ) {
                            Telefono tl2=new Telefono(marca,modelo,precio,stock);
                            boolean comprobar=true;
                            comprobar=compribareditar(tl2, index);
                            if(comprobar==true) {
                                datos.set(index, tl2);
                                tostada("TELÉFONO EDITADO");
                            }else{
                                tostada("TELÉFONO REPETIDO");
                            }
                            Collections.sort(datos);
                            ad.notifyDataSetChanged();
                            semaforo=true;
                        }else{
                            semaforo=false;
                        }
                        if(semaforo==false) {
                            tostada("NO RELLENASTE TODOS LOS CAMPOS");
                        }
                    }
                });
        alert.setNegativeButton(android.R.string.no,null);
        alert.show();
        return true;
    }

    private void iniciarComponentes() {
        datos = new ArrayList();
        Telefono tl1= new Telefono("SONY", "XPERIA M2","225","4");
        datos.add(tl1);
        Telefono tl2= new Telefono("SAMSUNG", "GALAXY EXPRESS","285","2");
        datos.add(tl2);
        Telefono tl3=new Telefono("iPHONE","5","559" ,"1");
        datos.add(tl3);
        Collections.sort(datos);

        ad = new Adaptador(this,R.layout.itemlista,datos);
        lv = (ListView)findViewById(R.id.lvlista);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
    }

    public void tostada (String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    /***********************************************************************/
    /*                                                                     */
    /*                METODOS ONCLICK SOBRE LAS IMAGENES                   */
    /*                                                                     */
    /***********************************************************************/

    public void delete(View v){
        int index=-1;
        index=(Integer)v.getTag();
        datos.remove(index);
        ad.notifyDataSetChanged();
    }

    public void edit(View v){
        int index=-1;
        index=(Integer)v.getTag();
        editar(index);
    }

}