package ui.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.PlateControlVPrueba;
import objects.Diet;
import objects.GoalEnum;
import objects.MealEnum;
import objects.Plate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 * CRUD test for the Plate management window.
 * @author HaizeaF
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlateControlVControllerTest extends ApplicationTest {

    private final TableView tableViewPlates = lookup("#tableViewPlates").query();
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(PlateControlVPrueba.class);
    }
    
    /**
     * This method tests the initial stage of the window.
     */
    @Test
    public void test1_initialStage() {
        verifyThat("#buttonInsertRow", isEnabled());
        verifyThat("#tableViewPlates", isEnabled());
        verifyThat("#textFieldSearchBar", hasText(""));
        verifyThat("#buttonSearch", isEnabled());
        verifyThat("#buttonFilter", isEnabled());
        verifyThat("#tableColumnName", isEnabled());
        verifyThat("#tableColumnMealType", isEnabled());
        verifyThat("#tableColumnCalories", isEnabled());
        verifyThat("#tableColumnProteins", isEnabled());
        verifyThat("#tableColumnLipids", isEnabled());
        verifyThat("#tableColumnCarbohydrates", isEnabled());
        verifyThat("#tableColumnVegetarian", isEnabled());
        verifyThat("#tableColumnIngredients", isEnabled());
        verifyThat("#tableColumnImage", isEnabled());
    }
    
    /**
     * This method tests if a plate is correctly created.
     */
    @Test
    public void test2_handleAddRow() {
        Integer count = tableViewPlates.getItems().size();
        clickOn("#buttonInsertRow");
        assertEquals(tableViewPlates.getItems().size(), count + 1);
    }
    
    /**
     * This method tests if a plate is correctly modified.
     */
    @Test
    public void test3_modifyPlate() {
        Node row = lookup(".table-row-cell").nth(0).query();
        Node tableColumnName = lookup("#tableColumnName").nth(1).query();
        Node tableColumnMealType = lookup("#tableColumnMealType").nth(1).query();
        Node tableColumnCalories = lookup("#tableColumnCalories").nth(1).query();
        Node tableColumnCarbohydrates = lookup("#tableColumnCarbohydrates").nth(1).query();
        Node tableColumnLipids = lookup("#tableColumnLipids").nth(1).query();
        Node tableColumnProteins = lookup("#tableColumnProteins").nth(1).query();
        Node tableColumnVegetarian = lookup("#tableColumnVegetarian").nth(1).query();

        clickOn(row);

        Plate selectedPlate = (Plate) tableViewPlates.getSelectionModel().getSelectedItem();
        String oldName = selectedPlate.getPlateName();
        MealEnum oldMealType = selectedPlate.getMealType();
        Float oldCalories = selectedPlate.getCalories();
        Float oldCarbohydrates = selectedPlate.getCarbohydrates();
        Float oldLipids = selectedPlate.getLipids();
        Float oldProteins = selectedPlate.getProteins();
        Boolean oldVegetarian = selectedPlate.getIsVegetarian();

        clickOn(tableColumnName);
        write("example");
        push(KeyCode.ENTER);
        doubleClickOn(tableColumnMealType);
        push(KeyCode.DOWN);
        clickOn(tableColumnCalories);
        write("321");
        push(KeyCode.ENTER);
        clickOn(tableColumnCarbohydrates);
        write("125");
        push(KeyCode.ENTER);
        clickOn(tableColumnLipids);
        write("10,3");
        push(KeyCode.ENTER);
        clickOn(tableColumnProteins);
        write("8,1");
        push(KeyCode.ENTER);
        clickOn(tableColumnVegetarian);

        Plate modifiedPlate = (Plate) tableViewPlates.getSelectionModel().getSelectedItem();

        assertNotEquals(oldName, modifiedPlate.getPlateName());
        assertNotEquals(oldMealType, modifiedPlate.getMealType());
        assertNotEquals(oldCalories, modifiedPlate.getCalories());
        assertNotEquals(oldCarbohydrates, modifiedPlate.getCarbohydrates());
        assertNotEquals(oldLipids, modifiedPlate.getLipids());
        assertNotEquals(oldProteins, modifiedPlate.getProteins());
        assertNotEquals(oldVegetarian, modifiedPlate.getIsVegetarian());
    }
    
    /**
     * This method tests if a plate is correctly searched.
     */
    @Test
    public void test4_searchPlate() {
        clickOn("#textFieldSearchBar");
        write("example");
        push(KeyCode.ENTER);
        assertEquals(((Plate) tableViewPlates.getItems().get(0)).getPlateName(), "example");
        List<Plate> plates = new ArrayList<>(tableViewPlates.getItems());
        plates.forEach((plate) -> {
            assertTrue(plate.getPlateName().contains("example"));
        });
        clickOn("#textFieldSearchBar");
        eraseText(7);
        push(KeyCode.ENTER);
        write("abcdefghijklmnopqrst");
        push(KeyCode.ENTER);
        verifyThat("There are no plates with that name.", isVisible());
        push(KeyCode.ENTER);
    }
    
    /**
     * This method tests if a plate is correctly deleted.
     */
    @Test
    public void test5_handleDeleteRow() {
        Integer count = tableViewPlates.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();
        rightClickOn(row);
        clickOn("#menuItemDelete");
        verifyThat("Are you sure you want to delete this plate?", isVisible());
        push(KeyCode.ENTER);
        assertEquals(tableViewPlates.getItems().size(), count - 1);
    }
}
