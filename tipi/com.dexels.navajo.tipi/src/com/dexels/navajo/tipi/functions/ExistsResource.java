/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiContext;

/**
 * @author marte
 * 
 * 
 */
public class ExistsResource extends FunctionInterface {

	private final static Logger logger = LoggerFactory
			.getLogger(ExistsResource.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Checks for the existence of a resource";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "ExistsResource(Context,name)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() == 0) {
			throw new TMLExpressionException(this,
					"ExistsResource NEEDS arguments");
		}

		Object pp = getOperand(0);
		if (pp == null) {
			throw new TMLExpressionException(this,
					"Invalid operand: null context ");
		}
		if (pp instanceof TipiContext) {
			TipiContext tt = (TipiContext) pp;

			String name = (String) getOperand(1);
			// try to get the resource. Any exception = false. Success = true.
			try
			{
		        return Boolean.valueOf(check(tt.getResourceURL(name)));
			}
			catch(Exception e)
			{
				logger.debug("ExistsResource could not find resource in context {} with name " + name + " because of exception: ", tt.toString(), e);
				// logging statement on e.g. debug specifying the exception to ensure that information is not lost?
				return Boolean.FALSE;
			}
		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

    public boolean check(URL u) {
        InputStream os = null;
        try {
            os = u.openConnection().getInputStream();
           return true;
        } catch (IOException e) {
           return false;
        } finally {
            if (os!=null) {
               try {
                   os.close();
               } catch (IOException e) {
            	   logger.info("Closing problem: ",e);
               }
           }
        }
    }

}
