package com.slq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.slq.util.IDGenerateUtil;

@SpringBootApplication
public class ProductionModuleApplication {

	public static void main(String[] args) {
		System.out.println(IDGenerateUtil.getID("cs"));
		//SpringApplication.run(ProductionModuleApplication.class, args);
	}
}
