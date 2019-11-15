package com.github.kyriosdata.healthcodec;

import java.util.*;

import static com.github.kyriosdata.healthcodec.RMObject.*;

/**
 * Classe responsável por criar instâncias com parâmetros fixos que serão
 * utilizados na classe de teste RMObjectSerializationClient.
 *
 */
public class RMObjectTestHelper {

    /**
     * Cria uma instância de DvBoolean com valores fixos.
     *
     * @param value
     * @return DvBoolean(true) se @param true, DvBoolean(false) se @param false
     */
    public static DvBoolean dvBoolean(boolean value){
        return RMObjectFactory.newDvBoolean(value);
    }

    /**
     * Cria uma instância de DvIdentifier com valores fixos.
     *
     * @return instância de DvIdentifier
     */
    public static DvIdentifier dvIdentifier(){
        return RMObjectFactory.newDvIdentifier("issuer",
                "assigner", "id", "type");
    }

    /**
     * Cria uma instância de UID com valor fixo.
     *
     * @return instância de UID
     */
    public static UID uID(){
        return RMObjectFactory.newUID("value");
    }

    /**
     * Cria uma instância de InternetID com valor fixo.
     *
     * @return instância de InternetID
     */
    public static InternetID internetID(){
        return RMObjectFactory.newInternetID("openehr");
    }

    /**
     * Cria uma instância de ISO_OID com valor fixo.
     *
     * @return instância de ISO_OID
     */
    public static ISO_OID isooid(){
        return RMObjectFactory.newISOOID("value");
    }

    /**
     * Cria uma instância de UUID com valor fixo.
     *
     * @return instância de UUID
     */
    public static RMObject.UUID uUID(){
        return RMObjectFactory.newUUID("value");
    }

    /**
     * Cria uma instância de GenericID com valor fixo.
     *
     * @return instância de GenericID
     */
    public static GenericID genericID(){
        return RMObjectFactory.newGenericID("value","scheme");
    }

    /**
     * Cria uma instância de TemplateID com valor fixo.
     *
     * @return instância de TemplateID
     */
    public static TemplateID templateID(){
        return RMObjectFactory.newTemplateID("value");
    }

    /**
     * Cria uma instância de TerminologyID com valor fixo.
     *
     * @return instância de TerminologyID
     */
    public static TerminologyID terminologyID(){
        return RMObjectFactory.newTerminologyID("name", "version");
    }

    /**
     * Cria uma instância de CodePhrase com valor fixo.
     *
     * @return instância de CodePhrase
     */
    public static CodePhrase codePhrase(){
        TerminologyID terminologyID = RMObjectTestHelper.terminologyID();
        return RMObjectFactory.newCodePhrase(terminologyID,
                "codeString");
    }

    /**
     * Cria uma instância de DVURI com valor fixo.
     *
     * @return instância de DVURI
     */
    public static DVURI dVURI(){
        return RMObjectFactory.newDVURI("value" );
    }

    /**
     * Cria uma instância de DVEHRURI com valor fixo.
     *
     * @return instância de DVEHRURI
     */
    public static DVEHRURI dVEHRURI(){
        return RMObjectFactory.newDVEHRURI("value");
    }

    /**
     * Cria uma instância de VersionTreeID com valor fixo.
     *
     * @return instância de VersionTreeID
     */
    public static VersionTreeID versionTreeID(){
        return RMObjectFactory.newVersionTreeID("value");
    }

    /**
     * Cria uma instância de ArchetypeID com valor fixo.
     *
     * @return instância de ArchetypeID
     */
    public static ArchetypeID archetypeID(){
        return RMObjectFactory.newArchetypeID("value");
    }

    /**
     * Cria uma instância de ObjectVersionID com valor fixo
     *
     * @return instância de ObjectVersionID
     */
    public static ObjectVersionID objectVersionID(){
        return RMObjectFactory.newObjectVersionID("value");
    }

    /**
     * Cria uma instância de HierObjectID com valor fixo.
     *
     * @return instância de HierObjectID
     */
    public static HierObjectID hierObjectID(){
        return RMObjectFactory.newHierObjectID("value");
    }

    /**
     * Cria uma instância de ObjectID com valor fixo.
     *
     * @return instância de ObjectID
     */
    public static ObjectID objectID(){
        return RMObjectFactory.newObjectID("value");
    }

