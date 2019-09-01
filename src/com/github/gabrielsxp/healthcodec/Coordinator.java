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
public class Coordinator {

    /*
    * Instância responsável por escrever todos os tipos do modelo de referência
    * no buffer
     */
    private final DerivativeOperations operations;
    
    private final static int MATRIX_DIMENSION_ROW = 50;
    private final static int MATRIX_DIMENSION_COLUMN = 3;

    /*
    * Matriz que controla a posição na qual os cabeçalhos, dados e informações 
    * pertinentes a classes que permitem sobrecarga de métodos são armazenados
     */
    private final int[][] controlMatrix = new int[MATRIX_DIMENSION_ROW][MATRIX_DIMENSION_COLUMN];
    
    /*
    * Posição atual que é utilizada para leitura e escrita de dados no buffer
    */
    private int offset;
    
    /*
    * Construtor do serializador
    * 
    * Recebe um array de bytes como entrada e o encapsula para que a classe
    * DerivativeOperations, que possui métodos para definição e escrita dos
    * dados presentes nas classes do Modelo Relacional.
    * 
    * Inicializa ainda os dados da matriz de controle. Sendo possível definir
    * erros a partir dos dados iniciais.
    *
    * @param data array de bytes vazia que será utilizada para as escritas
    */
    public Coordinator(byte[] data) {
        this.operations = new DerivativeOperations(data);
        this.offset = 0;

        controlMatrix[Constants.CLASS_DVBOOLEAN][Constants.HEADER_POSITION] = -1;
        controlMatrix[Constants.CLASS_DVIDENTIFIER][Constants.DATA_POSITION] = -1;
        controlMatrix[Constants.CLASS_DVIDENTIFIER][Constants.META_INFO] = -1;
    }
    
    /*
    * Contrutor do deserializador
    *
    * Recebe o array de btyes proveniente da instância de Coordinator
    * responsável pela serialização dos dados e a matriz de controle
    * do mesmo após as sucessivas escritas no buffer.
    *
    * @param data array de bytes proveniente do serializador
    * @param matrix matriz de controle do serializador
    */
    public Coordinator(byte data[], int[][] matrix){
        this.operations = new DerivativeOperations(data);
        
        for(int i = 0; i < MATRIX_DIMENSION_ROW; i++){
            for(int j = 0; j < MATRIX_DIMENSION_COLUMN; j++){
                setControlMatrix(i,j, controlMatrix[i][j]);
            }
        }
    }
    
    /*
    * O tipo DvBoolean possui um atributo boolean value
    * Esse método é responsável por definir o metadado do tipo
    * e o dado lógico em bytes
    *
    * @param value o valor lógico que será serializado
    */
    public void insertDvBoolean(boolean value) throws Exception {
        setControlMatrixHeaderPosition(Constants.CLASS_DVBOOLEAN, offset);
        int dataPosition = operations.defineDvBoolean(offset);
        setControlMatrixDataPosition(Constants.CLASS_DVIDENTIFIER, dataPosition);

        operations.setDvBoolean(dataPosition, value);
        setOffset(dataPosition + Constants.TYPE_BOOLEAN_SIZE);
    }
    
    
    /*
    * Método responsável por consultar a matriz de controle e retornar a primeira
    * posição do metadado do DvBoolean que foi armazenado
    *
    * @return position posição de início da escrita do metadado de DvBoolean
    */
    public int getDvBoolean() {
        return controlMatrix[Constants.TYPE_BOOLEAN_ID][Constants.HEADER_POSITION];
    }
    
    /*
    * A classe DvIdentifier possui 4 Strings que são obrigatórias (issuer, assigner, id, type);
    * Esse método é responsável por serializar primeiramente o cabeçalho de definição e
    * cada umas das strings sucessivamente.
    *
    * @param issuer String issuer
    * @param assigner String assigner
    * @param id String id
    * @param type String type
    */
    public void insertDvIdentifier(String issuer, String assigner, String id, String type) throws Exception {
        setControlMatrixHeaderPosition(Constants.CLASS_DVIDENTIFIER, offset);
        int dataPosition = operations.defineDvIdentifierHeader(offset, issuer, assigner, id, type);
        System.out.println(dataPosition);

        setControlMatrixDataPosition(Constants.CLASS_DVIDENTIFIER, dataPosition);
        setOffset(operations.setDvIdentifier(dataPosition, issuer, assigner, id, type));
    }
    
    /*
    * Método responsável por retornar a posição do início do cabeçalho 
    * de DvIdentifier que foi armazenado via {@link #insertDvIdentifier(String, String, String, String)}
    *
    * @return position posição de início da escrita do cabeçalho de DvIdentifier
    */
    public int getDvIdentifier() {
        return getControlMatrixHeaderPosition(Constants.CLASS_DVIDENTIFIER);
    }
    
    
    /*
    * Retorna um boolean após a leitura do byte do buffer da classe BufferOperations
    * 
    * @param position posição de leitura do byte
    * @return value valor lógico obtido a partir do byte recebido como parâmetro
    */
    public boolean getBoolean(int position) throws Exception {
        return operations.getPrimitiveOperations().readBoolean(position);
    }
    
