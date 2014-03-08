package com.stackunderflow.findit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gautam on 08/03/14.
 */
public class Memory extends Activity {
    private List<Card> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createCards();

        mCardScrollView = new CardScrollView(this);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
    }

    private void createCards() {
        mCards = new ArrayList<Card>();

        Card card;

        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/test0.jpg");
        Uri uri = Uri.fromFile(file);

        card = new Card(this);
        card.setImageLayout(Card.ImageLayout.FULL);
        card.addImage(uri);
        mCards.add(card);
    }

    private class ExampleCardScrollAdapter extends CardScrollAdapter /*implements OnItemClickListener*/ {

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

        /*public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            checklistIntent.putExtra("steps", getChecklistSteps(adapter.findItemPosition(position)));
            startActivity(checklistIntent);
        }*/
    }
}
