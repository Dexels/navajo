package net.atlanticbb.tantlinger.io;

import java.io.File;




public interface FileCopyMonitor extends CopyMonitor
{
    public void copyingFile(File f);
}
