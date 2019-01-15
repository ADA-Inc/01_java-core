/*
 * MIT License 
 * 
 * Copyright (c) 2018 Ownk
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 */

package com.ada.audit.accessdata.config;
import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
@ComponentScan("com.ada")
@MapperScan(value = {"com.ada.audit.accessdata.**"}, sqlSessionFactoryRef="sqlSessionFactoryBeanAudit")
@Profile("AdarchitureCore")
public class ComponetAuditConfigBD implements EnvironmentAware{

	
	//private static Logger logger = Logger.getLogger(AppConfigBD.class);
	
	@Autowired
    Environment env;
	
	
	
	@Profile("AdarchitureCore")
	@Bean(destroyMethod="")
	public DataSource getDataSourceComponetAudit() {
		Properties props =null;
		String dataSource = null;
		Boolean isDatasourcePar = false;
		InitialContext ic2;
		DataSource ds = null;
		try {
			ic2 = new InitialContext();
			props = new Properties();
			props.load(this.getClass().getResourceAsStream("/applicationaudit.properties"));
			dataSource = props.getProperty("audit.xcomp.datasource.name");
			isDatasourcePar = Boolean.valueOf(props.getProperty("audit.xcomp.isdatasource"));
			/*
			  * =====================================
			  * Este if se encarga de validar la 
			  * existencia del datasource
			  * desde compilacion
			  * =====================================
			*/
			if (isDatasourcePar&& dataSource!=null && !dataSource.isEmpty()) {
				ds = (DataSource) ic2.lookup(dataSource);
			}

		} catch (NamingException e) {
			//logger.error(e.getMessage(), e);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		  

		return ds;

	}
	
	@Bean
	@Profile("AdarchitureCore")
	public DataSourceTransactionManager transactionManager(@Qualifier("dsPrimary") DataSource dsAda) {
//		if(logger.isDebugEnabled()){
//			logger.debug("transactionManager");				
//		}
		//		System.out.println("transactionManager");
		DataSource ds = getDataSourceComponetAudit();
		/*
		  * =====================================
		  * Este if se encarga de evaluar
		  * y asignar el datasource primario
		  * si el componente no cuenta con
		  * datasource
		  * =====================================
		*/
		if (ds!=null) {
		return new DataSourceTransactionManager(ds);
		}else {
			return new DataSourceTransactionManager(dsAda);
		}
		
	}



	@Bean(name="sqlSessionFactoryBeanAudit")
	@Autowired
	@Profile("AdarchitureCore")
	public SqlSessionFactoryBean sqlSessionFactoryBeanAudit(@Qualifier("dsPrimary") DataSource dsAda) throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		DataSource ds = getDataSourceComponetAudit();
		/*
		  * =====================================
		  * Este if se encarga de evaluar
		  * y asignar el datasource primario
		  * si el componente no cuenta con
		  * datasource
		  * =====================================
		*/
		if (ds!=null) {
		sqlSessionFactoryBean.setDataSource(ds);
		}else {
			sqlSessionFactoryBean.setDataSource(dsAda);
		}
		PathMatchingResourcePatternResolver pathM3R = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(pathM3R.getResources("classpath*:com/itc/adarchitecture/audit/accessdata/persistence/mapper/*.xml"));
		return sqlSessionFactoryBean;
	}
	


	@Bean("audit")
	@Profile("AdarchitureCore")
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer =
				new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.ada.audit.accessdata.persistence.**");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanAudit");
		return mapperScannerConfigurer;
	}


	@Override
	public void setEnvironment(Environment environment) {
		 this.env = environment;

	}


}
