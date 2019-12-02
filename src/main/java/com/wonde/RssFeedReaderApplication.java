package com.wonde;

import com.wonde.database.FeedDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RssFeedReaderApplication {
	public static void main(String[] args) {

		FeedDatabase feedDB = new FeedDatabase();
			try {
			feedDB.readDatabase();
	//			SpringApplication.run(SpringbootrestApplication.class, args);
			} catch (Exception e) {
			e.printStackTrace();
			}
			SpringApplication.run(RssFeedReaderApplication.class, args);

	}


}
