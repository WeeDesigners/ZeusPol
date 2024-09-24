package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.endpoints.requests.ExecuteRequest;
import agh.edu.zeuspol.services.ThemisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


@RestController
@RequestMapping("/app")
public class ControlEndpoints {

    private final ThemisService themisService;

    @Autowired
    public ControlEndpoints(ThemisService themisService) {
        this.themisService = themisService;
    }

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
    public String executeThemis(@RequestBody ExecuteRequest request) {
        String response = this.themisService.executeAction(request);
        return "Themis response: " + response;
    }

}
