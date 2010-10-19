package org.fanhongtao.swing;

import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

/**
 * For JDK 1.5 only.
 * For JDK 1.6, use FileNameExtensionFilter .
 * @author Dharma
 * @created 2009-5-6
 */
public class ExtensionFileFilter extends FileFilter
{
    public void addExtension(String extension)
    {
        if (!extension.startsWith("."))
        {
            extension = "." + extension;
        }
        extensions.add(extension);
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Override
    public String getDescription()
    {
        return description;
    }
    
    @Override
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }
        
        String name = f.getName().toLowerCase();
        
        for (String ext : extensions)
        {
            if (name.endsWith(ext))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private String description = "";
    
    private ArrayList<String> extensions = new ArrayList<String>();
    
}
