package com.izv.android.telefonos;

/**
 * Created by 2dam on 10/10/2014.
 */
public class Telefono implements Comparable{
    private String marca, modelo, precio, stock;

    public Telefono(String marca, String modelo, String precio, String stock) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
    }

    public Telefono() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public int compareTo(Object o) {
        Telefono tl = (Telefono) o;
        if (this.getMarca().compareToIgnoreCase(tl.getMarca()) == 0) {
            if (this.getModelo().compareToIgnoreCase(tl.getModelo()) != 0) {
                return this.getModelo().compareToIgnoreCase(tl.getModelo());
            }
        } else {
            return this.getMarca().compareToIgnoreCase(tl.getMarca());
        }
        return 0;
    }

}
