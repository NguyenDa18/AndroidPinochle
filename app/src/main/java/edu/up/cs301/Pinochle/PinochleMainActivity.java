package edu.up.cs301.Pinochle;

import android.widget.NumberPicker;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.R;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

import java.util.ArrayList;
/**
 * Main Activity
 * Created by Matthew Yuen on 10/13/2015.
 *
 * @author Matthew Yuen
 * @version October 13, 2015
 * set default config
 * @version October 26, 2015
 * changed localgame constructor
 */
public class PinochleMainActivity extends edu.up.cs301.game.GameMainActivity {
    //This was a variable in Slapjack
    public static final int PORT_NUMBER=4752;


    @Override
    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> players=new ArrayList<>();
        players.add(new GamePlayerType("Human") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PinochleHumanPlayer(name);
            }
        });
        players.add(new GamePlayerType("Computer") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new PinochleComputerPlayer(name);
            }
        });
         GameConfig defaultConfiguration = new GameConfig(players, 4, 4, "Pinochle", PORT_NUMBER);

        defaultConfiguration.addPlayer("Human",0);
        defaultConfiguration.addPlayer("Computer1",1);
        defaultConfiguration.addPlayer("Computer2",1);
        defaultConfiguration.addPlayer("Computer3",1);

        defaultConfiguration.setRemoteData("Guest","",0);
        return defaultConfiguration;
    }

    @Override
    public LocalGame createLocalGame() {
        return new PinochleLocalGame(getNumPointsToWin());
    }
}
