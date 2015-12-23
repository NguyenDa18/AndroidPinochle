package edu.up.cs301.Pinochle;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.infoMsg.GameState;

/**
 *
 * Game state
 *
 * Created by Matthew Yuen on 10/16/2015.
 *
 * @author Matthew Yuen
 * @version October 16, 2015
 * Game state
 *
 * @author Matthew Yuen
 * @version October 26, 2015
 * Added functionality
 *
 * @author Matthew Yuen
 * @version November 7, 2015
 * mostly completed the game state
 */
public class PinochleGameState extends GameState {
    //satisfies Serializable interface
    public static final long serialVersionUID = 0L;

    //gamePhase constants
    public static final int BIDDING = 0;
    public static final int CHOOSE_TRUMP_SUIT = 1;
    public static final int PASSING = 2;
    public static final int MELDS = 3;
    public static final int TRICKTAKING = 4;

    //Holds the cards in the player's hands
    private ArrayList<ArrayList<Card>> playerCards;

    //holds the current score for the hand
    private int[] currentScore;

    //holds the current game score
    private int[] totalScore;

    //holds the cards the the players hold
    private ArrayList<Card> playerCurrentCard;

    //holds the id of the current player
    private int currentPlayer;

    //holds the current bid
    private int currentBid;

    //phase of game 0-bidding, 1-card passing, 2-trick taking
    private int gamePhase;

    //holds the contents of the log
    private String log;

    //holds the winner of the bid
    private int bidWinner;

    //hodls the current Trumpsuit
    private Suit trumpSuit;

    //cards that players pass
    private ArrayList<Card> passedCards;

    //holds id of players who passed in the bidding phase
    private ArrayList<Integer> playersPassList;
    /**
     * constructor
     */
    public PinochleGameState() {

        //add all cards to all players hands and sort them

        playerCards = new ArrayList<>();
        ArrayList<Card> player1Cards = new ArrayList<>();
        ArrayList<Card> player2Cards = new ArrayList<>();
        ArrayList<Card> player3Cards = new ArrayList<>();
        ArrayList<Card> player4Cards = new ArrayList<>();

        playerCards.add(player1Cards);
        playerCards.add(player2Cards);
        playerCards.add(player3Cards);
        playerCards.add(player4Cards);

        fillPlayerHands(makeDeck(), player1Cards, player2Cards, player3Cards, player4Cards);

        sortPlayerHand(player1Cards);
        sortPlayerHand(player2Cards);
        sortPlayerHand(player3Cards);
        sortPlayerHand(player4Cards);

        //current score for both teams set to 0
        currentScore = new int[2];
        currentScore[0] = 0;
        currentScore[1] = 0;
        //total score for both teams set to 0
        totalScore = new int[2];
        totalScore[0] = 0;
        totalScore[1] = 0;

        //set the currently played cards for each player to null
        playerCurrentCard = new ArrayList();
        Card player1CurrentCard = null;
        Card player2CurrentCard = null;
        Card player3CurrentCard = null;
        Card player4CurrentCard = null;
        playerCurrentCard.add(player1CurrentCard);
        playerCurrentCard.add(player2CurrentCard);
        playerCurrentCard.add(player3CurrentCard);
        playerCurrentCard.add(player4CurrentCard);

        currentPlayer = 0; //game starts with player number 0
        currentBid = 0; //bid starts with 0
        gamePhase = PinochleGameState.BIDDING; //game starts with bidding phase
        log=""; //default log text
        bidWinner=-1; //there is no bid winner yet
        trumpSuit=null;
        passedCards=new ArrayList<>();
        playersPassList=new ArrayList<>();

    }

    /**
     * makes a this a copy of the state passed
     * @param state state to copy
     */
    public PinochleGameState(PinochleGameState state) {
        //set variables to values of old state
        playerCards = state.getPlayerCards();

        currentScore = state.getCurrentScore();

        totalScore = state.getTotalScore();

        playerCurrentCard = state.getPlayerCurrentCard();

        currentPlayer = state.getCurrentPlayer();
        currentBid = state.getCurrentBid();
        gamePhase = state.getGamePhase();
        log = state.getLog();
        bidWinner=state.getBidWinner();
        trumpSuit=state.getTrumpSuit();
        passedCards=state.getPassedCards();
        playersPassList=state.getPlayersPassList();
    }

