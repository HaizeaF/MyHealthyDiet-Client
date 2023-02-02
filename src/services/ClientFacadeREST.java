/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.ClientInterface;
import exceptions.BusinessLogicException;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;
import objects.StatusEnum;

/**
 * Jersey REST client generated for REST resource:ClientFacadeREST [client]<br>
 * USAGE:
 * <pre>
 *        ClientFacadeREST client = new ClientFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Sendoa
 */
public class ClientFacadeREST implements ClientInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("files.URLCredentials");
    private final String BASE_URI = bundle.getString("BASE_URI");

    public ClientFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("client");
    }

    public <T> T findClientById(GenericType<T> responseType, String id) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to find the client:" + ex.getMessage());
        }
    }

    public <T> T findClientByStatus(GenericType<T> responseType, String status) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("status/{0}", new Object[]{status}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to find the clients by status:" + ex.getMessage());
        }
    }

    public void editPassword(ClientOBJ requestEntity) throws BusinessLogicException {
        try {
            webTarget.path("updatePassword").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), ClientOBJ.class);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to edit the clients password:" + ex.getMessage());
        }

    }

    public void edit(ClientOBJ requestEntity) throws BusinessLogicException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), ClientOBJ.class);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to edit the client:" + ex.getMessage());
        }

    }

    public <T> T findClientByLogin(GenericType<T> responseType, String usrLogin) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("login/{0}", new Object[]{usrLogin}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to find the client by login:" + ex.getMessage());
        }
    }

    public void create(ClientOBJ requestEntity) throws BusinessLogicException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), ClientOBJ.class);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to create the client:" + ex.getMessage());
        }

    }

    public <T> T findClientBySearch(GenericType<T> responseType, String usrValue) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("search/{0}", new Object[]{usrValue}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to find the clients:" + ex.getMessage());
        }

    }

    public <T> T findAll(GenericType<T> responseType) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to find all the clients:" + ex.getMessage());
        }

    }

    public void remove(Integer id) throws BusinessLogicException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(ClientOBJ.class);
        } catch (Exception ex) {
            throw new BusinessLogicException("An error occurred while trying to remove the client:" + ex.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
