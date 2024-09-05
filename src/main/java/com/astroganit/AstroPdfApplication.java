package com.astroganit;

import java.io.File;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import com.astroganit.calculation.util.Local;
import com.astroganit.kundlilib.dhoro.DesktopHoro;
import com.astroganit.kundlilib.util.Constants;

@SpringBootApplication
public class AstroPdfApplication {
	//static DesktopHoro desktopHoro;

	public static void main(String[] args) {
		SpringApplication.run(AstroPdfApplication.class, args);
		//initHoro();
	}
	
}
