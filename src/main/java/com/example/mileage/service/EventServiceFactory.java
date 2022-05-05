package com.example.mileage.service;

import com.example.mileage.enums.EventType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventServiceFactory {

    private final Map<EventType, EventService> eventServiceMap = new HashMap<>();

    public EventServiceFactory(List<EventService> eventServiceList) {
        if(CollectionUtils.isEmpty(eventServiceList)) {
            throw new IllegalArgumentException("존재하는 eventService 가 없음");
        }

        eventServiceList.stream().forEach(eventService -> this.eventServiceMap.put(eventService.getEventType(), eventService));
    }

    public EventService getEventService(EventType eventType) {
        return eventServiceMap.get(eventType);
    }
}
