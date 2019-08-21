package com.example.demo.base;

public class Invoker {

    private Command command = null;

    public Invoker(Command command){
        this.command = command;
    }

    public void action(){
        command.execute();
    }
}
