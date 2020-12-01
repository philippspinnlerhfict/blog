package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.StatusDto;

@RestController
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<StatusDto> status() {
        return new ResponseEntity<>(new StatusDto(), HttpStatus.OK);
    }
}
