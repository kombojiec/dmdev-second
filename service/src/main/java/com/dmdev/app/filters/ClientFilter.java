package com.dmdev.app.filters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientFilter {
    String firstName;
    String secondName;
    String middleName;
}
