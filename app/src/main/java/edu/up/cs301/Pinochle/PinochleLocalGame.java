package edu.up.cs301.Pinochle;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Local Game
 * Created by Matthew Yuen on 10/13/2015.
 *
 * @author Matthew Yuen
 * @version October 13, 2015
 * Local Game
 *
 * @author Matthew Yuen
 * @version October 26, 2015
 * changed constructor
 * incomplete makemove
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * finished basic local game up to tricktaking
 */
public class PinochleLocalGame extends LocalGame {
    //holds the current game state
    PinochleGameState state;

    //holds the number of points required to win
    private int pointsToWin;


    //number used to make sure all players have moved
    private ArrayList<String> playerCycleNumber;

    /**
     * constructor
     * @param pointsToWin number of points to win the game
     */
    public PinochleLocalGame(int pointsToWin) {
        super();

        //create new state
        state = new PinochleGameState();

        //save the number of points to win
        this.pointsToWin=pointsToWin;

        //begin with player id 0
        playerCycleNumber =new ArrayList();
    }

    /**
     * sends state to player
     * @param p the player who we are sending the data
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //do nothing if null state
        if (state==null){
            return;
        }
        //send the player the state
        p.sendInfo(new PinochleGameState(state));

    }

    /**
     * checks if player can move
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return if the player can move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx==state.getCurrentPlayer();
    }

    /**
     * checks if the game is over
     * @return a string of who won or null if not over
     */
    @Override
    protected String checkIfGameOver() {
        return null;
    }

