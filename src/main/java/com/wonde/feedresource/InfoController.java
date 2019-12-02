package com.wonde.feedresource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    @RequestMapping("/")
    public Map getStatus(){
        Map map = new HashMap<String, String>();
        map.put("app-version", appVersion);
        return map;
    }
    @Value("${database.name}")
    private String databaseName;
    @GetMapping
    @RequestMapping("/databasename")
    public Map getDatabaseName(){
        Map map = new HashMap<String, String>();
        map.put("Database-name", databaseName);
        return map;
    }

    @Value("${table.name}")
    private String tableName;
    @GetMapping
    @RequestMapping("/databasename/tablename")
    public Map getTableName(){
        Map map = new HashMap<String, String>();
        map.put("Table-name", tableName);
        return map;
    }

    @GetMapping
    @RequestMapping("/databaseinfo")
    public Map getDatabaseInfo(){
        Map map = new HashMap<String, String>();
        map.put("Table-name", tableName);
        map.put("Database-name", databaseName);
        return map;
    }
}
