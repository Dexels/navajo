package com.dexels.navajo.server;


import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.*;


public class MaintainanceRequest extends Request {

    public static final String NAVAJO_VERSION = "Navajo v2";

    // Navajo method names
    public static final String METHOD_NAVAJO = "navajo";
    public static final String METHOD_NAVAJO_SHOWUSERS = METHOD_NAVAJO
            + "_showusers";
    public static final String METHOD_NAVAJO_SHOWPARAMETERS = METHOD_NAVAJO
            + "_showparameters";
    public static final String METHOD_NAVAJO_SHOWCONDITIONS = METHOD_NAVAJO
            + "_showconditions";
    public static final String METHOD_NAVAJO_ADD_USER = METHOD_NAVAJO
            + "_add_user";
    public static final String METHOD_NAVAJO_ADD_SERVICE = METHOD_NAVAJO
            + "_add_service";
    public static final String METHOD_NAVAJO_AUTHORISE = METHOD_NAVAJO
            + "_authorise";
    public static final String METHOD_NAVAJO_SHOW_AUTHORISED = METHOD_NAVAJO
            + "_show_authorised";
    public static final String METHOD_NAVAJO_ADD_DEFINITION = METHOD_NAVAJO
            + "_add_definition";
    public static final String METHOD_NAVAJO_DELETE_DEFINITION = METHOD_NAVAJO
            + "_delete_definition";
    public static final String METHOD_NAVAJO_ADD_VALUE = METHOD_NAVAJO
            + "_add_value";
    public static final String METHOD_NAVAJO_FIND_VALUE = METHOD_NAVAJO
            + "_find_value";
    public static final String METHOD_NAVAJO_EDIT_VALUE = METHOD_NAVAJO
            + "_edit_value";
    public static final String METHOD_NAVAJO_DELETE_VALUE = METHOD_NAVAJO
            + "_delete_value";
    public static final String METHOD_NAVAJO_SHOW_VALUES = METHOD_NAVAJO
            + "_show_values";
    public static final String METHOD_NAVAJO_DELETE_USER = METHOD_NAVAJO
            + "_delete_user";
    public static final String METHOD_NAVAJO_DELETE_SERVICE = METHOD_NAVAJO
            + "_delete_service";
    public static final String METHOD_NAVAJO_DELETE_AUTHORISATION = METHOD_NAVAJO
            + "_delete_authorisation";
    public static final String METHOD_NAVAJO_ADD_CONDITION = METHOD_NAVAJO
            + "_add_condition";
    public static final String METHOD_NAVAJO_SHOW_CONDITIONS = METHOD_NAVAJO
            + "_show_conditions";
    public static final String METHOD_NAVAJO_FIND_CONDITION = METHOD_NAVAJO
            + "_find_condition";
    public static final String METHOD_NAVAJO_EDIT_CONDITION = METHOD_NAVAJO
            + "_edit_condition";
    public static final String METHOD_NAVAJO_DELETE_CONDITION = METHOD_NAVAJO
            + "_delete_condition";
    public static final String METHOD_NAVAJO_SHOWLOG = METHOD_NAVAJO
            + "_showlog";
    public static final String METHOD_NAVAJO_SHOW_VIEW = METHOD_NAVAJO
            + "_show_view";
    public static final String METHOD_NAVAJO_LOGON = METHOD_NAVAJO + "_logon";
    public static final String METHOD_NAVAJO_LOGON_SEND = METHOD_NAVAJO
            + "_logon_send";
    public static final String METHOD_NAVAJO_PING = METHOD_NAVAJO + "_ping";

    private Repository repository = null;
    private Authorisation authorisation = null;

    public MaintainanceRequest(HashMap rb, Repository repository) {
        super(rb);
        Util.debugLog("In MaintainanceRequest constructor()");
        Util.debugLog("Leaving constructor");
        this.repository = repository;
        if (repository instanceof SQLRepository)
            authorisation = ((SQLRepository) repository).getAuthorisation();
    }

