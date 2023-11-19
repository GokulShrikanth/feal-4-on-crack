package com.blaze;

public class data {

    public static int pairs = 200;
    public data data;
    public int L0, R0, L4, R4;
    public String plaintext[] = new String[pairs];
    public String cyphertext[] = new String[pairs];

    public data data(){
        if(data.equals(null)){
            data = new data();
            return data;
        }
        return data;
    }

    public static data createData() {
        return new data();
    }
}
