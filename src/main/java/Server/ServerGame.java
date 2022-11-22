package Server;

public class ServerGame {
    ServerSidePlayer theCurrentPlayer;

    public synchronized boolean playerTurn(ServerSidePlayer player){
        if(player == theCurrentPlayer){
            theCurrentPlayer = theCurrentPlayer.opponant;
            return true;
        }
        return false;
    }

    /*
        Eventuellt ta inspiration från:
        https://github.com/sigrunolafsdottir/natverksprogrammering/blob/master/src/TicTacToeSimple/ServerSideGame.java
        Vad vill vi behålla och inte behålla.
    */
}
