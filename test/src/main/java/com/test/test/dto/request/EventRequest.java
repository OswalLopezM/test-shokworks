package com.test.test.dto.request;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotNull
    private String address;

    @NotNull
    private Date date;

}
