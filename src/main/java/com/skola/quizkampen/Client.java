package com.skola.quizkampen;


import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client extends /*Task<Void> */ Thread {

    private/* static final */ String serverAddress; /* = "127.0.0.1"*/
    ;

    private Socket socket;
    private static int PORT = 5555;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    public Client(String serverAdress) throws IOException {
        this.serverAddress = serverAdress;

    }

    @Override
    /* protected Void call() */ public void run()/* throws Exception */ {
        try {
            socket = new Socket(serverAddress, PORT);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Object fromServer;
            while ((fromServer = inputStream.readObject()) != null) {
                processResponse(fromServer);
            }
        } catch (IOException | ClassNotFoundException e) {

        }
        /*return null;*/
    }


    public void sendObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    /**
     * Skickar object i form av sträng för att sätta användarnamn
     *
     * @param username som hämtas från GUI controllern
     * @throws IOException
     */
    public void initializeUser(String username /* TODO: bild för profilbild */) throws IOException {
        sendObject(username);
    }

    public void requestCategoryQuestions(Category category) {
        //TODO: skicka kategori
    }

    public void requestOtherUsername() {
        //TODO: skickar förfrågan till servern för motståndarens användarnamn
        // initierar GUIn med motståndarens användarnamn
    }

    public void requestStatistics(Integer numOfWins) throws IOException {
        //TODO: fundera ut vilket format vi ska skicka till servern
        sendObject(numOfWins);
    }


    /**
     * Metoden hanterar all inkommande data från servern och väljer vad som ska göras med den
     *
     * @param resFromServer objekt som kommer från servern
     */
    public void processResponse(Object resFromServer) {

        /*

        OM servern skickar en lista med frågor från ur en kategori
            starta runda med frågor
            startRound(List<Questions>)
            controller.startRound
        OM servern skickar resultat från föregående omgång
            be controllern visa ruta med statisktik och knapp att börja nästa omgång
        OM servern skickar motståndarens namn
            be controllern initiera GUIn med motståndarens namn

         */

    }

    public void startRound(List<Question> questions) {

        int numOfRound = questions.size();
        List<Boolean> answers;

        for (Question q : questions) {
            //TODO: be kontrollern visa fönster med fråga och alternativ som knappar
        }

        //skickar antal vinster till server

    }


    public static void main(String[] args) throws IOException {
        Client p = new Client("127.0.0.1");
        p.start();
    }


}
