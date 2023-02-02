/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import main.IngredientControlApplicationPrueba;
import objects.FoodTypeEnum;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import objects.Ingredient;
import org.testfx.api.FxRobot;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Class testFx to test IngredientControlVController window.
 * @author Mikel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IngredientControlVControllerTest extends ApplicationTest {
    
    private Pane paneIngredientWindow;
    
    private Pane paneIngredientMenu;
    
    private Label labelTitle;
    
    private Button buttonTips;
    
    private Button buttonPlates;
    
    private Button buttonClients;
    
    private Button buttonDiets;
    
    private Button buttonIngredients;
    
    private Button buttonLogout;
    
    private TableView tableIngredient = lookup("#tableIngredient").query();
    
    private TableColumn<Ingredient, String> columnIngredientName;
    
    private TableColumn<Ingredient, FoodTypeEnum> columnFoodType;
    
    private TableColumn<Ingredient, Boolean> columnIsInSeason;
    
    private TableColumn<Ingredient, Float> columnWaterIndex;
    
    private TextField texfieldSearchbar;
    
    private Button buttonSearch;
    
    private MenuButton menuButtonFilters;
    
    private MenuItem menuItemVegetable;
    
    private MenuItem menuItemFruit;
    
    private MenuItem menuItemNut;
    
    private MenuItem menuItemGrain;
    
    private MenuItem menuItemBean;
    
    private MenuItem menuItemMeat;
    
    private MenuItem menuItemPorultry;
    
    private MenuItem menuItemFish;
    
    private MenuItem menuItemSeafood;
    
    private MenuItem menuItemDairy;
    
    private MenuItem menuItemDelete;
    
    private Pane paneButtons;
    
    private Button buttonInsertRow;
    
    private Button buttonHelp;
    
    private Button buttonReport;
    
    private ContextMenu contextMenu;
    
    private CheckBox cuadro;
    
    /**
     *
     */
    public IngredientControlVControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(IngredientControlApplicationPrueba.class);
    }
    
    /**
     * Test of initStage method, of class IngredientControlVController.
     */
    @Test
    public void test1_InsertRow() {
        tableIngredient = lookup("#tableIngredient").query();
        int size1 = tableIngredient.getItems().size();
        buttonInsertRow = lookup("#buttonInsertRow").query();
        clickOn("#buttonInsertRow");
        int size2 = tableIngredient.getItems().size();
        assertEquals(size1+1, size2);
    }
    
    @Test
    public void test2_UpdateRowPart1() {
        Node row=lookup(".table-row-cell").nth(0).query();
        
        Node tablecolumnIngredientName = lookup("#columnIngredientName").nth(1).query();
        Node tablecolumnFoodType = lookup("#columnFoodType").nth(1).query();
        Node tablecolumnIsInSeason = lookup("#columnIsInSeason").nth(1).query();
        Node tablecolumnWaterIndex = lookup("#columnWaterIndex").nth(1).query();
        
        clickOn(row);
        
        Ingredient selectedIngredient = (Ingredient) tableIngredient.getSelectionModel().getSelectedItem();
        String string = selectedIngredient.getWaterIndex().toString();
        
        clickOn(tablecolumnIsInSeason);
        doubleClickOn(tablecolumnFoodType);
        push(KeyCode.DOWN);
        
        clickOn(tablecolumnWaterIndex);
        write("2,3");
        push(KeyCode.ENTER);
              
        Ingredient modifiedIngredient = (Ingredient) tableIngredient.getSelectionModel().getSelectedItem();
        assertNotEquals(string, modifiedIngredient.getWaterIndex().toString());
    }
    
     @Test
    public void test3_UpdateRowPart2() {
        Node row=lookup(".table-row-cell").nth(0).query();
        
        Node tablecolumnIngredientName = lookup("#columnIngredientName").nth(1).query();
        
        clickOn(row);
        
        Ingredient selectedIngredient = (Ingredient) tableIngredient.getSelectionModel().getSelectedItem();
        String string = selectedIngredient.getIngredientName();
        
        doubleClickOn(tablecolumnIngredientName);
        write("aaaa");
        push(KeyCode.ENTER);   
        
        Ingredient modifiedIngredient = (Ingredient) tableIngredient.getSelectionModel().getSelectedItem();
        assertNotEquals(string, modifiedIngredient.getIngredientName());
    }
    /**
     * Test of search by name 
     */
    @Test
    public void test4_SearchByName() {
        texfieldSearchbar = lookup("#texfieldSearchbar").query();
        clickOn("#texfieldSearchbar");
        write("aa");
        buttonSearch = lookup("#buttonSearch").query();
        /*menuButtonFilters = lookup("#menuButtonFilters").query();
        clickOn("#menuButtonFilters");*/
        clickOn("#buttonSearch");
        clickOn("#texfieldSearchbar");
        eraseText(2);
        clickOn("#buttonSearch");
    }
    
    @Test
    public void test5_SearchFilter() {
        menuButtonFilters = lookup("#menuButtonFilters").query();
        clickOn("#texfieldSearchbar");
        push(KeyCode.DOWN);
    }
    
    /**
     * Test of delete method.
     */
    @Test
    public void test6_delete() {
        /*Node row=lookup(".table-row-cell").nth(0).query();
        rightClickOn(row).moveBy(10, 30);
        push(KeyCode.ENTER);
        push(KeyCode.ENTER);*/
        
        Integer count = tableIngredient.getItems().size();
        Node row=lookup(".table-row-cell").nth(0).query();
        rightClickOn(row);
        clickOn("#menuItemDelete");
        verifyThat("Are you sure you want to delete this Ingredient?", isVisible());
        push(KeyCode.ENTER);
        assertEquals(tableIngredient.getItems().size(),count-1);
    }
}
