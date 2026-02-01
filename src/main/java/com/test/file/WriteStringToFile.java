package com.test.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;


public class WriteStringToFile {
    public static void main(String[] args)throws IOException {
        String filePath = "guids.txt";

        try(FileWriter writer = new FileWriter(filePath)){
            for(int i=0;i<Integer.MAX_VALUE;i++){
                writer.write(UUID.randomUUID().toString()+ "\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
