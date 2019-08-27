/*
 * Gabriel Soares Costa - 2019
 * Projeto Final de Curso - Universidade Federal de Goiás
 */
package com.github.gabrielsxp.healthcodec;

import com.github.gabrielsxp.healthcodec.DvParsable;
import com.github.gabrielsxp.healthcodec.TerminologyID;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel
 */
public class BufferOperations {

    static Map<Byte, Integer> TYPE_SIZE = new HashMap<Byte, Integer>() {
        {
            put((byte)0, 4); //int
            put((byte)1, 4); //float
            put((byte)2, 1); //byte
            put((byte)3, 2); //short
            put((byte)4, 8); //long
            put((byte)5, 8); //double
            put((byte)6, 1); //boolean
            put((byte)7, 2); //char
        }
    };
    
    static Map<String, Integer> TYPE_META_BYTES = new HashMap<String, Integer>() {
        {
            put("DVParsable", 10); // int, str1, str1_len, str1, str2_len
            put("TerminologyID", 10); //str1, str1_len, str2, str2_len
        }
    };

    static final int MAX_SIZE_BUFFER = 256;

    private ByteBuffer buffer;
    
    private int offset;
    
    private BufferOperations() {
        
    }

    public static BufferOperations serialize(byte[] data) {

        BufferOperations bf = new BufferOperations();

        bf.buffer = ByteBuffer.allocate(MAX_SIZE_BUFFER);

        bf.buffer.put(data);
        
        return bf;
    }

    public static BufferOperations deserialize(byte[] data) {
        BufferOperations bf = new BufferOperations();
        bf.buffer = ByteBuffer.wrap(data);
        
        
        return bf;
    }
    
    public int readInteger(){
        int offset = getOffset();
        int value = buffer.getInt(offset);
        setOffset(offset + 4);
        return value;
    }
    
    public void writeInteger(int i){
        int offset = getOffset();
        buffer.putInt(offset, i);
        setOffset(offset + 4);
    }
    
    public float readFloat(int position){
        return buffer.getFloat(position);
    }   
    
    public void writeFloat(int position, float f){
        buffer.position(position);
        buffer.putFloat(position, f);
    }
    
    public long readLong(int position){
        return buffer.getLong(position);
    }
    
    public void writeLong(int position, long l){
        buffer.position(position);
        buffer.putLong(position, l);
    }
    
    public short readShort(int position){
        return buffer.getShort(position);
    }
    
    public void writeShort(int position, short s){
        buffer.position(position);
        buffer.putShort(position, s);
    }
    
    public byte readByte(){
        int offset = getOffset();
        System.out.println("OFFSET READ BYTE: " + offset);
        byte returnedByte = buffer.get(offset);
        setOffset(offset + bufferVariation(offset, offset + 1));
        return returnedByte;
    }
    
    public void writeByte(byte b){
        buffer.put(getOffset(), b);
        setOffset(getOffset() + 1);
    }
    
    public char getChar(int position){
        return buffer.getChar(position);
    }
    
    public void writeChar(int position, char c){
        buffer.position(position);
        buffer.putChar(position, c);
    }
    
    public byte[] readByteStream(int length){
        byte[] byteArray = new byte[length+1];
        int offset = getOffset();
        int j = 0;
        for(int i = offset; i < offset + length; i++){
            byteArray[j] = buffer.get(i);
            j++;
        }
        setOffset(offset + length);
        return byteArray;
    }
    
    public void writeBufferStream(byte[] b){
        if(( b.length + offset ) > MAX_SIZE_BUFFER - 1){
            return;
        }
        for(int i = 0; i < b.length; i++){
            writeByte(b[i]);
        }
    }
    
    public byte readBoolean(int position){
        return buffer.get(position);
    }
    
    public void writeBoolean(int position, boolean b){
        buffer.position(position);
        if(b == true){
            buffer.put(position,(byte) 1);
        } else {
            buffer.put(position, (byte) 0);
        }
    }
    
    public void defineDvParsable(int firstStringLength, int secondStringLength){
        buffer.position(getOffset());
        buffer.put((byte) 0);
        buffer.put((byte) 8);
        buffer.putInt(firstStringLength);
        buffer.put((byte) 8);
        buffer.putInt(secondStringLength);
        setOffset(getOffset() + 11);
    }
    
    public void setDvParsable(int value, String firstString, String secondString) throws UnsupportedEncodingException{;
        setOffset(getOffset());
        defineDvParsable(firstString.length(), secondString.length());
        writeInteger(value);
        writeBufferStream(firstString.getBytes("UTF-8"));
        writeBufferStream(secondString.getBytes("UTF-8"));
    }
    
    public DvParsable getDvParsable(){
        readByte();
        readByte();
        int str1LengthOffset = readInteger();
        readByte();
        int str2LengthOffset = readInteger();
        
        int value = readInteger();
        String str1 = getStringFromByteArray(readByteStream(str1LengthOffset));
        String str2 = getStringFromByteArray(readByteStream(str2LengthOffset));
        
        return new DvParsable(value, str1, str2);
    }
    
    public void defineTerminologyID(int firstStringLength, int secondStringLength){
        buffer.put((byte) 8);
        buffer.putInt(firstStringLength);
        buffer.put((byte) 8);
        buffer.putInt(secondStringLength);
        setOffset(getOffset() + 10);
    }
    
    public void setTerminologyID(String firstString, String secondString) throws UnsupportedEncodingException{
        setOffset(getOffset());
        int firstStringLength = firstString.length();
        int secondStringLength = secondString.length();
        defineTerminologyID(firstStringLength, secondStringLength);
        writeBufferStream(firstString.getBytes("UTF-8"));
        writeBufferStream(secondString.getBytes("UTF-8"));
    }
    
    public TerminologyID getTerminologyID(){
        readByte();
        int str1LengthOffset = readInteger();
        readByte();
        int str2LengthOffset = readInteger();
        
        String str1 = getStringFromByteArray(readByteStream(str1LengthOffset));
        String str2 = getStringFromByteArray(readByteStream(str2LengthOffset));
        setOffset(getOffset() + str1LengthOffset + str2LengthOffset);
        
        return new TerminologyID(str1, str2);
    }
    
    public String getStringFromByteArray(byte[] utf8ByteArray){
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }
    
    public String printStringFromByteArray(byte[] utf8ByteArray){
        String string = new String(utf8ByteArray, StandardCharsets.UTF_8);
        return string;
    }
    
    public byte[] data() {
        return buffer.array();
    }
    
    public int bufferVariation(int offset, int endPosition){
        return endPosition - offset;
    }
    
    public void setOffset(int offset){
        this.offset = offset;
        buffer.position(offset);
    }
    
    public int getOffset(){
        return this.offset;
    }
}
