package metadata;

import java.io.*;
import java.util.*;

/**
 * Static helper class that tries to identify the file format
 * for a given file or byte array representing the first bytes of a file.
 * <h3>Usage</h3>
 * <pre>
 * FormatDescription desc = FormatIdentification.identify(new File("testfile.zip"));
 * if (desc != null) {
 *   System.out.println(desc.getShortName());
 * }
 * </pre>
 * @author Marco Schmidt
 */
public class FormatIdentification implements Serializable
{
	private static List<FormatDescription> descriptions;
	private static int minBufferSize;

	static
	{
		init();
	}

	/**
	 * Private empty constructor to avoid instantiations.	 *
	 */
	private FormatIdentification()
	{
	}

	public static FormatDescription identify(byte[] data)
	{
		if (data == null || data.length < 1)
		{
			return null;
		}
		Iterator<FormatDescription> iter = descriptions.iterator();
		while (iter.hasNext())
		{
			FormatDescription desc = iter.next();
			if (desc.matches(data))
			{
				return desc;
			}
		}
		return null;
	}

	public static FormatDescription identify(File file)
	{
		if (!file.isFile())
		{
			return null;
		}
		long size = file.length();
		int numBytes;
		if (size > 1024)
		{
			numBytes = 1024;
		}
		else
		{
			numBytes = (int) size;
		}
		byte[] data = new byte[ numBytes + 1 ];
		RandomAccessFile in = null;
		try
		{
			in = new RandomAccessFile(file, "r");
			in.read( data, 0, numBytes );
			in.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace( System.err );
			return null;
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace( System.err );
			}
		}
		return identify(data);
	}

	private static void init()
	{
		descriptions = new ArrayList<FormatDescription>();
		minBufferSize = 1;
		try
		{
			InputStream input =  FormatIdentification.class.getResource("formats.txt").openStream();
			if (input == null)
			{
				return;
			} else {
                        }
			FormatDescriptionReader in = new FormatDescriptionReader(new InputStreamReader(input));
			FormatDescription desc;
			while ((desc = in.read()) != null)
			{
				byte[] magic = desc.getMagicBytes();
				Integer offset = desc.getOffset();
				if (magic != null && offset != null && offset.intValue() + magic.length > minBufferSize)
				{
					minBufferSize = offset.intValue() + magic.length;
				}
				descriptions.add(desc);
			}
			input.close();
		}
		catch (Exception e)
		{
                  e.printStackTrace(System.err);
		}
	}

}
