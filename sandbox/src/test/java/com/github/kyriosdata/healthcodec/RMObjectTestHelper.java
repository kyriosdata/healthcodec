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

    /**
     * Cria uma instância de CodePhrase com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em CodePhrase
     * @return instância de CodePhrase
     */
    public static CodePhrase CodePhrase(boolean forceException){
        TerminologyID terminologyID = RMObjectTestHelper.TerminologyID();
        return RMObjectFactory.newCodePhrase(
                forceException ? null : terminologyID, "codeString");
    }

    /**
     * Cria uma instância de DVURI com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DVURI
     * @return instância de DVURI
     */
    public static DVURI DVURI(boolean forceException){
        return RMObjectFactory.newDVURI(forceException ? null : "value" );
    }

    /**
     * Cria uma instância de DVEHRURI com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em DVEHRURI
     * @return instância de DVEHRURI
     */
    public static DVEHRURI DVEHRURI(boolean forceException){
        return RMObjectFactory.newDVEHRURI(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de VersionTreeID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em VersionTreeID
     * @return instância de VersionTreeID
     */
    public static VersionTreeID VersionTreeID(boolean forceException){
        return RMObjectFactory.newVersionTreeID(forceException ? "" : "value");
    }

    /**
     * Cria uma instância de ArchetypeID com valor fixo
     *
     * @param forceException flag que aciona uma IllegalArgumentException de
     *                       variável null ou vazia em ArchetypeID
     * @return instância de ArchetypeID
     */
    public static ArchetypeID ArchetypeID(boolean forceException){
        return RMObjectFactory.newArchetypeID(forceException ? "" : "value");
    }
}
