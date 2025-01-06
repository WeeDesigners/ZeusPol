package agh.edu.zeuspol.controllers;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import agh.edu.zeuspol.services.ThemisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<String> startApp() {
    if (ZeuspolApplication.isRunning()) {
      return ResponseEntity.badRequest().body("App is already running");
    }

    // start app
    ZeuspolApplication.startApp();

    return ResponseEntity.ok("App started");
  }

  @PostMapping("/stop")
  public ResponseEntity<String> stopApp() {
    if (!ZeuspolApplication.isRunning()) {
      return ResponseEntity.badRequest().body("App is not running");
    }

    // stop app
    ZeuspolApplication.stopApp();

    return ResponseEntity.ok("App stopped");
  }

  @PostMapping("/execute-action")
  public ResponseEntity<String> executeThemis(@RequestBody ExecutionRequest request) {
    String response = this.themisService.executeAction(request);
    return ResponseEntity.ok(response);
  }
}
