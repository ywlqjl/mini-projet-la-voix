package com.polytech.di.tianxue.voix_analyse;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 14/10/2017.
 */


public class WaveFile {
    private RandomAccessFile randomAccessFile;

    WaveFile(RandomAccessFile randomAccessFile){
        this.randomAccessFile = randomAccessFile;
    }

    public void addWaveHeader(int sampleRate, int bitsPerSecond){

        try {
            /* RIFF header */
            randomAccessFile.writeBytes("RIFF"); // riff id
            randomAccessFile.writeInt(0); // riff chunk size *PLACEHOLDER*
            randomAccessFile.writeBytes("WAVE"); // wave type

            /* fmt chunk */
            randomAccessFile.writeBytes("fmt "); // fmt id
            randomAccessFile.writeInt(Integer.reverseBytes(16)); // fmt chunk size
            randomAccessFile.writeShort(Short.reverseBytes((short) 1)); // format: 1(PCM)
            randomAccessFile.writeShort(Short.reverseBytes((short) 1)); // channels: 1
            randomAccessFile.writeInt(Integer.reverseBytes(sampleRate)); // samples per second
            randomAccessFile.writeInt(Integer.reverseBytes((int) (sampleRate * bitsPerSecond / 8))); // BPSecond
            randomAccessFile.writeShort(Short.reverseBytes((short) (bitsPerSecond / 8))); // BPSample
            randomAccessFile.writeShort(Short.reverseBytes((short) (bitsPerSecond))); // bPSample

            /* data chunk */
            randomAccessFile.writeBytes("data"); // data id
            randomAccessFile.writeInt(0); // data chunk size
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setWaveHeaderChunkSize(){

        try {
            // set RIFF chunk size
            randomAccessFile.seek(4);
            randomAccessFile.writeInt(Integer.reverseBytes((int) (randomAccessFile.length() - 8)));

            // set data chunk size
            randomAccessFile.seek(40);
            randomAccessFile.writeInt(Integer.reverseBytes((int) (randomAccessFile.length() - 44)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
