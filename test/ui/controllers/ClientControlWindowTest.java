/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.ClientControlTest;
import objects.ClientOBJ;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;

/**
 *
 * @author Sendoa
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientControlWindowTest extends ApplicationTest {
    
    private Button buttonReport;
    private Button buttonInsert;
    private Button buttonSearch;
    private Button buttonHelp;
    private TableView tableClients = lookup("#tableClients").query();
    private Pane paneClientAdmin;
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientControlTest.class);
    }

    /**
     * Test of initStage method, of class ClientControlWindow.
     */
    @Test
    public void test1_InitStage() {
        verifyThat("#tableClients", isVisible());
        verifyThat("#buttonReport", isEnabled());
        verifyThat("#buttonInsert", isEnabled());
        verifyThat("#buttonSearch", isEnabled());
        verifyThat("#buttonHelp", isEnabled());
    }
    
    /**
     * Test of create method, of class ClientControlWindow
     */
    @Test
    //@Ignore
    public void test2_AddClient() {
        Integer count = tableClients.getItems().size();
        clickOn("#buttonInsert");
        assertEquals(tableClients.getItems().size(), count+1);
    }
    
    /**
     * Test of search method, of class ClientControlWindow
     */
    @Test
    //@Ignore
    public void test3_Search() {
        clickOn("#texfieldSearchbar");
        write("example");
        Node row = lookup(".table-row-cell").nth(1).query();
        clickOn("#buttonSearch");
    }
    
    /**
     * Test of update method, of class ClientControlWindow
     */
    @Test
    //@Ignore
    public void test4_UpdateClient() {
        Node row = lookup(".table-row-cell").nth(0).query();
        
        clickOn(row);
        
        ClientOBJ selectedClient = (ClientOBJ) tableClients.getSelectionModel().getSelectedItem();
        String string = selectedClient.getLogin();
        String string2 = selectedClient.getEmail();
        
        Node columnEmail = lookup("#columnEmail").nth(1).query();
        clickOn(columnEmail);
        write("test");
        push(KeyCode.ENTER);
        verifyThat("The email must have a correct pattern and less than 50 characters.", isVisible());
        push(KeyCode.ENTER);
        
        columnEmail = lookup("#columnEmail").nth(1).query();
        clickOn(columnEmail);
        write("test@gmail.com");
        push(KeyCode.ENTER);
        
        Node columnName = lookup("#columnName").nth(1).query();
        clickOn(columnName);
        write("modifiedClient");
        push(KeyCode.ENTER);
        
        Node columnLogin = lookup("#columnLogin").nth(1).query();
        clickOn(columnLogin);
        write("javi");
        push(KeyCode.ENTER);
        verifyThat("The entered login already exists", isVisible());
        push(KeyCode.ENTER);
        
        columnLogin = lookup("#columnLogin").nth(1).query();
        clickOn(columnLogin);
        write("testLogin");
        push(KeyCode.ENTER);
        
        Node columnStatus = lookup("#columnStatus").nth(1).query();
        doubleClickOn(columnStatus);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        
        Node columnAge = lookup("#columnAge").nth(1).query();
        clickOn(columnAge);
        write("25");
        push(KeyCode.ENTER);
        
        Node columnGenre = lookup("#columnGenre").nth(1).query();
        doubleClickOn(columnGenre);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        
        Node columnGoal = lookup("#columnGoal").nth(1).query();
        doubleClickOn(columnGoal);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        
        Node columnHeight = lookup("#columnHeight").nth(1).query();
        clickOn(columnHeight);
        write("1,58");
        push(KeyCode.ENTER);
        
        ClientOBJ modifiedClient = (ClientOBJ) tableClients.getSelectionModel().getSelectedItem();
        
        assertNotEquals(string, modifiedClient.getLogin());
        assertNotEquals(string2, modifiedClient.getFullName());
    }
    
    /**
     * Test of delete method, of class ClientControlWindow
     */
    @Test
    //@Ignore
    public void test5_DeleteClient() {
        Integer count = tableClients.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();
        rightClickOn(row);
        clickOn("#menuItemDelete");
        verifyThat("Are you sure you want to delete this item?", isVisible());
        push(KeyCode.ENTER);
        assertEquals(tableClients.getItems().size(), count-1);
    }
    
}