    public void addUsersToMessage(Access access, Parameters parms, Navajo inMessage, boolean multiple) throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {

        Message users = Util.getMessage(inMessage, "users", true);

        Vector allUsers = authorisation.allUsers(access);

        Util.debugLog(2, "Got all users: " + allUsers.size());

        String card = (multiple) ? "+" : "1";

        Property userprop = Property.create(inMessage, "all_users", card, "Alle gebruikers", Property.DIR_IN);

        for (int i = 0; i < allUsers.size(); i++) {
            Util.debugLog(2, "Adding user: " + (String) allUsers.get(i));
            Selection sel = Selection.create(inMessage, (String) allUsers.get(i), i + "", false);

            userprop.addSelection(sel);
        }

        users.addProperty(userprop);
    }

    public void addServicesToMessage(Access access, Parameters parms, Navajo inMessage, boolean multiple) throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {

        Message services = Util.getMessage(inMessage, "services", true);

        String all[] = repository.getServices(access);

        if (all != null) {
            Util.debugLog("About to get allServices");
            // Vector allServices = authorisation.allServices(access);

            Util.debugLog("Got them");
            // Util.debugLog("Got all services: " + allServices.size());

            String card = (multiple) ? "+" : "1";

            Property serviceprop = Property.create(inMessage, "all_services", card, "Alle diensten", Property.DIR_IN);

            Util.debugLog("Adding property: " + serviceprop);

            for (int i = 0; i < all.length; i++) {
                Util.debugLog("Adding user: " + all[i]);
                Selection sel = Selection.create(inMessage, all[i], i + "", false);

                serviceprop.addSelection(sel);
            }

            services.addProperty(serviceprop);
        } else {
            // Add free text field for servicename if database not available.
            Property serviceprop = Property.create(inMessage, "service", Property.STRING_PROPERTY, "", 25, "Requested Navajo service", Property.DIR_IN);

            services.addProperty(serviceprop);
        }
    }

    public void addDefinitionsToMessage(Access access, Parameters parms, Navajo inMessage) throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {

        Message definitions = Util.getMessage(inMessage, "definitions", true);

        Vector all = authorisation.allDefinitions(access);

        Util.debugLog(2, "Got all definitions: " + all.size());

        Property prop = Property.create(inMessage, "all_definitions", "1", "Alle definities", Property.DIR_IN);

        for (int i = 0; i < all.size(); i++) {
            Parameter param = (Parameter) all.get(i);

            Util.debugLog(2, "Adding definition: " + param.name);
            Selection sel = Selection.create(inMessage, param.name, i + "", false);

            prop.addSelection(sel);
        }

        definitions.addProperty(prop);
    }

    public Navajo addUser(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message new_user = Util.getMessage(inMessage, "new_user", true);
        Property prop1 = Util.getProperty(new_user, "username", true);
        Property prop2 = Util.getProperty(new_user, "password", true);

        int userId = authorisation.addUser(access, prop1.getValue().trim(), prop2.getValue().trim());

        if (userId == -1)
            throw new UserException(UserException.USER_EXISTS, prop1.getValue());

        outMessage = getThanksMessage("Gebruiker is toegevoegd (id = " + userId + ")");

        Util.debugLog(2, "About to leave addUser()");

        return outMessage;
    }

    /**
     * Delete a user and all of its references.
     */
    public Navajo deleteUser(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        String user = ((Selection) selectedUsers.get(0)).getName();

        int userId = authorisation.deleteUser(access, user);

        if (userId == -1)
            throw new UserException(UserException.USER_EXISTS, "");

        outMessage = getThanksMessage("Gebruiker is verwijderd (id = " + userId + ")");

        Util.debugLog(2, "About to leave deleteUser()");

        return outMessage;
    }

