package view.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIController;


/**
 * 
 * Controler for the game runner to choose a level
 * 
 * @author Jonathan Tseng
 *
 */
public class LevelChooserController implements GUIController {

    private static final String PLAY_KEY = "Play";
    private static final String CAMPAIGN_KEY = "Campaign";
    private static final String LEVEL_KEY = "Level";
    private static final String INVALID_SELECTION_KEY = "InvalidSelection";

    @FXML
    private BorderPane root;
    @FXML
    private ComboBox<String> campaignDropDown;
    @FXML
    private ComboBox<String> levelDropDown;
    @FXML
    private Button submitButton;

    private BiConsumer<String, String> mySubmitConsumer = (String campaign, String level) -> {
    };

    private Map<String, List<String>> myCampaignLevelMap;

    public void updateDropDowns (Map<String, List<String>> campaignLevelMap) {
        myCampaignLevelMap = campaignLevelMap;
        campaignDropDown.setItems(FXCollections.observableArrayList(campaignLevelMap.keySet()));
        initDropDowns();
    }

    public void setOnSubmit (BiConsumer<String, String> submitConsumer) {
        mySubmitConsumer = submitConsumer;
    }

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    public void initialize () {
        myCampaignLevelMap = new HashMap<>();
        initDropDowns();
        campaignDropDown.getSelectionModel().selectedItemProperty()
                .addListener( (observable, oldVal, newVal) -> {
                    updateLevelDropDown(newVal);
                });
        submitButton.setOnAction(e -> {
            String campaign = campaignDropDown.getSelectionModel().getSelectedItem();
            String level = levelDropDown.getSelectionModel().getSelectedItem();
            if (campaign != null && level != null) {
                mySubmitConsumer.accept(campaign, level);
            }
                else {
                    try {
                        DialogBoxUtility.createMessageDialog(MultiLanguageUtility.getInstance()
                                .getStringProperty(INVALID_SELECTION_KEY).getValue());
                    }
                    catch (Exception e1) {
                        DialogBoxUtility.createMessageDialog(e1.toString());
                    }
                }
            });
        attachTextProperties();
    }

    private void attachTextProperties () {
        try {
            campaignDropDown.promptTextProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(CAMPAIGN_KEY));
            levelDropDown.promptTextProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(LEVEL_KEY));
            submitButton.textProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(PLAY_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(e.toString());
        }

    }

    private void initDropDowns () {
        campaignDropDown.arm();
        levelDropDown.disarm();
        levelDropDown.setItems(FXCollections.observableArrayList());
    }

    private void updateLevelDropDown (String campaign) {
        levelDropDown.arm();
        levelDropDown.setItems(FXCollections.observableArrayList(myCampaignLevelMap.get(campaign)));
    }

}
