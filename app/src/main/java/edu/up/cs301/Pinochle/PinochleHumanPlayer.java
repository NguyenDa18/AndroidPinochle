package edu.up.cs301.Pinochle;

import android.app.ActionBar;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Human player
 *
 * Created by Matthew Yuen on 10/13/2015.
 *
 * @author Matthew Yuen
 * @version October 13, 2015
 *
 * @version October 26, 2015
 * basic gui for the bidding phase
 */
public class PinochleHumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    //value if no screen is loaded
    private static int NO_SCREEN_LOADED=-1;

    //holds the activity of game
    protected GameMainActivity activity;

    //holds the state of the game
    protected PinochleGameState state;

    //holds which layout is currently loaded
    private int currentLayout;

    //holds a list of the card that a player would pass to another
    private ArrayList<Card> passingCards;

    //holds the bid amount textview so it can be updated every move
    private TextView bidAmount;

    /**
     * constructor
     *
     * @param name name of the player
     */
    public PinochleHumanPlayer(String name) {
        super(name);
        //no screen is loaded yet
        currentLayout=NO_SCREEN_LOADED;
    }

    /**
     * getTopView
     * @return the topview of the activity
     */
    @Override
    public View getTopView() {
        return activity.findViewById(R.id.layoutPinochleHumanPlayer);
    }

    /**
     * what to do when the player recieves information
     * @param info the gamestate
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //save the game state if it is a pinochle game state
        if (info instanceof PinochleGameState) state = (PinochleGameState) info;

        //if there is no activity do nothing
        if (activity == null) return;

        //update the log
        TextView log = ((TextView) (activity.findViewById(R.id.textViewLog)));
        log.setText("Log\n______________\n" + state.getLog());

        //update currentplayer
        TextView currentPlayerTextView=((TextView) activity.findViewById(R.id.textViewCurrentPlayer));
        currentPlayerTextView.setText("Player Moving: "+allPlayerNames[state.getCurrentPlayer()]);

        //update totalgame score
        TextView totalScore0TextView=((TextView) activity.findViewById(R.id.textViewTotalScore0));
        totalScore0TextView.setText(allPlayerNames[0]+" & "+allPlayerNames[2]+ " total game score: "+state.getTotalScore()[0]);
        TextView totalScore1TextView=((TextView) activity.findViewById(R.id.textViewTotalScore1));
        totalScore1TextView.setText(allPlayerNames[1]+" & "+allPlayerNames[1]+ " total game score: "+state.getTotalScore()[1]);
        //update currentgame score
        TextView currentScore0TextView=((TextView) activity.findViewById(R.id.textViewCurrentScore0));
        currentScore0TextView.setText(allPlayerNames[0]+" & "+allPlayerNames[2]+ " current game score: "+state.getCurrentScore()[0]);
        TextView currentScore1TextView=((TextView) activity.findViewById(R.id.textViewCurrentScore1));
        currentScore1TextView.setText(allPlayerNames[1]+" & "+allPlayerNames[3]+ " current game score: "+state.getCurrentScore()[1]);

        //update the bid amount
        TextView bidAmountTextView=(TextView)(activity.findViewById(R.id.textViewBidAmount));
        bidAmountTextView.setText("Bid: " + Integer.toString(state.getCurrentBid()));



        //add cards if no screen is loaded
        if (currentLayout == NO_SCREEN_LOADED) {
            updateCards();
        }

        //go through the different game phases and act approriately

        if (state.getGamePhase() == PinochleGameState.BIDDING) {

            //if the current layout is not the bidding layout
            if (currentLayout != PinochleGameState.BIDDING) {
                //bidding GUI
                bidding();

                //update current layout
                currentLayout = PinochleGameState.BIDDING;
            }
        } else if (state.getGamePhase() == PinochleGameState.CHOOSE_TRUMP_SUIT) {

            //if the current layout is not the trump suit layout
            if (currentLayout != PinochleGameState.CHOOSE_TRUMP_SUIT) {
                if (state.getCurrentPlayer() == playerNum) {
                    //trump suit gui
                    chooseTrumpSuit();

                    //update current layout
                    currentLayout = PinochleGameState.CHOOSE_TRUMP_SUIT;
                }
            }
        } else if (state.getGamePhase() == PinochleGameState.PASSING) {

            //if the current layout is not already the passing layout
            if (currentLayout != PinochleGameState.PASSING) {

                //if it is this player's turn
                if (state.getCurrentPlayer() == playerNum) {
                    //update cards to reflect cards being passed to the player
                    updateCards();

                    //passing gui
                    passing();

                    //update current layout
                    currentLayout = PinochleGameState.PASSING;
                }
            }
        } else if (state.getGamePhase() == PinochleGameState.MELDS) {

            //if the current layout is not already the melds layout
            if (currentLayout != PinochleGameState.MELDS) {

                //update cards to reflect passing changes
                updateCards();

                //melds gui
                melds();

                //update the current layout
                currentLayout = PinochleGameState.MELDS;
            }
        } else if (state.getGamePhase() == PinochleGameState.TRICKTAKING) {

            //update cards to show that cards have been played
            updateCards();

            //tricktaking gui
            trickTaking();

            //update current layout
            currentLayout=PinochleGameState.TRICKTAKING;
        }
    }

    /**
     * called when the screen rotates or the game starts
     * @param activity the game activity
     */
    public void setAsGui(GameMainActivity activity) {
        //save the activity
        this.activity=activity;

        //set content to the game content
        activity.setContentView(R.layout.pinochle_human_player);

        //no screen is loaded
        currentLayout=NO_SCREEN_LOADED;

        //add gui based on the gamephase
        if (state!=null) {
            sendInfo(state);
        }
    }


    /**
     * adds cards to the screen
     */
    private void updateCards() {
        LinearLayout playerCards = (LinearLayout) activity.findViewById(R.id.layoutPlayerCards);
        //remove the cards already there
        playerCards.removeAllViewsInLayout();

        //get screen dimensions
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get the cards that could be passed to the player
        passingCards=state.getPassedCards();

        //dimensions of card based on size of screen and how many cards the player has
        int width = (dm.widthPixels - 200 - state.getPlayerCards().get(playerNum).size() * 2) / (state.getPlayerCards().get(playerNum).size()==16?16:12);
        int height= width * 100 / 69;

        //iterate through the cards
        for (Card card : state.getPlayerCards().get(playerNum)) {

            //create a card
            PinochleImageViewCard imageViewCard = new PinochleImageViewCard(activity,card);


            //add padding so there is a border for pass cards
            imageViewCard.setPadding(2, 2, 2, 2);

            //add a border to the card if it was passed to the player
            for (Card cardToPass:passingCards) {
                if (cardToPass == card) {
                    imageViewCard.setBackgroundColor(Color.BLUE);
                }
            }

            //make them clickable
            imageViewCard.setOnClickListener(this);

            //add them to the layout
            playerCards.addView(imageViewCard, new ActionBar.LayoutParams(width,height));
        }
    }

    /**
     * bidding gui
     */
    private void bidding(){
        //GUI
        RelativeLayout variableScreen=(RelativeLayout)activity.findViewById(R.id.layoutVariableScreen);
        variableScreen.removeAllViewsInLayout();

        LinearLayout bidOptions = new LinearLayout(activity);
        int bidOptionsID=1;
        bidOptions.setId(bidOptionsID);
        bidOptions.setOrientation(LinearLayout.HORIZONTAL);
        bidOptions.setGravity(Gravity.CENTER);
        Button pass=new Button(activity);
        pass.setText("Pass");
        pass.setOnClickListener(this);
        Button passWithHelp=new Button(activity);
        passWithHelp.setText("Pass With Help");
        passWithHelp.setOnClickListener(this);
        Button bid=new Button(activity);
        bid.setText("Bid");
        bid.setOnClickListener(this);
        bidOptions.addView(pass, new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bidOptions.addView(passWithHelp, new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bidOptions.addView(bid, new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams bidOptionsParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bidOptionsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        variableScreen.addView(bidOptions, bidOptionsParams);
        ImageView possibleMelds=new ImageView(activity);
        possibleMelds.setImageResource(R.mipmap.ic_launcher);
        RelativeLayout.LayoutParams possibleMeldsParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        possibleMeldsParams.addRule(RelativeLayout.ABOVE, bidOptionsID);
        variableScreen.addView(possibleMelds, possibleMeldsParams);
    }

    /**
     * trump suit layout
     */
    private void chooseTrumpSuit(){

    }

    /**
     * passing layout
     */
    private void passing() {
        RelativeLayout variableScreen = (RelativeLayout) activity.findViewById(R.id.layoutVariableScreen);
        variableScreen.removeAllViewsInLayout();

        Button buttonPass = new Button(activity);
        buttonPass.setText("Pass Cards");
        buttonPass.setOnClickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        variableScreen.addView(buttonPass, params);
    }

    /**
     * melds layout
     */
    private void melds(){
        RelativeLayout variableScreen = (RelativeLayout) activity.findViewById(R.id.layoutVariableScreen);
        variableScreen.removeAllViewsInLayout();
        ImageView imageView=new ImageView(activity);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setOnClickListener(this);
        variableScreen.addView(imageView,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * trick taking layout
     */
    private void trickTaking(){
        RelativeLayout variableScreen = (RelativeLayout) activity.findViewById(R.id.layoutVariableScreen);
        int height=Math.min(variableScreen.getWidth(),variableScreen.getHeight())/4;
        int width=height*100/69;
        variableScreen.removeAllViewsInLayout();
        ImageView playerCard=new ImageView(activity);
        playerCard.setImageResource((state.getPlayerCurrentCard().get(playerNum) == null ? R.mipmap.ic_launcher : state.getPlayerCurrentCard().get(playerNum).getImage()));
        RelativeLayout.LayoutParams paramsPlayerCard=new RelativeLayout.LayoutParams(width,height);
        paramsPlayerCard.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsPlayerCard.addRule(RelativeLayout.CENTER_HORIZONTAL);
        variableScreen.addView(playerCard, paramsPlayerCard);
        ImageView playerCardLeft=new ImageView(activity);
        playerCardLeft.setImageResource((state.getPlayerCurrentCard().get((playerNum+1)%4)==null?R.mipmap.ic_launcher:state.getPlayerCurrentCard().get((playerNum+1)%4).getImage()));
        playerCardLeft.setRotation(90);
        RelativeLayout.LayoutParams paramsPlayerCardLeft=new RelativeLayout.LayoutParams(width,height);
        paramsPlayerCardLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        paramsPlayerCardLeft.addRule(RelativeLayout.CENTER_VERTICAL);
        variableScreen.addView(playerCardLeft, paramsPlayerCardLeft);
        ImageView playerCardAcross=new ImageView(activity);
        playerCardAcross.setImageResource((state.getPlayerCurrentCard().get((playerNum + 2) % 4) == null ? R.mipmap.ic_launcher : state.getPlayerCurrentCard().get((playerNum + 2) % 4).getImage()));
        playerCardAcross.setRotation(180);
        RelativeLayout.LayoutParams paramsPlayerCardAcross=new RelativeLayout.LayoutParams(width,height);
        paramsPlayerCardAcross.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        paramsPlayerCardAcross.addRule(RelativeLayout.CENTER_HORIZONTAL);
        variableScreen.addView(playerCardAcross,paramsPlayerCardAcross);
        ImageView playerCardRight=new ImageView(activity);
        playerCardRight.setImageResource((state.getPlayerCurrentCard().get((playerNum+3)%4)==null?R.mipmap.ic_launcher:state.getPlayerCurrentCard().get((playerNum+3)%4).getImage()));
        playerCardRight.setRotation(270);
        RelativeLayout.LayoutParams paramsPlayerCardRight=new RelativeLayout.LayoutParams(width,height);
        paramsPlayerCardRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsPlayerCardRight.addRule(RelativeLayout.CENTER_VERTICAL);
        variableScreen.addView(playerCardRight, paramsPlayerCardRight);
    }

    /**
     * onClick
     * @param v the clicked view
     */
    @Override
    public void onClick(View v) {
        //disable clicks when not player's turn
        if (state.getCurrentPlayer()!=playerNum) return;

        //different click possibilities for each phase
        if (state.getGamePhase()==PinochleGameState.BIDDING){
            //all clicks are buttons
            if (!(v instanceof Button)) return;
            String buttonText=((Button)v).getText().toString();

            if (buttonText.equals("Pass")){
                game.sendAction(new PinochlePassAction(this, name));
            }
            else if (buttonText.equals("Pass With Help")){
                game.sendAction(new PinochlePassWithHelpAction(this, name));
            }
            else if (buttonText.equals("Bid")){
                int bid=Math.max(25,state.getCurrentBid()+1);
                game.sendAction(new PinochleBidAction(this,name,bid));
            }

        }
        else if (state.getGamePhase()==PinochleGameState.CHOOSE_TRUMP_SUIT) {
            //only click is the dialog
            Suit trumpSuit= Suit.Heart;
            game.sendAction(new PinochleChooseTrumpSuitAction(this,name,trumpSuit));
        }
        else if (state.getGamePhase()==PinochleGameState.PASSING) {
            //click the cards
            if (v instanceof PinochleImageViewCard) {
                //can only select 4 cards
                if (!passingCards.contains(((PinochleImageViewCard) v).getCard()) && passingCards.size() < 4) {
                    //select card by adding border
                    v.setBackgroundColor(Color.BLUE);
                    //add card to pass list
                    passingCards.add(((PinochleImageViewCard) v).getCard());
                } else {
                    //unselect card
                    v.setBackgroundColor(Color.WHITE);
                    //remove card from list
                    passingCards.remove(((PinochleImageViewCard) v).getCard());
                }
            }
            //the button is the pass cards button
            else if (v instanceof Button) {
                if (((Button) v).getText().equals("Pass Cards")) {
                    if (passingCards.size() == 4) {
                        game.sendAction(new PinochlePassCardsAction(this, name, passingCards));
                    }
                }
            }
        }
        else if (state.getGamePhase()==PinochleGameState.MELDS) {
            //click the screen to finish viewing melds
            if (v instanceof ImageView) {
                game.sendAction(new PinochleMeldsAction(this,name));
            }
        }
        else if (state.getGamePhase()==PinochleGameState.TRICKTAKING){
            //can only click cards
            if (v instanceof PinochleImageViewCard){
                game.sendAction(new PinochlePlayCardAction(this,name,((PinochleImageViewCard) v).getCard()));
            }
        }
    }
}