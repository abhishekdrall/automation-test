package com.lattice.automation.service.impl;

import com.lattice.automation.exception.GenericException;
import com.lattice.automation.model.ProcessStatus;
import com.lattice.automation.service.AutomationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class AutomationServiceImpl implements AutomationService {

    @Autowired
    private Map<String, ProcessStatus> processStatusMap;

    @Autowired
    private Path path;

    @Autowired
    private Map<String,File> pomMap;

    @Override
    public ResponseEntity<?> automateProjectFile(final MultipartFile multipartFile) throws IOException, GenericException, InterruptedException {
        var unzippedDir= unzipProjectFile(uploadZipProjectFile(multipartFile));
        var process= runProjectFile(unzippedDir);
        System.out.println(process);
        return ResponseEntity.ok("process.getAbsolutePath()");
    }

    @Override
    public File uploadZipProjectFile(final MultipartFile multipartFile) throws GenericException, IOException {
        path.toFile().mkdirs();
        if(multipartFile.isEmpty())
            throw new GenericException(HttpStatus.NOT_ACCEPTABLE.value(),"File cannot to be empty");
        if(!multipartFile.getOriginalFilename().endsWith(".zip"))
            throw new GenericException(HttpStatus.NOT_ACCEPTABLE.value(),"Only zip file will be acceptable");
        var key=UUID.randomUUID().toString();
        pomMap.put(key,null);
        var path= Paths.get(this.path.toFile().getAbsolutePath(),key);
        log.info(multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(),path.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        log.info("Downloaded path-"+path.resolve(multipartFile.getOriginalFilename()).toFile().getAbsolutePath());
        return path.resolve(multipartFile.getOriginalFilename()).toFile();
    }

    @Override
    public File unzipProjectFile(final File zipFilePath) throws IOException {
        File destinationDir= new File(zipFilePath.getParent()+File.separator+ zipFilePath.getName().substring(0,zipFilePath.getName().indexOf(".zip")));
        destinationDir.mkdirs();
        Enumeration<? extends ZipEntry> zipEntries=new ZipFile(zipFilePath).entries();
        while(zipEntries.hasMoreElements()){
            ZipEntry entry=zipEntries.nextElement();
            InputStream inputStream=new ZipFile(zipFilePath.getAbsolutePath()).getInputStream(entry);
            BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
            if(entry.isDirectory()){
                new File(destinationDir.getAbsolutePath()+File.separator+entry.getName()).mkdirs();
                bufferedInputStream.close();
                inputStream.close();
                continue;
            }
            new File(destinationDir+File.separator+entry.getName()).getParentFile().mkdirs();
            FileOutputStream fileOutputStream=new FileOutputStream(destinationDir+File.separator+entry.getName());
            while(bufferedInputStream.available()>0)
                fileOutputStream.write(bufferedInputStream.read());
            fileOutputStream.flush();
            fileOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
        }
        return destinationDir;
    }

    @Override
    public File runProjectFile(final File destinationProjectDir) throws IOException, InterruptedException, GenericException {
        searchPomFile(destinationProjectDir);
        var pomPath=pomMap.get(pomMap.keySet().stream().filter(key->destinationProjectDir.getAbsolutePath().contains(key)).collect(Collectors.toList()).get(0));
        if(pomPath==null)
            throw new GenericException(HttpStatus.NOT_ACCEPTABLE.value(),"In this project cannot have Pom.xml file");
        processStatusMap.values().stream().filter(processStatus -> processStatus.getProcess().isAlive()).collect(Collectors.toList())
                .stream().forEach(processStatus -> processStatus.getProcess().destroyForcibly());
        Process process=Runtime.getRuntime().exec("mvn -f "+pomPath.getAbsolutePath()+" spring-boot:run");
        processStatusMap.put(pomMap.keySet().stream().filter(key->destinationProjectDir.getAbsolutePath().contains(key)).collect(Collectors.toList()).get(0),new ProcessStatus(process,false));
        System.out.println("process.exitValue()-"+process.pid());
//        process.destroy();
        System.out.println("end-"+pomMap);
        return null;
    }

    private void searchPomFile(File destinationProjectDir){
        var listFiles=destinationProjectDir.listFiles();
        if(listFiles!=null)
            for(File file:listFiles){
                if(file.isFile() && file.getName().toLowerCase().contains("pom.xml")) {
                    pomMap.put(pomMap.keySet().stream().filter(key->file.getAbsolutePath().contains(key)).collect(Collectors.toList()).get(0),file);
                    return;
                }
                else
                    searchPomFile(file);
            }
    }

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        System.out.println("Connection begin");
        var connection= DriverManager.getConnection("jdbc:mysql://139.59.6.59:3306","root","root");
        System.out.println("Connection success-"+connection);
    }
}