    /**
     * when a player makes a move
     * @param action
     * 			The move that the player has sent to the game
     * @return whether or not the player moved
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //different move based on the phase of the game
        if (state.getGamePhase()==PinochleGameState.BIDDING) {

            //make sure action is a possible action
            if (action instanceof PinochlePassAction || action instanceof PinochlePassWithHelpAction || action instanceof PinochleBidAction) {

                //update current bid if a bid action
                if (action instanceof PinochleBidAction) {
                    //make sure bid is legal
                    if (((PinochleBidAction) action).getBid()<25||state.getCurrentBid()>=((PinochleBidAction) action).getBid()) return false;
                    //update
                    state.setCurrentBid(((PinochleBidAction) action).getBid());
                }
                //add to list of players who passed if not
                else {
                    state.getPlayersPassList().add(state.getCurrentPlayer());
                }
                //next player to bid
                state.setNextBidPlayer();
                state.addToLog(action.toString());
            }
            //if 3 players pass then next game phase
            if (state.getPlayersPassList().size() == 3) {
                state.nextGamePhase();
                state.setBidWinner(state.getCurrentPlayer());
                state.clearPlayerPassList();
                if (state.getCurrentBid()==0) {
                    state.setCurrentBid(25);
                }
            }
        }
        else if (state.getGamePhase()==PinochleGameState.CHOOSE_TRUMP_SUIT) {
            //make sure action is possible
            if (action instanceof PinochleChooseTrumpSuitAction) {
                //next phase
                state.nextGamePhase();
                //set current player to the partner who will be passing first
                state.setCurrentPlayer((state.getCurrentPlayer() + 2) % 4);
                state.setTrumpSuit(((PinochleChooseTrumpSuitAction) action).getSuit());

                //used for comparing cards
                Card.setTrumpSuit(((PinochleChooseTrumpSuitAction) action).getSuit());
                state.addToLog(action.toString());
            }
        }
        else if (state.getGamePhase()==PinochleGameState.PASSING) {
            //make sure action is a possible move
            if (action instanceof PinochlePassCardsAction){
                //add cards to partner and remove from this player's hand
                state.getPlayerCards().get((state.getCurrentPlayer()+2)%4).addAll(((PinochlePassCardsAction) action).getPassCards());
                state.getPlayerCards().get(state.getCurrentPlayer()).removeAll(((PinochlePassCardsAction) action).getPassCards());
                state.sortPlayerHand(state.getPlayerCards().get((state.getCurrentPlayer() + 2) % 4));
                state.setPassedCards(((PinochlePassCardsAction) action).getPassCards());
                //if it is the bid winner's turn again
                if (state.getCurrentPlayer()==state.getBidWinner()){

                    state.nextGamePhase();
                }
                else{
                    //its the bid winners turn now that his partner has passed cards
                    state.setCurrentPlayer(state.getBidWinner());
                }
                state.addToLog(action.toString());
            }
        }
        else if (state.getGamePhase()==PinochleGameState.MELDS){
            //only possible action
            if (action instanceof PinochleMeldsAction){

                //if player already passed return false;
                if (playerCycleNumber.contains(((PinochleMeldsAction) action).getName())) return false;

                //add another player who is ready
                playerCycleNumber.add(((PinochleMeldsAction) action).getName());

                //next player to confirm ready for next phase
                state.nextCurrentPlayer();
                //if all players are ready
                if (playerCycleNumber.size()==4){
                    //next game phase once all players are ready
                    state.clearPassedCards();
                    //reset cycle number
                    playerCycleNumber.clear();
                    state.nextGamePhase();
                }
                state.addToLog(action.toString());
            }
        }
        else if(state.getGamePhase()==PinochleGameState.TRICKTAKING){
            //only action allowed
            if (action instanceof PinochlePlayCardAction){
                //check if player already played a card
                if (playerCycleNumber.contains(((PinochlePlayCardAction) action).getName())) return false;

                //another player moved
                playerCycleNumber.add(((PinochlePlayCardAction) action).getName());

                state.addToLog(action.toString());
                //retrieve card and remove it from the players hand and add it to the played card list
                ArrayList<Card> currentCards=state.getPlayerCurrentCard();
                state.getPlayerCards().get(state.getCurrentPlayer()).remove(((PinochlePlayCardAction) action).getCard());
                currentCards.set(state.getCurrentPlayer(), ((PinochlePlayCardAction) action).getCard());

                //next player
                state.nextCurrentPlayer();

                //all players have moved?
                if (playerCycleNumber.size()==4){
                    //show all cards to the players
                    sendAllUpdatedState();
                    //let them see it for a second
                    try {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e) {
                        System.exit(1);
                    }

                    //check who won the trick

                    //save current player
                    int currentPlayer=state.getCurrentPlayer();

                    Card first=state.getPlayerCurrentCard().get(currentPlayer);
                    Card second=state.getPlayerCurrentCard().get((currentPlayer+1)%4);
                    Card third=state.getPlayerCurrentCard().get((currentPlayer+2)%4);
                    Card fourth=state.getPlayerCurrentCard().get((currentPlayer+3)%4);
                    Card winner=Card.compareTo(Card.compareTo(Card.compareTo(first, second), third), fourth);

                    int trickWinner=-1;

                    //check which card is the winning card
                    if (winner==first){
                        trickWinner=currentPlayer;
                    }
                    else if (winner==second){
                        trickWinner=(currentPlayer+1)%4;
                    }
                    else if (winner==third){
                        trickWinner=(currentPlayer+2)%4;
                    }
                    else if (winner==fourth){
                        trickWinner=(currentPlayer+3)%4;
                    }
                    state.addToLog(playerNames[trickWinner]+ " won the trick");

                    //current player should be winner of the trick
                    state.setCurrentPlayer(trickWinner);

                    //calculate points of the cards
                    int points=0;
                    if (first.isCounter()) points++;
                    if (second.isCounter()) points++;
                    if (third.isCounter()) points++;
                    if (fourth.isCounter()) points++;

                    state.getCurrentScore()[trickWinner%2]+=points;

                    //reset the number of players that played a card
                    playerCycleNumber.clear();

                    //no current cards cause end of trick
                    state.getPlayerCurrentCard().set(0,null);
                    state.getPlayerCurrentCard().set(1,null);
                    state.getPlayerCurrentCard().set(2, null);
                    state.getPlayerCurrentCard().set(3, null);


                }
            }
        }

        return true;
    }
}
