package Utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import models.CreateAD_AccountResponse;
import models.CreateClinicalPatientResponse;
import models.CreatePatientPatientResponse;
import models.OAuthResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Created by bsc4518 on 1/30/2018.
 */
public class API_Testing {

    public static void main(String ard[]) throws InterruptedException {
        String env = "qanightly";
        String patient = "patient51";

        createPortalPatient(env, patient);
        mapAdAccount(env, createAD_Acount(env,patient), patient);
    }


    protected static String getOAuth() {
        String uri = "https://login.microsoftonline.com/hdev.in/oauth2/token";
        OAuthResponse OAuthResponse = null;

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            HttpPost httppost = new HttpPost(uri);


            // Filling the form data with in order to get the access token using MultipartEntityBuilder
            builder.addTextBody("client_id", "04b07795-8ddb-461a-bbee-02f9e1bf7b46");
            builder.addTextBody("grant_type", "password");
            builder.addTextBody("username", "e2euser@hdev.in");
            builder.addTextBody("password", "H@lo2017");
            builder.addTextBody("resource", "https://management.core.windows.net/");

            //Assign form data to post request url
            httppost.setEntity(builder.build());

            // execute the request
            HttpResponse response = httpClient.execute(httppost);
            //System.out.println("getAuth Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            OAuthResponse = mapper.readValue(responseString, OAuthResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OAuthResponse.access_token;
    }

    protected static void callRest() {
//        String uri = baseURl+"/messages?tray?inbox";
        OAuthResponse OAuthResponse = null;
        String token = getOAuth();

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            HttpGet httppost = new HttpGet("");

            // Filling the form data with in order to get the access token using MultipartEntityBuilder
            String tokenStr = "Bearer " + token;
            httppost.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);

            builder.setContentType(ContentType.APPLICATION_JSON);


            // execute the request
            HttpResponse response = httpClient.execute(httppost);
            System.out.println("Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

//            //Construct response
//            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
//            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//            OAuthResponse = mapper.readValue(responseString, OAuthResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected static String createClinicalPatient(String env, String patientName) {
        String uri = "https://server-"+env+".hdev.in/patients?addUserFacilities=true";
        CreateClinicalPatientResponse createClinicalPatientResponse = null;
        String token = getOAuth();

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            HttpPost httppost = new HttpPost(uri);
            // Filling the form data with in order to get the access token using MultipartEntityBuilder
            String tokenStr = "Bearer " + token;

//            System.out.println(" createClinicalPatient > > " + tokenStr);

            httppost.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);
            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");


            JSONObject json = new JSONObject();
            json.put("givenName", "fn_" + patientName);
            json.put("familyName", "ln_" + patientName);
            json.put("patientId", "pt_patient" + new Date().getTime());
            json.put("doB", "1960-04-20T00:00:00.000Z");
            json.put("sexId", "M");
            StringEntity sEntity = new StringEntity(json.toString());

            // execute the request
            httppost.setEntity(sEntity);
            HttpResponse response = httpClient.execute(httppost);
            System.out.println("createClinicalPatient Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            createClinicalPatientResponse = mapper.readValue(responseString, CreateClinicalPatientResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createClinicalPatientResponse.id;
    }

    protected static String createPortalPatient(String env, String username) {
        String uri = "https://server-"+env+".hdev.in/patients/account";

        CreatePatientPatientResponse  createPatientPatientResponse = null;
        String id = createClinicalPatient(env, username);
        String token = getOAuth();


        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            HttpPost httppost = new HttpPost(uri);
            // Filling the form data with in order to get the access token using MultipartEntityBuilder
            String tokenStr = "Bearer " + token;

            httppost.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);
            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            String email = "vmshalorms"+new Date().getTime()+"@gmail.com";
            System.out.println(email);

            JSONObject json = new JSONObject();
            json.put("ipid", id);
            json.put("username", username+"_auto");
            json.put("email", email);
            json.put("comment", "creating portal account for the user");
            json.put("activateLogin", "y");
            json.put("zipPostalCode", "T6R 8UY");
            json.put("gender", "M");
            json.put("locale", "en");
            json.put("contactInfoTypeID", "HOME");
            StringEntity sEntity = new StringEntity(json.toString());


            // execute the request
            httppost.setEntity(sEntity);
            HttpResponse response = httpClient.execute(httppost);
            System.out.println("createPortalPatient Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            createPatientPatientResponse = mapper.readValue(responseString, CreatePatientPatientResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createPatientPatientResponse.status;
    }

    protected static String createAD_Acount(String env, String username) {
        String uri = "https://server-"+env+".hdev.in/createOrGetADUser";

        CreateAD_AccountResponse createAD_AccountResponse = null;
        String token = getOAuth();


        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(uri);
            String tokenStr = "Bearer " + token;


            httppost.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);
            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            JSONObject json = new JSONObject();
            JSONObject passwordProfile = new JSONObject();
            JSONArray signInNames = new JSONArray();


            passwordProfile.put("password","H@lo2017");
            passwordProfile.put("forceChangePasswordNextLogin","false");
            signInNames.put(0, new JSONObject().put("type","username").put("value",username+"_auto"));

            json.put("accountEnabled", "true");
            json.put("creationType", "LocalAccount");
            json.put("passwordPolicies", "DisablePasswordExpiration");
            json.put("displayName", "ln_patient"+new Date().getTime()+" fn_patient");
            json.put("passwordProfile", passwordProfile);
            json.put("signInNames", signInNames);
            StringEntity sEntity = new StringEntity(json.toString());

            // execute the request
            httppost.setEntity(sEntity);
            HttpResponse response = httpClient.execute(httppost);
            System.out.println("createAD_Acount Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            createAD_AccountResponse = mapper.readValue(responseString, CreateAD_AccountResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createAD_AccountResponse.data;
    }

    protected static String mapAdAccount(String env, String externalID, String username) {
        String uri = "https://server-"+env+".hdev.in/mapuser_sso";

        CreateAD_AccountResponse createAD_AccountResponse = null;
        String token = getOAuth();


        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            HttpPost httppost = new HttpPost(uri);
            // Filling the form data with in order to get the access token using MultipartEntityBuilder
            String tokenStr = "Bearer " + token;

//            System.out.println("mapAdAccount > > " + tokenStr);

            httppost.setHeader(HttpHeaders.AUTHORIZATION, tokenStr);
            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            JSONObject json = new JSONObject();
            json.put("externalID", externalID);
            json.put("username", username+"_auto");
            StringEntity sEntity = new StringEntity(json.toString());
            System.out.println(json.toString());

            // execute the request
            httppost.setEntity(sEntity);
            HttpResponse response = httpClient.execute(httppost);
            System.out.println("mapAdAccount Response Status Code : [" + response.getStatusLine().getStatusCode() + "] ");

            //convert the response to json formatter
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

            //Construct response
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
            createAD_AccountResponse = mapper.readValue(responseString, CreateAD_AccountResponse.class);

        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection....");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file.....");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createAD_AccountResponse.data;
    }


}
