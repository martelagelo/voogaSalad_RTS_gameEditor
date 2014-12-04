package view.editor.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class is the data structure which backs all Wizards. The data structure holds a type
 * specified by an enum along with a map of enums to Strings to specify the data at the lowest
 * level. Furthermore, one can nest WizardData objects within one another to represent Wizards being
 * nested within one another.
 * 
 * @author Nishad Agrawal
 *
 */
public class WizardData {
    private WizardType myType;
    private Map<WizardDataType, String> myData;
    private List<WizardData> myWizards;

    public WizardData () {
        myType = WizardType.UNSPECIFIED;
        myData = new HashMap<>();
        myWizards = new ArrayList<>();
    }

    public void setType (WizardType type) {
        myType = type;
    }

    public void addDataPair (WizardDataType key, String value) {
        myData.put(key, value);
    }

    public void addWizardData (WizardData wizData) {
        myWizards.add(wizData);
    }
    
    public void clear() {
        myData.clear();
        myWizards.clear();
    }

    public Map<WizardDataType, String> getData () {
        return myData;
    }

    public List<WizardData> getWizards () {
        return myWizards;
    }

    public WizardType getType () {
        return myType;
    }

    public String getValueByKey (WizardDataType key) {
        return myData.get(key);
    }

    public List<WizardData> getWizardDataByType (WizardType type) {
        return myWizards.stream().filter(e -> e.getType().equals(type))
                .collect(Collectors.toList());
    }
    
    public void removeWizardData (WizardData data) {
        myWizards.remove(data);
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
