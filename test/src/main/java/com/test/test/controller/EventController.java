package com.test.test.controller;

import com.test.test.dto.request.EventRequest;
import com.test.test.dto.request.LoginRequestDTO;
import com.test.test.dto.response.MessageResponse;
import com.test.test.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    EventService eventService;

    /**
     * Method that allows the creation of event requests
     * @param eventRequest the body of the event to be created
     * @param request the http request to get the jwt
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest eventRequest, HttpServletRequest request) {
        return eventService.createEvent(eventRequest,request);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getEvents(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return eventService.listEvents(page, size);
    }
}
