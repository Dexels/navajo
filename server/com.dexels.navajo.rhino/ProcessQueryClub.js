// Starting javascript.
//Wed Jun 27 17:33:23 CEST 2012.. and it is a beautiful day
// -- Compiled code. Edit at your own peril.

// ---- Validations
   if(evaluateNavajo("( ?[/QueryUpdateClub/ClubIdentifier] AND Trim([/QueryUpdateClub/ClubIdentifier]) != '' AND               SingleValueQuery('sportlinkkernel:SELECT COUNT(*) FROM organization WHERE organizationid = ?',                                Trim([/QueryUpdateClub/ClubIdentifier])) == 1             )             OR             ( ?[/Club/ClubIdentifier] AND Trim([/Club/ClubIdentifier]) != '' AND               SingleValueQuery('sportlinkkernel:SELECT COUNT(*) FROM organization WHERE organizationid = ?',                                Trim([/Club/ClubIdentifier])) == 1             )")==false) {
      appendConditionError("2002",null);
   } else {
      env.log("Condition passed!");
   }
breakOnConditionErrors();
// ---- End of Validations
// start of include: include/IncludeOracle
// -- Compiled code. Edit at your own peril.

callMap("com.dexels.navajo.adapter.NavajoAccess",function() {
   addParam("NavajoUser",evaluateNavajo("$rpcUser"),{});
   if(evaluateNavajo("?[/__globals__/RegionOwner]")==true) {
      addParam("UpdateBy",evaluateNavajo("$rpcUser + '/' + [/__globals__/RegionOwner]"),{});
   }
   else {
      addParam("UpdateBy",evaluateNavajo("$rpcUser + '/'"),{});
   }
});
addParam("DatabaseProductName",evaluateNavajo("'Oracle'"),{});
addParam("DatabaseVersion",evaluateNavajo("'20.1'"),{});
// compiletime: 12

