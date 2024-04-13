package com.stormx.hicoder.controllers.helpers;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MulticastMessageRepresentation {
    private String title;
    private String body;
    private List<String> registrationTokens;

}