package org.willwin.draftolio;

import org.springframework.boot.SpringApplication;

public class TestDraftolioApplication
{

    public static void main(String[] args)
    {
        SpringApplication.from(DraftolioApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
