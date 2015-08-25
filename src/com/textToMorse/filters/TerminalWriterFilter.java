package com.textToMorse.filters;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class TerminalWriterFilter extends Thread {

    private PipedReader inputPipe;

    public TerminalWriterFilter (final PipedWriter inputPipe){

        try {

            this.inputPipe = new PipedReader();
            this.inputPipe.connect(inputPipe);

        } catch (IOException e) {

            System.out.println("Error while instantiating TerminalWriterFilter");
        }
    }

    @Override
    public void run() {

        Boolean done;
        Integer currentByte;

        done = false;

        try {

            while (!done) {

                currentByte = inputPipe.read();

                if (currentByte == -1) {

                    done = true;
                } else {

                    System.out.print(Character.toChars(currentByte));
                }


            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
