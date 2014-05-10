package net.atlanticbb.tantlinger.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Various IO utility methods
 * 
 * @author Bob Tantlinger
 *
 */
public class IOUtils
{
    private static final int BUFFER_SIZE = 1024 * 4;
    private static final NullCopyMonitor nullMon = new NullCopyMonitor();
    
    
    private static void getDirectoryContents(List onlyFiles, File rootdir)
    {
        File[] list = rootdir.listFiles();
        for(int i = 0; i < list.length; i++)
        {
            File eachFile = (java.io.File)list[i];
            if(eachFile.isDirectory())
            {
                getDirectoryContents(onlyFiles, eachFile);
            }
            else if(eachFile.isFile())
            {
                onlyFiles.add(eachFile);
            }
        }
    }

    public static File[] getDirectoryContents(File rootdir)
    {
        List list = new ArrayList();
        getDirectoryContents(list, rootdir);
        return (File[])list.toArray(new File[list.size()]);
    }
    
    public static String sanitize(String x)
    {
        /* Characters that are OK in a file are described
        by regular expressions as:

         \w - alphanumeric (A-Za-z0-9)
         \. - dot
         \- - dash
         \: - colon
         \; - semicolon
         \# - number sign
         \_ - underscore

       Each \ above must be escaped to allow javac to parse
       it correctly. That's why it looks so bad below.

       Since we want to replace things that are not the above,
       set negation ([^ and ]) is used.
     */  
        return x.replaceAll("[^\\w\\.\\-\\:\\;\\#\\_]", "_");
    }
    
    /**
     * Gets the extension of a file e.g anything after the '.'
     * @param f The file from which to get the extension
     * @return The extension, or an empty string if the file has no extension
     */
    public static String getExtension(File f)
    {
        String name = f.getName();
        
        int i = name.lastIndexOf(".");        
        if(i == -1 || i == name.length() - 1)
            return "";        
        
        return name.substring(i + 1, name.length());
    }
    
    /**
     * Gets the name of the file without the extension
     * 
     * @param f The file
     * @return The name, sans any extension
     */
    public static String getName(File f)
    {
        String name = f.getName();
        
        int i = name.lastIndexOf(".");        
        if(i == -1)
            return name;        
        
        return name.substring(0, i);
    }
    
    /**
     * If the specified file already exists, a new, uniquely named file is
     * returned. It's name is incremented. For example if a file named "file.txt"
     * already exists, a file named "file-1.txt" is returned.
     * 
     * @param f
     * @return
     */
    public static File createUniqueFile(File f)
    {        
        while(f.exists())
        {
            /*String uniqueName = (getExtension(f).equals("")) ? 
                (getName(f) + count) : 
                (count + "." + getExtension(f));*/
            
            String ext = getExtension(f);
            String name = getName(f);
            int dashPos = name.lastIndexOf('-');
            if(dashPos == -1)
            {
                name = name + "-1";
            }
            else
            {
                String num = name.substring(dashPos + 1, name.length());
                String temp = name.substring(0, dashPos);
                try
                {
                    int cur = Integer.parseInt(num) + 1;
                    name = temp + "-" + cur;
                }
                catch(NumberFormatException nfe)
                {
                    name = name + "-1";
                }                
            }
                
            String uniqueName = name;
            if(!ext.equals(""))
                uniqueName = uniqueName + "." + ext;
            
            String parent = "";
            if(f.getParent() != null)
                parent = f.getParent();
            f = new File(parent, uniqueName);            
        }
        
        return f;
    }

    /**
     * Copies a Reader to a Writer.
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copy(Reader src, Writer dst) throws IOException
    {
        char[] buffer = new char[BUFFER_SIZE];
        int n = 0;
        while((n = src.read(buffer)) != -1)
        {
            dst.write(buffer, 0, n);
        }
    }
    
    /**
     * Copies an InputStream to an OutputStream
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copy(InputStream src, OutputStream dst) throws IOException
    {
        copy(src, dst, nullMon);
    }
    
    
    /**
     * Copies an InputStream to an OutputStream
     * @param src
     * @param dst
     * @param mon monitors the bytes copied and if the copy was aborted
     * @throws IOException
     */
    public static void copy(InputStream src, OutputStream dst, CopyMonitor mon) throws IOException
    {
        byte[] buffer = new byte[BUFFER_SIZE];
        int n = 0;
        while((n = src.read(buffer)) != -1 && !mon.isCopyAborted())
        {
            dst.write(buffer, 0, n); 
            mon.bytesCopied(n);
        }
        //mon.copyComplete();
    }
    