    /**
     * creates all Cards in a standard Pinochle deck
     * @return an ArrayList of all the cards in the deck
     */
    private ArrayList<Card> makeDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        //add two cards of each suit and rank
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }

    /**
     * distributes all the cards in the deck to all the players
     * @param deck ArrayList of all the cards
     * @param player1Cards ArrayList of cards of player 1
     * @param player2Cards ArrayList of cards of player 2
     * @param player3Cards ArrayList of cards of player 3
     * @param player4Cards ArrayList of cards of player 4
     * @return false if any of the parameters were null
     */
    private boolean fillPlayerHands(ArrayList<Card> deck, ArrayList<Card> player1Cards, ArrayList<Card> player2Cards, ArrayList<Card> player3Cards, ArrayList<Card> player4Cards) {
        if (deck==null||player1Cards==null||player2Cards==null|player3Cards==null||player4Cards==null) return false;
        while (deck.size() >= 4) {
            player1Cards.add(deck.remove((int) (Math.random() * deck.size())));
            player2Cards.add(deck.remove((int) (Math.random() * deck.size())));
            player3Cards.add(deck.remove((int) (Math.random() * deck.size())));
            player4Cards.add(deck.remove((int) (Math.random() * deck.size())));
        }
        return true;
    }

    /**
     * sorts the cards in the player's hand (insertion sort)
     * @param playerCards ArrayList of the players cards
     * @return false if passed a null object
     */
    public boolean sortPlayerHand(ArrayList<Card> playerCards) {
        if (playerCards==null) return false;
        //insertion sort
        for (int i = 1; i < playerCards.size(); i++) {
            int j;
            for (j = i; j >= 0; j--) {
                //convert the suit and rank into a number to compare more easily
                if (playerCards.get(i).getSuit().ordinal() * 10 + playerCards.get(i).getRank().ordinal()
                        < playerCards.get(j).getSuit().ordinal() * 10 + playerCards.get(j).getRank().ordinal()) {
                    break;
                }
            }
            playerCards.add(j + 1, playerCards.remove(i));
        }
        return true;
    }

    public ArrayList<ArrayList<Card>> getPlayerCards() {
        return playerCards;
    }

    public void setCurrentScore(int[] currentScore) {
        this.currentScore = currentScore;
    }

    public int[] getCurrentScore() {
        return currentScore;
    }

    public void setTotalScore(int[] totalScore) {
        this.totalScore = totalScore;
    }

    public int[] getTotalScore() {
        return totalScore;
    }

    public ArrayList<Card> getPlayerCurrentCard() {
        return playerCurrentCard;
    }

    public boolean setCurrentPlayer(int currentPlayer) {
        if (currentPlayer>3||currentPlayer<0) return false;
        this.currentPlayer = currentPlayer;
        return true;
    }

    public void nextCurrentPlayer(){ currentPlayer=(currentPlayer+1)%4;}

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void nextGamePhase() {
        gamePhase = (gamePhase + 1) % 5;
    }

    public int getGamePhase() {
        return gamePhase;
    }

    public void addToLog(String entry) {
       log = entry + "\n"+log;
    }

    public void clearLog(){
        log="";
    }
    public String getLog() {
        return log;
    }

    public void setBidWinner(int bidWinner) {
        this.bidWinner = bidWinner;
    }

    public int getBidWinner() {
        return bidWinner;
    }

    public void setTrumpSuit(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public boolean setPassedCards(ArrayList<Card> passedCards) {
        if (passedCards==null||passedCards.size()!=4) return false;
        this.passedCards = passedCards;
        return true;
    }
    public void clearPassedCards(){
        passedCards=new ArrayList<>();
    }

    public ArrayList<Card> getPassedCards() {
        return passedCards;
    }

    public void clearPlayerPassList(){
        playersPassList=new ArrayList();
    }
    public ArrayList<Integer> getPlayersPassList() {
        return playersPassList;
    }

    /**
     * next player to bid in the bidding phase
     */
    public void setNextBidPlayer(){
        nextCurrentPlayer();
        //iterate through pass list and check if player is not on it
        for (Integer player:playersPassList){
            if (getCurrentPlayer()==player){
                //call again until player is not on it
                setNextBidPlayer();
            }
        }
    }
}
