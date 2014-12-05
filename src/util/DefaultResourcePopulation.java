package util;

import java.io.File;
import java.io.IOException;

import model.exceptions.SaveLoadException;

import org.apache.commons.io.FileUtils;

/**
 * Class to perform copying of default library resources to game
 * 
 * @author Rahul
 *
 */
public class DefaultResourcePopulation {

    public static final String LIBRARY_DIRECTORY = "src/resources/img/graphics/buttons";
    Thread myThread;

    private void copyResources (String filePath) throws SaveLoadException {
        File libraryDirectory = new File(LIBRARY_DIRECTORY);
        File destinationDirectory = new File(filePath);
        try {
            FileUtils.copyDirectory(libraryDirectory, destinationDirectory);
        } catch (IOException e) {
            throw new SaveLoadException("Unable to copy default resources", e);
        }

    }

    void setDefaults (String destination) throws SaveLoadException {

        class Copy implements Runnable {
            String myDestination;

            Copy (String destination) {
                myDestination = destination;
            }

            public void run () {
                try {
                    copyResources(destination);
                } catch (SaveLoadException e) {
                    return;
                }
            }
        }
        myThread = new Thread(new Copy(destination));
        myThread.start();
    }
}