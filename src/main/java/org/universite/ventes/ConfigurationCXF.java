/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes;

import java.util.List;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration du framework CXF
 * @author akriks
 */
@Configuration
public class ConfigurationCXF {
    
    @Autowired
    private Bus bus;
  
    @Bean
    public LoggingFeature myLogger() {
        LoggingFeature logger= new LoggingFeature();
        logger.setLimit(4096);
        return logger;
    }

    @Bean
    public ValidationExceptionMapper myMapper() {
        ValidationExceptionMapper myMapper= new ValidationExceptionMapper();
        myMapper.setAddMessageToResponse(true);
        return myMapper;
    }

}
