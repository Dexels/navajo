package metadata;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data class to store information on a file format.
 * @author Marco Schmidt
 */
public class FormatDescription implements Comparable, Serializable
{
	private List fileExtensions;
	private String group;
	private String longName;
	private byte[] magicBytes;
	private List mimeTypes;
	private Integer minimumSize;
	private Integer offset;
	private String shortName;

	public FormatDescription() {
	}
	
	/**
	 * Add a single file extension to the internal list of typical file extensions for this format.
	 * @param ext file extension
	 */
	public void addFileExtension(String ext)
	{
		if (ext == null || ext.length() < 1)
		{
			return;
		}
		if (fileExtensions == null)
		{
			fileExtensions = new ArrayList();
		}
		fileExtensions.add(ext);
	}

	/**
	 * Add all file extensions to the internal list of typical file extensions for this format.
	 * @param ext a comma-separated list of typical file extensions
	 */
	public void addFileExtensions(String ext)
	{
		if (ext == null)
		{
			return;
		}
		if (ext.indexOf(',') == -1)
		{
			addFileExtension(ext);
			return;
		}
		String[] extensions = ext.split(",");
		for (int i = 0; i < extensions.length; i++)
		{
			addFileExtension(extensions[i]);
		}
	}

	/**
	 * Add a single MIME type to the internal list of MIME types for this format.
	 * @param mimeType the MIME type to be added
	 */
	public void addMimeType(String mimeType)
	{
		if (mimeType == null || mimeType.length() < 1)
		{
			return;
		}
		if (mimeTypes == null)
		{
			mimeTypes = new ArrayList();
		}
		mimeTypes.add(mimeType);
	}

	/**
	 * Add a list of MIME types to the internal list of MIME types for this format.
	 * @param mimeType a comma-separated list of MIME types to be added
	 */
	public void addMimeTypes(String mimeType)
	{
		if (mimeType == null)
		{
			return;
		}
                //System.err.println("in addMimeTypes(" + mimeType + ")");
		if (mimeType.indexOf(',') == -1)
		{
			addMimeType(mimeType);
			return;
		}
		String[] types = mimeType.split(",");
		for (int i = 0; i < types.length; i++)
		{
			addMimeType(types[i]);
		}
	}

	public int compareTo(Object obj)
	{
		FormatDescription desc = (FormatDescription)obj;
		int relation = getGroup().compareTo(desc.getGroup());
		if (relation != 0)
		{
			return relation;
		}
		return getLongName().compareTo(desc.getLongName());
	}

	public List getFileExtensions()
	{
		return fileExtensions;
	}

	public String getGroup()
	{
		return group;
	}

	public String getLongName()
	{
		return longName;
	}

	public byte[] getMagicBytes()
	{
		return magicBytes;
	}

	public String getMimeType()
	{
   	return getMimeType(0);
	}

	public String getMimeType(int index)
	{
		if (mimeTypes != null && index >= 0 && index < mimeTypes.size())
		{
			return (String)mimeTypes.get(index);
		}
		else
		{
			return null;
		}
	}

	public List getMimeTypes()
	{
		return mimeTypes;
	}

	public Integer getMinimumSize()
	{
		return minimumSize;
	}

	public Integer getOffset()
	{
		return offset;
	}

	public String getShortName()
	{
		return shortName;
	}

	public boolean matches(byte[] data)
	{
		if (magicBytes == null || offset == null)
		{
			return false;
		}
		int index1 = 0;
		int index2 = offset.intValue();
		if (index2 + magicBytes.length > data.length)
		{
			return false;
		}
		int num = magicBytes.length;
		do
		{
			if (magicBytes[index1++] != data[index2++])
			{
				return false;
			}
			num--;
		}
		while (num > 0);
		return true;
	}

	public void setGroup(String newValue)
	{
		group = newValue;
	}

	public void setLongName(String newValue)
	{
		longName = newValue;
	}

	public void setMagicBytes(byte[] newValue)
	{
		magicBytes = newValue;
	}

	public void setMagicBytes(String newValue)
	{
		if ( newValue == null || newValue.length() < 1 )
		{
			magicBytes = null;
			return;
		}
		if (newValue.length() > 2 &&
		    newValue.charAt(0) == '"' &&
		    newValue.charAt(newValue.length() - 1) == '"')
		{
			newValue = newValue.substring(1, newValue.length() - 1);
			try
			{
				magicBytes = newValue.getBytes("iso-8859-1");
			}
			catch (UnsupportedEncodingException uee)
			{
				magicBytes = null;
			}
			return;
		}
		if ((newValue.length() % 2) == 0)
		{
			newValue = newValue.toLowerCase();
			byte[] data = new byte[newValue.length() / 2];
			int byteValue = 0;
			for (int i = 0; i < newValue.length(); i++)
			{
				char c = newValue.charAt(i);
				int number;
				if (c >= '0' && c <= '9')
				{
					number = c - '0';
				}
				else
				if (c >= 'a' && c <= 'f')
				{
					number = 10 + c - 'a';
				}
				else
				{
					return;
				}
				if ((i % 2) == 0)
				{
					byteValue = number * 16;
				}
				else
				{
					byteValue += number;
					data[i / 2] = (byte)byteValue;
				}
			}
			magicBytes = data;
		}
	}

	public void setMinimumSize(Integer newValue)
	{
		minimumSize = newValue;
	}

	public void setOffset(Integer newValue)
	{
		offset = newValue;
	}

	public void setShortName(String newValue)
	{
		shortName = newValue;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(80);
		sb.append(getGroup());
		sb.append(";");
		sb.append(getShortName());
		sb.append(";");
		sb.append(getLongName());
		sb.append(";");
		if (getMimeTypes() != null)
		{
			Iterator iter = getMimeTypes().iterator();
			while (iter.hasNext())
			{
				sb.append(iter.next());
				sb.append(";");
			}
		}
		else
		{
			sb.append(";");
		}
		sb.append(";");
		if (getFileExtensions() != null)
		{
			Iterator iter = getFileExtensions().iterator();
			while (iter.hasNext())
			{
				sb.append(iter.next());
				sb.append(";");
			}
		}
		else
		{
			sb.append(";");
		}
		sb.append(getOffset());
		sb.append(";");
		sb.append(";");
		return sb.toString();
	}
}
