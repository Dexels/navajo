package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Coordinate;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 */

public class ToCoordinate extends FunctionInterface {
    public ToCoordinate() {
    }

    @Override
    public String remarks() {
        return "Create a coordinate Object.";
    }

    @Override
    public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = getOperand(0);
        Object o1 = getOperand(1);
        Coordinate cor = null;
        try {
            if (o != null && o1 != null) {
                cor = new Coordinate(o, o1);
            } else {
                throw new TMLExpressionException("Error creating coordinate. No null arguments are allowed");
            }
            // verify coordinates
            if (cor.getLongitude() < -180 || cor.getLongitude() > 180) {
                throw new TMLExpressionException("Wrong Longitude. Longitude must be in [-180,180]");
            }
            if (cor.getLatitude() < -90 || cor.getLatitude() > 90) {
                throw new TMLExpressionException("Wrong Latitute. Latitude must be in [-90,90]");
            }
            return cor;

        } catch (Exception e) {
            e.printStackTrace();
            throw new TMLExpressionException(e.getMessage(), e);
        }
    }

    @Override
    public String usage() {
        return "ToCoordinate(Longitude: Double, Latitude: Double): Coordinate";
    }

    @Override
    public boolean isPure() {
        return false;
    }

}
