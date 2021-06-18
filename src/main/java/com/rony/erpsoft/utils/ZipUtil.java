/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author mdshahadat.sarker
 */
public class ZipUtil {
    
    public static void compress(String destinationPath, String format, String[] files){
        try {
                FileOutputStream fos = new FileOutputStream(destinationPath + "." + format);
                ZipOutputStream zos = new ZipOutputStream(fos);

                Files.createDirectories(Paths.get(destinationPath));
                for(String file : files){
                    copyFiles(destinationPath, file);
                }
                
                File dest = new File(destinationPath);
                File[] nFiles = dest.listFiles();
                
                for(File file : nFiles){
                    addToZipFile(file, zos);
                }

                zos.close();
                fos.close();

        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } finally{
            try{FileUtils.forceDelete(new File(destinationPath));} catch(Exception e){e.printStackTrace();}
        }
    }
    
    private static synchronized void copyFiles(String destinationPath, String originalFile){
        
        try{
            String pattern = java.util.regex.Pattern.quote(System.getProperty("file.separator"));
            String[] ar = originalFile.split(pattern);

            Path sDir = Paths.get(originalFile);
            Path tDir = Paths.get(destinationPath + File.separator + ar[ar.length-1]);

            Files.copy(sDir, tDir, StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static synchronized void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {
        
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}
