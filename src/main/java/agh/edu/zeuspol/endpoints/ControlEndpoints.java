package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.ZeuspolApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ControlEndpoints {

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

    @PostMapping("/execute")
    public String executeThemis(){
        return "Execution";
    }






}
