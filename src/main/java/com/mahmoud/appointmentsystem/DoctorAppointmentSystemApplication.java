package com.mahmoud.appointmentsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.catalina.util.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DoctorAppointmentSystemApplication {

	private  static final Logger logger = LoggerFactory.getLogger(DoctorAppointmentSystemApplication.class);

	/**
	 * Main method to run the Doctor Appointment System application.
	 * It loads environment variables from a .env file based on the active profile.
	 *
	 * @param args command line arguments
	 */

	public static void main(String[] args) throws UnknownHostException {

        //
        //Properties properties = System.getProperties();
		String inetAddress = InetAddress.getLocalHost().getHostName();
        System.out.println("InetAddress: " + inetAddress);
        // my Desktop
		if (inetAddress.toUpperCase().equals("DESKTOP-7HGNMJC")) {
            System.setProperty("spring.profiles.active", "dev");
        } else  if (inetAddress.toUpperCase().equals("production server name")){
            System.setProperty("spring.profiles.active", "prod");
        }
        // in above, we can specify dev or prod based on InetAddress


		// auto Load environment variables from the.env file based on the active profile
		String profile = System.getProperty("spring.profiles.active","dev");

				logger.info("Active profile: {}", profile);

		Dotenv dotenv = Dotenv.configure()
				.filename(".%s.env".formatted(profile))
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(DoctorAppointmentSystemApplication.class, args);
	}

}
