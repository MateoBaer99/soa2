/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes;

import javax.ws.rs.ext.Provider;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.universite.ventes.api.impl.MyValidationExceptionMapper;

/**
 * Configuration du framework CXF
 * @author akriks
 */
@Configuration
public class ConfigurationCXF {
 
@Bean
public LoggingFeature myLogger() {
    LoggingFeature logger= new LoggingFeature();
    logger.setLimit(4096);
    return logger;
}
@Bean
public MyValidationExceptionMapper myMapper() {
    MyValidationExceptionMapper myMapper= new MyValidationExceptionMapper();
  //  myMapper.setAddMessageToResponse(true);
    return myMapper;
}

}
