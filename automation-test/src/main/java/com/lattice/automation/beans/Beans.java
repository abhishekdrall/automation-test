package com.lattice.automation.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Map<String,File> mapPomFile(){
        return new HashMap<String,File>();
    }
}
