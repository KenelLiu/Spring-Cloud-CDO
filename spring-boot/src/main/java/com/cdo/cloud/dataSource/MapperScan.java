package com.cdo.cloud.dataSource;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
@AutoConfigureAfter(MybatisSessionFactory.class)  
public class MapperScan {
	  @Bean(name="writeMapperScanner")
	  public static MapperScannerConfigurer writeMapperScannerConfigurer() {
	        MapperScannerConfigurer writeMapperScannerConfigurer = new MapperScannerConfigurer();
	 
	        writeMapperScannerConfigurer.setBasePackage("com.cdo.cloud.mapper.write");
	        writeMapperScannerConfigurer.setSqlSessionFactoryBeanName("writeFactory");
	      
	        return writeMapperScannerConfigurer;
	    }
	  
	  @Bean(name="readMapperScanner")
	  public static  MapperScannerConfigurer readMapperScannerConfigurer() {
	        MapperScannerConfigurer readMapperScannerConfigurer = new MapperScannerConfigurer();
	 
	        readMapperScannerConfigurer.setBasePackage("com.cdo.cloud.mapper.read");
	        readMapperScannerConfigurer.setSqlSessionFactoryBeanName("readFactory");
	        return readMapperScannerConfigurer;
	    }
}
