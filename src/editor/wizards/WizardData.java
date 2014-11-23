package editor.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @author Nishad Agrawal
 *
 */
public class WizardData {
    private Map<String, String> myData;
    private List<WizardData> myWizards;

    public WizardData () {
        myData = new HashMap<>();
        myWizards = new ArrayList<>();
    }

    public void addDataPair (String key, String value) {
        myData.put(key, value);
    }

    public void addWizardData (WizardData wizData) {
        myWizards.add(wizData);
    }

    public Map<String, String> getData () {
        return myData;
    }

    public List<WizardData> getWizards () {
        return myWizards;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        for (String key : myData.keySet()) {
            sb.append(key + ": " + myData.get(key) + "\n");
        }
        for (WizardData wiz : myWizards) {
            sb.append(wiz.toString());
        }
        return sb.toString();
    }

}
