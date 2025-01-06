package agh.edu.zeuspol.controllers;

import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hermes")
public class HermesController {

    @PostMapping("")
    public String getAll(){
        return "";
    }

    @PostMapping("/policies")
    public String getPolicies() {
        String response = this.themisService.executeAction(request);
        return "Themis response: " + response;
    }

    @PostMapping("/sla")
    public String getSlas() {
        String response = this.themisService.executeAction(request);
        return "Themis response: " + response;
    }

    @PostMapping("/sla/{id}")
    public String getSla(@PathVariable("id") long id) {
        String response = this.themisService.executeAction(request);
        return "Themis response: " + response;
    }

}
