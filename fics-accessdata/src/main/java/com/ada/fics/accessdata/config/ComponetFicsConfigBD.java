/*
 * MIT License 
 * 
 * Copyright (c) 2018 Ownk
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 */

package com.ada.fics.accessdata.config;
import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * <h1>ComponetConfigBD</h1>
 *
 * Description
 *
 * @author TheOverLordKotan (ADA)
 * @version 1.0
 * 
 */
@EnableTransactionManagement  
@Configuration
@MapperScan(value = {"com.ada.fics.accessdata.persistence.**"}, sqlSessionFactoryRef="sqlSessionFactoryBeanFics")
@Profile("AdarchitureCore")
public class ComponetFicsConfigBD implements EnvironmentAware{


	//private static Logger logger = Logger.getLogger(AppConfigBD.class);

	@Autowired
	Environment env;

	@Profile("AdarchitureCore")
	@Bean(name="dsPrimary",destroyMethod="")
	@Primary
	public DataSource getDataSourceComponetFicsTest() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@192.168.0.18:1521:xe");
		dataSource.setUsername("system");
		dataSource.setPassword("oracle");

		return dataSource;
	}

	



	@Bean
	@Profile("AdarchitureCore")
	public DataSourceTransactionManager transactionManagerTest() {
		//		if(logger.isDebugEnabled()){
		//			logger.debug("transactionManager");				
		//		}
		//		System.out.println("transactionManager");
		return new DataSourceTransactionManager(getDataSourceComponetFicsTest());
	}



	@Bean(name="sqlSessionFactoryBeanFics")
	@Profile("AdarchitureCore")
	public SqlSessionFactoryBean sqlSessionFactoryBeanFics() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		DataSource primaryDs = getDataSourceComponetFicsTest();
		/*
		 * =====================================
		 * Este if se encarga de evaluar
		 * los datasource y recargarlos
		 * =====================================
		 */
		if (primaryDs!=null) {
			sqlSessionFactoryBean.setDataSource(primaryDs);
		}
		PathMatchingResourcePatternResolver pathM3R = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(pathM3R.getResources("classpath*:com/itc/adarchitecture/fics/accessdata/persistence/mapper/*.xml"));
		return sqlSessionFactoryBean;
	}

	@Bean("fics")
	@Profile("AdarchitureCore")
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer =
				new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.ada.fics.accessdata.persistence.**");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanFics");
		return mapperScannerConfigurer;
	}


	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;

	}
}