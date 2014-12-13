package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.gui.GUIPanePath;


/**
 * This is the concrete implementation of a Wizard used to create a new LevelState within our
 * GameState. It is spawned within the file menu and holds two textfields for campaignName and
 * campaignLevel. It is important to note that the Wizard does not hold the responsibility for
 * checking whether or not the campaign actually exists because it has no knowledge of the
 * GameState. Therefore, it will send this to the class which launches the wizard which should hold
 * the checking knowledge.
 * 
 * @author Nishad
 *
 */
public class LevelWizard extends Wizard {

    private final String NEW_LEVEL_DEFAULT_KEY = "NewLevelDefault";
    private final String CAMPAIGN_KEY = "Campaign";
    private final String LEVEL_WIDTH_KEY = "LevelWidth";
    private final String LEVEL_HEIGHT_KEY = "LevelHeight";
    private final String PARTICIPANT_KEY = "NewParticipant";

    private final int PARTICIPANT_WIZARD_WIDTH = 500;
    private final int PARTICIPANT_WIZARD_HEIGHT = 500;
    private final String BACKGROUND_KEY = "Background";

    @FXML
    private ComboBox<String> campaignName;
    @FXML
    private TextField levelName;
    @FXML
    private TextField levelWidth;
    @FXML
    private TextField levelHeight;
    @FXML
    private Button participant;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button image;

    private ObservableList<String> campaigns;
    private String backgroundPath;

    @Override
    public boolean checkCanSave () {
        return campaignName.getSelectionModel().selectedItemProperty().isNotNull().get() &&
               !levelName.getText().isEmpty() && !levelWidth.getText().isEmpty() &&
               isNumber(levelWidth.getText()) &&
               !levelHeight.getText().isEmpty() &&
               isNumber(levelHeight.getText()) && !backgroundPath.isEmpty();
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.LEVEL);
        addToData(WizardDataType.CAMPAIGN_NAME, campaignName.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.NAME, levelName.getText());
        addToData(WizardDataType.WIDTH, levelWidth.getText());
        addToData(WizardDataType.HEIGHT, levelHeight.getText());
        addToData(WizardDataType.IMAGE, backgroundPath);
    }

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            levelName.promptTextProperty().bind(util.getStringProperty(NEW_LEVEL_DEFAULT_KEY));
            campaignName.promptTextProperty().bind(util.getStringProperty(CAMPAIGN_KEY));
            levelWidth.promptTextProperty().bind(util.getStringProperty(LEVEL_WIDTH_KEY));
            levelHeight.promptTextProperty().bind(util.getStringProperty(LEVEL_HEIGHT_KEY));
            participant.textProperty().bind(util.getStringProperty(PARTICIPANT_KEY));
            image.textProperty().bind(util.getStringProperty(BACKGROUND_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void initialize () {
        super.initialize();
        backgroundPath = "";
        campaigns = FXCollections.observableList(new ArrayList<>());
        campaignName.setItems(campaigns);
        participant.setOnAction(e -> launchParticipantEditor());
        image.setOnAction(e -> selectBackground());
    }

    private void selectBackground () {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        backgroundPath = (file.getPath() == null) ? "" : file.getPath();
    }

    private void launchParticipantEditor () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.PARTICIPANT_WIZARD,
                                              new Dimension(PARTICIPANT_WIZARD_WIDTH,
                                                            PARTICIPANT_WIZARD_HEIGHT
                                              ));
        Consumer<WizardData> cons = (data) -> {
            addWizardData(data);
            wiz.closeStage();
        };
        wiz.setSubmit(cons);
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        campaignName.getSelectionModel()
                .select(oldValues.getValueByKey(WizardDataType.CAMPAIGN_NAME));
        levelName.setText(oldValues.getValueByKey(WizardDataType.NAME));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        campaigns.addAll(values);
    }
}
