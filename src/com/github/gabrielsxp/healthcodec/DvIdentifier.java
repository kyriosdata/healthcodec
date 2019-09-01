/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.gabrielsxp.healthcodec;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Gabriel
 */
public class DvIdentifier {

    /*
    * Define os metadados da classe
     */
    public static byte[] META = {
        Constants.TYPE_STRING_ID
    };

    /*
    * Define respectivamente as posições das Strings issuer, assigner, id e type
    * no cabeçalho
     */
    public static int issuerLengthPosition = 4;
    public static int assignerLengthPosition = 8;
    public static int idLengthPosition = 12;
    public static int typeLengthPosition = 16;

    /*
    * Retorna o cabeçalho com os metadados e as posições das Strings que formar DvIdentifier
     */
    public static byte[] getHeader(String issuer, String assigner, String id, String type) throws UnsupportedEncodingException {
        int issuerLength = issuer.length();
        int assignerLength = assigner.length();
        int idLength = id.length();
        int typeLength = type.length();
        byte[] header = new byte[4 * Constants.TYPE_BYTE_SIZE + 4 * Constants.TYPE_INT_SIZE];
        header[0] = Constants.TYPE_STRING_ID;
        header[1] = Constants.TYPE_STRING_ID;
        header[2] = Constants.TYPE_STRING_ID;
        header[3] = Constants.TYPE_STRING_ID;

        header = UsefulMethods.writeIntegerOnByteArray(issuerLength, issuerLengthPosition, header);
        header = UsefulMethods.writeIntegerOnByteArray(assignerLength, assignerLengthPosition, header);
        header = UsefulMethods.writeIntegerOnByteArray(idLength, idLengthPosition, header);
        header = UsefulMethods.writeIntegerOnByteArray(typeLength, typeLengthPosition, header);

        return header;
    }

    /*
    * Retorna a posição de issuer, ou seja, como as strings possuem tamanho variados
    * o ínicio da string issuer é exatamente a posição de início dos dados da classe
     */
    public static int getIssuer(int handle) {
        return handle + 4 * Constants.TYPE_INT_SIZE + 4 * Constants.TYPE_BYTE_SIZE;
    }

    /*
    * Retorna o tamanho da string issuer
     */
    public static int getIssuerLength(int handle) {
        return handle + issuerLengthPosition * Constants.TYPE_BYTE_SIZE;
    }

    /*
    * Retorna o tamanho da string assigner
     */
    public static int getAssignerLength(int handle) {
        return handle + assignerLengthPosition * Constants.TYPE_BYTE_SIZE;
    }

    /*
    * Retorna o tamanho da string id
     */
    public static int getIdLength(int handle) {
        return handle + idLengthPosition * Constants.TYPE_BYTE_SIZE;
    }

    /*
    * Retorna o tamanho da string type
     */
    public static int getTypeLength(int handle) {
        return handle + typeLengthPosition * Constants.TYPE_BYTE_SIZE;
    }

}
