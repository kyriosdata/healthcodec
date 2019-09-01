package com.github.gabrielsxp.healthcodec;

/**
 *
 * @author Gabriel
 * 
 * Classe responsável por definir as operações de E/S sobre as classes
 * do Modelo de referência, como DvBoolean, DvIdentifier, etc.
 */
public class DerivativeOperations {
    
    /*
    * Instância de BufferOperations para a realização de operações
    * com os tipos primitivos
    */
    private final BufferOperations operations;
    
    /*
    * Construtor que encapsula um array de bytes vazio para utilizar
    * as operações de BufferOperations em um nível mais baixo.
    *
    * @param data array de bytes que será encapsulado em ByteBuffer
    */
    public DerivativeOperations(byte data[]) {
        operations = BufferOperations.serialize(data);
    }
    
    /*
    * Método responsável pela escrita dos metados de DvBoolean no buffer
    *
    * @param position Posição na qual os metadados serão escritos
    * @return offset posição após a escrita dos metadados
    */
    public int defineDvBoolean(int position) throws Exception {
        operations.writeByte(position, DvBoolean.META);
        return position + Constants.TYPE_BYTE_SIZE;
    }

    /*
    * Função responsável por escrever um DvBoolean no buffer
    *
    * @param position Posição do array onde os metadados serão escritos
    * @param value Valor lógico que será escrito após os metadados
     */
    public int setDvBoolean(int position, boolean value) throws Exception {
        operations.writeBoolean(position, value);
        return position + Constants.TYPE_BOOLEAN_SIZE;
    }
    
    /*
    * Método responsável pela escrita do cabeçalho de DvIdentifier no buffer
    *
    * @param position Posição na qual os metadados serão escritos
    * @param issuer String issuer
    * @param assigner String assigner
    * @param id String id
    * @param type String type
    * @return offset posição após a escrita do cabeçalho de DvIdentifier
    */
    public int defineDvIdentifierHeader(int position, String issuer, String assigner, String id, String type) throws Exception {
        byte[] DvIdentifierBytes;
        DvIdentifierBytes = DvIdentifier.getHeader(issuer, assigner, id, type);
        operations.writeByteArray(position, DvIdentifierBytes);

        return position + DvIdentifierBytes.length;
    }
    
    /*
    * Função responsável por escrever todos os dados de DvIdentifier no buffer
    *
    * @param position Posição do array onde os metadados serão escritos
    * @param issuer String issuer
    * @param assigner String assigner
    * @param id String id
    * @param type String type
    * @return offset posição final após a escrita de todos os dados de DvIdentifier
     */
    public int setDvIdentifier(int position, String issuer, String assigner, String id, String type) throws Exception {
        int issuerLength = issuer.length();
        int assignerLength = assigner.length();
        int idLength = id.length();
        int typeLength = type.length();
        
        int assignerPosition = issuerLength + position;
        int idPosition = assignerPosition + assignerLength;
        int typePosition = idPosition + idLength;
        
        operations.writeString(position, issuer);
        operations.writeString(assignerPosition, assigner);
        operations.writeString(idPosition, id);
        operations.writeString(typePosition, type);
        
        
        return position + 
                issuerLength +
                assignerLength +
                idLength +
                typeLength;
    }
    
    /*
    * Permite o acesso às operações presentes em BufferOperations para
    * que possam ser utilizadas na class Coordinator. Principalemente
    * para extrair o buffer para o deserializador e definir as operações
    * de obtenças dos tipos primitivos a partir d
    */
    public BufferOperations getPrimitiveOperations() {
        return this.operations;
    }
    
    //TODO definir os gets das classes baseadas nas entradas da matriz de controle do Coordinator
}
