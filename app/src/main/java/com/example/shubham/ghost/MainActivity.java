package com.example.shubham.ghost;

/*import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
*/
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
//editText.setText("smb://");
//edit text.setSelection(edittext.getText().length());

public class MainActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private FastDictionary simpleFastDictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    EditText text;
    TextView label;
    String wordFragment="",currentWord="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        try{
            InputStream dictionaryWords=getAssets().open("words.txt");
            dictionary =new SimpleDictionary(dictionaryWords);
           // dictionary = new FastDictionary(dictionaryWords);
        }catch (IOException e){
            e.printStackTrace();
        }
        text =(EditText)findViewById(R.id.ghostText);
        label=(TextView)findViewById(R.id.gameStatus);
        onStart(null);
    }
    public void reset(View view){

        currentWord="";
        wordFragment="";
        text.setText("");

        onStart(null);
    }

    public void challenge(View view) {


        if(dictionary.isWord(wordFragment)){
            label.setText("You Win...");
        }
        else{
            String wordFromDictionary = dictionary.getAnyWordStartingWith(wordFragment);
            if(wordFromDictionary!=null){
                text.setText("The word was"+wordFromDictionary);
                label.setText("You lose...");
                userTurn = true;
            }else{
                label.setText("No word can be formed from this prefix...");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        EditText text = (EditText) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);

        } else {
            label.setText(COMPUTER_TURN);
            text.setEnabled(false);
            computerTurn();
            text.setEnabled(true);
        }
        return true;
    }
    private void userTurn(){
        text.setText(currentWord.length());
        text.setSelection(currentWord.length());

    }

    private void computerTurn() {
        if (wordFragment.length()>=4&& dictionary.isWord(wordFragment))
        {
            label.setText("Computer Win...");


        }
        else {
            String wordFromDictionary=dictionary.getAnyWordStartingWith(wordFragment);
            if (wordFromDictionary!=null){
                wordFragment=wordFragment.concat(wordFromDictionary.substring(wordFragment.length(),wordFragment.length()+1));
                text.setText((wordFragment));
                currentWord=wordFragment;
                userTurn=true;

            }
            else {
                label.setText("No word can be formed from this prifix.....");
                text.setText("Match Draw...");
            }
        }

      //TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        if(keyCode>=29&&keyCode<=54){
            char c =(char)event.getUnicodeChar();
            wordFragment=text.getText().toString();
            currentWord=wordFragment;
            text.setText(wordFragment);
            computerTurn();
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }
        
    }

}

