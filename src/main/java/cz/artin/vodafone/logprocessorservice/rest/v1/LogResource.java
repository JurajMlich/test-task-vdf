package cz.artin.vodafone.logprocessorservice.rest.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages")
public class LogResource {
    @RequestMapping(path = "")
    public String selectLogFile()
}
