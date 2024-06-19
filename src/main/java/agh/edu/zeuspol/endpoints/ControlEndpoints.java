package agh.edu.zeuspol.endpoints;


import agh.edu.zeuspol.ZeuspolApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ControlEndpoints {

    @PostMapping("/run")
    public String runApp() {
        if(ZeuspolApplication.isRunning()){
            return "App is already running";
        }

        //run main loop
        Thread thread = new Thread(ZeuspolApplication::mainLoop);
        thread.start();

        return "ZeusPol started!";
    }


}
