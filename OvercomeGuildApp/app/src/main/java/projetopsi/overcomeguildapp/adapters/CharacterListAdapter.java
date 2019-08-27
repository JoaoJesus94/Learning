package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.CharacterModel;


public class CharacterListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CharacterModel> characters;

    public CharacterListAdapter(Context context, ArrayList<CharacterModel> characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_character_list, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(characters.get(position));
        return convertView;
    }

    public void refresh(ArrayList<CharacterModel> characters){
        this.characters = characters;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewCharName;
        private TextView textViewLevel;
        private TextView textViewClass;
        private TextView textViewRace;
        private TextView textViewMainSpec;
        private TextView textViewOffSpec;


        public ViewHolderList(View convertView){
            textViewCharName = convertView.findViewById(R.id.item_character_charName);
            textViewLevel = convertView.findViewById(R.id.item_character_level);
            textViewClass = convertView.findViewById(R.id.item_character_class);
            textViewRace = convertView.findViewById(R.id.item_character_race);
            textViewMainSpec = convertView.findViewById(R.id.item_character_mainSpec);
            textViewOffSpec = convertView.findViewById(R.id.item_character_offSpec);
        }

        public void update(CharacterModel character){
            textViewCharName.setText(character.getCharName());
            textViewLevel.setText(character.getLevel() + "");
            textViewClass.setText(character.get_Class());
            textViewRace.setText(character.getRace());
            textViewMainSpec.setText(character.getMainSpec());
            textViewOffSpec.setText(character.getOffSpec());
        }
    }
}
