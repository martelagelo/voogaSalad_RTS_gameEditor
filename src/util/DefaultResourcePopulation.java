package util;

import java.io.File;
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

    private void copyResources (String filePath) throws Exception {
        File libraryDirectory = new File(LIBRARY_DIRECTORY);
        File destinationDirectory = new File(filePath);
        FileUtils.copyDirectory(libraryDirectory, destinationDirectory);
    }
    
    /**
     * Sets a default destination to which
     * resources should be saved
     * 
     * @param destination 
     *        A string of the filepath that the resources
     *        should be saved to
     * @throws Exception
     */
    void setDefaults (String destination) throws Exception {

        class Copy implements Runnable {
            String myDestination;

            Copy (String destination) {
                myDestination = destination;
            }

            public void run () {
                try {
                    copyResources(destination);
                } catch (Exception e) {
                	
                }
            }

        }
        
        myThread = new Thread(new Copy(destination));
        myThread.start();
    }
}
