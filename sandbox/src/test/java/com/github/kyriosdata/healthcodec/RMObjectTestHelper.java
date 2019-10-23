package com.github.kyriosdata.healthcodec;

import static com.github.kyriosdata.healthcodec.RMObject.*;

/**
 * Classe responsável por criar instâncias com parâmetros fixos que serão
 * utilizados na classe de teste RMObjectSerializationClient
 */
public class RMObjectTestHelper {

    /**
     * Cria uma instância de DvBoolean
     *
     * @param value
     * @return DvBoolean(true) se @param true, DvBoolean(false) se @param false
     */
    public static DvBoolean DvBoolean(boolean value){
       return RMObjectFactory.newDvBoolean(value);
    }

    /**
     * Cria uma instância de DvIdentifier com valores fixos
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DvIdentifier
     * @return instância de DvIdentifier
     */
    public static DvIdentifier DvIdentifier(boolean forceException){
        return RMObjectFactory.newDvIdentifier(forceException ? "" : "issuer",
                "assigner", "id", "type");
    }

    /**
     * Cria uma instância de UID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em UID
     * @return instância de UID
     */
    public static UID UID(boolean forceException){
        return RMObjectFactory.newUID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de InternetID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em InternetID
     * @return instância de InternetID
     */
    public static InternetID InternetID(boolean forceException){
        return RMObjectFactory.newInternetID(forceException ? "000" : "openehr");
    }

    /**
     * Cria uma instância de ISO_OID com valor fixo
     *
     * @return instância de ISO_OID
     */
    public static ISO_OID ISOOID(){
        return RMObjectFactory.newISOOID("value");
    }

    /**
     * Cria uma instância de UUID com valor fixo
     *
     * @return instância de UUID
     */
    public static UUID UUID(){
        return RMObjectFactory.newUUID("value");
    }

    /**
     * Cria uma instância de GenericID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em GenericID
     * @return instância de GenericID
     */
    public static GenericID GenericID(boolean forceException){
        return RMObjectFactory.newGenericID(
                "value", forceException ? "" : "scheme");
    }

    /**
     * Cria uma instância de TemplateID com valor fixo
     *
     * @return instância de TemplateID
     */
    public static TemplateID TemplateID(){
        return RMObjectFactory.newTemplateID("value");
    }

    /**
     * Cria uma instância de TerminologyID com valor fixo
     *
     * @return instância de TerminologyID
     */
    public static TerminologyID TerminologyID(){
        return RMObjectFactory.newTerminologyID("name", "version");
    }
}
