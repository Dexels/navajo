/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dexels.navajo.document.Property;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TmlPropertySerializer extends StdSerializer<Property> {

    private static final long serialVersionUID = 6193576754986884043L;

    public TmlPropertySerializer() {
        this(null);
    }

    public TmlPropertySerializer(Class<Property> t) {
        super(t);
    }

    @Override
    public void serialize(Property property, JsonGenerator jg, SerializerProvider provider) throws IOException {
        
        Object value = property.getTypedValue();
        if (value == null) {
            jg.writeNull();
        } else if (property.getType().equals(Property.DATE_PROPERTY)) {
            DateFormat df = new SimpleDateFormat(Property.DATE_FORMAT2);
            try {
                jg.writeString(df.format((Date) value));
            } catch (ClassCastException e) {
                jg.writeString(value.toString());
            }
        } else if (property.getType().equals(Property.TIMESTAMP_PROPERTY)) {
            DateFormat df = new SimpleDateFormat(Property.TIMESTAMP_FORMAT);
            try {
                jg.writeString(df.format((Date) value));
            } catch (ClassCastException e) {
                jg.writeString(value.toString());
            }
        } else if (property.getType().equals(Property.MONEY_PROPERTY) || property.getType().equals(Property.PERCENTAGE_PROPERTY)) {
            try {
                jg.writeNumber(Double.parseDouble(value.toString()));
            } catch (NumberFormatException e) {
                jg.writeString(value.toString());
            }
        } else if (property.getType().equals(Property.COORDINATE_PROPERTY)) {
            jg.writeString(value.toString());
        } else {
            JsonSerializer<Object> serializer = provider.findValueSerializer(value.getClass());
            serializer.serialize(value, jg, provider);
        }
        
    }

}
