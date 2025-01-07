package com.zgamelogic.dataotter.agent.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

@Service
public class PersistenceService {
    private final File dataFile;

    public PersistenceService() {
        dataFile = new File("agent.dat");;
    }

    public Optional<Long> getAgentId(){
        if(!dataFile.exists()) return Optional.empty();
        try {
            Scanner scanner = new Scanner(dataFile);
            return Optional.of(Long.parseLong(scanner.nextLine()));
        } catch (FileNotFoundException | NumberFormatException e) {
            return Optional.empty();
        }
    }

    public void saveAgentId(long id) {
        dataFile.delete();
        try {
            dataFile.createNewFile();
            PrintWriter printWriter = new PrintWriter(dataFile);
            printWriter.println(id);
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
