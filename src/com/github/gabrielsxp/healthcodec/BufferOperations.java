/*
 * Gabriel Soares Costa - 2019
 * Projeto Final de Curso - Universidade Federal de Goiás
 */
package com.github.gabrielsxp.healthcodec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gabriel Descrição: Classe Responsável por primeiramente encapsular o
 * buffer de dados e fornecer as operações de E/S sobre o mesmo
 */
public class BufferOperations {

    /*
    * Estrutura com as possíveis exceções e as correspondentes mensagens de
    * aviso sobre as mesmas
     */
    static Map<String, String> EXCEPTIONS = new HashMap<String, String>() {
        {
            put("java.lang.IndexOutOfBoundsException", "Não foi possível realizar a leitura da quantidade necessária de bytes a partir deste offset");
            put("java.lang.IllegalArgumentException", "Tentativa de definir a posição do buffer com uma nova posição que ultrapassa o limite do mesmo ou com um valor negativo");
            put("java.lang.ReadOnlyBufferException ", "O buffer está definido como apenas-leitura");
            put("java.lang.BufferOverflowException", "Não existe espaço suficiente no buffer para realizar a escrita dos dados presentes no array de bytes");
        }
    };

    //Tamanho máximo assumido pelo buffer que armazenará os dados serializados
    static final int MAX_SIZE_BUFFER = 256;

    /**
     * Buffer que será utilizado para armazenar os headers e os dados passados como
     * argumento na função serialize. Veja {@link #serialize(byte[])} 
     *
    */
    private ByteBuffer buffer;

    //Impede a criação desnecessária da instância
    private BufferOperations() {

    }

    /*
    * Função responsável por encapsular um array de bytes 
    * recebido com entrada em um ByteBuffer que será armazenado
    * no atributo da classe
    *
    * @param data array de bytes que é recebido como entrada
    *             contendo os headers (metadados e informações importantes
    *             como o tamanho de uma string, por exemplo) e os conteúdos
    *             associado a cada header presente no array
    * @return Retorna uma instância com o buffer encapsulado e pronto para
    *         as operações de E/S
     */
    public static BufferOperations serialize(byte[] data) {

        BufferOperations bf = new BufferOperations();

        bf.buffer = ByteBuffer.allocate(MAX_SIZE_BUFFER);

        bf.buffer.put(data);

        return bf;
    }

    /*
    * Função responsável por encapsular um array de bytes 
    * recebido com entrada em um ByteBuffer que será armazenado
    * no atributo da classe
    *
    * @param data array de bytes que é recebido como entrada
    *             contendo os headers (metadados e informações importantes
    *             como o tamanho de uma string, por exemplo) e os conteúdos
    *             associado a cada header presente no array
    * @return Retorna uma instância com o buffer encapsulado e pronto para
    *         as operações de E/S
     */
    public static BufferOperations deserialize(byte[] data) {
        BufferOperations bf = new BufferOperations();
        bf.buffer = ByteBuffer.wrap(data);

        return bf;
    }

    /*
    * Função responsável por ler 4 bytes do buffer e retornar o número
    * inteiro que corresponde a sequência de bytes lida.
    *
    * @param position Posição do array
    * @return intValue Valor inteiro correspondente aos 4 btyes lidos na operação
    * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do buffer
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
    * Função responsável por escrever um número inteiro (4 bytes) no buffer
    *
    * @param position Posição do array
    * @param i Número inteiro que será escrito
    * @throws IndexOutOfBoundsException no caso de uma posição maior que o tamanho do array
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
    * Função responsável por ler 1 byte do buffer e retorná-lo
    *
    * @param position Posição do array
    * @return byteValue correspondente
    * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do buffer
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
    * Função responsável por escrever um único byte no buffer
    *
    * @param position Posição do array
    * @param b byte que será escrito
    * @throws IndexOutOfBoundsException no caso de uma posição maior que o tamanho do array
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
    * Função responsável por ler um array de bytes do buffer a partir de 
    * uma posição inicial e da quantidade de bytes que serão lidos a partir
    * desta posição
    *
    * @param position Posição do array
    * @param length Quantidade de bytes que serão lidos a partir da posição incial
    * @return byteArray Retorna o array de bytes que armazena os bytes solicitados.
    * @throws BufferUnderflowException no caso de não conseguir ler a quantidade de bytes do buffer
    * @throws IndexOutOfBoundsException no caso de não conseguir acessar a posição no buffer
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
    * Função responsável por escrever um array de bytes do buffer a partir de 
    * uma posição inicial e da quantidade de bytes que serão lidos a partir
    * desta posição
    *
    * @param position Posição do array
    * @param b[] Array de bytes fonte que será armazenado no buffer
    * @throws BufferUnderflowException no caso de não conseguir ler a quantidade de bytes do buffer
    * @throws IndexOutOfBoundsException no caso de não conseguir acessar a posição no buffer
    * @throws BufferOverflowException se não existe espaço suficiente para armazenar o array de bytes
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
    * Função responsável por ler 1 byte do buffer e retornar o byte correspondente
    * ao valor lógico armazenado
    *
    * @param position Posição do array
    * @return booleanValue correspondente
    * @throws BufferUnderflowException no caso de não conseguir ler 4 bytes do buffer
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
    * Função responsável por escrever um único valor booleano no buffer
    *
    * @param position Posição do array
    * @param b boolean que será escrito
    * @throws IndexOutOfBoundsException no caso de uma posição maior que o tamanho do array
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
    * Função repsonsável por receber um array de bytes com os bytes na codificação
    * UTF-8 e realizar a conversão da mesma em String
    * 
    * @params utf8ByteArray Array de bytes com os bytes da String em UTF-8
    * @return a String convertida
    */
    public String getStringFromByteArray(byte[] utf8ByteArray) {
        return new String(utf8ByteArray, StandardCharsets.UTF_8);
    }

    /*
    *   Função responsável por retornar a representação em array e bytes
    *   do buffer (ByteBuffer)
    *   @return byte[] data Array de bytes correspondente ao buffer
    */
    public byte[] data() {
        return buffer.array();
    }

    /*
    *   Função responsável por receber um tipo de exceção, por exemplo
    *   java.lang.IndexOutOfBoundsException e retornar a mensagem customizada
    *   correspondente no HashMap.
    *   Veja {@link #EXCEPTIONS}
    *
    *   @params exception e Exceção capturada em algum bloco catch
    *   @return Exception Exceção com a mensagem customizada
     */
    public Exception getExceptionMessage(Exception exception) {
        String typeException = exception.toString();
        return new Exception(EXCEPTIONS.get(typeException));
    }
}
