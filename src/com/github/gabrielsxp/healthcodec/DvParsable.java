/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

/**
 *
 * @author Gabriel
 */
public class DvParsable {
    private int integer;
    private String firstString;
    private String secondString;

    public DvParsable(int integer, String firstString, String secondString) {
        this.integer = integer;
        this.firstString = firstString;
        this.secondString = secondString;
    }

    public int getInteger() {
        return integer;
    }

    public String getFirstString() {
        return firstString;
    }

    public String getSecondString() {
        return secondString;
    }
    
    
}