    /**
     * Cria uma instância de PartyRef com valor fixo.
     *
     * @return instância de PartyRef
     */
    public static PartyRef partyRef(){
        return RMObjectFactory.newPartyRef(
                RMObjectTestHelper.objectID(),"type");
    }

    /**
     * Cria uma instância de ObjectRef com valor fixo.
     *
     * @return instância de ObjectRef
     */
    public static ObjectRef objectRef(){
        return RMObjectFactory.newObjectRef(RMObjectTestHelper.objectID(),
                "namespace",
                "type");
    }

    /**
     * Cria uma instância de LocatableRef com valor fixo.
     *
     * @return instância de LocatableRef
     */
    public static LocatableRef locatableRef() {
        return RMObjectFactory.newLocatableRef(
                RMObjectTestHelper.objectVersionID(),
                "namespace", "type", "path");
    }

    /**
     * Cria uma instância de ProportionKind com valor fixo.
     *
     * @return instância de ProportionKind
     */
    public static ProportionKind proportionKind(){
        return RMObjectFactory.newPropotionKind(1);
    }

    /**
     * Cria uma instância de AccessGroupRef com valor fixo.
     *
     * @return instância de LocatableRef
     */
    public static AccessGroupRef accessGroupRef(){
        return RMObjectFactory.newAccessGroupRef(
                RMObjectFactory.newObjectID("value"));
    }



    /**
     * Cria uma instância de PartyIdentified com valor fixo.
     *
     *
     * @return instância de PartyIdentified
     */
    public static PartyIdentified partyIdentified(){
        return RMObjectFactory.newPartyIdentified(
                RMObjectFactory.newPartyRef(
                        RMObjectFactory.newObjectID("value"),
                        "type"), "name",
                dvIdentifierList(false));
    }

    /**
     * Cria uma instância de Archetyped com valor fixo
     *
     * @return instância de Archetyped
     */
    public static Archetyped archetyped(){
        return RMObjectFactory.newArchetyped(
                RMObjectFactory.newArchetypeID("value"),
                RMObjectTestHelper.templateID(), "rmVersion");
    }

    /**
     * Cria uma instância de UIDBasedID com valor fixo
     *
     * @return instância de UIDBasedID
     */
    public static UIDBasedID uIDBasedID(){
        return RMObjectFactory.newUIDBasedID("value" );
    }

    /**
     * Cria uma instância de DvEncapsulated com valor fixo
     *
     * @return instância de DvEncapsulated
     */
    public static DvEncapsulated dvEncapsulated(){
        return RMObjectFactory.newDvEncapsulated(
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());
    }

    /**
     * Cria uma instância de DvParsable com valor fixo
     *
     * @return instância de DvParsable
     */
    public static DvParsable dvParsable(){
        return RMObjectFactory.newDvParsable(
                RMObjectTestHelper.dvEncapsulated(),"value",
                "formalism");
    }

    /**
     * Cria uma instância de DvTimeSpecification com valor fixo
     *
     * @return instância de DvTimeSpecification
     */
    public static DvTimeSpecification dvTimeSpecification(){
        return RMObjectFactory.newDvTimeSpecification(
                RMObjectTestHelper.dvParsable());
    }

    public static DvMultimedia dvMultimedia(){
        DvEncapsulated de = RMObjectTestHelper.dvEncapsulated();
        String alternateText = "alternateText";
        CodePhrase mediaType = RMObjectTestHelper.codePhrase();
        CodePhrase compressionAlgorithm =
                RMObjectTestHelper.codePhrase();
        byte[] integrityCheck = {0,1,0,1};
        CodePhrase integrityCheckAlgorithm =
                RMObjectTestHelper.codePhrase();
        DVURI du = RMObjectTestHelper.dVURI();
        byte[] data = {1,0,1,0};

        return RMObjectFactory.newDvMultimedia(
                de,alternateText,mediaType,compressionAlgorithm,integrityCheck,
                integrityCheckAlgorithm,null, du, data);
    }

    /**
     * Cria uma instância de DvTextTest com valor fixo
     *
     * @return instância de DvTextTest
     */
    public static DvText dvText(){
        String value = "value";
        List<TermMapping> mappings =
                RMObjectTestHelper.termMappingList(false);
        String formatting = "formatting";
        DVURI hyperlink = RMObjectTestHelper.dVURI();
        CodePhrase language = RMObjectTestHelper.codePhrase();
        CodePhrase charset = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvText(value, mappings, formatting,
                hyperlink, language, charset);
    }

