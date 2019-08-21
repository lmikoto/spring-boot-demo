package com.example.demo.audio;

public class RewindCommand implements Command {
    private AudioPlayer myAudio;

    public RewindCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }

    @Override
    public void execute() {
        myAudio.rewind();
    }
}
