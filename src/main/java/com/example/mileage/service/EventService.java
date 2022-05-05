package com.example.mileage.service;

import com.example.mileage.enums.EventType;
import com.example.mileage.vo.BaseRequest;

public interface EventService {
    EventType getEventType();
    void addEvent(BaseRequest request);
    void modifyEvent(BaseRequest request);
    void deleteEvent(BaseRequest request);
}
