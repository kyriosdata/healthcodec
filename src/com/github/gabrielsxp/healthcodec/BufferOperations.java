/*
 * Gabriel Soares Costa - 2019
 * Projeto Final de Curso - Universidade Federal de Goi�s
 */
package com.github.gabrielsxp.healthcodec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel Descri��o: Classe Respons�vel por primeiramente encapsular o
 * buffer de dados e fornecer as opera��es de E/S sobre o mesmo
 */
public class BufferOperations {

    /*
    * Estrutura com as poss�veis exce��es e as correspondentes mensagens de
    * aviso sobre as mesmas
     */
    static Map<String, String> EXCEPTIONS = new HashMap<String, String>() {
        {
            put("java.lang.IndexOutOfBoundsException", "N�o foi poss�vel realizar a leitura da quantidade necess�ria de bytes a partir deste offset");
            put("java.lang.IllegalArgumentException", "Tentativa de definir a posi��o do buffer com uma nova posi��o que ultrapassa o limite do mesmo ou com um valor negativo");
            put("java.lang.ReadOnlyBufferException ", "O buffer est� definido como apenas-leitura");
            put("java.lang.BufferOverflowException", "N�o existe espa�o suficiente no buffer para realizar a escrita dos dados presentes no array de bytes");
        }
    };

    //Tamanho m�ximo assumido pelo buffer que armazenar� os dados serializados
    static final int MAX_SIZE_BUFFER = 256;

    /**
     * Buffer que ser� utilizado para armazenar os headers e os dados passados como
     * argumento na fun��o serialize. Veja {@link #serialize(byte[])} 
     *
    */
    private ByteBuffer buffer;

    //Impede a cria��o desnecess�ria da inst�ncia
    private BufferOperations() {

    }

    /*
    * Fun��o respons�vel por encapsular um array de bytes 
    * recebido com entrada em um ByteBuffer que ser� armazenado
    * no atributo da classe
    *
    * @param data array de bytes que � recebido como entrada
    *             contendo os headers (metadados e informa��es importantes
    *             como o tamanho de uma string, por exemplo) e os conte�dos
    *             associado a cada header presente no array
    * @return Retorna uma inst�ncia com o buffer encapsulado e pronto para
    *         as opera��es de E/S
     */
    public static BufferOperations serialize(byte[] data) {

        BufferOperations bf = new BufferOperations();

        bf.buffer = ByteBuffer.allocate(MAX_SIZE_BUFFER);

        bf.buffer.put(data);

        return bf;
    }

    /*
    * Fun��o respons�vel por encapsular um array de bytes 
    * recebido com entrada em um ByteBuffer que ser� armazenado
    * no atributo da classe
    *
    * @param data array de bytes que � recebido como entrada
    *             contendo os headers (metadados e informa��es importantes
    *             como o tamanho de uma string, por exemplo) e os conte�dos
    *             associado a cada header presente no array
    * @return Retorna uma inst�ncia com o buffer encapsulado e pronto para
    *         as opera��es de E/S
     */
    public static BufferOperations deserialize(byte[] data) {
        BufferOperations bf = new BufferOperations();
        bf.buffer = ByteBuffer.wrap(data);

        return bf;
    }

    /*
    * Fun��o respons�vel por ler 4 bytes do buffer e retornar o n�mero
    * inteiro que corresponde a sequ�ncia de bytes lida.
    *
    * @param position Posi��o do array
    * @return intValue Valor inteiro correspondente aos 4 btyes lidos na opera��o
    * @throws BufferUnderflowException no caso de n�o conseguir ler 4 bytes do buffer
     */
    public int readInteger(int position) throws Exception {
        int intValue = 0;
        try {
            intValue = buffer.getInt(position);
        } catch (Exception e) {
            throw getExceptionMessage(e);
        }
        return intValue;
    }

    /*
    * Fun��o respons�vel por escrever um n�mero inteiro (4 bytes) no buffer
    *
    * @param position Posi��o do array
    * @param i N�mero inteiro que ser� escrito
    * @throws IndexOutOfBoundsException no caso de uma posi��o maior que o tamanho do array
    * @throws ReadOnlyBufferException no caso do buffer ser apenas para escrita
     */
    public void writeInteger(int position, int i) throws Exception {
        try {
            buffer.position(position);
            buffer.putInt(position, i);
        } catch (Exception e) {
            throw getExceptionMessage(e);
        }
    }

    /*
    * Fun��o respons�vel por ler 1 byte do buffer e retorn�-lo
    *
    * @param position Posi��o do array
    * @return byteValue correspondente
    * @throws BufferUnderflowException no caso de n�o conseguir ler 4 bytes do buffer
     */
    public byte readByte(int position) throws Exception {
        byte byteValue = (byte) 0;
        try {
            byteValue = buffer.get(position);
        } catch (Exception e) {
            throw getExceptionMessage(e);
        }
        return byteValue;
    }

