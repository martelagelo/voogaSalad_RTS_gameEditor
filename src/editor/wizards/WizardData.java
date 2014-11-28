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
    private WizardDataType myType;
    private Map<WizardDataType, String> myData;
    private List<WizardData> myWizards;

    public WizardData () {
        myType = WizardDataType.UNSPECIFIED;
        myData = new HashMap<>();
        myWizards = new ArrayList<>();
    }

    public void setType (WizardDataType type) {
        myType = type;
    }

    public void addDataPair (WizardDataType key, String value) {
        myData.put(key, value);
    }

    public void addWizardData (WizardData wizData) {
        myWizards.add(wizData);
    }

    public Map<WizardDataType, String> getData () {
        return myData;
    }

    public List<WizardData> getWizards () {
        return myWizards;
    }

    public WizardDataType getType () {
        return myType;
    }

    public String getValueByKey(WizardDataType key) {
        return myData.get(key);
    }
    
    public List<WizardData> getWizardDataByType (WizardDataType type) {
        return myWizards.stream().filter(e -> e.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder(myType + "\n");
        for (WizardDataType key : myData.keySet()) {
            sb.append(key + ": " + myData.get(key) + "\n");
        }
        for (WizardData wiz : myWizards) {
            sb.append(wiz.toString());
        }
        return sb.toString();
    }

}
