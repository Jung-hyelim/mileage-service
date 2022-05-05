package com.example.mileage.vo;

import com.example.mileage.enums.EventAction;
import com.example.mileage.enums.EventType;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class BaseRequest {
    @NotNull
    private EventType type;

    @NotNull
    private EventAction action;

    @NotNull
    private String userId;

    private String key; // event 의 구분 key 값을 상속받은 Request 에서 지정하여 넣어준다.

}
