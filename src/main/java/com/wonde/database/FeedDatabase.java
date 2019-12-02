package com.wonde.database;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import com.wonde.feed.Feed;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class FeedDatabase {

    String databaseName = "";
    String url = ""; // https://www.elle.com/rss/all.xml/

    final String USER_NAME = "root";
    final String PASSWORD = "root";

    // JDBC driver name and MySql database url
    private Feed feed = new Feed();
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String MYSQL_URL = "jdbc:mysql://localhost:3306/";

    Connection conn = null;

    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS feed "
            + "(id INTEGER NOT NULL AUTO_INCREMENT, "
            + "Title TEXT, "
            + "Link TEXT, "
            + "Category VARCHAR(255), "
            + "Description TEXT, "
            + "Author VARCHAR(255), "
            + "Published_date VARCHAR(255), "
            + "PRIMARY KEY ( id ))";

    public FeedDatabase() {

        boolean entity = true;
        Scanner in = new Scanner(System.in);
        System.out.println("enter the url");

        String input = in.nextLine();

        while (entity) {

            if (input.contains("https://www.") && input.contains(".xml")) {

                this.url = input;
                for (int i = 12; i < input.length(); i++) {
                    if (input.charAt(i) == '.') {
                        this.databaseName = input.substring(12, i);
                        System.out.println("Database name : " + this.databaseName);
                        entity = false;
                        break;
                    }
                }
            } else if (input.contains("http://www.") && input.contains(".xml")) {

                this.url = input;
                for (int i = 11; i < input.length(); i++) {
                    if (input.charAt(i) == '.') {
                        this.databaseName = input.substring(11, i);
                        System.out.println("Database name : " + this.databaseName);
                        entity = false;
                        break;
                    }
                }
            }
            else
            {
                System.out.println("Wrong URL, please try again");

                System.out.println("1. To continue press 1\n2. To terminate press 0");

                input = in.nextLine();

                try {
                    int choice = Integer.parseInt(input);
                    if (choice == 1) {
                        System.out.println("please enter url again");

                        input = in.nextLine();
                        entity = true;
                    } else if (choice == 0) {
                        System.out.println("The program is terminated");
                        System.exit(0);
                    } else {

                        System.out.println("Not in option, please try again");
                        System.out.println("1. To continue press 1\n2. To terminate press 0");
                        choice = Integer.parseInt(input);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong choice please try again");
                    entity = true;
                }
            }

        }

        in.close();
    }

    public void readDatabase() throws Exception {

        URL feedSource = new URL(this.url); // "https://www.elle.com/rss/all.xml/"
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feeder = input.build(new XmlReader(feedSource));

//		Loading jdbc driver
        Class.forName(this.JDBC_DRIVER);

//		Creating database
        String sqlDB = "CREATE DATABASE IF NOT EXISTS " + this.databaseName;

        String urlSql = this.MYSQL_URL + this.databaseName;

        try {
//			Opening connection
            conn = DriverManager.getConnection(this.MYSQL_URL, this.USER_NAME, this.PASSWORD);

//			Executing the query
            PreparedStatement prepDB = conn.prepareStatement(sqlDB);

            prepDB.execute();

            System.out.println("Database created!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        conn = DriverManager.getConnection(urlSql, this.USER_NAME, this.PASSWORD);

        PreparedStatement prepTable = conn.prepareStatement(this.CREATE_TABLE_SQL);
        prepTable.execute();

        for (SyndEntry entry : feeder.getEntries()) {

            try {

//				Opening connection
                conn = DriverManager.getConnection(urlSql, this.USER_NAME, this.PASSWORD);

                String query = " insert into feed (Title, Link, Category, Description, Author, Published_date) "
                        + "Values (?, ?, ?, ?, ?, ?)";

                PreparedStatement prepStmt = conn.prepareStatement(query);

                prepStmt.setString(1, entry.getTitle());
                prepStmt.setString(2, entry.getLink());
                prepStmt.setString(3, entry.getCategories().get(feed.getId()).toString());
                prepStmt.setString(4, entry.getDescription().getValue());
                prepStmt.setString(5, entry.getAuthor());
                prepStmt.setString(6, entry.getPublishedDate().toString());

                prepStmt.executeUpdate();

                conn.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        System.out.println("Database connected seccefully and data inserted into the table created");
    }

}
