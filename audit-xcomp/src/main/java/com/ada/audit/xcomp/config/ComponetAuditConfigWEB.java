/*
 * MIT License 
 * 
 * Copyright (c) 2018 Ownk
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 */

package com.ada.audit.xcomp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * <h1>ComponetConfigWEB</h1>
 *
 * Description
 *
 * @author TheOverLordKotan (ADA)
 * @version 1.0
 * 
 */
@EnableTransactionManagement  
@Configuration
@ComponentScan(basePackages = "com.ada.audit")
@Profile("AdarchitureCore")
public class ComponetAuditConfigWEB {

}
