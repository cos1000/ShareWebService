/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.sharewebservice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author matt_
 */
public class FileHandler {
    
    public static boolean SaveToFile(String filename, String content) throws IOException {
        File file = new File(filename);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(content);
            fileWriter.flush();
            return true; 
        } 
    }
    
    public static String ReadFromFile(String filename) throws IOException {
        StringBuilder answer = new StringBuilder();
        File file = new File(filename);
        try (FileReader fileReader = new FileReader(file)) {
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while (bufferedReader.ready()) {
                    answer.append(bufferedReader.readLine()); 
                }
            }
        } 
        return answer.toString(); 
    }
    
}
