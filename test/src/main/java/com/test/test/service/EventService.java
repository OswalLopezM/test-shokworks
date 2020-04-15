package com.test.test.service;

import com.test.test.dto.request.EventRequest;
import com.test.test.dto.response.EventResponse;
import com.test.test.dto.response.UserResponse;
import com.test.test.model.Event;
import com.test.test.model.UserAccount;
import com.test.test.repository.EventRepository;
import com.test.test.repository.UserRepository;
import com.test.test.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?>  createEvent(EventRequest eventRequest, HttpServletRequest request){

        String headerAuth = request.getHeader("Authorization");
        String jwt = headerAuth.substring(7, headerAuth.length());
        String email = jwtUtils.getUserNameFromJwtToken(jwt);
        Optional<UserAccount> user = userRepo.findByEmail(email);

        Event eventEntity = Event.builder()
                .address(eventRequest.getAddress())
                .date(eventRequest.getDate())
                .user(user.get())
                .build();
        eventEntity = eventRepo.save(eventEntity);

        UserResponse userResponse = UserResponse
                .builder()
                .id(eventEntity.getUser().getId())
                .email(eventEntity.getUser().getEmail())
                .build();

        return ResponseEntity.ok(EventResponse.builder()
                .id(eventEntity.getId())
                .address(eventEntity.getAddress())
                .date(eventEntity.getDate())
                .user(userResponse)
                .build());
    }

    public ResponseEntity<?>  listEvents(Integer page, Integer size){
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        Page<Event> pageElements = eventRepo.findAll(firstPageWithTwoElements);
        List<Event> list = pageElements.getContent();
        List<EventResponse> listResponse = new ArrayList<>();
        for(Event event : list){
            UserResponse userResponse = UserResponse
                    .builder()
                    .id(event.getUser().getId())
                    .email(event.getUser().getEmail())
                    .build();
            EventResponse eventResponse = EventResponse.builder()
                    .id(event.getId())
                    .address(event.getAddress())
                    .date(event.getDate())
                    .user(userResponse)
                    .build();
            listResponse.add(eventResponse);
        }

        return ResponseEntity.ok(listResponse);
    }

}
