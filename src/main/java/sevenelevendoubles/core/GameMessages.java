package sevenelevendoubles.core;

public class GameMessages {
    static final String INVALID_NUMBER_MESSAGE = "Please provide a non zero positive number";
    static final String INVALID_ARGUMENTS = "Incorrect number of arguments for 'ADD";
    static final String INVALID_NAME = "Please enter a valid non empty string to use for name";
    static final String NAME_ALREADY_TAKEN = "Enter a different name as the provided one is already taken";
    static final String HELP_STRING = "This program simulates the 7-11-Doubles drinking game.\n" +
            "\n" +
            "The object of the game is to make other players drink their favorite beverage.  Each player takes turns rolling a pair of dice.  A winning roll of the dice occurs if the total of the dice is 7 or 11, or the dice show doubles (1-1, 2-2, etc.)  When the current player rolls a winning combination, they choose some other player to drink their beverage and the current player gets to roll again.  Any other combination is a losing roll and the current player's turn is over and the dice are passed to the next player.  If the current player rolls a losing combination and any other player is has not finished drinking, the current player ignores the losing roll and rolls again. Winning rolls are treated as above.  It is possible for more than one other player to be drinking at the same time.  In this simulation, the game can begin after two players have joined.  The game ends when only one player is left.  A player will leave the game after being made to drink a specified number of drinks.  If the player has drinks remaining, they must finish them, but should not be assigned any more drinks.\n" +
            " \n" +
            "Command:\n" +
            "\tHELP\tPrint these instruction\n" +
            "\tADD [player name] [drinking time]\tAdds named player.  \n" +
            "\t\tDrinking time is time to finish 1 drink\n" +
            "\tSPEED [seconds]\tNumber of seconds between rolls. \n" +
            "\t\tDefault 2.\n" +
            "\tMAX [drinks]\tNumber of drinks before player.\n" +
            "\t\tdrops out. Default 5.\n" +
            "\tSTART\tStart the simulation";
    static final String PAUSE_TIME = "Pause time between rolls is ";
    static final String MAX_DRINK_MESSAGE = "Maximum number of drinks is ";
    static final String MORE_PLAYERS_NEEDED = "At least 2 players required to start game";
}