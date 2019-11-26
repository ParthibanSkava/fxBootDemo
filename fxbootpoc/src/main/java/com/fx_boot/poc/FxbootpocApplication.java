package com.fx_boot.poc;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fx_boot.poc.controller.PocHelper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@ComponentScan(basePackages = { "com.fx_boot.poc" })
@EnableAutoConfiguration
@SpringBootApplication
public class FxbootpocApplication extends Application {

	private ConfigurableApplicationContext context;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		launch(args);

		// SpringApplication.run(FxbootpocApplication.class, args);
	}

	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FxbootpocApplication.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();

		String pID = "Hello";
		primaryStage.setTitle("Hello World !");
		Button btn = new Button();
		btn.setText(" Say, Hello !!! ");
		btn.setOnAction(evt -> {
			PocHelper pocHelper = new PocHelper();
			pocHelper.getDetailsById(pID);
		});
		Scene scene = new Scene(root, 300, 300);
		root.getChildren().add(btn);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		context.close();
	}

	// TODO: Create a form in FX

	// TODO: Write action to the button

	// TODO: Implement a service

	// TODO: Service Implementation

	// TODO: set the value to the second text box
}
