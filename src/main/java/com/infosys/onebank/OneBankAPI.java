package com.infosys.onebank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.infosys.onebank"})
public class OneBankAPI
{
    public static void main( String[] args )
    {
        SpringApplication.run(OneBankAPI.class, args);
    }
}
