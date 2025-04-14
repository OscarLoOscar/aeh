package com.example.dimerco.hawb.aehawb;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dimerco.hawb.aehawb.model.CombinedResult;
import com.example.dimerco.hawb.aehawb.model.InputData;
import com.example.dimerco.hawb.aehawb.model.InvoiceData;
import com.example.dimerco.hawb.aehawb.model.Mapper;

@SpringBootApplication
public class AehawbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AehawbApplication.class, args);
	}
}
