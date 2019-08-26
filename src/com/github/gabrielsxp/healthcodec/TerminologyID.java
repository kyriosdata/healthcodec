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
public class TerminologyID {
    private String firstString;
    private String secondString;

    public TerminologyID(String firstString, String secondString) {
        this.firstString = firstString;
        this.secondString = secondString;
    }

    public String getFirstString() {
        return firstString;
    }

    public String getSecondString() {
        return secondString;
    }
    
    
}
