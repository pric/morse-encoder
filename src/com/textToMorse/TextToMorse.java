package com.textToMorse;

import com.textToMorse.filters.FileReaderFilter;
import com.textToMorse.filters.MorseFilter;
import com.textToMorse.filters.SoundFileWriterFilter;
import com.textToMorse.filters.TerminalWriterFilter;

import java.io.PipedWriter;

public class TextToMorse {

    public static void main(String[] argc) {

        final Timer timer = new Timer();

        timer.start();

        final PipedWriter pipe01 = new PipedWriter();
        final PipedWriter pipe02 = new PipedWriter();
        final PipedWriter pipe03 = new PipedWriter();

        final Thread fileReaderFilter = new FileReaderFilter("input.txt", pipe01);
        final Thread morseFilter = new MorseFilter(pipe01, pipe02, pipe03);
        final Thread terminalWriterFilter = new TerminalWriterFilter(pipe02);
        final Thread soundFilter = new SoundFileWriterFilter(pipe03, "output.wav", timer);

        fileReaderFilter.start();
        morseFilter.start();
        terminalWriterFilter.start();
        soundFilter.start();
    }
}
