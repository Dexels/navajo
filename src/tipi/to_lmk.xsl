<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="message">
    <xsl:if test="@name = 'CurrentMemberData'">
      <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
        <!-- defines the layout master -->
        <fo:layout-master-set>
          <fo:simple-page-master master-name="first" page-height="29.7cm" page-width="21cm" margin-top="16mm" margin-bottom="2cm" margin-left="16mm" margin-right="16mm">
            <fo:region-body margin-top="0mm" margin-bottom="0mm"/>
            <fo:region-before extent="0mm"/>
            <fo:region-after extent="0mm"/>
          </fo:simple-page-master>
        </fo:layout-master-set>
        <!-- starts actual layout -->
        <fo:page-sequence master-reference="first">
          <fo:flow flow-name="xsl-region-body">
            <!--                Start constructing the LMK              -->
            <!-- Afschrift KNVB-->
            <fo:block font-size="14pt" font-family="sans-serif" line-height="18pt" space-after.optimum="8pt" color="black" text-align="left">
        Leden Mutatiekaart Afschrift tbv KNVB
      </fo:block>
            <!-- table start  Naam, relatienummer-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="113mm"/>
              <fo:table-column column-width="26mm"/>
              <fo:table-column column-width="39mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'MemberIdentifier']" mode="ignore"/>
                  <xsl:apply-templates select="property[@name = 'FullName']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Lidmaatschap vereniging-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="38mm"/>
              <fo:table-column column-width="101mm"/>
              <fo:table-column column-width="20mm"/>
              <fo:table-column column-width="19mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Relatienummer vereniging</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Verenigingsnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Aanmelddatum</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ClubRegistrationDate']" mode="ignore"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'ClubIdentifier']" mode="ignore"/>
                  <xsl:apply-templates select="property[@name = 'ClubName']" mode="ignore"/>
                  <fo:table-cell>
                    <fo:block>Afmelddatum</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ClubDeregistrationDate']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Naam en geboorte gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="41mm"/>
              <fo:table-column column-width="19mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-column column-width="23mm"/>
              <fo:table-column column-width="31mm"/>
              <fo:table-column column-width="6mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Achternaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Roepnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Tussenv.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Voorlet.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>m/v</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Geboortedatum</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Geboorteplaats</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nat.</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldLastName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldFirstName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldInfix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldInitials']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldGender']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldDateOfBirth']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldBirthPlace']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldNationality']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'LastName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'FirstName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Infix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Initials']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Gender']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'DateOfBirth']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'BirthPlace']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Nationality']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Adresgegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="53mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="41mm"/>
              <fo:table-column column-width="40mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Straatnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>nr.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Toev.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Postcode</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Woonplaats</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Land</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldStreetName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressNumberAppendix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldZipCode']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldCity']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressCountryCode']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'StreetName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressNumberAppendix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'ZipCode']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'City']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressCountryCode']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Bankgegevens en Andere lidmaatschappen-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="25mm"/>
              <fo:table-column column-width="93mm"/>
              <fo:table-column column-width="43mm"/>
              <fo:table-column column-width="17mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>(Post)bank nr</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Tenaamstelling</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Andere vereniging</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>District</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldBankAccountNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAscription']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>1. -</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'BankAccountNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Ascription']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>2. -</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Telefoon gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="25mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="28mm"/>
              <fo:table-column column-width="28mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="27mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort 2e telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>2e telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Telefoon werk</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort 2e telefoon werk</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>2e telefoon werk</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldTelephoneNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>Mobiel</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldMobileNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Fax</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldFaxNumber']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'TelephoneNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>Mobiel</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'MobileNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Fax</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'FaxNumber']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Speltype, functies en andere sporten -->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-column column-width="44mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="29mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>ZAT</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>ZON</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>ZVB</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>RECR.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>B.Z.A.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Niet spelend</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Andere sport</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Erelid</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Vereniginsfunctie</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Trainer</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldisSaturdayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisSundayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisFutsalMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldisNonPlayingMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisOtherSportMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisHonoraryMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldisClubTrainer']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'isSaturdayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isSundayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isFutsalMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'isNonPlayingMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isOtherSportMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isHonoraryMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'isClubTrainer']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Contributie gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="32mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Contributie</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Betaalwijze</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Contributie periode</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort legitimatie bewijs</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nummer</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <!-- table start Contributie gegevens typen -->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="11mm"/>
              <fo:table-column column-width="6mm"/>
              <fo:table-column column-width="7mm"/>
              <fo:table-column column-width="8mm"/>
              <fo:table-column column-width="11mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>code</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Incasso</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Acc.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Giro.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nota</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Maand</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Kwart.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>1/2 jaar</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Jaar</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>----------</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>----------</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"/>
            <!-- table start  Datum verwerkt-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="116mm"/>
              <fo:table-column column-width="32mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Datum verwerkt</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ProcessingDate']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="30pt"> </fo:block>
            <!-- Afschrift Vereniging-->
            <fo:block font-size="14pt" font-family="sans-serif" line-height="18pt" space-after.optimum="8pt" color="black" text-align="left">
        Leden Mutatiekaart Afschrift tbv vereniging
      </fo:block>
            <!-- table start  Naam, relatienummer-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="113mm"/>
              <fo:table-column column-width="26mm"/>
              <fo:table-column column-width="39mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'MemberIdentifier']" mode="ignore"/>
                  <xsl:apply-templates select="property[@name = 'FullName']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Lidmaatschap vereniging-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="38mm"/>
              <fo:table-column column-width="101mm"/>
              <fo:table-column column-width="20mm"/>
              <fo:table-column column-width="19mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Relatienummer vereniging</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Verenigingsnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Aanmelddatum</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ClubRegistrationDate']" mode="ignore"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'ClubIdentifier']" mode="ignore"/>
                  <xsl:apply-templates select="property[@name = 'ClubName']" mode="ignore"/>
                  <fo:table-cell>
                    <fo:block>Afmelddatum</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ClubDeregistrationDate']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Naam en geboorte gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="41mm"/>
              <fo:table-column column-width="19mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-column column-width="23mm"/>
              <fo:table-column column-width="31mm"/>
              <fo:table-column column-width="6mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Achternaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Roepnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Tussenv.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Voorlet.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>m/v</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Geboortedatum</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Geboorteplaats</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nat.</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldLastName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldFirstName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldInfix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldInitials']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldGender']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldDateOfBirth']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldBirthPlace']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldNationality']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'LastName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'FirstName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Infix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Initials']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Gender']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'DateOfBirth']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'BirthPlace']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Nationality']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Adresgegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="53mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="41mm"/>
              <fo:table-column column-width="40mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Straatnaam</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>nr.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Toev.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Postcode</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Woonplaats</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Land</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldStreetName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressNumberAppendix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldZipCode']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldCity']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAddressCountryCode']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'StreetName']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressNumberAppendix']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'ZipCode']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'City']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'AddressCountryCode']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Bankgegevens en Andere lidmaatschappen-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="25mm"/>
              <fo:table-column column-width="93mm"/>
              <fo:table-column column-width="43mm"/>
              <fo:table-column column-width="17mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>(Post)bank nr</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Tenaamstelling</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Andere vereniging</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>District</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldBankAccountNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldAscription']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>1. -</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'BankAccountNumber']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'Ascription']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>2. -</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Telefoon gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="25mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="28mm"/>
              <fo:table-column column-width="28mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="27mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort 2e telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>2e telefoon prive</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Telefoon werk</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort 2e telefoon werk</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>2e telefoon werk</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldTelephoneNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>Mobiel</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldMobileNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Fax</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldFaxNumber']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'TelephoneNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>Mobiel</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'MobileNumber']" mode="string"/>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Fax</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'FaxNumber']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Speltype, functies en andere sporten -->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="16mm"/>
              <fo:table-column column-width="44mm"/>
              <fo:table-column column-width="9mm"/>
              <fo:table-column column-width="35mm"/>
              <fo:table-column column-width="29mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>ZAT</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>ZON</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>ZVB</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>RECR.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>B.Z.A.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Niet spelend</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Andere sport</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Erelid</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Vereniginsfunctie</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Trainer</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'OldisSaturdayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisSundayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisFutsalMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldisNonPlayingMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisOtherSportMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'OldisHonoraryMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'OldisClubTrainer']" mode="string"/>
                </fo:table-row>
                <fo:table-row>
                  <xsl:apply-templates select="property[@name = 'isSaturdayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isSundayMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isFutsalMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'isNonPlayingMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isOtherSportMember']" mode="string"/>
                  <xsl:apply-templates select="property[@name = 'isHonoraryMember']" mode="string"/>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'isClubTrainer']" mode="string"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"> </fo:block>
            <!-- table start Contributie gegevens-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="32mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>Contributie</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Betaalwijze</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Contributie periode</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Soort legitimatie bewijs</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nummer</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <!-- table start Contributie gegevens typen -->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="18mm"/>
              <fo:table-column column-width="11mm"/>
              <fo:table-column column-width="6mm"/>
              <fo:table-column column-width="7mm"/>
              <fo:table-column column-width="8mm"/>
              <fo:table-column column-width="11mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="12mm"/>
              <fo:table-column column-width="14mm"/>
              <fo:table-column column-width="49mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>code</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Incasso</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Acc.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Giro.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Nota</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Maand</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Kwart.</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>1/2 jaar</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Jaar</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>----------</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>----------</fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>-</fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <fo:block space-before.optimum="8pt"/>
            <!-- table start  Datum verwerkt-->
            <fo:table table-layout="fixed">
              <fo:table-column column-width="116mm"/>
              <fo:table-column column-width="32mm"/>
              <fo:table-column column-width="30mm"/>
              <fo:table-body font-size="8pt">
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block> </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block>Datum verwerkt</fo:block>
                  </fo:table-cell>
                  <xsl:apply-templates select="property[@name = 'ProcessingDate']" mode="ignore"/>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
            <!-- table end -->
            <!--                 End of LMK                             -->
          </fo:flow>
        </fo:page-sequence>
      </fo:root>
    </xsl:if>
  </xsl:template>
  <xsl:template match="property" mode="string">
    <xsl:variable name="name" select="normalize-space(@name)"/>
    <xsl:choose>
      <xsl:when test="contains($name, 'Old')">
        <fo:table-cell>
          <fo:block>
            <xsl:value-of select="@value"/>
          </fo:block>
        </fo:table-cell>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="oldvalue" select="normalize-space(../property[@name = concat('Old', $name)]/@value)"/>
        <!--<fo:table-cell>
          <fo:block>
            <xsl:value-of select="normalize-space(../property[@name = concat('Old', $name)]/@value)"/>
          </fo:block>
          </fo:table-cell>-->
        <xsl:choose>
          <xsl:when test="normalize-space($oldvalue) = normalize-space(@value)">
            <fo:table-cell>
              <fo:block>
                <xsl:value-of select="@value"/>
              </fo:block>
            </fo:table-cell>
          </xsl:when>
          <xsl:otherwise>
            <fo:table-cell>
              <fo:block font-weight="bold">
                <xsl:value-of select="@value"/>
              </fo:block>
            </fo:table-cell>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match="property" mode="date">
    <xsl:variable name="name" select="normalize-space(@name)"/>
    <xsl:choose>
      <xsl:when test="starts-with($name, 'Old')">
        <fo:table-cell>
          <fo:block>
            <xsl:value-of select="substring-before(@value,  ' ')"/>
          </fo:block>
        </fo:table-cell>
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="oldvalue" select="normalize-space(../property[@name = concat('Old', $name)]/@value)"/>
        <!--<fo:table-cell>
          <fo:block>
            <xsl:value-of select="concat(@value, ' ; ', $oldvalue)"/>
          </fo:block>
          </fo:table-cell>-->
        <xsl:choose>
          <xsl:when test="normalize-space($oldvalue) = normalize-space(@value)">
            <fo:table-cell>
              <fo:block>
                <xsl:value-of select="substring-before(@value,  ' ')"/>
              </fo:block>
            </fo:table-cell>
          </xsl:when>
          <xsl:otherwise>
            <fo:table-cell>
              <fo:block font-weight="bold">
                <xsl:value-of select="substring-before(@value,  ' ')"/>
              </fo:block>
            </fo:table-cell>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <xsl:template match="property" mode="bold">
    <fo:table-cell>
      <fo:block font-weight="bold">
        <xsl:value-of select="normalize-space(@value)"/>
      </fo:block>
    </fo:table-cell>
  </xsl:template>
  <xsl:template match="property" mode="ignore">
    <fo:table-cell>
      <fo:block>
        <xsl:value-of select="@value"/>
      </fo:block>
    </fo:table-cell>
  </xsl:template>
  <xsl:template match="property" mode="date_ignore">
    <fo:table-cell>
      <fo:block>
        <xsl:value-of select="normalize-space(substring-before(@value,  ' '))"/>
      </fo:block>
    </fo:table-cell>
  </xsl:template>
</xsl:stylesheet>