    /**
     * Copies a file. Overwrites the destination file if it already exists
     * @param src
     * @param dst
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(File src, File dst) throws FileNotFoundException, IOException
    {
        copy(src, dst, nullMon);
    }
    
    /**
     * Copies a file.
     * 
     * @param src
     * @param dst
     * @param overwrite If true overwrites the dest file if it exists
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(File src, File dst, boolean overwrite)
    throws FileNotFoundException, IOException
    {
        copy(src, dst, nullMon, overwrite);
    }
    
    /**
     * Copies a file. Overwrites the destination file if it already exists
     * @param src
     * @param dst
     * @param mon monitors the copy
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(File src, File dst, FileCopyMonitor mon) throws FileNotFoundException, IOException
    {        
        copy(src, dst, mon, true);        
    }
    
    /**
     * Copies a file
     * 
     * @param src The source file
     * @param dst The destination file
     * @param mon Monitors the copy
     * @param overwrite if true, overwrites a destination file with the same name
     * otherwise, it creates a new destination file with a unique name before the copy
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(File src, File dst, FileCopyMonitor mon, boolean overwrite)
    throws FileNotFoundException, IOException
    {
        if(!overwrite)
            dst = createUniqueFile(dst);
        
        //System.err.println("copy " + dst);
        mon.copyingFile(src);
        InputStream in = null;
        OutputStream out = null;        
        try
        {
            in = new FileInputStream(src);        
            out = new FileOutputStream(dst);
            copy(in, out, mon);
        }
        catch(FileNotFoundException nfe)
        {
            throw nfe;
        }
        catch(IOException ioe)
        {
            throw ioe;
        }
        finally
        {        
            close(in);
            close(out);  
        }
    }
    
    

    /**
     * Copies the file at the specified source path to the destination path.
     * Overwrites the destination file if it already exists.
     * @param srcPath
     * @param dstPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(String srcPath, String dstPath) throws FileNotFoundException, IOException
    {
        copy(new File(srcPath), new File(dstPath));
    }
    
    /**
     * Copies the file at the specified source path to the destination path.
     * Overwrites the destination file if it already exists.
     * @param srcPath
     * @param mon Monitors the copy
     * @param dstPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(String srcPath, String dstPath, FileCopyMonitor mon) 
    throws FileNotFoundException, IOException
    {
        copy(new File(srcPath), new File(dstPath), mon);
    }
    
    /**
     * Copies the file at the specified source path to the destination path.
     * 
     * @param srcPath
     * @param dstPath
     * @param overwrite If true overwrites the file at the dest path if it exists
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(String srcPath, String dstPath, boolean overwrite) 
    throws FileNotFoundException, IOException
    {
        copy(new File(srcPath), new File(dstPath), overwrite);
    }
    
    
    /**
     * Copies the file at the specified source path to the destination path.
     * 
     * @param srcPath
     * @param dstPath
     * @param mon Monitors the copy
     * @param overwrite If true overwrites the file at the dest path if it exists
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void copy(String srcPath, String dstPath, FileCopyMonitor mon, boolean overwrite) 
    throws FileNotFoundException, IOException
    {
        copy(new File(srcPath), new File(dstPath), mon, overwrite);
    }
    
    
    public static void copyFiles(File src, File dest) 
    throws IOException, FileNotFoundException
    {
        copyFiles(src, dest, nullMon);
    }
    
    /**
     * Recursively copy all files from one directory to another.
     * 
     * @param src File or directory to copy from.
     * @param dest File or directory to copy to.
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws IOException
     */
    public static void copyFiles(File src, File dest, FileCopyMonitor mon) 
    throws FileNotFoundException, IOException
    {
        copyFilesRecursively(src, dest, mon);
        //mon.copyComplete();
    }
    
    
    private static void copyFilesRecursively(File src, File dest, FileCopyMonitor mon) 
    throws FileNotFoundException, IOException 
    {        
        if(mon.isCopyAborted())                   
            return;
        if(!src.exists())
            throw new FileNotFoundException("File not found:" + src);

        if(src.isDirectory()) 
        {            
            // Create destination directory
            if(!dest.exists()) 
            {
                dest.mkdirs();
            }
            // Go through the contents of the directory
            String list[] = src.list();
            
            java.util.Arrays.sort(list);            
            for(int i = 0; i < list.length; i++) 
            {
                copyFilesRecursively(new File(src, list[i]), new File(dest, list[i]), mon);
            }
        } 
        else 
        {
            copy(src, dest, mon, true);
        }
    }
    
