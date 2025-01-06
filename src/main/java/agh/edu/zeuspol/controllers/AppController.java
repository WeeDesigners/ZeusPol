package agh.edu.zeuspol.controllers;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import agh.edu.zeuspol.services.ThemisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

  private final ThemisService themisService;

  @Autowired
  public AppController(ThemisService themisService) {
    this.themisService = themisService;
  }

  @PostMapping("/start")
  public String startApp() {
    if (ZeuspolApplication.isRunning()) {
      return "App is already running";
    }

    // start app
    ZeuspolApplication.startApp();

    return "ZeusPol started!";
  }

  @PostMapping("/stop")
  public String stopApp() {
    if (!ZeuspolApplication.isRunning()) {
      return "App is already not running";
    }

    // stop app
    ZeuspolApplication.stopApp();

    return "ZeusPol stopped!";
  }

  @PostMapping("/execute-action")
  public String executeThemis(@RequestBody ExecutionRequest request) {
    String response = this.themisService.executeAction(request);
    return "Themis response: " + response;
  }
}
