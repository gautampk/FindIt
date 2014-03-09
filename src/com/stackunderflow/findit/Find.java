package com.stackunderflow.findit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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
                startActivity( procImg.launchNav( selectedCard.getFootnote() ) );
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
        String filename;
        String filepath;
        File file;
        Uri uri;



        filename = "test0r";
        filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/"+filename+".jpg";
        file = new File(filepath);
        uri = Uri.fromFile(file);
        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(uri);
        card.setFootnote(filename);
        mCards.add(card);

        filename = "test1r";
        filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/"+filename+".jpg";
        file = new File(filepath);
        uri = Uri.fromFile(file);
        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(uri);
        card.setFootnote(filename);
        mCards.add(card);
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