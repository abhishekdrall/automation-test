package com.lattice.automation.service.impl;

import com.lattice.automation.exception.GenericException;
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
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Slf4j
public class AutomationServiceImpl implements AutomationService {

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
        var key=UUID.randomUUID().toString();
        pomMap.put(key,null);
        var path= Paths.get(this.path.toFile().getAbsolutePath(),key);
        path.toFile().mkdirs();
        if(multipartFile.isEmpty())
            throw new GenericException(HttpStatus.NOT_ACCEPTABLE.value(),"File cannot to be empty");
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
    public File runProjectFile(final File destinationProjectDir) throws IOException, InterruptedException {
        searchPomFile(destinationProjectDir);
        var pomPath=pomMap.get(pomMap.keySet().stream().filter(key->destinationProjectDir.getAbsolutePath().contains(key)).collect(Collectors.toList()).get(0));
        System.out.println("Shiv-"+pomPath.getAbsolutePath());
        Process process=Runtime.getRuntime().exec("mvn -f "+pomPath.getAbsolutePath()+" spring-boot:run");
//        System.out.println("end exc"+process.waitFor());
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

    public static void main(String[] args) throws IOException, InterruptedException {
        var p=new File("E:\\Lattice tasks\\Projects\\automation-test\\automation-test\\assignments\\df6e803a-4db6-4dce-b1be-0c3a0d49ef57\\lattice-decryptor\\lattice-decryptor\\lattice-decryptor");
        var cmd="mvn clean install";
        System.out.println(cmd);
        Process process=Runtime.getRuntime().exec("sudo jenkins",null,p);
        int exitCode= process.waitFor();
        System.out.println(exitCode);
    }
}
