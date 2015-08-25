# Morse Encoder
A morse encoder based on the pipe and filter architecture. The data goes through 5 major filters running on different threads:

- *FileReaderFilter:* Reads the file and transmit the data byte by byte to the output pipe
- *MorseFilter:* Translates the bytes received from the input pipe to a morse sequence. The morse sequence is passed by signal ('-', '.', ' ') to the output pipes. Note that this specific filter will split the signal in two output pipes.
- *TerminalFilter:* Displays the data received from the input pipe to the terminal.
- *SoundFileWriterFilter:* Writes the data received from the input pipe to a WAV file.

## How to run
The program is expecting a file named "input.txt" at the root of the execution path. A "output.wav" file will be generated beside the input file. To run the program simply run the jar.