    /**
     * Delete a service and all of its references.
     */
    public Navajo deleteService(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message msg = Util.getMessage(inMessage, "services", true);
        Property prop = Util.getProperty(msg, "all_services", true);
        ArrayList all = prop.getAllSelectedSelections();
        String service = ((Selection) all.get(0)).getName();

        int id = 0;

        authorisation.deleteService(access, service);

        if (id == -1)
            throw new UserException(UserException.USER_EXISTS, "");

        outMessage = getThanksMessage("Dienst is verwijderd (id = " + id + ")");

        Util.debugLog(2, "About to leave deleteService()");

        return outMessage;
    }

    public Navajo addService(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message new_user = Util.getMessage(inMessage, "new_service", true);
        Property prop1 = Util.getProperty(new_user, "servicename", true);
        Property prop2 = Util.getProperty(new_user, "servlet", false);

        int serviceId = authorisation.addService(access, prop1.getValue(), 0);

        if ((prop2 != null) && (!prop2.getValue().equals("")))
            authorisation.addDispatch(access, prop1.getValue(), prop2.getValue());
        else
            authorisation.addDispatch(access, prop1.getValue(), "GenericServlet");

        if (serviceId == -1)
            throw new UserException(UserException.USER_EXISTS, prop1.getValue());

        outMessage = getThanksMessage("Service is toegevoegd (id = " + serviceId + ")");

        Util.debugLog(2, "About to leave addService()");

        return outMessage;
    }

    public Navajo addDefinition(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message new_def = Util.getMessage(inMessage, "new_definition", true);
        Property prop1 = Util.getProperty(new_def, "name", true);
        Property prop2 = Util.getProperty(new_def, "type", true);

        ArrayList all = prop2.getAllSelectedSelections();
        String type = ((Selection) all.get(0)).getName();

        Util.debugLog(2, "Adding: " + prop1.getValue() + "(type = " + type + ")");

        int defId = authorisation.addDefinition(access, prop1.getValue(), type);

        if (defId == -1)
            throw new UserException(UserException.USER_EXISTS, prop1.getValue());

        outMessage = getThanksMessage("Definitie is toegevoegd (id = " + defId + ")");

        Util.debugLog(2, "About to leave addDefinition()");

        return outMessage;
    }

    /**
     * Delete a parameter definition and all related records.
     */
    public Navajo deleteDefinition(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message msg = Util.getMessage(inMessage, "definitions", true);
        Property prop = Util.getProperty(msg, "all_definitions", true);

        ArrayList all = prop.getAllSelectedSelections();
        String definition = ((Selection) all.get(0)).getName();

        int id = authorisation.deleteDefinition(access, definition);

        outMessage = getThanksMessage("Definitie is verwijderd (id = " + id + ")");

        return outMessage;
    }

    public Navajo deleteValue(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message parameter = Util.getMessage(inMessage, "parameter", true);
        Property idProp = Util.getProperty(parameter, "id", true);
        int id = Util.getInt(idProp.getValue());

        authorisation.deleteValue(access, id);
        // authorisation.deleteValue(access, Integer.toString(id));

        outMessage = getThanksMessage("Parameter is verwijderd");

        return outMessage;

    }

    public void showValue(Access access, Navajo inMessage, Navajo outMessage) throws SystemException, UserException,
            java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {

        Message parameter = Util.getMessage(inMessage, "parameter", true);
        Property idProp = Util.getProperty(parameter, "id", true);
        int id = Util.getInt(idProp.getValue());

        Parameter parm = authorisation.showParameter(access, id);

        if (parm == null) {
            throw new UserException(UserException.USER_EXISTS, "");
        }

        parameter = Util.getMessage(outMessage, "parameter", true);
        idProp = Util.getProperty(parameter, "id", true);
        idProp.setValue(parm.id + "");

        Message new_value = Util.getMessage(outMessage, "new_value", true);
        Property value = Util.getProperty(new_value, "value", true);
        Property condition = Util.getProperty(new_value, "condition", true);

        value.setValue(parm.value.toString());
        condition.setValue(parm.condition);
    }

