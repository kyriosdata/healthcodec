/*
* Copyright 2019 Instituto de Informática - UFG
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*    http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.kyriosdata.healthcodec;

/**
 *
 * @author Gabriel
 */
public enum RMObjectID {
    UNKNOWN(-1),
    DVBOOLEAN(0),
    DVIDENTIFIER(1),
    INTERNETID(2),
    ISO_OID(3),
    UUID(4),
    TERMINOLOGYID(5),
    GENERICID(6),
    TEMPLATEID(7),
    CODEPHRASE(8),
    DVURI(9),
    DVEHRURI(10),
    VERSIONTREEID(11),
    ARCHETYPEID(12),
    OBJECTVERSIONID(13),
    HIEROBJECTID(14),
    OBJECTID(15),
    PARTYREF(16),
    OBJECTREF(17),
    LOCATABLEREF(18),
    PROPORTIONKIND(19),
    ACCESSGROUPREF(20),
    PARTYIDENTIFIED(21),
    ARCHETYPED(22),
    DVENCAPSULATED(23),
    UIDBASEDID(24),
    DVPARSABLE(25),
    DVTIMESPECIFICATION(26),
    DVMULTIMEDIA(27),
    DVTEXT(28),
    DVCODEDTEXT(29),
    TERMMAPPING(30),
    LINK(31),
    DVSTATE(32),
    DVPARAGRAPH(33),
    PARTYPROXY(34),
    FEEDERAUDITDETAILS(35),
    FEEDERAUDIT(36),
    LOCATABLE(37),
    PARTYRELATED(38),
    PARTYSELF(39),
    RESOURCEDESCRIPTIONITEM(40),
    TRANSLATIONDETAILS(41),
    ITEM(42),
    CLUSTER(43),
    ELEMENT(44),
    DATASTRUCTURE(45),
    ITEMLIST(46),
    ITEMSTRUCTURE(47),
    ITEMSINGLE(48),
    ITEMTABLE(49),
    ITEMTREE(50),
    PARTYIDENTITY(51),
    PARTYRELATIONSHIP(52),
    ADDRESS(53),
    CONTACT(54),
    PARTY(55),
    CAPABILITY(56),
    ROLE(57),
    ACTOR(58),
    AGENT(59),
    GROUP(60),
    ORGANISATION(61),
    PERSON(62),
    INSTRUCTIONDETAILS(63),
    ISMTRANSITION(64),
    ACTIVITY(65),
    UID(66),
    INTERVAL(67),
    DVINTERVAL(68),
    REFERENCERANGE(69),
    DVORDERED(70),
    DVQUANTIFIED(71),
    DVAMOUNT(72),
    DVORDINAL(73),
    DVCOUNT(74),
    DVPROPORTION(75),
    DVQUANTITY(76),
    DVDURATION(77),
    DVABSOLUTEQUANTITY(78),
    DVDATE(79),
    DVTIME(80),
    DVDATETIME(81),
    DVTEMPORAL(82),
    PARTICIPATION(83),
    AUDITDETAILS(84),
    ATTESTATION(85),
    REVISIONHISTORYITEM(86),
    REVISIONHISTORY(87),
    CONTRIBUTION(88),
    FOLDER(89),
    AUTHOREDRESOURCE(90),
    RESOURCEDESCRIPTION(91),
    EVENT(92),
    INTERVALEVENT(93),
    HISTORY(94),
    POINTEVENT(95),
    CONTENTITEM(96),
    ENTRY(97),
    CAREENTRY(98),
    ACTION(99),
    ADMINENTRY(100),
    EVALUATION(101);

    
    private final int value;

    RMObjectID(int value) {
        this.value = value;
    }

    public static RMObjectID fromValue(int value) {
        if (value >  0) {
            for (RMObjectID id : values()) {
                if(id.getValue() == value){
                    return id;
                }
            }
        }

        return UNKNOWN;
    }

    public int getValue() {
        return value;
    }
}
