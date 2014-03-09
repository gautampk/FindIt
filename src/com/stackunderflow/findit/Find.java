package com.stackunderflow.findit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Context;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Find extends Activity {
    private List<Card> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCards();

        mCardScrollView = new CardScrollView(this);
        mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card selectedCard = mCards.get(mCardScrollView.getSelectedItemPosition());
                try{
                    startActivity( procImg.launchNav(selectedCard.getFootnote()+".jpg" , Find.this) );
                }catch (NullPointerException e){
                    Card fail = new Card(Find.this);
                    fail.setText(R.string.storefailhead);
                    fail.setFootnote(R.string.storefailfoot);
                    fail.setImageLayout(Card.ImageLayout.FULL);
                    fail.addImage(R.drawable.storefailbg);
                    View failView = fail.toView();
                    setContentView(failView);
                }
            }
        });
        ScrollAdapter adapter = new ScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
    }

    private void createCards() {
        mCards = new ArrayList<Card>();

        Card card;
        String filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/";
        File file;
        Uri uri;

        File folder = new File(filepath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            file = new File(filepath+listOfFiles[i].getName());
            uri = Uri.fromFile(file);
            card = new Card(this);
            card.setImageLayout(Card.ImageLayout.FULL);
            card.addImage(uri);
            card.setFootnote( listOfFiles[i].getName().replace(".jpg","") );
            mCards.add(card);
        }
    }

    private class ScrollAdapter extends CardScrollAdapter {

        @Override
        public int findIdPosition(Object id) {
            return -1;
        }

        @Override
        public int findItemPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).toView();
        }
    }
}