    /**
     * Gets the total bytes in the file or directory
     * 
     * @param file A File or directoy.
     * @return The total bytes of the File. If the File is a directory
     * the total bytes of all files in all subfolders is returned
     */
    public static long getTotalBytes(File file)
    {
        long bytes = 0;
        
        if(file.isDirectory())
        {
            File f[] = file.listFiles();
            for(int i = 0; i < f.length; i++)
                bytes += getTotalBytes(f[i]);
        }
        else
        {
            bytes = file.length();
        }
        
        return bytes;
    }
    

    public static String read(InputStream input) throws IOException
    {
        return read(new InputStreamReader(input));
    }

    /**
     * Reads a File and returns the contents as a String
     * @param file The File to read
     * @return The contents of the file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String read(File file) throws FileNotFoundException, IOException
    {
        return read(new FileReader(file));
    }

    /**
     * Reads a String from a Reader
     * @param input
     * @return
     * @throws IOException
     */
    public static String read(Reader input) throws IOException
    {
        BufferedReader reader = new BufferedReader(input);
        StringBuffer sb = new StringBuffer();
        int ch;

        while((ch = reader.read()) != -1)
            sb.append((char)ch);

        close(reader);
        
        return sb.toString();
    }


    /**
     * Writes a String to a File using a PrintWriter
     * @param file
     * @param str
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void write(File file, String str) throws FileNotFoundException, IOException
    {      
        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        out.print(str);  
        close(out);
    }
    
    /**
     * Writes the raw data from an InputStream to a File
     * @param file
     * @param input
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void write(File file, InputStream input) throws FileNotFoundException, IOException
    {
        InputStream in = new BufferedInputStream(input);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        int ch;
        while((ch = in.read()) != -1)        
            out.write(ch);        
        
        close(in);
        close(out);
    }

    /**
     * Recursively deletes a directory, thereby removing all its contents
     * @param file the file or file to delete
     * @return true if the file(s) were successfully deleted
     */
    public static boolean deleteRecursively(File file)
    {
        if(file.isDirectory())
        {
            File[] children = file.listFiles();
            for(int i = 0; i < children.length; i++)
            {
                boolean success = deleteRecursively(children[i]);
                if(!success)
                    return false;
            }
        }

        return file.delete();
    }

    /**
     * Closes a Closeable, swallowing any exceptions
     * @param out
     */
    public static void close(InputStream c)
    {
        if(c != null)
        {
            try
            {
                c.close();
            }
            catch(IOException ignored)
            {
            }
        }
    }
    
    public static void close(OutputStream c)
    {
        if(c != null)
        {
            try
            {
                c.close();
            }
            catch(IOException ignored)
            {
            }
        }
    }
    
    public static void close(Reader c)
    {
        if(c != null)
        {
            try
            {
                c.close();
            }
            catch(IOException ignored)
            {
            }
        }
    }
    
    public static void close(Writer c)
    {
        if(c != null)
        {
            try
            {
                c.close();
            }
            catch(IOException ignored)
            {
            }
        }
    }

    
    private static class NullCopyMonitor implements FileCopyMonitor
    {        
        public void bytesCopied(int numBytes){}             
        public void copyingFile(File f){}
        //public void copyComplete(){}
        
        public boolean isCopyAborted()
        {
            return false;
        }
    }
}