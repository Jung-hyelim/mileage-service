package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class BaseRequest {
    @NotNull
    private EventType type;

    @NotNull
    private EventAction action;

    @NotNull
    private String userId;

}
