package com.textToMorse.filters;

import com.textToMorse.Timer;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Arrays;

public class SoundFileWriterFilter extends Thread {

    private PipedReader inputPipe;
    private AudioFormat audioFormat;
    private ByteArrayOutputStream fileContent;
    private String fileName;
    private  Timer timer;

    private final static Integer DOT_LENGTH = 1000;
    private final static Integer BAR_LENGTH = 3 * DOT_LENGTH;
    private final static Integer TONALITY_SPACE_LENGTH = DOT_LENGTH;
    private final static Integer LETTER_SPACE_LENGTH = 3 * DOT_LENGTH;
    private final static Integer WORD_SPACE_LENGTH = 5 * DOT_LENGTH;

    public SoundFileWriterFilter(final PipedWriter inputPipe, final String fileName, final Timer timer){

        try {

            this.inputPipe = new PipedReader();
            this.audioFormat = new AudioFormat(10000f, 8, 1, true, false);
            this.fileContent = new ByteArrayOutputStream();
            this.fileName = fileName;
            this.timer = timer;

            this.inputPipe.connect(inputPipe);

        } catch (IOException e) {

            System.out.println("Error while instantiating SoundFileWriterFilter");
        }
    }

    private byte[] tone(final Integer length) {

        byte[] buf = new byte[length];

        for (int i = 0; i < length; i++) {
            buf[i] = (byte) (i * 200);
        }

        return buf;
    }

    private byte[] silence(final Integer length) {

        byte [] bytes = new byte[length];
        Arrays.fill(bytes, (byte)0);

        return bytes;
    }

    private void saveFile(String filename) throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        InputStream byteArray = new ByteArrayInputStream(this.fileContent.toByteArray());

        AudioInputStream ais = new AudioInputStream(byteArray, this.audioFormat, (long) this.fileContent.toByteArray().length);

        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
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

                    if (currentByte == '-') {

                        this.fileContent.write(tone(BAR_LENGTH));
                        this.fileContent.write(silence(TONALITY_SPACE_LENGTH));
                    } else if (currentByte == '.') {

                        this.fileContent.write(tone(DOT_LENGTH));
                        this.fileContent.write(silence(TONALITY_SPACE_LENGTH));
                    } else if (currentByte == ' ') {

                        this.fileContent.write(silence(LETTER_SPACE_LENGTH - TONALITY_SPACE_LENGTH));
                    } else if (currentByte == '/') {

                        this.fileContent.write(silence(WORD_SPACE_LENGTH - TONALITY_SPACE_LENGTH));
                    }
                }
            }

            this.saveFile(this.fileName);
            this.timer.stop();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
