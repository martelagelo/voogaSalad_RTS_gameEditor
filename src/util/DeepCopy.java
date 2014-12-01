package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * Deep copy utility that makes deep copies using serialization..
 *
 * @author Steve
 *
 */
public class DeepCopy {
    /**
     * Return a deep copy of an object
     *
     * @param originalObject the serializable object to deep copy
     * @return a deep copy of the object
     */
    public static Object deepCopy (Object originalObject) {
        Object newObject = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(originalObject);
            objectOutputStream.flush();
            objectOutputStream.close();

            ObjectInputStream inputStream =
                    new ObjectInputStream(new ByteArrayInputStream(
                                                                   byteArrayOutputStream
                                                                           .toByteArray()));
            newObject = inputStream.readObject();
        }
        catch (IOException ioE) {
            ioE.printStackTrace();
        }
        catch (ClassNotFoundException notFoundE) {
            notFoundE.printStackTrace();
        }
        return newObject;
    }

}
