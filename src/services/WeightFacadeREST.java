/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:WeightFacadeREST
 * [entities.weight]<br>
 * USAGE:
 * <pre>
 *        WeightFacadeREST client = new WeightFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author User
 */
public class WeightFacadeREST {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/MyHealthyDiet/webresources";

    public WeightFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("weight");
    }

    public void removeWeight(String id) throws ClientErrorException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public void createWeight_XML(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public void createWeight_JSON(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findAllWeights_XML(Class<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findAllWeights_JSON(Class<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findWeight_XML(Class<T> responseType, String id) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findWeight_JSON(Class<T> responseType, String id) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public void editWeight_XML(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public void editWeight_JSON(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findAllWeightsByClient_XML(Class<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path("(id)");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public <T> T findAllWeightsByClient_JSON(Class<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path("(id)");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
