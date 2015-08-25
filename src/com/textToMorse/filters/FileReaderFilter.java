package com.textToMorse.filters;

import java.io.File;
import java.io.FileInputStream;
import java.io.PipedWriter;

public class FileReaderFilter extends Thread {

    private PipedWriter outputPipe;
    private File inputFile;

    public FileReaderFilter (final String fileName, final PipedWriter outputPipe) {

        if (!fileName.isEmpty()) {

            this.inputFile = new File(fileName);
            this.outputPipe = outputPipe;

            if (!inputFile.exists()) {

                throw new IllegalArgumentException(String.format("[%s] doesn't exist", inputFile.getAbsolutePath()));
            }
        } else {

            throw new IllegalArgumentException("[fileName] is mandatory");
        }

    }

    @Override
    public void run () {

        FileInputStream fileInputStream;
        Integer readByte;

        try {

            fileInputStream = new FileInputStream(inputFile);

            while ((readByte = fileInputStream.read()) != -1) {

                outputPipe.write(readByte);
                outputPipe.flush();
            }

            outputPipe.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
