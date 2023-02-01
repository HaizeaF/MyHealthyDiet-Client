/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import myhealthydiet.ClientControlTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;

/**
 *
 * @author Sendoa
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientControlWindowTest {
    
    private Button buttonReport;
    private Button buttonInsert;
    private Button buttonSearch;
    private Button buttonHelp;
    private TableView tableClients;
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientControlTest.class);
    }

    /**
     * Test of initStage method, of class ClientControlWindow.
     */
    @Test
    public void testInitStage() {
        System.out.println("initStage");
        Parent root = null;
        ClientControlWindow instance = new ClientControlWindow();
        instance.initStage(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
