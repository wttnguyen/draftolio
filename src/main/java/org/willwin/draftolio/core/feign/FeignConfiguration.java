package org.willwin.draftolio.core.feign;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignConfiguration
{

    @Bean
    public Client defaultFeignClient()
    {
        return new Client.Default(null, null);
    }

}
