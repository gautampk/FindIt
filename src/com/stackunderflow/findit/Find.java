package com.stackunderflow.findit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.speech.RecognizerIntent;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Find extends Activity {
    private List<Card> mCards;
    private CardScrollView mCardScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

        try{
            File fXmlFile = new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/tags.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            NodeList nList = doc.getElementsByTagName("tag");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                procImg.tags.put(eElement.getAttribute("name"),eElement.getAttribute("photo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(voiceResults.get(0).contentEquals("all")){
            createCards();

            mCardScrollView = new CardScrollView(this);
            mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Card selectedCard = mCards.get(mCardScrollView.getSelectedItemPosition());
                    try{
                        startActivity( procImg.launchNav("/Pictures/FindIt/"+selectedCard.getFootnote()+".jpg", Find.this) );
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
        }else if( !(voiceResults.get(0).contentEquals("all")) && voiceResults != null ){
            try{
                startActivity( procImg.launchNav(procImg.tags.get( voiceResults.get(0) ) , Find.this) );
            }catch (NullPointerException e){
                Card fail = new Card(Find.this);
                fail.setText(R.string.storefailhead);
                fail.setFootnote(R.string.storefailfoot);
                fail.setImageLayout(Card.ImageLayout.FULL);
                fail.addImage(R.drawable.storefailbg);
                View failView = fail.toView();
                setContentView(failView);
            }
        }else{
            createCards();

            mCardScrollView = new CardScrollView(this);
            mCardScrollView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Card selectedCard = mCards.get(mCardScrollView.getSelectedItemPosition());
                    try{
                        startActivity( procImg.launchNav("/Pictures/FindIt/"+selectedCard.getFootnote()+".jpg", Find.this) );
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
    }

    private void createCards() {
        mCards = new ArrayList<Card>();

        Card card;
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file;
        Uri uri;

        Iterator it = procImg.tags.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            file = new File(filepath + pairs.getValue());
            uri = Uri.fromFile(file);
            card = new Card(this);
            card.setImageLayout(Card.ImageLayout.FULL);
            card.addImage(uri);
            card.setFootnote( file.getName().replace(".jpg","") );
            mCards.add(card);
            continue;
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
//A Change.