// end of include: include/IncludeOracle
addMethod("club/ProcessUpdateClub",['ClubData']);
callMap("com.dexels.navajo.adapter.SQLMap",function() {
   if(evaluateNavajo("?[/QueryUpdateClub/ClubIdentifier] AND StringFunction( 'length', [/QueryUpdateClub/ClubIdentifier] ) > 0")==true) {
      addParam("ClubIdentifier",evaluateNavajo("[/QueryUpdateClub/ClubIdentifier]"),{});
   }
   else {
      addParam("ClubIdentifier",evaluateNavajo("[/Club/ClubIdentifier]"),{});
   }
   addField("datasource",evaluateNavajo("'sportlinkkernel'"),null);
   addParam("TransactionContext",evaluateNavajo("$transactionContext"),{});
   addField("query","SELECT vw_club.name\
                     , vw_club.shortname\
                     , vw_club.activeentity\
                     , vw_club.lastupdate\
                     , vw_club.updateby\
                     , vw_club.addressid\
                     , vw_club.streetname\
                     , vw_club.housenumber\
                     , vw_club.numberappendix\
                     , vw_club.city\
                     , vw_club.zipcode\
                     , vw_club.countryid\
                     , vw_club.remarks\
                     , vw_club.referencetype\
                     , vw_club.typeofaddress\
                     , vw_club.secretaryid\
                     , vw_club.secretaryname\
                     , vw_club.secretarytitle\
                     , vw_club.accountid\
                     , vw_club.accountnumber\
                     , vw_club.accounttype\
                     , vw_club.ascription\
                     , vw_club.ascriptionplace\
                     , vw_club.parentorganizationid\
                     , get_districtstring( vw_club.organizationid ) AS parentorganizationname\
                     , vw_club.annotationattribid\
                     , vw_club.annotation\
                     , vw_club.info\
                     , vw_club.telephoneid\
                     , vw_club.typeoftelephone\
                     , vw_club.telephonedata\
                     , vw_club.faxid\
                     , vw_club.typeoffax\
                     , vw_club.faxdata\
                     , vw_club.emailid\
                     , vw_club.typeofemail\
                     , vw_club.emaildata\
                     , vw_club.urlid\
                     , vw_club.typeofurl\
                     , vw_club.urldata\
                     , vw_club.typeofclub\
                     , vw_club.categoryofclub\
                     , vw_club.searchname\
                     , vw_club.legalname\
                     , vw_club.legalform\
                     , vw_club.foundeddt\
                     , vw_club.dissolveddt\
                     , vw_club.registernr\
                     , vw_club.shirtcol\
                     , vw_club.trousercol\
                     , vw_club.sockcol\
                     , vw_club.awayshirtcol\
                     , vw_club.awaytrousercol\
                     , vw_club.awaysockcol\
                     , vw_club.reserveshirtcol\
                     , vw_club.reservetrousercol\
                     , vw_club.reservesockcol\
                     , vw_club.mainsponsor\
                     , vw_club.shirtsponsor\
                     , vw_club.youthtraininglevel\
                     , vw_club.zaalregio\
                     , vw_club.zaalregiodesc\
                     , vw_club.veldregio\
                     , vw_club.veldregiodesc\
                     , vw_club.verservice\
                     , vw_club.svcpaymethod\
                     , vw_club.svcaccountid\
                     , vw_club.svcaccountnumber\
                     , vw_club.svcaccounttype\
                     , vw_club.svcascription\
                     , vw_club.svcascriptionplace\
                     , vw_club.svcaltaccountid\
                     , vw_club.svcaltaccountnumber\
                     , vw_club.svcaltaccounttype\
                     , vw_club.svcaltascription\
                     , vw_club.svcaltascriptionplace\
                     , vw_club.mergedintoclubid\
                     , vw_club.mergedintoclub\
                     , vw_club.placeofbusiness\
                     , vw_club.logo\
                     , (SELECT COUNT(*)\
                         FROM   document\
                         WHERE  objectid = vw_club.organizationid\
                       ) AS documentcount\
                     , get_syb_districtcode( vw_club.parentorganizationid ) AS SportlinkClubRegionOwner\
                FROM   vw_club\
                WHERE  typeoforganization = 'CLUB'\
                AND    vw_club.organizationid = ?",null);
   addField("parameter",evaluateNavajo("[/__parms__/ClubIdentifier]"),null);
   if(evaluateNavajo("$rowCount > 0")==true) {
      addMessage("ClubData","",function() {
         addParam("TransactionContext",evaluateNavajo("$transactionContext"),{});
         addParam("ParentOrganizationId",evaluateNavajo("$columnValue('parentorganizationid')"),{});
         addParam("SelectedRegionIndoor",evaluateNavajo("$columnValue('zaalregio')"),{});
         addParam("SelectedRegion",evaluateNavajo("$columnValue('veldregio')"),{});
         if(evaluateNavajo("StringFunction('indexOf', [/@ParentOrganizationId], '-') != -1")==true) {
            addParam("PlayingRegionTable",evaluateNavajo("ToUpper( StringField( [/@ParentOrganizationId], '-', 3 ) ) + '_PLAYING_REGION'"),{});
         }
         else {
            addParam("PlayingRegionTable",evaluateNavajo("'BOND_PLAYING_REGION'"),{});
         }
         addParam("DissolutionDate",evaluateNavajo("$columnValue( 'dissolveddt' )"),{});
         addProperty("ClubIdentifier",evaluateNavajo("[/__parms__/ClubIdentifier]"),{description:"Clubcode",direction:"out",type:"string"});
         addProperty("RegionOwner",evaluateNavajo("Trim($columnValue('parentorganizationid'))"),{description:"District",direction:"out",type:"string"});
         addProperty("RegionOwnerName",evaluateNavajo("Trim($columnValue('parentorganizationname'))"),{description:"District",direction:"out",type:"string"});
         addProperty("ClubName",evaluateNavajo("Trim($columnValue('name'))"),{description:"Clubnaam",direction:"in",length:"64",type:"string"});
         addProperty("OriginalClubName",evaluateNavajo("Trim($columnValue('name'))"),{description:"Originele Clubnaam",direction:"out",length:"64",type:"string"});
         addProperty("ClubShortName",evaluateNavajo("Trim($columnValue('shortname'))"),{description:"Verkorte naam",direction:"in",length:"64",type:"string"});
         addParam("TypeOfClub",evaluateNavajo("Trim($columnValue('typeofclub'))"),{});
         callMap("com.dexels.sportlink.adapters.CodeDropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/@TransactionContext]"),null);
            addField("typeOfCode",evaluateNavajo("'CLUB_TYPE'"),null);
            addField("orderByDescription",evaluateNavajo("true"),null);
            addField("description",evaluateNavajo("'Clubtype'"),null);
            addField("name",evaluateNavajo("'TypeOfClub'"),null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/@TypeOfClub]"),null);
         });
         addProperty("CategoryOfClub",evaluateNavajo("Trim($columnValue('categoryofclub'))"),{description:"Clubcategorie",direction:"in",length:"32",type:"string"});
         addProperty("ClubSearchName",evaluateNavajo("Trim($columnValue('searchname'))"),{description:"Zoeknaam",direction:"in",subtype:"capitalization=upper",length:"15",type:"string"});
         addProperty("LegalName",evaluateNavajo("Trim($columnValue('legalname'))"),{description:"Juridische naam",direction:"in",length:"95",type:"string"});
         addProperty("CurrentCanonicalLegalName",evaluateNavajo("GetCanonicalString( $columnValue('legalname') )"),{description:"Huidige kanonieke juridische naam",direction:"out",length:"94",type:"string"});
         addParam("SelectedLegalForm",evaluateNavajo("$columnValue('legalform')"),{});
         callMap("com.dexels.sportlink.adapters.CodeDropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("useCache",evaluateNavajo("'LEGAL_FORMS'"),null);
            addField("typeOfCode",evaluateNavajo("'LEGAL_FORMS'"),null);
            addField("orderByDescription",evaluateNavajo("true"),null);
            addField("description",evaluateNavajo("'Rechtsvorm'"),null);
            addField("name",evaluateNavajo("'LegalForm'"),null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/SelectedLegalForm]"),null);
         });
         addProperty("TypeOfCharge",evaluateNavajo("'NAME_CHANGE'"),{description:"Kostentype bij naamsverandering",direction:"out",length:"32",type:"string"});
         addProperty("EstablishedDate",evaluateNavajo("$columnValue('foundeddt')"),{description:"Oprichtingsdatum",direction:"in",type:"date"});
         if(evaluateNavajo("FormatDate( [/__parms__/DissolutionDate], 'yyyy-MM-dd' ) == '9999-12-31'")==true) {
            addProperty("ClubLiquidationDate",evaluateNavajo("null"),{description:"Opheffingsdatum",direction:"in",type:"date"});
         }
         else {
            addProperty("ClubLiquidationDate",evaluateNavajo("[/__parms__/DissolutionDate]"),{description:"Opheffingsdatum",direction:"in",type:"date"});
         }
         addParam("selectedTypeOfAddress",evaluateNavajo("$columnValue('typeofaddress')"),{});
         callMap("com.dexels.sportlink.adapters.DropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("query","SELECT DISTINCT codeid, codedesc\
                        FROM            codetable\
                        WHERE           typeofcode = 'ADDRESS_TYPE'\
                        AND             language   = get_default_language()\
                        ORDER BY        codedesc",null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/selectedTypeOfAddress]"),null);
            addField("description",evaluateNavajo("'Type adres'"),null);
            addField("name",evaluateNavajo("'TypeOfAddress'"),null);
         });
         addProperty("StreetName",evaluateNavajo("Trim($columnValue('streetname'))"),{description:"Straat",direction:"in",length:"45",type:"string"});
         addProperty("AnnotationAttribId",evaluateNavajo("$columnValue('annotationattribid')"),{description:"Annotation attribute id",direction:"out",length:"12",type:"integer"});
         addProperty("Annotation",evaluateNavajo("$columnValue('annotation')"),{description:"Annotation",direction:"in",length:"256",type:"string"});
         addProperty("Info",evaluateNavajo("$columnValue('info')"),{description:"Info",direction:"in",length:"256",type:"string"});
         addProperty("TelephoneId",evaluateNavajo("$columnValue('telephoneid')"),{description:"Telefoon Identifier",direction:"out",length:"16",type:"string"});
         addParam("selectedTypeOfTelephone",evaluateNavajo("$columnValue('typeoftelephone')"),{});
         callMap("com.dexels.sportlink.adapters.DropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("useCache",evaluateNavajo("'COMMUNICATION_TYPE_PHONE'"),null);
            addField("query","SELECT DISTINCT codeid, codedesc\
                        FROM            codetable\
                        WHERE           typeofcode = 'COMMUNICATION_TYPE'\
                        AND             language   = get_default_language()\
                        AND             codeid     LIKE '%PHONE%'\
                        ORDER BY        codedesc",null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/selectedTypeOfTelephone]"),null);
            addField("description",evaluateNavajo("'Telephone Type'"),null);
            addField("name",evaluateNavajo("'TypeOfTelephone'"),null);
         });
         addProperty("TelephoneData",evaluateNavajo("$columnValue('telephonedata')"),{description:"Telefoon",direction:"in",length:"32",type:"string"});
         addParam("DefaultFacilityNumber",evaluateNavajo("SingleValueQuery([/@TransactionContext], 'SELECT communicationdata FROM orgfacilitycommunication WHERE organizationid = ? AND facilityid = (SELECT facilityid FROM organizationfacilityattribute WHERE organizationid = orgfacilitycommunication.organizationid AND attribname = ? AND attribvalue = ? AND rownum = 1) AND typeofcommunication = ? AND rownum = 1 ORDER BY preferred ASC', [/__parms__/ClubIdentifier], 'DEFAULT_FACILITY', '1', 'TELEPHONE')"),{});
         if(evaluateNavajo("[/@DefaultFacilityNumber] != null")==true) {
            addProperty("DefaultFacilityTelephoneData",evaluateNavajo("[/@DefaultFacilityNumber]"),{description:"Telefoon standaard accommodatie",direction:"out",length:"32",type:"string"});
         }
         else {
            addProperty("DefaultFacilityTelephoneData",evaluateNavajo("SingleValueQuery([/@TransactionContext], 'SELECT telephonedata FROM vw_facility WHERE facilityid = (SELECT facilityid FROM organizationfacilityattribute WHERE organizationid = ? AND attribname = ? AND attribvalue = ? AND rownum = 1)', [/__parms__/ClubIdentifier], 'DEFAULT_FACILITY', '1')"),{description:"Telefoon standaard accommodatie",direction:"out",length:"32",type:"string"});
         }
         addProperty("FaxId",evaluateNavajo("$columnValue('faxid')"),{description:"Fax Identifier",direction:"out",length:"16",type:"string"});
         addParam("selectedTypeOfFax",evaluateNavajo("$columnValue('typeoffax')"),{});
         callMap("com.dexels.sportlink.adapters.DropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("useCache",evaluateNavajo("'COMMUNICATION_TYPE_FAX'"),null);
            addField("query","SELECT DISTINCT codeid, codedesc\
                        FROM            codetable\
                        WHERE           typeofcode = 'COMMUNICATION_TYPE'\
                        AND             language   = get_default_language()\
                        AND             codeid     LIKE '%FAX%'\
                        ORDER BY        codedesc",null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/selectedTypeOfFax]"),null);
            addField("description",evaluateNavajo("'Fax Type'"),null);
            addField("name",evaluateNavajo("'TypeOfFax'"),null);
         });
         addProperty("FaxData",evaluateNavajo("$columnValue('faxdata')"),{description:"Fax",direction:"in",length:"32",type:"string"});
         addProperty("EmailId",evaluateNavajo("$columnValue('emailid')"),{description:"E-mail Identifier",direction:"out",length:"16",type:"string"});
         addParam("selectedTypeOfEmail",evaluateNavajo("$columnValue('typeofemail')"),{});
         callMap("com.dexels.sportlink.adapters.DropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("useCache",evaluateNavajo("'COMMUNICATION_TYPE_EMAIL'"),null);
            addField("query","SELECT DISTINCT codeid, codedesc\
                        FROM            codetable\
                        WHERE           typeofcode = 'COMMUNICATION_TYPE'\
                        AND             language   = get_default_language()\
                        AND             codeid     LIKE '%MAIL%'\
                        ORDER BY        codedesc",null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/selectedTypeOfEmail]"),null);
            addField("description",evaluateNavajo("'E-mail Type'"),null);
            addField("name",evaluateNavajo("'TypeOfEmail'"),null);
            if(evaluateNavajo("( [/@selectedTypeOfEmail] == 'MDESK_EMAIL' OR [/@selectedTypeOfEmail] == 'FORWARD_EMAIL' ) AND ! UserInRole( 'ADMINISTRATOR' )")==true) {
               addField("direction",evaluateNavajo("'out'"),null);
            }
            else {
               addField("direction",evaluateNavajo("'in'"),null);
            }
         });
         if(evaluateNavajo("( [/@selectedTypeOfEmail] != 'MDESK_EMAIL' AND [/@selectedTypeOfEmail] != 'FORWARD_EMAIL' ) OR UserInRole( 'ADMINISTRATOR' )")==true) {
            addProperty("EmailData",evaluateNavajo("$columnValue('emaildata')"),{description:"E-mail",direction:"in",subtype:"uri=true",length:"64",type:"string"});
         }
         if(evaluateNavajo("( [/@selectedTypeOfEmail] == 'MDESK_EMAIL' OR  [/@selectedTypeOfEmail] == 'FORWARD_EMAIL' ) AND ! UserInRole( 'ADMINISTRATOR' )")==true) {
            addProperty("EmailData",evaluateNavajo("$columnValue('emaildata')"),{description:"E-mail",direction:"out",subtype:"uri=true",length:"64",type:"string"});
         }
         addProperty("WebsiteId",evaluateNavajo("$columnValue('urlid')"),{description:"Website Identifier",direction:"out",length:"16",type:"string"});
         addParam("selectedTypeOfWebsite",evaluateNavajo("$columnValue('typeofurl')"),{});
         callMap("com.dexels.sportlink.adapters.DropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("useCache",evaluateNavajo("'COMMUNICATION_TYPE_URL'"),null);
            addField("query","SELECT DISTINCT codeid, codedesc\
                        FROM            codetable\
                        WHERE           typeofcode = 'COMMUNICATION_TYPE'\
                        AND             language   = get_default_language()\
                        AND             codeid     LIKE '%URL%'\
                        ORDER BY        codedesc",null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/selectedTypeOfWebsite]"),null);
            addField("description",evaluateNavajo("'Website Type'"),null);
            addField("name",evaluateNavajo("'TypeOfWebsite'"),null);
         });
         if(evaluateNavajo("$columnValue('urldata') != null AND $columnValue('urldata') != '' AND StringFunction('indexOf', $columnValue('urldata'), 'http://') > -1")==true) {
            addProperty("WebsiteData",evaluateNavajo("$columnValue('urldata')"),{description:"Website",direction:"in",subtype:"uri=true",length:"64",type:"string"});
         }
         else if(evaluateNavajo("$columnValue('urldata') != null AND $columnValue('urldata') != ''")==true) {
            addProperty("WebsiteData",evaluateNavajo("'http://' + $columnValue('urldata')"),{description:"Website",direction:"in",subtype:"uri=true",length:"64",type:"string"});
         }
         else {
            addProperty("WebsiteData",evaluateNavajo("$columnValue('urldata')"),{description:"Website",direction:"in",subtype:"uri=true",length:"64",type:"string"});
         }
         addProperty("AddressNumber",evaluateNavajo("Trim($columnValue('housenumber'))"),{description:"Huisnummer",direction:"in",length:"15",type:"string"});
         addProperty("AddressNumberAppendix",evaluateNavajo("Trim($columnValue('numberappendix'))"),{description:"Toevoeging",direction:"in",length:"15",type:"string"});
         addProperty("ZipCode",evaluateNavajo("Trim($columnValue('zipcode'))"),{description:"Postcode",direction:"in",length:"15",type:"string"});
         addProperty("City",evaluateNavajo("Trim($columnValue('city'))"),{description:"Plaats",direction:"in",length:"32",type:"string"});
         addProperty("AddressCountryCode",evaluateNavajo("Trim($columnValue('countryid'))"),{description:"Landcode",direction:"in"});
         addProperty("BankAccountNumber",evaluateNavajo("Trim($columnValue('accountnumber'))"),{description:"Banknummer",direction:"in",length:"11",type:"string"});
         addProperty("Ascription",evaluateNavajo("Trim($columnValue('ascription'))"),{description:"Tenaamstelling",direction:"in",length:"51",type:"string"});
         addProperty("AscriptionPlace",evaluateNavajo("Trim($columnValue('ascriptionplace'))"),{description:"Plaats tenaamstelling",direction:"in",length:"51",type:"string"});
         addParam("SelectedPaymentMethod",evaluateNavajo("Trim( $columnValue( 'svcpaymethod' ) )"),{});
         addProperty("SecretaryMemberIdentifier",evaluateNavajo("Trim( $columnValue( 'secretaryid' ) )"),{description:"Secretaris",direction:"out",length:"7",type:"string"});
         addProperty("SecretaryName",evaluateNavajo("Trim( $columnValue( 'secretaryname' ) )"),{description:"Naam",direction:"out",length:"36",type:"string"});
         if(evaluateNavajo("$columnValue( 'secretarytitle' ) != null")==true) {
            addProperty("TitledSecretaryName",evaluateNavajo("$columnValue( 'secretarytitle' ) + ' ' + Trim( $columnValue( 'secretaryname' ) )"),{description:"Naam secretaris",direction:"out",length:"36",type:"string"});
         }
         else {
            addProperty("TitledSecretaryName",evaluateNavajo("Trim( $columnValue( 'secretaryname' ) )"),{description:"Naam secretaris",direction:"out",length:"36",type:"string"});
         }
         callMap("com.dexels.sportlink.adapters.CodeDropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("typeOfCode",evaluateNavajo("'PAYMENT_METHOD_TYPE'"),null);
            addField("useCache",evaluateNavajo("'PAYMENT_METHOD_TYPE'"),null);
            addField("name",evaluateNavajo("'CollectionServicePaymentMethod'"),null);
            addField("description",evaluateNavajo("'Contributieservice betaalwijze'"),null);
            addField("orderByDescription",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/SelectedPaymentMethod]"),null);
         });
         callMap("com.dexels.sportlink.adapters.CodeDropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("typeOfCode",evaluateNavajo("[/__parms__/PlayingRegionTable]"),null);
            addField("name",evaluateNavajo("'RegionField'"),null);
            addField("description",evaluateNavajo("'Regio veld'"),null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/SelectedRegion]"),null);
         });
         callMap("com.dexels.sportlink.adapters.CodeDropdownList",function() {
            addField("transactionContext",evaluateNavajo("[/__parms__/TransactionContext]"),null);
            addField("typeOfCode",evaluateNavajo("[/__parms__/PlayingRegionTable]"),null);
            addField("name",evaluateNavajo("'RegionIndoor'"),null);
            addField("description",evaluateNavajo("'Regio zaal'"),null);
            addField("emptyOption",evaluateNavajo("true"),null);
            addField("selectedValue",evaluateNavajo("[/__parms__/SelectedRegionIndoor]"),null);
         });
         addProperty("BusinessRegistrationNumber",evaluateNavajo("Trim( $columnValue( 'registernr' ) )"),{description:"Kamer van Koophandelnummer",direction:"in",length:"15",type:"string"});
         addProperty("HomeShirtColors",evaluateNavajo("Trim( $columnValue( 'shirtcol' ) )"),{description:"Shirtkleuren (thuis)",direction:"in",length:"128",type:"string"});
         addProperty("HomeShortsColors",evaluateNavajo("Trim( $columnValue( 'trousercol' ) )"),{description:"Broekkleuren (thuis)",direction:"in",length:"128",type:"string"});
         addProperty("HomeStockingColors",evaluateNavajo("Trim( $columnValue( 'sockcol' )) "),{description:"Kousenkleuren (thuis)",direction:"in",length:"128",type:"string"});
         addProperty("AwayShirtColors",evaluateNavajo("Trim( $columnValue( 'awayshirtcol' ) )"),{description:"Shirtkleuren (uit)",direction:"in",length:"128",type:"string"});
         addProperty("AwayShortsColors",evaluateNavajo("Trim( $columnValue( 'awaytrousercol' ) )"),{description:"Broekkleuren (uit)",direction:"in",length:"128",type:"string"});
         addProperty("AwayStockingColors",evaluateNavajo("Trim( $columnValue( 'awaysockcol' )) "),{description:"Kousenkleuren (uit)",direction:"in",length:"128",type:"string"});
         addProperty("ReserveShirtColors",evaluateNavajo("Trim( $columnValue( 'reserveshirtcol' ) )"),{description:"Shirtkleuren (reserve)",direction:"in",length:"128",type:"string"});
         addProperty("ReserveShortsColors",evaluateNavajo("Trim( $columnValue( 'reservetrousercol' ) )"),{description:"Broekkleuren (reserve)",direction:"in",length:"128",type:"string"});
         addProperty("ReserveStockingColors",evaluateNavajo("Trim( $columnValue( 'reservesockcol' )) "),{description:"Kousenkleuren (reserve)",direction:"in",length:"128",type:"string"});
         addProperty("MainSponsor",evaluateNavajo("$columnValue( 'mainsponsor' )"),{description:"Hoofdsponsor",direction:"in",length:"64",type:"string"});
         addProperty("ShirtSponsor",evaluateNavajo("$columnValue( 'shirtsponsor' )"),{description:"Shirtsponsor",direction:"in",length:"64",type:"string"});
         if(evaluateNavajo("$columnValue( 'youthtraininglevel' ) != null")==true) {
            addProperty("YouthTrainingLevel",evaluateNavajo("$columnValue( 'youthtraininglevel' ) "),{description:"Niveau jeugdopleiding",direction:"in",length:"32",type:"string"});
         }
         else {
            addProperty("YouthTrainingLevel",evaluateNavajo("'0'"),{description:"Niveau jeugdopleiding",direction:"in",length:"32",type:"string"});
         }
         if(evaluateNavajo("$columnValue( 'verservice' ) == '1'")==true) {
            addProperty("isCollectionServiced",evaluateNavajo("true"),{description:"Deelnemend in Contributieservice",direction:"in",length:"15"});
         }
         else {
            addProperty("isCollectionServiced",evaluateNavajo("false"),{description:"Deelnemend in Contributieservice",direction:"in",length:"15"});
         }
         addProperty("CollectionServiceBankAccountNumber",evaluateNavajo("Trim( $columnValue( 'svcaccountnumber' ) )"),{description:"Contributieservice banknummer",direction:"in",length:"11",type:"string"});
         addProperty("CollectionServiceAscription",evaluateNavajo("Trim( $columnValue( 'svcascription' ) )"),{description:"Contributieservice tenaamstelling",direction:"in",length:"51",type:"string"});
         addProperty("CollectionServiceAscriptionPlace",evaluateNavajo("Trim( $columnValue( 'svcascriptionplace' ) )"),{description:"Contributieservice plaats tenaamstelling",direction:"in",length:"51",type:"string"});
         addProperty("CollectionServiceAlternateBankAccountNumber",evaluateNavajo("Trim( $columnValue( 'svcaltaccountnumber' ) )"),{description:"Contributieservice alternatief banknummer",direction:"in",length:"11",type:"string"});
         addProperty("CollectionServiceAlternateAscription",evaluateNavajo("Trim( $columnValue( 'svcaltascription' ) )"),{description:"Contributieservice alternatieve tenaamstelling",direction:"in",length:"51",type:"string"});
         addProperty("CollectionServiceAlternateAscriptionPlace",evaluateNavajo("Trim( $columnValue( 'svcascriptionplace' ) )"),{description:"Contributieservice alternatieve plaats tenaamstelling",direction:"in",length:"51",type:"string"});
         addProperty("MergedIntoClubId",evaluateNavajo("$columnValue( 'mergedintoclubid' ) )"),{description:"Club id of club into which this club was merged",direction:"out",length:"32",type:"string"});
         addProperty("MergedIntoClub",evaluateNavajo("$columnValue( 'mergedintoclub' ) )"),{description:"Club name of club into which this club was merged",direction:"out",length:"64",type:"string"});
         addProperty("PlaceOfBusiness",evaluateNavajo("$columnValue( 'placeofbusiness' ) )"),{description:"Place of business",direction:"in",length:"64",type:"string"});
         addProperty("Logo",evaluateNavajo(" $columnValue( 'logo' )"),{description:"Clublogo",direction:"in",type:"binary"});
         addProperty("SmallLogo",evaluateNavajo("ScaleImageMax( $columnValue( 'logo' ), 240, 240 )"),{description:"Small Club logo",direction:"out",type:"binary"});
         addProperty("HasDocuments",evaluateNavajo("$columnValue('documentcount') > 0"),{description:"Has Couple of Documents",direction:"out",length:"16",type:"string"});
         // comment: ' the SLC application wants some extra properties.. '
         if(evaluateNavajo("$columnValue('SportlinkClubRegionOwner') != null")==true) {
            addProperty("SportlinkClubRegionOwner",evaluateNavajo("$columnValue('SportlinkClubRegionOwner')"),{description:"Sportlink Club Region Owner (special format..)",direction:"out",type:"string"});
         }
         else {
            addProperty("SportlinkClubRegionOwner",evaluateNavajo("$columnValue('parentorganizationid')"),{description:"Sportlink Club Region Owner (special format..)",direction:"out",type:"string"});
         }
         addProperty("TelephoneNumber",evaluateNavajo("''"),{direction:"in",length:"15",type:"string"});
         addProperty("isCOS",evaluateNavajo("$columnValue('verservice')"),{direction:"out",type:"boolean"});
         addProperty("StatutorySport",evaluateNavajo("SingleValueQuery([/__parms__/TransactionContext], 'SELECT sportdesc FROM vw_club_sport WHERE isstatutory = 1 AND organizationid = ? AND rownum = 1', [/@ClubIdentifier])"),{description:"Statutaire speeldag",direction:"out",type:"string"});
         addProperty("HasFinancialRole",evaluateNavajo("true"),{direction:"out",length:"4",type:"boolean"});
         addProperty("AllowPaymentsAfterLiquidation",evaluateNavajo("UserInRole( 'FINANCE' )"),{direction:"out",length:"4",type:"boolean"});
         addProperty("MemberLastUpdate",evaluateNavajo("SingleValueQuery([/__parms__/TransactionContext], 'SELECT MAX( organizationperson.lastupdate ) FROM organizationperson WHERE organizationperson.organizationid = ?', [/@ClubIdentifier])"),{description:"Laatste mutatie lid",direction:"out",type:"string"});
      },{});
   }
   if(evaluateNavajo("$rowCount == 0")==true) {
      addMessage("ClubNotFound","",function() {
      },{});
   }
   addField("query","SELECT address.addressid\
                ,      address.streetname\
                ,      address.housenumber\
                ,      address.numberappendix\
                ,      address.city\
                ,      address.zipcode\
                ,      address.countryid\
                ,      ( SELECT codedesc\
				         FROM   codetable\
				         WHERE  codeid     = LTRIM(RTRIM(address.countryid))\
				         AND    typeofcode = 'COUNTRY'\
				         AND    language   = get_default_language()\
				       ) AS country                \
                FROM   address\
                ,      organizationaddress\
                WHERE  address.addressid                  = organizationaddress.addressid\
                AND    organizationaddress.organizationid = ?                \
                AND    organizationaddress.typeofaddress  = 'VISITOR_ADDRESS'\
                AND    organizationaddress.preferred     != '1'\
                AND    rownum = 1",null);
   addField("parameter",evaluateNavajo("[/@ClubIdentifier]"),null);
   addMessage("VisitorAddress","",function() {
      addProperty("OrganizationId",evaluateNavajo("[/@ClubIdentifier]"),{direction:"out",type:"string"});
      addProperty("TypeOfAddress",evaluateNavajo("'VISITOR_ADDRESS'"),{direction:"out",type:"integer"});
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("AddressId",evaluateNavajo("$columnValue('addressid')"),{direction:"out",type:"integer"});
      }
      else {
         addProperty("AddressId",evaluateNavajo("null"),{direction:"out",type:"integer"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("StreetName",evaluateNavajo("$columnValue('streetname')"),{description:"Straat",direction:"in",type:"string"});
      }
      else {
         addProperty("StreetName",evaluateNavajo("null"),{description:"Straat",direction:"in",type:"string"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("AddressNumber",evaluateNavajo("$columnValue('housenumber')"),{description:"Huisnummer",direction:"in",type:"integer"});
      }
      else {
         addProperty("AddressNumber",evaluateNavajo("null"),{description:"Huisnummer",direction:"in",type:"integer"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("AddressNumberAppendix",evaluateNavajo("$columnValue('numberappendix')"),{description:"Toevoeging",direction:"in",type:"string"});
      }
      else {
         addProperty("AddressNumberAppendix",evaluateNavajo("null"),{description:"Toevoeging",direction:"in",type:"string"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("City",evaluateNavajo("$columnValue('city')"),{description:"Plaats",direction:"in",type:"string"});
      }
      else {
         addProperty("City",evaluateNavajo("null"),{description:"Plaats",direction:"in",type:"string"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("ZipCode",evaluateNavajo("$columnValue('zipcode')"),{description:"Postcode",direction:"in",type:"string"});
      }
      else {
         addProperty("ZipCode",evaluateNavajo("null"),{description:"Postcode",direction:"in",type:"string"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("AddressCountryCode",evaluateNavajo("$columnValue('countryid')"),{description:"Landcode",direction:"in",type:"string"});
      }
      else {
         addProperty("AddressCountryCode",evaluateNavajo("'NL'"),{description:"Landcode",direction:"in",type:"string"});
      }
      if(evaluateNavajo("$rowCount > 0")==true) {
         addProperty("RouteTo",evaluateNavajo("'http://www.google.com/maps?source=uds&daddr=' + $columnValue('streetname') + '+' + $columnValue('housenumber') + ',+' + $columnValue('zipcode') + ',+' + $columnValue('city') + ',+' + $columnValue('country') + '&iwstat1=dir:to'"),{description:"Plan route naar..",direction:"out",type:"string"});
      }
      else {
         addProperty("RouteTo",evaluateNavajo("null"),{description:"Plan route naar..",direction:"out",type:"string"});
      }
   },{});
   addField("query","SELECT streetname\
						,      housenumber\
						,      numberappendix\
						,      zipcode\
						,      city\
						FROM   facilityaddress fa\
						,      address a\
						WHERE  fa.addressid = a.addressid\
						AND    fa.facilityid = NVL( ( SELECT facilityid \
								                      FROM   organizationfacilityattribute \
								                      WHERE  organizationid = ?\
								                      AND    attribname = 'DEFAULT_FACILITY' \
								                      AND    attribvalue = '1' \
								                      AND    rownum = 1\
								                    ),\
								                    ( SELECT facilityid \
								                      FROM   organizationfacility\
								                      WHERE  organizationid = ? \
								                      AND    subfacilityid = 'FACILITY' \
								                      AND rownum = 1								                      \
								                    )\
								                  )",null);
   addField("parameter",evaluateNavajo("[/__parms__/ClubIdentifier]"),null);
   addField("parameter",evaluateNavajo("[/__parms__/ClubIdentifier]"),null);
   if(evaluateNavajo("$rowCount > 0")==true) {
      addMessage("DefaultFacilityAddress","",function() {
         addProperty("StreetName",evaluateNavajo("Trim($columnValue('streetname'))"),{description:"Straat",direction:"in",length:"45",type:"string"});
         addProperty("AddressNumber",evaluateNavajo("Trim($columnValue('housenumber'))"),{description:"Huisnummer",direction:"in",length:"15",type:"string"});
         addProperty("AddressNumberAppendix",evaluateNavajo("Trim($columnValue('numberappendix'))"),{description:"Toevoeging",direction:"in",length:"15",type:"string"});
         addProperty("ZipCode",evaluateNavajo("Trim($columnValue('zipcode'))"),{description:"Postcode",direction:"in",length:"15",type:"string"});
         addProperty("City",evaluateNavajo("Trim($columnValue('city'))"),{description:"Plaats",direction:"in",length:"32",type:"string"});
      },{});
   }
});
addMessage("DatabaseStatus","",function() {
   addProperty("DatabaseProductName",evaluateNavajo("[/__parms__/DatabaseProductName]"),{direction:"out",length:"32",type:"string"});
   addProperty("DatabaseVersion",evaluateNavajo("[/__parms__/DatabaseVersion]"),{direction:"out",length:"32",type:"string"});
   addProperty("DatabaseSchemaOwner",evaluateNavajo("[/__globals__/KernelSchemaOwner]"),{direction:"out",length:"32",type:"string"});
},{});
// compiletime: 20

// End of compile.