    public Navajo editValue(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message parameter = Util.getMessage(inMessage, "parameter", true);
        Message new_value = Util.getMessage(inMessage, "new_value", true);

        Property idProp = Util.getProperty(parameter, "id", true);
        Property valueProp = Util.getProperty(new_value, "value", true);
        Property conditionProp = Util.getProperty(new_value, "condition", true);

        Util.debugLog(2, "valueProp: " + valueProp);
        Util.debugLog(2, "conditionProp: " + conditionProp);

        int id = Util.getInt(idProp.getValue());

        String value = valueProp.getValue();
        String condition = "";

        if (conditionProp != null)
            condition = conditionProp.getValue();

        Util.debugLog(2, "value: " + value);
        Util.debugLog(2, "condition: " + condition);

        authorisation.editValue(access, id, value, condition);

        outMessage = getThanksMessage("Parameter is aangepast");

        return outMessage;

    }

    public Navajo addValue(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message users = Util.getMessage(inMessage, "users", true);
        Message definitions = Util.getMessage(inMessage, "definitions", true);
        Message new_value = Util.getMessage(inMessage, "new_value", true);

        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        String user = ((Selection) selectedUsers.get(0)).getName();

        Property defProp = Util.getProperty(definitions, "all_definitions", true);
        ArrayList selectedDefs = defProp.getAllSelectedSelections();
        String definition = ((Selection) selectedDefs.get(0)).getName();

        Property valueProp = Util.getProperty(new_value, "value", true);
        Property conditionProp = Util.getProperty(new_value, "condition", false);

        String value = valueProp.getValue();
        String condition = "";

        if (conditionProp != null)
            condition = conditionProp.getValue();

        authorisation.addValue(access, user, definition, value, condition);

        outMessage = getThanksMessage("Parameter waarde is toegevoegd");

        Util.debugLog(2, "About to leave addDefinition()");

        return outMessage;
    }

    //
    public Navajo addCondition(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();

        Message services = Util.getMessage(inMessage, "services", true);
        Property serviceProp = Util.getProperty(services, "all_services", true);
        ArrayList selectedServices = serviceProp.getAllSelectedSelections();

        Message new_condition = Util.getMessage(inMessage, "new_condition", true);
        String condition = Util.getProperty(new_condition, "condition", true).getValue();
        String comment = Util.getProperty(new_condition, "comment", true).getValue();

        for (int i = 0; i < selectedUsers.size(); i++) {
            for (int j = 0; j < selectedServices.size(); j++) {
                String user = ((Selection) selectedUsers.get(i)).getName();
                String service = ((Selection) selectedServices.get(j)).getName();

                authorisation.addCondition(access, user, service, condition, comment);
            }
        }

        outMessage = getThanksMessage("Conditie(s) zijn toegevoegd");

        Util.debugLog(2, "About to leave addCondition()");

        return outMessage;
    }

    public Navajo addAuthorisation(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Util.debugLog(2, "in addauthorisation()");

        Message users = Util.getMessage(inMessage, "users", true);
        Message services = Util.getMessage(inMessage, "services", true);

        Property userProp = Util.getProperty(users, "all_users", true);
        Property serviceProp = Util.getProperty(services, "all_services", true);

        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        ArrayList selectedServices = serviceProp.getAllSelectedSelections();

        Util.debugLog(2, "selectedUsers.size(): " + selectedUsers.size());// + ", value: " + (String) selectedUsers.get(0));
        Util.debugLog(2, "selectedServices.size(): " + selectedServices.size());// + ", value: " + (String) selectedServices.get(0));

        String user = ((Selection) selectedUsers.get(0)).getName();
        String service = ((Selection) selectedServices.get(0)).getName();

        // int result = authorisation.addAuthorisation(access, user, service);
        int result = authorisation.addAuthorisation(access, user, 0);

        if (result == 0)
            outMessage = getThanksMessage(user + " is now authorised to use " + service);
        else
            outMessage = getThanksMessage(user + " was already authorised to use " + service);

        return outMessage;
    }

