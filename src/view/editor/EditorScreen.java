package view.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.CampaignState;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelIdentifier;
import model.state.LevelState;
import view.GUILoadStyleUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIPanePath;
import view.gui.GUIScreen;

/**
 * 
 * Screen for the Editor. Holds many sub-GUI panes
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */

public class EditorScreen extends GUIScreen {

	@FXML
	private TabPane tabPane;
	@FXML
	private TreeView<String> projectExplorer;
	@FXML
	private ProjectExplorerController projectExplorerController;
	@FXML
	private VBox gameInfoBox;
	@FXML
	private DescribableInfoBoxController gameInfoBoxController;
	@FXML
	private Parent editorMenuBar;
	@FXML
	private EditorMenuBarController editorMenuBarController;
	@FXML
	private BorderPane editorRoot;
	@FXML
	private Button newGameElement;
	@FXML
	private Button newTerrain;
	@FXML
	private Button save;
	@FXML
	private Accordion levelElementAccordion;
	@FXML
	private ElementAccordionController levelElementAccordionController;

	private List<TabViewController> myTabViewControllers;
	private Tab myCurrentTab;
	private final int myHashingPrime = 31;

	@Override
	public Node getRoot() {
		return editorRoot;
	}

	private void initProjectExplorer() {
		projectExplorerController.setOnSelectionChanged((String[] selection) -> {
			updateInfoBox(selection);
		});
		projectExplorerController.setOnLevelClicked((String level) -> {
			String campaign = projectExplorerController.getSelectedHierarchy()[1];
			launchTab(new LevelIdentifier(level, campaign));
		});
	}

	public void updateModelToSave() {
		myTabViewControllers.forEach((tabController) -> {
			tabController.updateModelToSave();
		});
	}

	private void initInfoBox() {
		gameInfoBoxController.setSubmitAction((name, description) -> {
			try {
				myMainModel.updateDescribableState(projectExplorerController.getSelectedHierarchy(), name,
						description);
			} catch (CampaignNotFoundException | LevelNotFoundException e) {
				DialogBoxUtility.createMessageDialog(String.format(
						"Failed to update selected describable state due to: %s", e.toString()));
			}
		});
	}

	private void updateInfoBox(String[] selection) {
		try {
			DescribableState state = myMainModel.getDescribableState(selection);
			gameInfoBoxController.setText(state.getName(), state.getDescription());
		} catch (Exception e) {
			DialogBoxUtility.createMessageDialog(String.format(
					"Failed to update InfoBox based on project selection due to: %s", e.toString()));
		}
	}

	private void initTabs() {
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldTab, newTab) -> tabChanged(observable, oldTab, newTab));
	}

	private void launchTab(LevelIdentifier levelID) {
		Optional<Tab> option = tabPane
				.getTabs()
				.stream()
				.filter((tab) -> ((CampaignLevelPair) tab.getUserData())
						.equals(new CampaignLevelPair(levelID))).findFirst();
		Tab tab = (option.isPresent()) ? option.get() : createNewTab(levelID);
		tabPane.getSelectionModel().select(tab);
	}

	private Tab createNewTab(LevelIdentifier levelID) {
		Optional<TabViewController> option = myTabViewControllers.stream()
				.filter((tabController) -> tabController.isLevel(levelID)).findFirst();
		TabViewController tabController;
		if (option.isPresent()) {
			tabController = option.get();
		} else {
			tabController = (TabViewController) GUILoadStyleUtility
					.generateGUIPane(GUIPanePath.EDITOR_TAB_VIEW.getFilePath());
			attachChildContainers(tabController);
			try {
				tabController.setLevel(levelID);
				levelElementAccordionController.setLevel(levelID);
				tabController.modelUpdate();
			} catch (LevelNotFoundException | CampaignNotFoundException e) {
				// Should not happen
				DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
			}
			myTabViewControllers.add(tabController);
		}
		Tab tab = new Tab(String.format("%s: %s", levelID.campaignName, levelID.levelName));
		tab.setUserData(new CampaignLevelPair(levelID));
		tab.setContent((BorderPane) tabController.getRoot());
		tabPane.getTabs().add(tab);
		return tab;
	}

	private void tabChanged(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
		myCurrentTab = newTab;
		if (myCurrentTab != null) {
			CampaignLevelPair id = (CampaignLevelPair) myCurrentTab.getUserData();
			try {
				levelElementAccordionController.setLevel(new LevelIdentifier(id.myLevel.getName(),
						id.myCampaign.getName()));
			} catch (LevelNotFoundException | CampaignNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init() {
		myTabViewControllers = new ArrayList<>();
		attachChildContainers(editorMenuBarController, levelElementAccordionController);
		initTabs();
		initProjectExplorer();
		initInfoBox();
		editorMenuBarController.attachScreen(this);
	}

	@Override
	public void modelUpdate() {
		updateProjectExplorer();
		updateTabTexts();
		updateInfoBox(projectExplorerController.getSelectedHierarchy());
	}

	private void updateTabTexts() {
		tabPane.getTabs().forEach((tab) -> {
			CampaignLevelPair id = (CampaignLevelPair) tab.getUserData();
			tab.setText(String.format("%s: %s", id.myCampaign.getName(), id.myLevel.getName()));
		});
	}

	private void updateProjectExplorer() {
		GameState game = myMainModel.getCurrentGame();
		Map<String, List<String>> campaignLevelMap = new HashMap<>();
		List<String> campaigns = game.getCampaigns().stream().map((campaign) -> {
			return campaign.getName();
		}).collect(Collectors.toList());
		game.getCampaigns().forEach((campaignState) -> {
			campaignLevelMap.put(campaignState.getName(), campaignState.getLevels().stream().map((level) -> {
				return level.getName();
			}).collect(Collectors.toList()));
		});
		projectExplorerController.update(game.getName(), campaigns, campaignLevelMap);
	}

	/**
	 * private datastructure to differentiate between different tabs
	 * 
	 * @author Jonathan Tseng
	 *
	 */
	private class CampaignLevelPair {
		public CampaignState myCampaign;
		public LevelState myLevel;

		public CampaignLevelPair(LevelIdentifier levelID) {
			try {
				myCampaign = myMainModel.getCampaign(levelID.campaignName);
				myLevel = myMainModel.getLevel(levelID);
			} catch (CampaignNotFoundException | LevelNotFoundException e) {
				// Should never happen
				DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
			}
		}

		@Override
		public int hashCode() {
			final int prime = myHashingPrime; // this prime is magic. should be
												// extracted.
			int result = 1;
			result = prime * result + ((myCampaign == null) ? 0 : myCampaign.getName().hashCode());
			result = prime * result + ((myLevel == null) ? 0 : myLevel.getName().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CampaignLevelPair other = (CampaignLevelPair) obj;
			return myCampaign.getName() == other.myCampaign.getName()
					&& myLevel.getName() == other.myLevel.getName();
		}
	}

}
