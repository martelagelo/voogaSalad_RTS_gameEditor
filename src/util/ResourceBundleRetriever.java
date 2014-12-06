package util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class to load a resource bundle from a file with a .properties
 * extension. Reference: http://stackoverflow.com/questions
 * /1172424/how-to-load-a-resource-bundle-from-a-file-resource-in-java
 * 
 * @author Rahul
 *
 */
public class ResourceBundleRetriever {
    /**
     * 
     * @param file
     *            name with .properties extension
     * @return resource bundle associated with the provided file
     * @throws MalformedURLException
     */
    public ResourceBundle getBundle (File file) throws MalformedURLException {
        File externalFolder = file.getParentFile();
        URL[] urls = { externalFolder.toURI().toURL() };
        ClassLoader loader = new URLClassLoader(urls);
        String fileName = removeFileExtension(file.getName());
        ResourceBundle bundle = ResourceBundle.getBundle(fileName, Locale.getDefault(), loader);     
        return bundle;
    }

    /**
     * Helper method to remove file extension (.properties, in this case, but
     * general enough for any other extension type)
     * 
     * @param file
     * @return file name without extensions (which may be the file name passed
     *         in when no extension exists)
     */
    protected String removeFileExtension (String fileName) {
        int fileExtensionIndex = fileName.lastIndexOf(".");
        return (fileExtensionIndex > 0) ? fileName.substring(0, fileExtensionIndex) : fileName;
    }
}