    /*
     public Navajo deleteAuthorisation(Access access, Parameters parms, Navajo inMessage)
     throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
     org.xml.sax.SAXException, NavajoException
     {
     Navajo outMessage = null;

     Util.debugLog(2, "in deleteauthorisation()");

     Message users = Util.getMessage(inMessage, "users", true);
     Message services = Util.getMessage(inMessage, "services", true);

     Property userProp = Util.getProperty(users, "all_users", true);
     Property serviceProp = Util.getProperty(services, "all_services", true);

     ArrayList selectedUsers = userProp.getAllSelectedSelections();
     ArrayList selectedServices = serviceProp.getAllSelectedSelections();

     Util.debugLog(2, "selectedUsers.size(): " + selectedUsers.size());// + ", value: " + (String) selectedUsers.get(0));
     Util.debugLog(2, "selectedServices.size(): " + selectedServices.size());// + ", value: " + (String) selectedServices.get(0));

     String user = ((Selection) selectedUsers.get(0)).getName();
     String service = ((Selection) selectedServices.get(0)).getName();

     int result = authorisation.deleteAuthorisation(access, user, service);

     if (result == 0)
     outMessage = getThanksMessage(user + " is no longer authorised to use " + service);
     else
     outMessage = getThanksMessage(user + " was not authorised to use " + service);

     return outMessage;
     }
     */
    public void showCondition(Access access, Navajo inMessage, Navajo outMessage) throws SystemException, UserException,
            java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {

        Message condition = Util.getMessage(inMessage, "condition", true);
        Property idProp = Util.getProperty(condition, "id", true);
        int id = Util.getInt(idProp.getValue());

        ConditionData cnd = authorisation.showCondition(access, id);

        if (cnd == null) {
            throw new UserException(UserException.USER_EXISTS, "");
        }

        condition = Util.getMessage(outMessage, "condition", true);
        idProp = Util.getProperty(condition, "id", true);
        idProp.setValue(cnd.id + "");

        Message new_condition = Util.getMessage(outMessage, "new_condition", true);
        Property cond = Util.getProperty(new_condition, "condition", true);
        Property comment = Util.getProperty(new_condition, "comment", true);

        cond.setValue(cnd.condition);
        comment.setValue(cnd.comment);
    }

    public Navajo editCondition(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message condition = Util.getMessage(inMessage, "condition", true);
        Message new_condition = Util.getMessage(inMessage, "new_condition", true);

        Property idProp = Util.getProperty(condition, "id", true);
        Property conditionProp = Util.getProperty(new_condition, "condition", true);
        Property commentProp = Util.getProperty(new_condition, "comment", true);

        int id = Util.getInt(idProp.getValue());

        String cond = conditionProp.getValue();
        String comment = "";

        if (commentProp != null)
            comment = commentProp.getValue();

        authorisation.editCondition(access, id, cond, comment);

        outMessage = getThanksMessage("Conditie is aangepast");

        return outMessage;

    }

    public Navajo deleteCondition(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message condition = Util.getMessage(inMessage, "condition", true);
        Property idProp = Util.getProperty(condition, "id", true);
        int id = Util.getInt(idProp.getValue());

        authorisation.deleteCondition(access, id);

        outMessage = getThanksMessage("Conditie is verwijderd");

        return outMessage;

    }

