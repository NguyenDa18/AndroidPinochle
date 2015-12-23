package edu.up.cs301.Pinochle;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * base computer class
 *
 * Created by Matthew Yuen on 10/16/2015
 *
 * @author Matthew Yuen
 * @version October 26, 2015
 * basic functionality used for testing other classes
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * framework for the rest of the computer players
 */
public class PinochleComputerPlayer extends GameComputerPlayer {
    //holds the current state of the game;
    protected PinochleGameState state;

    /**
     * constructor
     * @param name name of player
     */
    public PinochleComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        //do not do anything if we do not have a pinochle gamestate
        if (!(info instanceof PinochleGameState)) return;

        //computer is thinking for a second
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e){
            System.exit(1);
        }

        //do nothing if it is not our turn
        if (((PinochleGameState) info).getCurrentPlayer() != playerNum) return;

        //save the state
        state = (PinochleGameState) info;


        //send appropriate action based on the phase of the game
        if (state.getGamePhase() == PinochleGameState.BIDDING) {
            game.sendAction(biddingPhase());
        } else if (state.getGamePhase() == PinochleGameState.CHOOSE_TRUMP_SUIT) {
            game.sendAction(chooseTrumpSuit());
        } else if (state.getGamePhase() == PinochleGameState.PASSING) {
            game.sendAction(passingPhase());
        } else if (state != null && state.getGamePhase() == PinochleGameState.MELDS) {
            game.sendAction(melds());
        } else if (state.getGamePhase() == PinochleGameState.TRICKTAKING) {
            game.sendAction(trickTaking());
        }

    }

    protected /*abstract*/ GameAction biddingPhase() {
        //if (Math.random() < .1) {
            return (new PinochlePassAction(this, name));
       // }
       // int bid = state.getCurrentBid() + 1;
        //return (new PinochleBidAction(this, name, bid));
    }

    protected /*abstract*/ GameAction chooseTrumpSuit() {
        ArrayList<Card> cards = state.getPlayerCards().get(playerNum);
        for (Card card : cards) {
        }
        return (new PinochleChooseTrumpSuitAction(this, name, Suit.Spade));
    }

    protected /*abstract*/ GameAction passingPhase() {
        return (new PinochlePassCardsAction(this, name, new ArrayList<Card>(state.getPlayerCards().get(playerNum).subList(0, 4))));
    }

    protected /*abstract*/ GameAction melds() {
        return (new PinochleMeldsAction(this, name));
    }

    protected /*abstract*/ GameAction trickTaking() {
        return (new PinochlePlayCardAction(this, name, state.getPlayerCards().get(playerNum).get(0)));
    }
}
