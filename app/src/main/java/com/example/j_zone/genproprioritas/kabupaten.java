package com.example.j_zone.genproprioritas;

/**
 * Created by Muhammad on 11/4/2018.
 */

public class kabupaten {

    private String kode;
    private String nama;

    public kabupaten(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }


    public String getKode() {
        return kode;
    }

    public void setId(String kode) {
        this.kode = kode;
    }

    public String getName() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return nama;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof kabupaten) {
            kabupaten c = (kabupaten) obj;
            if (c.getName().equals(nama) && c.getKode() == kode) return true;
        }

        return false;
    }
}