    public Navajo showConditions(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = new Navajo();

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();

        Message services = Util.getMessage(inMessage, "services", true);
        Property serviceProp = Util.getProperty(services, "all_services", true);
        ArrayList selectedServices = serviceProp.getAllSelectedSelections();

        Message first = Message.create(outMessage, "selected_conditions");

        outMessage.addMessage(first);
        for (int i = 0; i < selectedUsers.size(); i++) {
            String user = ((Selection) selectedUsers.get(i)).getName();
            Message msg = Message.create(outMessage, user);

            outMessage.addMessage(msg);
            for (int j = 0; j < selectedServices.size(); j++) {
                String service = ((Selection) selectedServices.get(j)).getName();
                Message msg2 = Message.create(outMessage, service);

                msg.addMessage(msg2);
                Vector result = authorisation.showConditions(access, user, service);

                for (int k = 0; k < result.size(); k++) {
                    Message msg3 = Message.create(outMessage, "condition" + k);

                    msg2.addMessage(msg3);
                    ConditionData cnd = (ConditionData) result.get(k);
                    Property id =
                            Property.create(outMessage, "id", Property.INTEGER_PROPERTY, cnd.id + "", 0, "Id", Property.DIR_OUT);
                    Property condition =
                            Property.create(outMessage, "condition", Property.STRING_PROPERTY, cnd.condition, 0, "Conditie", Property.DIR_OUT);
                    Property comment =
                            Property.create(outMessage, "comment", Property.STRING_PROPERTY, cnd.comment, 0, "Commentaar", Property.DIR_OUT);

                    msg3.addProperty(id);
                    msg3.addProperty(condition);
                    msg3.addProperty(comment);
                }
            }
        }

        return outMessage;
    }

    public Navajo showAuthorised(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = new Navajo();

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        String user = ((Selection) selectedUsers.get(0)).getName();

        Vector all = authorisation.showAuthorised(access, user);

        Message services = Message.create(outMessage, "authorised_services");

        outMessage.addMessage(services);

        for (int i = 0; i < all.size(); i++) {
            String service = (String) all.get(i);
            Property prop = Property.create(outMessage, "service" + i, Property.STRING_PROPERTY, service,
                    0, "", Property.DIR_OUT);

            services.addProperty(prop);
        }

        return outMessage;
    }

    public Navajo showLogView(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = new Navajo();

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        Vector userVector = new Vector();

        for (int i = 0; i < selectedUsers.size(); i++) {
            userVector.add(((Selection) selectedUsers.get(i)).getName());
        }

        Message services = Util.getMessage(inMessage, "services", true);
        Property serviceProp = Util.getProperty(services, "all_services", true);
        ArrayList selectedServices = serviceProp.getAllSelectedSelections();
        Vector serviceVector = new Vector();

        for (int i = 0; i < selectedServices.size(); i++) {
            serviceVector.add(((Selection) selectedServices.get(i)).getName());
        }

        ArrayList levels = Util.getMessage(inMessage, "logview", true).getProperty("log_level").getAllSelectedSelections();
        Vector levelVector = new Vector();

        for (int i = 0; i < levels.size(); i++) {
            levelVector.add(((Selection) levels.get(i)).getValue());
        }

        Property allUsers = Util.getMessage(inMessage, "logview", true).getProperty("all_users");
        Property allServices = Util.getMessage(inMessage, "logview", true).getProperty("all_services");
        Property beginDate = Util.getMessage(inMessage, "logview", true).getProperty("begin_date");
        Property endDate = Util.getMessage(inMessage, "logview", true).getProperty("end_date");

        boolean everyUser = (allUsers.getValue().equals("1")) ? true : false;
        boolean everyService = (allServices.getValue().equals("1"))
                ? true
                : false;

        Vector result = authorisation.showLogView(access, userVector, serviceVector, levelVector,
                everyUser,
                everyService,
                beginDate.getValue(), endDate.getValue());

        Message logview = Message.create(outMessage, "log_output");

        outMessage.addMessage(logview);
        for (int i = 0; i < result.size(); i++) {
            Message logdata = Message.create(outMessage, "log_data" + i);

            logview.addMessage(logdata);
            LogData lg = (LogData) result.get(i);
            Property prop = Property.create(outMessage, "date", Property.DATE_PROPERTY,
                    Util.formatDate(lg.entered), 0, "Invoer datum",
                    Property.DIR_OUT);

            logdata.addProperty(prop);
            prop = Property.create(outMessage, "timestamp", Property.DATE_PROPERTY,
                    lg.timestamp, 0, "Invoer tijd",
                    Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "user", Property.STRING_PROPERTY,
                    lg.user, 0, "Gebruiker", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "service", Property.STRING_PROPERTY,
                    lg.rpc, 0, "Dienst", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "level", Property.STRING_PROPERTY,
                    lg.level, 0, "Niveau", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "comment", Property.STRING_PROPERTY,
                    lg.comment, 0, "Commentaar", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "hostname", Property.STRING_PROPERTY,
                    lg.hostName, 0, "Host naam", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "address", Property.STRING_PROPERTY,
                    lg.ipAddress, 0, "IP adres", Property.DIR_OUT);
            logdata.addProperty(prop);
            prop = Property.create(outMessage, "user_agent", Property.STRING_PROPERTY,
                    lg.userAgent, 0, "Client software", Property.DIR_OUT);
            logdata.addProperty(prop);

        }

        return outMessage;
    }

