package com.example.mileage.service;

import com.example.mileage.enums.EventType;
import com.example.mileage.vo.BaseRequest;
import org.springframework.stereotype.Service;

@Service
public class ReservationEventService implements EventService {
    @Override
    public EventType getEventType() {
        return EventType.RESERVATION;
    }

    @Override
    public void addEvent(BaseRequest request) {

    }

    @Override
    public void modifyEvent(BaseRequest request) {

    }

    @Override
    public void deleteEvent(BaseRequest request) {

    }
}
