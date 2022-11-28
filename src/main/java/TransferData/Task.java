package TransferData;

public enum Task {
    OPPONENT_NAME,
    PROPERTIES_PROTOCOL,
    NOT_YOUR_TURN,
    CHOOSE_CATEGORY,
    START_ROUND,
    GAME_FINISHED,
    ROUND_FINISHED,
    START_GAME,
    OPPONENT_SCORE,
    STATISTICS,
    OPPONENTS_NAME,
    GAME_RESULT,
    SET_QUESTIONS

}

// Håller koll på vilka uppgifter servern har.


/*public void sendData(Data data) {
        try {
            output.writeObject(data);
            flushAndReset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/