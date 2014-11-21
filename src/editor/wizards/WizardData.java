package editor.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 
 * @author Nishad Agrawal
 *
 */
public class WizardData {
    private String myName;
    private Map<String, String> myData;
    private List<WizardData> myWizards;

    public WizardData () {
        myName = "";
        myData = new HashMap<>();
        myWizards = new ArrayList<>();
    }

    public void setName (String name) {
        myName = name;
    }

    public void addDataPair (String key, String value) {
        myData.put(key, value);
    }

    public void addWizardData (WizardData wizData) {
        myWizards.add(wizData);
    }

    public List<WizardData> getWizards () {
        return myWizards;
    }

    public String getName () {
        return myName;
    }

    public String getValueByKey(String key) {
        return myData.get(key);
    }
    
    public List<WizardData> getWizardDataByType (String type) {
        return myWizards.stream().filter(e -> e.getName().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder(myName + "\n");
        for (String key : myData.keySet()) {
            sb.append(key + ": " + myData.get(key) + "\n");
        }
        for (WizardData wiz : myWizards) {
            sb.append(wiz.toString());
        }
        return sb.toString();
    }

}