    /**
     * Cria uma instância de TermMapping com valor fixo.
     *
     * @return instância de TermMapping
     */
    public static TermMapping termMapping(){
        return RMObjectFactory.newTermMapping(
                RMObjectTestHelper.codePhrase(),
                Match.BROADER,
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de DvCodedText com valor fixo.
     *
     * @return instância de DvCodedText
     */
    public static DvCodedText dvCodedText(){
        CodePhrase cp = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvCodedText(
                RMObjectTestHelper.dvText(), cp);

    }

    /**
     * Cria uma instância de Link com valor fixo.
     *
     * @return nova instância de Link
     */
    public static Link link(){
        return RMObjectFactory.newLink(RMObjectTestHelper.dvText(),
                RMObjectTestHelper.dvText(),RMObjectTestHelper.dVEHRURI());
    }

    /**
     * Cria uma instância de DvState com valor fixo.
     *
     * @return instância de DvState
     */
    public static DvState dvState(){
        return RMObjectFactory.newDvState(RMObjectTestHelper.dvCodedText(),
                "terminal");
    }

    /**
     * Cria uma instância de DvParagraphTest com valor fixo.
     *
     * @return nova instância de DvParagraphTest
     */
    public static DvParagraph dvParagraph(){
        return RMObjectFactory.newDvParagraph(
                RMObjectTestHelper.dvTextList(false));
    }

    /**
     * Cria uma instância de PartyProxy com valor fixo.
     *
     * @return nova instância de PartyProxy
     */
    public static PartyProxy partyProxy(){
        return RMObjectFactory.newPartyProxy(RMObjectTestHelper.partyRef());
    }

    /**
     * Cria uma instância de FeederAuditDetails com valor fixo
     *
     * @return nova instância de FeederAuditDetails
     */
    public static FeederAuditDetails feederAuditDetails(){
        String systemID = "systemID";
        PartyIdentified provider = RMObjectTestHelper.partyIdentified();
        PartyIdentified location = RMObjectTestHelper.partyIdentified();
        PartyProxy subject = RMObjectTestHelper.partyProxy();
        String versionID = "versionID";

        return RMObjectFactory.newFeederAuditDetails(systemID, provider,
                location, subject, versionID);
    }

    /**
     * Cria uma instância de FeederAudit com valor fixo
     *
     * @return nova instância de FeederAudit
     */
    public static FeederAudit feederAudit(){
        FeederAuditDetails originatingSystemAudit =
                RMObjectTestHelper.feederAuditDetails();
        List<DvIdentifier> originatingSystemItemIDs =
                RMObjectTestHelper.dvIdentifierList(false);
        FeederAuditDetails feederSystemAudit =
                RMObjectTestHelper.feederAuditDetails();

        List<DvIdentifier> feederSystemItemIDs =
                RMObjectTestHelper.dvIdentifierList(false);

        DvEncapsulated originalContent = RMObjectTestHelper.dvEncapsulated();

        return RMObjectFactory.newFeederAudit(originatingSystemAudit,
                originatingSystemItemIDs, feederSystemAudit,
                feederSystemItemIDs, originalContent);
    }

    /**
     * Cria uma instância de Locatable com valor fixo.
     *
     * @return nova instância de Locatable
     */
    public static Locatable locatable(){
        UIDBasedID uid = RMObjectTestHelper.uIDBasedID();
        String archetypeNodeId = "archetypeNodeId";
        DvText name = RMObjectTestHelper.dvText();
        Archetyped archetypeDetails = RMObjectTestHelper.archetyped();
        FeederAudit feederAudit = RMObjectTestHelper.feederAudit();
        Set<Link> links = RMObjectTestHelper.linkSet(false);

        return RMObjectFactory.newLocatable(uid, archetypeNodeId, name,
                archetypeDetails, feederAudit, links);
    }

    /**
     * Cria uma instância de PartyRelated com valor fixo
     *
     * @return nova instância de PartyRelated
     */
    public static PartyRelated partyRelated(){
        PartyIdentified pi = RMObjectTestHelper.partyIdentified();
        DvCodedText relationship = RMObjectTestHelper.dvCodedText();

        return RMObjectFactory.newPartyRelated(pi, relationship);
    }

    /**
     * Cria uma instância de PartySelf com valor fixo
     *
     * @return nova instância de PartySelf
     */
    public static PartySelf partySelf(){
        return RMObjectFactory.newPartySelf(RMObjectTestHelper.partyRef());
    }

    /**
     * Cria uma instância de ResourceDescriptionItem com valor fixo
     *
     * @return nova instância de ResourceDescriptionItem
     */
    public static ResourceDescriptionItem resourceDescriptionItem(){
        CodePhrase language =
                RMObjectTestHelper.codePhrase();
        String purpose = "purpose";
        List<String> keywords = RMObjectTestHelper.stringList(false);
        String use = "use";
        String misuse = "misuse";
        String copyright = "copyright";
        Map<String, String> originalResourceUri =
                RMObjectTestHelper.stringStringMap(false);
        Map<String, String> otherDetails =
                RMObjectTestHelper.stringStringMap(false);

        return RMObjectFactory.newResourceDescriptionItem(language,purpose,
                keywords,use,misuse,copyright,originalResourceUri,
                otherDetails);
    }

    /**
     * Cria uma instância de TranslationDetails com valor fixo
     *
     * @return nova instância de TranslationDetails
     */
    public static TranslationDetails translationDetails(){
        CodePhrase language = RMObjectTestHelper.codePhrase();
        Map<String, String> author = RMObjectTestHelper.stringStringMap(
                false);
        String accreditation = "accreditation";
        Map<String, String> otherDetails =RMObjectTestHelper.stringStringMap(
                false);

        return RMObjectFactory.newTranslationDetails(language,author,
                accreditation, otherDetails);
    }

    /**
     * Cria uma instância de Item com valor fixo
     *
     * @return nova instância de Item
     */
    public static Item item(){
        return RMObjectFactory.newItem(RMObjectTestHelper.locatable());
    }

    /**
     * Cria uma instância de Cluster com valor fixo
     *
     * @return nova instância de Cluster
     */
    public static Cluster cluster(){
        return RMObjectFactory.newCluster(
                RMObjectTestHelper.item(),
                RMObjectTestHelper.itemList(false));
    }

    /**
     * Cria uma instância de Element com valor fixo
     *
     * @return nova instância de Element
     */
    public static Element element(){
        return RMObjectFactory.newElement(
                RMObjectTestHelper.item(),
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de DataStructure com valor fixo
     *
     * @return nova instância de DataStructure
     */
    public static DataStructure dataStructure(){
        return RMObjectFactory.newDataStructure(
                RMObjectTestHelper.locatable());
    }

    /**
     * Cria uma instância de ItemListTest com valor fixo
     *
     * @return nova instância de ItemListTest
     */
    public static ItemList itemList(){
        return RMObjectFactory.newItemList(
                RMObjectTestHelper.uIDBasedID(),
                "archetypeNodeId",
                RMObjectTestHelper.dvText(),
                RMObjectTestHelper.archetyped(),
                RMObjectTestHelper.feederAudit(),
                RMObjectTestHelper.linkSet(false),
                RMObjectTestHelper.elementList(false));
    }

    /**
     * Cria uma instância de ItemStructure com valor fixo
     *
     * @return nova instância de ItemStructure
     */
    public static ItemStructure itemStructure(){
        return RMObjectFactory.newItemStructure(
                RMObjectTestHelper.dataStructure());
    }

    /**
     * Cria uma instância de ItemSingle com valor fixo
     *
     * @return nova instância de ItemSingle
     */
    public static ItemSingle itemSingle(){
        return RMObjectFactory.newItemSingle(RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.element());
    }

    /**
     * Cria uma instância de ItemTable com valor fixo
     *
     * @return nova instância de ItemTable
     */
    public static ItemTable itemTable(){
        return RMObjectFactory.newItemTable(
                RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.clusterList(false));
    }

    /**
     * Cria uma instância de ItemTree com valor fixo
     *
     * @return nova instância de ItemTree
     */
    public static ItemTree itemTree(){
        return RMObjectFactory.newItemTree(
                RMObjectTestHelper.itemStructure(),
                RMObjectTestHelper.itemList(false));
    }

    /**
     * Cria uma instância de PartyIdentity com valor fixo
     *
     * @return nova instância de PartyIdentity
     */
    public static PartyIdentity partyIdentity(){
        return RMObjectFactory.newPartyIdentity(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de PartyRelationship com valor fixo
     *
     * @return nova instância de PartyRelationship
     */
    public static PartyRelationship partyRelationship(){
        Locatable locatable = RMObjectTestHelper.locatable();
        ItemStructure details = RMObjectTestHelper.itemStructure();
        ObjectRef source = RMObjectTestHelper.objectRef();
        ObjectRef target = RMObjectTestHelper.objectRef();

        return RMObjectFactory.newPartyRelationship(locatable, details,
                source, target);
    }

    /**
     * Cria uma instância de Address com valor fixo
     *
     * @return nova instância de Address
     */
    public static Address address(){
        return RMObjectFactory.newAddress(RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de Contact com valor fixo
     *
     * @return nova instância de Contact
     */
    public static Contact contact(){
        return RMObjectFactory.newContact(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.addressList(false));
    }

    /**
     * Cria uma instância de Party com valor fixo
     *
     * @return nova instância de Party
     */
    public static Party party(boolean legalIdentity){
        Locatable locatable = RMObjectTestHelper.locatable();

        Set<PartyIdentity> identities = RMObjectTestHelper.partyIdentitySet(
                false, legalIdentity);

        Set<Contact> contacts = RMObjectTestHelper.contactSet(false);

        Set<PartyRelationship> relationships = RMObjectTestHelper.
                partyRelationshipSet(false);

        Set<LocatableRef> reverseRelationships =
                RMObjectTestHelper.locatableRefSet(false);

        ItemStructure details = RMObjectTestHelper.itemStructure();

        return RMObjectFactory.newParty(locatable, identities, contacts,
                relationships, reverseRelationships, details);
    }

    /**
     * Cria uma instância de Capability com valor fixo
     *
     * @return nova instância de Capability
     */
    public static Capability capability(){
        return RMObjectFactory.newCapability(
                RMObjectTestHelper.locatable(),
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de Role com valor fixo
     *
     * @return nova instância de Role
     */
    public static Role role(){
        Party party = RMObjectTestHelper.party(false);

        List<Capability> capabilities = RMObjectTestHelper.
                capabilityList(false);

        PartyRef performer = RMObjectTestHelper.partyRef();

        return RMObjectFactory.newRole(party, capabilities, performer);
    }

    /**
     * Cria uma instância de Actor com valor fixo
     *
     * @return nova instância de Actor
     */
    public static Actor actor(){
        Party party = RMObjectTestHelper.party(true);

        return RMObjectFactory.newActor(party,
                RMObjectTestHelper.roleSet(false),
                RMObjectTestHelper.dvTextSet(false));
    }

    /**
     * Cria uma instância de Agent com valor fixo
     *
     * @return nova instância de Agent
     */
    public static Agent agent(){
        return RMObjectFactory.newAgent(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Group com valor fixo
     *
     * @return nova instância de Group
     */
    public static Group group(){
        return RMObjectFactory.newGroup(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Organisation com valor fixo
     *
     * @return nova instância de Organisation
     */
    public static Organisation organisation(){
        return RMObjectFactory.newOrganisation(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de Person com valor fixo
     *
     * @return nova instância de Person
     */
    public static Person person(){
        return RMObjectFactory.newPerson(RMObjectTestHelper.actor());
    }

    /**
     * Cria uma instância de InstructionDetails com valor fixo
     *
     * @return nova instância de InstructionDetails
     */
    public static InstructionDetails instructionDetails() {
        return RMObjectFactory.newInstructionDetails(
                RMObjectTestHelper.locatableRef(),
                "activityId",
                RMObjectTestHelper.itemStructure());
    }

    /**
     * Cria uma instância de ISMTransition com valor fixo
     *
     * @return nova instância de ISMTransition
     */
    public static ISMTransition iSMTransition(){
        return RMObjectFactory.newISMTransition(
                RMObjectTestHelper.dvCodedText(),
                RMObjectTestHelper.dvCodedText(),
                RMObjectTestHelper.dvCodedText());
    }

    /**
     * Cria uma instância de Activity com valor fixo
     *
     * @return nova instância de Activity
     */
    public static Activity activity(){
        Locatable locatable = RMObjectTestHelper.locatable();
        ItemStructure description = RMObjectTestHelper.itemStructure();
        DvParsable timing = RMObjectTestHelper.dvParsable();
        String actionArchetypeId = "actionArchetypeId";

        return RMObjectFactory.newActivity(locatable, description, timing,
                actionArchetypeId);
    }

    /**
     * Cria uma instância de DvOrdered com valor fixo
     *
     * @return nova instância de DvOrdered
     */
    public static DvOrdered dvOrdered(){
        List<ReferenceRange> otherReferenceRanges =
                referenceRangeList(false);
        DvInterval normalRange = RMObjectFactory.newDvInterval(null,
                null);
        CodePhrase normalStatus = RMObjectTestHelper.codePhrase();

        return RMObjectFactory.newDvOrdered(otherReferenceRanges,
                null, normalStatus);
    }

    /**
     * Cria uma instância de Interval com valor fixo
     *
     * @return nova instância de Interval
     */
    public static Interval interval(){
        DvOrdered upper = RMObjectTestHelper.dvOrdered();
        DvOrdered lower = RMObjectTestHelper.dvOrdered();

        return RMObjectFactory.newInterval(lower, upper);
    }

    /**
     * Cria uma instância de DvInterval com valor fixo
     *
     * @return nova instância de DvInterval
     */
    public static DvInterval dvInterval(){
        DvOrdered upper = RMObjectTestHelper.dvOrdered();
        DvOrdered lower = RMObjectTestHelper.dvOrdered();

        return RMObjectFactory.newDvInterval(lower, upper);
    }

    /**
     * Cria uma instância de ReferenceRange com valor fixo
     *
     * @return nova instância de ReferenceRange
     */
    public static ReferenceRange referenceRange(){
        DvText meaning = RMObjectTestHelper.dvText();
        DvInterval range = RMObjectTestHelper.dvInterval();

        return RMObjectFactory.newReferenceRange(meaning, range);
    }

    public static DvQuantified dvQuantified(){
        DvOrdered dvOrdered = dvOrdered();
        String magnitudeStatus = "magnitudeStatus";

        return RMObjectFactory.newDvQuantified(dvOrdered, magnitudeStatus);
    }

    /**
     * Método que gera uma lista de DvIdentifier
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<DvIdentifier> dvIdentifierList(boolean emptyList){
        List<DvIdentifier> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        DvIdentifier id = RMObjectFactory.newDvIdentifier("issuer",
                "assigner", "id", "type");
        list.add(id);
        list.add(id);
        list.add(id);

        return list;
    }

    /**
     * Método que gera uma lista de ReferenceRange
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<ReferenceRange> referenceRangeList(boolean emptyList){
        List<ReferenceRange> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        ReferenceRange r = RMObjectFactory.newReferenceRange(
                RMObjectTestHelper.dvText(), RMObjectFactory.newDvInterval(
                        null, null));
        list.add(r);

        return list;
    }

    /**
     * Método que gera uma lista de TermMapping
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<TermMapping> termMappingList(boolean emptyList){
        List<TermMapping> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        DvText dt = RMObjectFactory.newDvText("value", null,
                "formatting",
                RMObjectTestHelper.dVURI(),
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());

        TermMapping tm = RMObjectFactory.newTermMapping(
                RMObjectTestHelper.codePhrase(),
                Match.BROADER,
                RMObjectFactory.newDvCodedText(dt,
                        RMObjectTestHelper.codePhrase()));
        list.add(tm);
        list.add(tm);
        list.add(tm);

        return list;
    }


    /**
     * Método que gera uma lista de DvTextTest
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<DvText> dvTextList(boolean emptyList){
        List<DvText> list = new ArrayList<>();
        if(emptyList){
            return list;
        }

        DvText dt = RMObjectFactory.newDvText("value", null,
                "formatting",
                RMObjectTestHelper.dVURI(),
                RMObjectTestHelper.codePhrase(),
                RMObjectTestHelper.codePhrase());
        list.add(dt);
        list.add(dt);
        list.add(dt);

        return list;
    }

    /**
     * Método que gera uma lista de Capability
     *
     * @param emptyList cria uma lista vazia
     * @return list
     */
    public static List<Capability> capabilityList(boolean emptyList){
        List<Capability> list = new ArrayList<>();
        if(emptyList){
            return list;
        }

        Capability c = RMObjectTestHelper.capability();
        list.add(c);

        return list;
    }


    /**
     * Método que gera um set de Link
     *
     * @param emptySet cria um set vazio
     * @return set
     */
    public static Set<Link> linkSet(boolean emptySet){
        Set<Link> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Link l = RMObjectTestHelper.link();
        set.add(l);
        set.add(l);
        set.add(l);

        return set;
    }

    /**
     * Método que gera um set de PartyIdentity
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<PartyIdentity> partyIdentitySet(boolean emptySet, boolean
            legalIdentity){
        Set<PartyIdentity> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        PartyIdentity p =RMObjectFactory.newPartyIdentity(
                RMObjectFactory.newLocatable(
                        RMObjectTestHelper.uIDBasedID(),
                        "archetypeNodeId",
                        RMObjectFactory.newDvText(legalIdentity ?
                                        "legal identity" : "value",
                                RMObjectTestHelper.
                                        termMappingList(false),
                                "formatting",
                                RMObjectTestHelper.
                                        dVURI(),
                                RMObjectTestHelper.
                                        codePhrase(),
                                RMObjectTestHelper.
                                        codePhrase()),
                        RMObjectTestHelper.archetyped(),
                        RMObjectTestHelper.feederAudit(),
                        RMObjectTestHelper.linkSet(false)),
                RMObjectTestHelper.itemStructure());
        set.add(p);

        return set;
    }

    /**
     * Método que gera um set de Contact
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<Contact> contactSet(boolean emptySet){
        Set<Contact> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Contact c = RMObjectTestHelper.contact();
        set.add(c);

        return set;
    }

    /**
     * Método que gera um set de PartyRelationship
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<PartyRelationship> partyRelationshipSet(boolean emptySet){
        Set<PartyRelationship> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        PartyRelationship pr = RMObjectTestHelper.partyRelationship();
        set.add(pr);

        return set;
    }

    /**
     * Método que gera um set de LocatableRef
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<LocatableRef> locatableRefSet(boolean emptySet){
        Set<LocatableRef> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        LocatableRef lr = RMObjectTestHelper.locatableRef();
        set.add(lr);

        return set;
    }

    /**
     * Método que gera um set de Role
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<Role> roleSet(boolean emptySet){
        Set<Role> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        Role r = RMObjectTestHelper.role();
        set.add(r);

        return set;
    }

    /**
     * Método que gera um set de DvTextTest
     *
     * @param emptySet cria um set vazio
     *
     * @return set
     */
    public static Set<DvText> dvTextSet(boolean emptySet){
        Set<DvText> set = new HashSet<>();
        if(emptySet){
            return set;
        }
        DvText d = RMObjectTestHelper.dvText();
        set.add(d);

        return set;
    }

    /**
     * Método que cria uma lista de Strings
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<String> stringList(boolean emptyList){
        List<String> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        list.add("value");
        list.add("value");
        list.add("value");

        return list;
    }

    /**
     * Método que cria uma lista de Items
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Item> itemList(boolean emptyList){
        List<Item> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Item i = RMObjectTestHelper.item();

        list.add(i);

        return list;
    }

    /**
     * Método que cria uma lista de Element
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Element> elementList(boolean emptyList){
        List<Element> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Element e = RMObjectTestHelper.element();

        list.add(e);

        return list;
    }

    /**
     * Método que cria uma lista de Cluster
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Cluster> clusterList(boolean emptyList){
        List<Cluster> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Cluster c = RMObjectTestHelper.cluster();

        list.add(c);

        return list;
    }

    /**
     * Método que cria uma lista de Address
     *
     * @param emptyList lista vazia
     *
     * @return list
     */
    public static List<Address> addressList(boolean emptyList){
        List<Address> list = new ArrayList<>();
        if(emptyList){
            return list;
        }
        Address a = RMObjectTestHelper.address();

        list.add(a);

        return list;
    }

    /**
     * Método que gera um map de String, String
     *
     * @param emptyMap map vazio
     *
     * @return map
     */
    public static Map<String, String> stringStringMap(boolean emptyMap){
        Map<String, String> map = new HashMap<>();
        if(emptyMap){
            return map;
        }
        map.put("key1", "value");
        map.put("key2", "value");
        map.put("key3", "value");

        return map;
    }
}