    /*
    * Retorna um inteiro após a leitura dos 4 bytes do buffer da classe BufferOperations
    * 
    * @param position posição de leitura do byte
    * @return value valor inteiro obtido a partir dos bytes recebidos como parâmetro
    */
    public int getInt(int position) throws Exception {
        return operations.getPrimitiveOperations().readInteger(position);
    }
    
    /*
    * Retorna uma String (UTF-8) após a leitura dos bytes do buffer da classe BufferOperations
    * 
    * @param position posição de leitura do byte
    * @param length tamanho da String que está armazenada no buffer
    * @return string string obtida a partir dos bytes recebidos como parâmetro e o tamanho da string
    */
    public String getString(int position, int length) throws Exception {
        return operations.getPrimitiveOperations().readString(position, length);
    }
    
    /*
    * Retorna o buffer extraído de ByteBuffer em BufferOperations.
    * Esse buffer é utilizado como parâmetro em {@link #Coordinator(byte[], int[][])}
    
    * @return buffer buffer que será utilizado em {@link #Coordinator(byte[], int[][])}
    *                para deserialização dos dados
    */
    public byte[] getBuffer() {
        return operations.getPrimitiveOperations().data();
    }
    
    /*
    * Definie para a classe passada como parâmetro, ex: 0 -> DvBoolean,
    * qual a posição que o cabeçalho / metadados serão armazenados
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @param headerPosition Posição efetiva na qual o cabeçalho / metadado foi armazenado
    */
    public void setControlMatrixHeaderPosition(int classId, int headerPosition) {
        controlMatrix[classId][Constants.HEADER_POSITION] = headerPosition;
    }
    
    /*
    * Retorna a posição na qual o cabeçalho / metadado uma determinada classe
    * foi armazenado.
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @return position Posição do cabeçalho / metadados da classe
    */
    public int getControlMatrixHeaderPosition(int classId) {
        return controlMatrix[classId][Constants.HEADER_POSITION];
    }
    
    /*
    * Definie para a classe passada como parâmetro, ex: 0 -> DvBoolean,
    * qual a posição que os dados serão armazenados
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @param headerPosition Posição efetiva na qual os dados foram armazenado
    */
    public void setControlMatrixDataPosition(int classId, int dataPosition) {
        controlMatrix[classId][Constants.DATA_POSITION] = dataPosition;
    }
    
    /*
    * Retorna a posição na qual os dados de uma determinada classe
    * foram armazenados.
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @return position Posição dos dados da classe
    */
    public int getControlMatrixDataPosition(int classId) {
        return controlMatrix[classId][Constants.DATA_POSITION];
    }
    
    /*
    * Definie para a classe passada como parâmetro, ex: 0 -> DvBoolean,
    * qual a posição que os dados relativos aos metadados da classe 
    * serão armazenados
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @param headerPosition Posição efetiva na qual as informações dos metadados
    * foram armazenados
    */
    public void setControlMatrixMetaInfoPosition(int classId, int metaInfo) {
        controlMatrix[classId][Constants.META_INFO] = metaInfo;
    }
    
    /*
    * Retorna a posição na qual as informações de metadados de uma 
    * determinada classe foram armazenados
    *
    * @param classId Identificador único da classe, definido na classe Constants
    * @return position Posição das informações relativas aos metadados
    */
    public int getControlMatrixMetaInfoPosition(int classId) {
        return controlMatrix[classId][Constants.META_INFO];
    }
    
    /*
    * Método responsável pela definição interna da matriz de controle que
    * é utilizada para a serialização
    *
    * @param i posição da linha da matriz
    * @param j posição da coluna da matriz
    * @param value valor que será armazenado em M[i][j]
    */
    private void setControlMatrix(int i, int j, int value){
        controlMatrix[i][j] = value;
    }
    
    /*
    * Retorna a matriz de controle do serializador com os dados preenchidos
    * 
    * @return controlMatrix matriz de controle com os dados preenchidos
    */
    public int[][] getControlMatrix(){
        return controlMatrix;
    }
    
    /*
    * Retorna a posição atual do ponteiro utilizado para E/S dos dados do buffer
    *
    * @return offset posição do ponteiro
    */
    public int getOffset() {
        return offset;
    }
    
    /*
    * Método responsável por definir a posição do ponteiro que é utilizado para
    * a E/S de dados do buffer
    * 
    * @param offset valor que será definido para o ponteiro
    */
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
