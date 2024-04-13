package com.stormx.hicoder.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Setter
@Getter
@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {
    private Resource serviceAccount;
}