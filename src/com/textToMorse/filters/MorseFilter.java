package com.textToMorse.filters;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.HashMap;

public class MorseFilter extends Thread {

    private HashMap<Character, String> dictionary;
    private PipedReader inputPipe;
    private PipedWriter outputPipe1;
    private PipedWriter outputPipe2;

    public MorseFilter (final PipedWriter inputPipe, final PipedWriter outputPipe1, final PipedWriter outputPipe2) {

        try {

            this.inputPipe = new PipedReader();

            this.inputPipe.connect(inputPipe);
            this.outputPipe1 = outputPipe1;
            this.outputPipe2 = outputPipe2;
            this.dictionary = buildDictionary();

        } catch (IOException e) {

            System.out.println("Error while instantiating MorseFilter");
        }
    }

    private HashMap<Character, String> buildDictionary() {

        final HashMap<Character, String> dictionary = new HashMap<>();
        dictionary.put('A', ".-");
        dictionary.put('B', "-...");
        dictionary.put('C', "-.-.");
        dictionary.put('D', "-..");
        dictionary.put('E', ".");
        dictionary.put('F', "..-.");
        dictionary.put('G', "--.");
        dictionary.put('H', "....");
        dictionary.put('I', "..");
        dictionary.put('J', ".---");
        dictionary.put('K', "-.-");
        dictionary.put('L', ".-..");
        dictionary.put('M', "--");
        dictionary.put('N', "-.");
        dictionary.put('O', "---");
        dictionary.put('P', ".--.");
        dictionary.put('Q', "--.-");
        dictionary.put('R', ".-.");
        dictionary.put('S', "...");
        dictionary.put('T', "-");
        dictionary.put('U', "..-");
        dictionary.put('V', "...-");
        dictionary.put('W', ".--");
        dictionary.put('X', "-..-");
        dictionary.put('Y', "-.--");
        dictionary.put('Z', "--..");
        dictionary.put('0', "-----");
        dictionary.put('1', ".----");
        dictionary.put('2', "..---");
        dictionary.put('3', "...--");
        dictionary.put('4', "....-");
        dictionary.put('5', ".....");
        dictionary.put('6', "-....");
        dictionary.put('7', "--...");
        dictionary.put('8', "---..");
        dictionary.put('9', "----.");
        dictionary.put(' ', "/");
        dictionary.put('.', ".-.-.-");
        dictionary.put(',', "--..--");
        dictionary.put(':', "---...");
        dictionary.put('?', "..--..");
        dictionary.put('\'', ".----.");
        dictionary.put('-', "-....-");
        dictionary.put('/', "-..-.");
        dictionary.put(')', "-.--.-");
        dictionary.put('(', "-.--.-");
        dictionary.put('\"', ".-..-.");
        dictionary.put('@', ".--.-.");
        dictionary.put('=', "-...-");

        return dictionary;
    }

    @Override
    public void run() {

        Boolean done;
        int currentByte;

        done = false;

        try {

            while (!done) {

                currentByte = inputPipe.read();

                if (currentByte == -1) {

                    done = true;

                } else {

                    char currentChar = Character.toUpperCase((char) currentByte);
                    String morseLetter = dictionary.get(currentChar);

                    outputPipe1.write(morseLetter);
                    outputPipe2.write(morseLetter);

                    outputPipe1.write(' ');
                    outputPipe2.write(' ');

                    outputPipe1.flush();
                    outputPipe2.flush();
                }

            }

            outputPipe1.close();
            outputPipe2.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
