package edu.up.cs301.Pinochle;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.actionMsg.MyNameIsAction;

/**
 * Play card action
 *
 * Created by Matthew Yuen on 10/23/2015.
 *
 * @author Matthew Yuen
 * @version October 23, 2015
 * created class
 * @author Matthew Yuen
 * @version November 7, 2015
 * extends PinochleGameAction
 */
public class PinochlePlayCardAction extends PinochleGameAction{
    //holds the card that the play is playing
    private Card card;

    /**
     * constructor
     *
     * @param p player who's action it is
     * @param name name of that player
     * @param card card they are sending
     */
    public PinochlePlayCardAction(GamePlayer p, String name,Card card) {
        super(p,name);
        this.card=card;
    }

    /**
     * getter method for card
     * @return the card
     */
    public Card getCard(){
        return card;
    }

    /**
     * toString method
     * @return a string used for the log
     */
    @Override
    public String toString(){
        return getName()+"\n  "+card.toString();
    }
}
