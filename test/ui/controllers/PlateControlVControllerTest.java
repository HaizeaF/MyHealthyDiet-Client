package ui.controllers;

import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.ApplicationPlateFX;
import myhealthydiet.MyHealthyDietClient;
import objects.Ingredient;
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
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author haize
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlateControlVControllerTest extends ApplicationTest {

    private Button buttonSearch;
    private Button buttonImage;
    private Button buttonAddRow;
    private Button buttonIngredients;
    private TextField textFieldSearchBar;
    private MenuItem menuItemDelete;
    private final TableView tableViewPlates = lookup("#tableViewPlates").query();

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationPlateFX.class);
    }
    
    @Test
    public void test1_handleAddRow() {
        Integer count = tableViewPlates.getItems().size();
        clickOn("#buttonInsertRow");
        assertEquals(tableViewPlates.getItems().size(),count+1);
    }
    
    @Test
    public void test2_modifyPlate() {
        Node row = lookup(".table-row-cell").nth(0).query();
        Node tableColumnName = lookup("#tableColumnName").nth(1).query();
        Node tableColumnMealType = lookup("#tableColumnMealType").nth(1).query();
        Node tableColumnCalories = lookup("#tableColumnCalories").nth(1).query();
        Node tableColumnCarbohydrates = lookup("#tableColumnCarbohydrates").nth(1).query();
        Node tableColumnLipids = lookup("#tableColumnLipids").nth(1).query();
        Node tableColumnProteins = lookup("#tableColumnProteins").nth(1).query();
        Node tableColumnVegetarian = lookup("#tableColumnVegetarian").nth(1).query();
        clickOn(row);
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
    }
    
    @Test
    public void test3_searchPlate() {
        clickOn("#textFieldSearchBar");
        write("example");
        push(KeyCode.ENTER);
        assertEquals(((Plate)tableViewPlates.getItems().get(0)).getPlateName(),"example");
        clickOn("#textFieldSearchBar");
        eraseText(7);
        push(KeyCode.ENTER);
        assertTrue(tableViewPlates.getItems().size() > 1);
        clickOn("#textFieldSearchBar");
        write("abcdefghijklmnopqrst");
        push(KeyCode.ENTER);
        verifyThat("There are no plates with that name.", isVisible());
        push(KeyCode.ENTER);
        assertTrue(tableViewPlates.getItems().size() > 1);
    }
    
    @Test
    public void test4_handleDeleteRow() {
        Integer count = tableViewPlates.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();
        rightClickOn(row);
        clickOn("#menuItemDelete");
        verifyThat("Are you sure you want to delete this plate?", isVisible());
        push(KeyCode.ENTER);
        assertEquals(tableViewPlates.getItems().size(),count-1);
    }
}
