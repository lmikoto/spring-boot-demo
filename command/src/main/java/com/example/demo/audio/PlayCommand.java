package com.example.demo.audio;

public class PlayCommand implements Command {
    private AudioPlayer myAudio;

    public PlayCommand(AudioPlayer audioPlayer){
        myAudio = audioPlayer;
    }

    @Override
    public void execute() {
        myAudio.play();
    }
}
