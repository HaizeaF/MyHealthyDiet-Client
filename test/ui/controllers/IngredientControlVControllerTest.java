/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.IngredientControlApplicationPrueba;
import objects.FoodTypeEnum;
import objects.Ingredient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.TextMatchers.hasText;
import objects.Ingredient;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author User
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
    public void test2_UpdateRow() {
        Node row=lookup(".table-row-cell").nth(0).query();
        Node row2=lookup(".table-row-cell").nth(1).query();
        
        Node tablecolumnIngredientName = lookup("#columnIngredientName").nth(1).query();
        Node tablecolumnFoodType = lookup("#columnFoodType").nth(1).query();
        Node tablecolumnIsInSeason = lookup("#columnIsInSeason").nth(1).query();
        Node tablecolumnWaterIndex = lookup("#columnWaterIndex").nth(1).query();
        
        clickOn(row);
        
        clickOn(tablecolumnWaterIndex);
        write("1,3");
        push(KeyCode.ENTER);
        
        clickOn(row2);
        clickOn(row);
        
        clickOn(tablecolumnIsInSeason);
        
        clickOn(row2);
        clickOn(row);
        
        clickOn(tablecolumnFoodType);
        push(KeyCode.DOWN);
        
        clickOn(row2);
        clickOn(row);
        
        clickOn(tablecolumnIngredientName);
        write("aaaaaaaa");
        push(KeyCode.ENTER);
    }
    @Test
    public void test3_SearchByName() {
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
    public void test4_delete() {
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