    public Navajo showValues(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = new Navajo();

        Message users = Util.getMessage(inMessage, "users", true);
        Property userProp = Util.getProperty(users, "all_users", true);
        ArrayList selectedUsers = userProp.getAllSelectedSelections();
        String user = ((Selection) selectedUsers.get(0)).getName();

        Vector all = authorisation.showParameters(access, user);

        Message parameters = Message.create(outMessage, "parameters");

        outMessage.addMessage(parameters);
        Property prop = null;

        for (int i = 0; i < all.size(); i++) {
            Message parameter = Message.create(outMessage, "parameter" + i);

            parameters.addMessage(parameter);

            Parameter parm = (Parameter) all.get(i);

            prop = Property.create(outMessage, "id", Property.INTEGER_PROPERTY, parm.id + "", 0, "Id", Property.DIR_OUT);
            parameter.addProperty(prop);
            prop = Property.create(outMessage, "name", Property.STRING_PROPERTY, parm.name, 0, "Parameter", Property.DIR_OUT);
            parameter.addProperty(prop);
            prop = Property.create(outMessage, "type", Property.STRING_PROPERTY, parm.type, 0, "Type", Property.DIR_OUT);
            parameter.addProperty(prop);
            prop = Property.create(outMessage, "value", Property.STRING_PROPERTY, parm.value.toString(), 20, "Waarde", Property.DIR_OUT);
            parameter.addProperty(prop);
            prop = Property.create(outMessage, "condition", Property.STRING_PROPERTY, parm.condition, 20, "Conditie", Property.DIR_OUT);
            parameter.addProperty(prop);

        }

        return outMessage;
    }

    public Navajo logonSend(Access access, Parameters parms, Navajo inMessage)
            throws SystemException, UserException, java.sql.SQLException, java.io.IOException,
            org.xml.sax.SAXException, NavajoException {
        Navajo outMessage = null;

        Message identification = Util.getMessage(inMessage, "identification", true);
        String username = Util.getProperty(identification, "username", true).getValue();
        String password = Util.getProperty(identification, "password", true).getValue();

        Message services = Util.getMessage(inMessage, "services", true);
        Property serviceProp = Util.getProperty(services, "all_services", false);
        String service = "";

        if (serviceProp == null) {
            service = Util.getProperty(services, "service", true).getValue();
        } else {
            ArrayList selectedServices = serviceProp.getAllSelectedSelections();

            service = ((Selection) selectedServices.get(0)).getName();
        }

        Access newAccess = repository.authorizeUser(username, password, service);

        if ((newAccess.userID != -1) && (newAccess.serviceID != -1))
            outMessage = getThanksMessage("geauthoriseerd");
        else
            throw new UserException(SystemException.NOT_AUTHORISED, "");

        return outMessage;
    }

}
