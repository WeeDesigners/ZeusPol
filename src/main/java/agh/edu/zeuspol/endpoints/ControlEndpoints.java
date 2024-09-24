package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.endpoints.requests.ExecuteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


@RestController
@RequestMapping("/app")
public class ControlEndpoints {

    @Value("${themis.url}")
    String themisUrl;

    @PostMapping("/start")
    public String startApp() {
        if(ZeuspolApplication.isRunning()){
            return "App is already running";
        }

        //start app
        ZeuspolApplication.startApp();

        return "ZeusPol started!";
    }


    @PostMapping("/stop")
    public String stopApp() {
        if(!ZeuspolApplication.isRunning()){
            return "App is already not running";
        }

        //stop app
        ZeuspolApplication.stopApp();

        return "ZeusPol stopped!";
    }

    @PostMapping("/execute-action")
    public String executeThemis(){
        String fullURL = "http://"+themisUrl+"/execute";

        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //create a request (TODO in future!)
        ExecuteRequest myJSON = new ExecuteRequest();
        HttpEntity<ExecuteRequest> request = new HttpEntity<ExecuteRequest>(myJSON, headers);

        try{
            String result = restTemplate.postForObject(fullURL, request, String.class);
            //to sth with response
            return "Themis response: " + result;
        } catch (HttpClientErrorException e) {
            return "Themis response: " + e.getResponseBodyAsString();
        }

    }






}
