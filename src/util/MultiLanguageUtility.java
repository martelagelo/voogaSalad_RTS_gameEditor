package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Utility for supporting multiple languages in the view
 * Requires a resource (.properties) file for each language.
 * Language name is the name of the .properties file
 * 
 * Singleton pattern: 1 MultiLanguageUtility instance per program to handle language
 * (makes sense to only have 1 language in the program at a given time)
 * 
 * @author Jonathan Tseng
 *
 */
public class MultiLanguageUtility {

    private static final ObjectProperty<String> DEFAULT_STRING =
            new SimpleObjectProperty<>("String not found");
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
    private static final String DOT = ".";

    private static MultiLanguageUtility myOnlyInstance;
    private static Map<String, Map<String, String>> myLanguagesMap;
    private static Map<String, ObjectProperty<String>> myCurrentStringProperties;

    private String myCurrentLanguage;

    /**
     * private constructor for the utility
     */
    private MultiLanguageUtility () {
    }

    /**
     * returns the single instance of the utility in the program
     * 
     * @return
     */
    public static synchronized MultiLanguageUtility getInstance () {
        if (myOnlyInstance == null) {
            myOnlyInstance = new MultiLanguageUtility();
        }
        return myOnlyInstance;
    }

    /**
     * sets the current language of the program if the language property file exists
     * 
     * @param language
     */
    public void setLanguage (String language) {
        if (myLanguagesMap.containsKey(language)) {
            myCurrentLanguage = language;
            updateStringProperties();
        } else {
            throw new RuntimeException("language does not exist in utility");
        }
    }

    /**
     * returns the string property for a given key
     * 
     * @param propertyKey
     * @return
     */
    public ObjectProperty<String> getStringProperty (String propertyKey) {
        return (myCurrentStringProperties.containsKey(propertyKey)) ? myCurrentStringProperties
                .get(propertyKey) : DEFAULT_STRING;
    }

    /**
     * initializes the languages available in the utility with the
     * given array of language resource files
     * 
     * @param languageResourceFiles
     */
    public void initLanguages (String ... languageResourceFiles) {
        List<String> paths = new ArrayList<>(Arrays.asList(languageResourceFiles));
        initLanguages(paths);
    }

    /**
     * initializes the languages available in the utility with the
     * .properties files (assumes language resource files) located at the
     * directory path
     * 
     * @param languageResourcesFolder
     */
    public void initLanguages (String languageResourcesFolder) {
        File folder = new File(languageResourcesFolder);
        List<File> files = new ArrayList<>(Arrays.asList(folder.listFiles()));
        List<String> languageFiles =
                files.stream().map( (file) -> file.getAbsolutePath()).collect(Collectors.toList());
        initLanguages(languageFiles);
    }

    /**
     * private helper method that takes list of files and reads them
     * also sets a random default language
     */
    private void initLanguages (List<String> fileNames) {
        List<String> languageFiles = fileNames.stream().filter( (path) -> {
            return PROPERTIES_FILE_EXTENSION.equals(fileExtension(path));
        }).collect(Collectors.toList());
        myLanguagesMap = new HashMap<>();
        languageFiles.forEach( (file) -> {
            String resourcePath =
                    file.substring(file.lastIndexOf("src\\") + 4).replace("\\", ".")
                            .replace(PROPERTIES_FILE_EXTENSION, "");
            ResourceBundle bundle = ResourceBundle.getBundle(resourcePath);
            Map<String, String> map = new HashMap<>();
            bundle.keySet().forEach( (key) -> map.put(key, bundle.getString(key)));
            myLanguagesMap.put(languageName(file), map);
        });
        myCurrentLanguage = new ArrayList<>(myLanguagesMap.keySet()).get(0);
    }

    /**
     * gets the extension of a filePath
     * if no "." found, then returns the given filePath
     * 
     * @param filePath
     */
    private String fileExtension (String filePath) {
        return (!filePath.contains(DOT)) ? filePath : filePath.substring(filePath.lastIndexOf(DOT));
    }

    /**
     * gets the language name from the file path by removing the extension
     * 
     * @param filePath
     * @return
     */
    private String languageName (String filePath) {
        return filePath.substring(filePath.lastIndexOf("\\")+1, filePath.lastIndexOf(DOT));
    }

    /**
     * creates the string properties that are sent to GUI components to bind to
     */
    private void createStringProperties () {
        myCurrentStringProperties = new HashMap<>();
        myLanguagesMap.get(myCurrentLanguage).forEach((key, value)->{
            myCurrentStringProperties.put(key, new SimpleObjectProperty<String>(value));
        });
    }

    /**
     * updates the string properties that GUI components are bound to
     */
    private void updateStringProperties () {
        if (myCurrentStringProperties == null) {
            createStringProperties();
        }
        myLanguagesMap.get(myCurrentLanguage).forEach( (key, value) -> {
            if (myCurrentStringProperties.containsKey(key)) {
                myCurrentStringProperties.get(key).setValue(value);
            }
        });
    }

}
