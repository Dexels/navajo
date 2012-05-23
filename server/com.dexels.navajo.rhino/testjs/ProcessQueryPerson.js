// -- Compiled code. Edit at your own peril.
addMethod("person/ProcessUpdatePerson",['Person']);
if(evaluateNavajo("!FALSE")==true) {
   addMethod("person/ProcessDeletePerson",['Person']);
}
if(evaluateNavajo("true")==true) {
   addParam("PersonId","Aap()",{});
}
else if(evaluateNavajo("false")==true) {
   addParam("PersonId","[/Person/PersonId]",{});
}
callMap("com.dexels.navajo.adapter.SQLMap",function() {
   addField("datasource","'default'",null);
   addField("query","\
               SELECT lastname\
               ,      infix\
               ,      initials\
               ,      firstname\
               ,      dateofbirth\
               ,      nationality\
               ,      sex\
               ,      streetname \
               ,      housenumber\
               ,      numberappendix\
               ,      city\
               ,      zipcode\
               ,      countrycode\
               ,      telephonenumber\
               ,      mobilenumber\
               ,      emailaddress\
               FROM   person\
               WHERE  id = ?\
            ",null);
   addField("parameter","[/@PersonId]",null);
   if(evaluateNavajo("$rowCount > 0")==true) {
      addMessage("Person",function() {
         addProperty("PersonId","[/@PersonId]",{description:"Code",direction:"out",type:"integer"});
         addProperty("LastName","$columnValue('lastname')",{description:"Lastname",direction:"in",type:"string"});
         addProperty("Infix","$columnValue('infix')",{description:"Infix",direction:"in",type:"string"});
         addProperty("Initials","$columnValue('initials')",{description:"Initials",direction:"in",type:"string"});
         addProperty("FirstName","$columnValue('firstname')",{description:"Firstname",direction:"in",type:"string"});
         addProperty("DateOfBirth","$columnValue('dateofbirth')",{description:"Birthdate",direction:"in",type:"date"});
         if(evaluateNavajo("$columnValue('dateofbirth') != null")==true) {
            addProperty("Age","Age($columnValue('dateofbirth'))",{description:"Age",direction:"out",type:"integer"});
         }
         else {
            addProperty("Age","null",{description:"Age",direction:"out",type:"integer"});
         }
         addProperty("Nationality","$columnValue('nationality')",{description:"Nationality",direction:"in",type:"string"});
         addProperty("Sex",null,{description:"Sex",direction:"in",type:"selection"});
         addProperty("StreetName","$columnValue('streetname')",{description:"Streetname",direction:"in",type:"string"});
         addProperty("HouseNumber","$columnValue('housenumber')",{description:"Housenumber",direction:"in",type:"integer"});
         addProperty("NumberAppendix","$columnValue('numberappendix')",{description:"Nr. appendix",direction:"in",type:"string"});
         addProperty("City","$columnValue('city')",{description:"City",direction:"in",type:"string"});
         addProperty("ZipCode","$columnValue('zipcode')",{description:"Zipcode",direction:"in",type:"string"});
         addProperty("CountryCode","$columnValue('countrycode')",{description:"Countrycode",direction:"in",type:"string"});
         addProperty("TelephoneNumber","$columnValue('telephonenumber')",{description:"Telephone",direction:"in",type:"string"});
         addProperty("MobileNumber","$columnValue('mobilenumber')",{description:"Mobile",direction:"in",type:"string"});
         addProperty("EmailAddress","$columnValue('emailaddress')",{description:"Emailaddress",direction:"in",type:"string"});
         addParam("PhotoDir","'/tmp/'",{});
         addProperty("Photo","File([/@PhotoDir] + [/@PersonId])",{description:"Photo",direction:"in",type:"binary"});
      });
   }
});
// compiletime: 24

