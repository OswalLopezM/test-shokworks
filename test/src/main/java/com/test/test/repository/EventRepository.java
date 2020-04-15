package com.test.test.repository;

import com.test.test.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository  extends JpaRepository<Event, Long> {

}
