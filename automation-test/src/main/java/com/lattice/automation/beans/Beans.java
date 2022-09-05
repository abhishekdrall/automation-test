package com.lattice.automation.beans;

import com.lattice.automation.model.ProcessStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Beans {

    @Bean
    public Path getServerDownloadPath(){
        File file= new File(System.getProperty("user.dir")+ File.separator+"assignments");
        file.mkdirs();
        return Paths.get(file.getAbsolutePath());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Map<String,File> mapPomFile(){
        return new HashMap<String,File>();
    }

    @Bean
    public Map<String, ProcessStatus> processBean(){
        return new HashMap<String,ProcessStatus>();
    }

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://139.59.6.59:3306/assignment","shiv","uP$4.wX2bhJW5Dqdgfthdx#");
    }
}
