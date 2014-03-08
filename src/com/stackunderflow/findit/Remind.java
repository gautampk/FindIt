package src.com.stackunderflow.findit;

import android.app.Activity;
import android.os.Bundle;import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

public class Remind extends Activity {
    private List<Card> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(procImg.launchNav("test0"));
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

        String filename = "test0";
        String filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/"+filename+".jpg";
        //File file = new File(filepath);
        Uri uri = Uri.parse(filepath);

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

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String filename = "test0";
            String filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/"+filename+".jpg";
            startActivity(procImg.launchNav(filepath));
        }
    }
    //A Change.
}