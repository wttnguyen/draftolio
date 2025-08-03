package org.willwin.draftolioai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.willwin.draftolioai.config.RsoProperties;

@SpringBootApplication
@EnableConfigurationProperties({ RsoProperties.class })
public class DraftolioAiApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(DraftolioAiApplication.class, args);
    }

}
