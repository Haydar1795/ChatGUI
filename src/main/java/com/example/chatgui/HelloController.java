package com.example.chatgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HelloController {
    DataOutputStream out;

    public HelloController() {
    }

    @FXML
    TextField textField;
    @FXML
    TextArea textArea;

    @FXML
    protected void handlerSand() throws IOException {
        String text = textField.getText();
        textArea.appendText(" Вы: " + text + "\n");
        textField.clear();
        textField.requestFocus();
        out.writeUTF(text);
    }

    @FXML
    public void connect() {
        try {
            Socket socket = new Socket("127.0.0.1", 9743);
            this.out = new DataOutputStream(socket.getOutputStream());
            DataInputStream is = new DataInputStream(socket.getInputStream());
            Thread thread = new Thread(new Runnable() {
                @Override

                public void run() {
                    while (true) {
                        try {
                            String response = is.readUTF();
                            textArea.appendText(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}