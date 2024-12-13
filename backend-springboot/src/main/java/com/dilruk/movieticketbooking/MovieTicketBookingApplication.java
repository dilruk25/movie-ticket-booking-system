package com.dilruk.movieticketbooking;

import com.dilruk.movieticketbooking.config.SystemConfig;
import com.dilruk.movieticketbooking.services.thread.CustomerThread;
import com.dilruk.movieticketbooking.services.thread.TicketProcessor;
import com.dilruk.movieticketbooking.services.thread.VendorThread;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MovieTicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketBookingApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(TicketProcessor ticketProcessor, SystemConfig systemConfig) {
//		return args -> {
//			VendorThread vendorThread = new VendorThread(systemConfig, ticketProcessor);
//			CustomerThread customerThread = new CustomerThread(systemConfig, ticketProcessor);
//
//			vendorThread.start();
//			customerThread.start();
//		};
//	}
}
