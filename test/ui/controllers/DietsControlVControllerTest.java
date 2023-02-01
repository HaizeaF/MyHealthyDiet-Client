package ui.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import main.DietMain;
import objects.Diet;
import objects.GoalEnum;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author JulenB
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DietsControlVControllerTest extends ApplicationTest {

    private Button buttonInsertRow;
    private TableView tableViewDiets = lookup("#tableViewDiets").query();
    private TextField texfieldSearchbar;
    private Button buttonSearch;
    private MenuItem menuItemDelete;
    private Button buttonFilters;
    private TableColumn<Diet, String> tableColumnDietName;
    private TableColumn<Diet, String> tableColumnDescription;
    private TableColumn<Diet, Float> tableColumnCalories;
    private TableColumn<Diet, Float> tableColumnProteins;
    private TableColumn<Diet, Float> tableColumnLipids;
    private TableColumn<Diet, Float> tableColumnCarbohydrates;
    private TableColumn<Diet, GoalEnum> tableColumnType;
    private TableColumn<Diet, Void> tableColumnPlates;
    private TableColumn<Diet, Void> tableColumnTips;
    private TableColumn<Diet, byte[]> tableColumnImage;
    private Pane panePlates;
    private Pane paneTipsAdmin;
    private Button buttonDiets;
    private Pane paneDiets;

    /**
     * Test of the initial stage of the DietsControlVController view
     */
    @Test
    public void testA_InitialStage() {
        verifyThat("#buttonInsertRow", isEnabled());
        verifyThat("#tableViewDiets", isEnabled());
        verifyThat("#texfieldSearchbar", hasText(""));
        verifyThat("#buttonSearch", isEnabled());
        verifyThat("#buttonFilters", isEnabled());
        verifyThat("#tableColumnDietName", isEnabled());
        verifyThat("#tableColumnDescription", isEnabled());
        verifyThat("#tableColumnCalories", isEnabled());
        verifyThat("#tableColumnProteins", isEnabled());
        verifyThat("#tableColumnLipids", isEnabled());
        verifyThat("#tableColumnCarbohydrates", isEnabled());
        verifyThat("#tableColumnType", isEnabled());
        verifyThat("#tableColumnPlates", isEnabled());
        verifyThat("#tableColumnTips", isEnabled());
        verifyThat("#tableColumnImage", isEnabled());

    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(DietMain.class);
    }

    /**
     * Test of search method clicking buttonSearch, of class
     * DietsControlVController.
     */
    @Test
    @Ignore
    public void testB_HandleButtonSearchAction() {
        List<Diet> dataList;
        texfieldSearchbar = lookup("#texfieldSearchbar").query();
        buttonSearch = lookup("#buttonSearch").query();

        clickOn(texfieldSearchbar);
        write("c");

        clickOn(buttonSearch);

        dataList = new ArrayList<>(tableViewDiets.getItems());
        for (Diet diet : dataList) {
            assertTrue(diet.getDietName().contains("c"));
        }

    }

    /**
     * Test of search method by pressing ENTER key, of class
     * DietsControlVController.
     */
    @Test
    @Ignore
    public void testC_HandleButtonSearchEnterAction() {
        List<Diet> dataList;
        texfieldSearchbar = lookup("#texfieldSearchbar").query();
        buttonSearch = lookup("#buttonSearch").query();

        clickOn(texfieldSearchbar);
        eraseText(1);

        clickOn(texfieldSearchbar);
        write("v");
        push(KeyCode.ENTER);

        dataList = new ArrayList<>(tableViewDiets.getItems());
        for (Diet diet : dataList) {
            assertTrue(diet.getDietName().contains("v"));
        }

    }

    /**
     * Test of filters method, of class DietsControlVController.
     */
    @Test
    @Ignore
    public void testD_HandleButtonFiltersAction() {
        List<Diet> dataList;
        buttonFilters = lookup("#buttonFilters").query();
        texfieldSearchbar = lookup("#texfieldSearchbar").query();

        texfieldSearchbar.clear();
        push(KeyCode.ENTER);

        clickOn(buttonFilters);
        verifyThat("Filters", isVisible());

        dataList = new ArrayList<>(tableViewDiets.getItems());
        for (Diet diet : dataList) {

        }

    }

    /**
     * Test of create method, of class DietsControlVController.
     */
    @Test
    @Ignore
    public void testE_HandleCreateAction() {
        Integer count = tableViewDiets.getItems().size();

        buttonInsertRow = lookup("#buttonInsertRow").query();
        clickOn(buttonInsertRow);

        assertEquals(tableViewDiets.getItems().size(), count + 1);

    }

    /**
     * Test of update method, of class DietsControlVController.
     */
    @Test
    @Ignore
    public void testF_HandleUpdateAction() {

        assertNotEquals("tableViewDiets has no data: Cannot test.", tableViewDiets.getItems().size(), 0);

        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: tableViewDiets has not that row. ", row);
        clickOn(row);
        Integer tablerow = tableViewDiets.getSelectionModel().getSelectedIndex();

        Node tableColumnDietName = lookup("#tableColumnDietName").nth(tablerow + 1).query();
        Node tableColumnDescription = lookup("#tableColumnDescription").nth(tablerow + 1).query();
        Node tableColumnCalories = lookup("#tableColumnCalories").nth(tablerow + 1).query();
        Node tableColumnProteins = lookup("#tableColumnProteins").nth(tablerow + 1).query();
        Node tableColumnLipids = lookup("#tableColumnLipids").nth(tablerow + 1).query();
        Node tableColumnCarbohydrates = lookup("#tableColumnCarbohydrates").nth(tablerow + 1).query();
        Node tableColumnType = lookup("#tableColumnType").nth(tablerow + 1).query();
        Node tableColumnPlates = lookup("#tableColumnPlates").nth(tablerow + 1).query();
        Node tableColumnTips = lookup("#tableColumnTips").nth(tablerow + 1).query();
        Node tableColumnImage = lookup("#tableColumnImage").nth(tablerow + 1).query();

        Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
        String name = selectedDiet.getDietName();
        String description = selectedDiet.getDescription();
        Float calories = selectedDiet.getCalories();
        Float proteins = selectedDiet.getProteins();
        Float lipids = selectedDiet.getLipids();
        Float carbohydrates = selectedDiet.getCarbohydrates();
        GoalEnum goal = selectedDiet.getType();

        clickOn(tableColumnDietName);
        write("modifiedDiet");
        push(KeyCode.ENTER);

        clickOn(tableColumnDescription);
        write("description");
        push(KeyCode.ENTER);

        clickOn(tableColumnCalories);
        write("1,0");
        push(KeyCode.ENTER);

        clickOn(tableColumnProteins);
        write("1,0");
        push(KeyCode.ENTER);

        clickOn(tableColumnLipids);
        write("1,0");
        push(KeyCode.ENTER);

        clickOn(tableColumnCarbohydrates);
        write("1,0");
        push(KeyCode.ENTER);

        doubleClickOn(tableColumnType);
        push(KeyCode.DOWN);
        clickOn(tableColumnCarbohydrates);
        doubleClickOn(tableColumnType);
        push(KeyCode.DOWN);
        clickOn(tableColumnCarbohydrates);
        doubleClickOn(tableColumnType);
        push(KeyCode.DOWN);

        Diet modifiedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();

        assertNotEquals("The name has been modified", name, modifiedDiet.getDietName());
        assertNotEquals("The description has been modified", description, modifiedDiet.getDescription());
        assertNotEquals("The calories has been modified", calories, modifiedDiet.getCalories());
        assertNotEquals("The proteins has been modified", proteins, modifiedDiet.getProteins());
        assertNotEquals("The lipids has been modified", lipids, modifiedDiet.getLipids());
        assertNotEquals("The carbohydrates has been modified", carbohydrates, modifiedDiet.getCarbohydrates());
        assertNotEquals("The goal has been modified", goal, modifiedDiet.getType());

//        clickOn(tableColumnPlates);
//        panePlates = lookup("#panePlates").query();
//        assertThat(panePlates, isVisible());
//        clickOn(buttonDiets);
//        assertThat(paneDiets, isVisible());
//        clickOn(tableColumnTips);
//        paneTipsAdmin = lookup("#paneTipsAdmin").query();
//        assertThat(paneTipsAdmin, isVisible());
    }

    /**
     * Test of delete method, of class DietsControlVController.
     */
    @Test
    @Ignore
    public void testG_HandleDeleteAction() {
        assertNotEquals("tableViewDiets has no data: Cannot test.", tableViewDiets.getItems().size(), 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: tableViewDiets has not that row. ", row);
        Integer count = tableViewDiets.getItems().size();

        rightClickOn(row);
        clickOn("#menuItemDelete");
        verifyThat("Are you sure you want to delete this diet?", isVisible());
        push(KeyCode.ENTER);
        assertEquals(tableViewDiets.getItems().size(), count - 1);

    }
}