    /*
    * Fun��o respons�vel por escrever um �nico byte no buffer
    *
    * @param position Posi��o do array
    * @param b byte que ser� escrito
    * @throws IndexOutOfBoundsException no caso de uma posi��o maior que o tamanho do array
    * @throws ReadOnlyBufferException no caso do buffer ser apenas para escrita
     */
    public void writeByte(int position, byte b) throws Exception {
        try {
            buffer.position(position);
            buffer.put(position, b);
        } catch (Exception e) {
            throw getExceptionMessage(e);
        }
    }

    /*
    * Fun��o respons�vel por ler um array de bytes do buffer a partir de 
    * uma posi��o inicial e da quantidade de bytes que ser�o lidos a partir
    * desta posi��o
    *
    * @param position Posi��o do array
    * @param length Quantidade de bytes que ser�o lidos a partir da posi��o incial
    * @return byteArray Retorna o array de bytes que armazena os bytes solicitados.
    * @throws BufferUnderflowException no caso de n�o conseguir ler a quantidade de bytes do buffer
    * @throws IndexOutOfBoundsException no caso de n�o conseguir acessar a posi��o no buffer
     */
    public byte[] readByteArray(int position, int length) throws Exception {
        byte[] byteArray = new byte[length + 1];
        int j = 0;
        for (int i = position; i < position + length; i++) {
            try {
                byteArray[j] = buffer.get(i);
            } catch (Exception e) {
                throw getExceptionMessage(e);
            }
            j++;
        }
        return byteArray;
    }

    /*
    * Fun��o respons�vel por escrever um array de bytes do buffer a partir de 
    * uma posi��o inicial e da quantidade de bytes que ser�o lidos a partir
    * desta posi��o
    *
    * @param position Posi��o do array
    * @param b[] Array de bytes fonte que ser� armazenado no buffer
    * @throws BufferUnderflowException no caso de n�o conseguir ler a quantidade de bytes do buffer
    * @throws IndexOutOfBoundsException no caso de n�o conseguir acessar a posi��o no buffer
    * @throws BufferOverflowException se n�o existe espa�o suficiente para armazenar o array de bytes
     */
    public void writeByteArray(int position, byte[] b) throws Exception {
        if ((b.length + position) > MAX_SIZE_BUFFER - 1) {
            throw new Exception(EXCEPTIONS.get("java.lang.BufferOverflowException"));
        }
        for (int i = 0; i < b.length; i++) {
            try {
                writeByte(i, b[i]);
            } catch(Exception e){
                throw getExceptionMessage(e);
            }
        }
    }
    
    /*
    * Fun��o respons�vel por ler 1 byte do buffer e retornar o byte correspondente
    * ao valor l�gico armazenado
    *
    * @param position Posi��o do array
    * @return booleanValue correspondente
    * @throws BufferUnderflowException no caso de n�o conseguir ler 4 bytes do buffer
     */
    public boolean readBoolean(int position) throws Exception {
        boolean booleanValue = true;
        byte byteValue = 0;
        try {
            byteValue = buffer.get(position);
            booleanValue = byteValue == 1;
        } catch(Exception e){
            throw getExceptionMessage(e);
        }
        return booleanValue;
    }
    
    /*
    * Fun��o respons�vel por escrever um �nico valor booleano no buffer
    *
    * @param position Posi��o do array
    * @param b boolean que ser� escrito
    * @throws IndexOutOfBoundsException no caso de uma posi��o maior que o tamanho do array
    * @throws ReadOnlyBufferException no caso do buffer ser apenas para escrita
     */
    public void writeBoolean(int position, boolean b) throws Exception {
        try {
            buffer.position(position);
            buffer.put(position, (byte)(b ? 1 : 0));
        } catch(Exception e){
            throw getExceptionMessage(e);
        }
    }
    
    /*
    * Fun��o repsons�vel por receber um array de bytes com os bytes na codifica��o
    * UTF-8 e realizar a convers�o da mesma em String
    * 
    * @params utf8ByteArray Array de bytes com os bytes da String em UTF-8
    * @return a String convertida
    */
    public String getStringFromByteArray(byte[] utf8ByteArray) {
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }

    /*
    *   Fun��o respons�vel por retornar a representa��o em array e bytes
    *   do buffer (ByteBuffer)
    *   @return byte[] data Array de bytes correspondente ao buffer
    */
    public byte[] data() {
        return buffer.array();
    }

    /*
    *   Fun��o respons�vel por receber um tipo de exce��o, por exemplo
    *   java.lang.IndexOutOfBoundsException e retornar a mensagem customizada
    *   correspondente no HashMap.
    *   Veja {@link #EXCEPTIONS}
    *
    *   @params exception e Exce��o capturada em algum bloco catch
    *   @return Exception Exce��o com a mensagem customizada
     */
    public Exception getExceptionMessage(Exception exception) {
        String typeException = exception.toString();
        return new Exception(EXCEPTIONS.get(typeException));
    }
}
