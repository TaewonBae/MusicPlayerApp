package navigationdrawer.navigationdrawer.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import navigationdrawer.navigationdrawer.R;
import navigationdrawer.navigationdrawer.ui.Adapter.SearchAdapter;
import navigationdrawer.navigationdrawer.ui.Database.Database;

public class SearchFragment extends Fragment {

    private View v_search;
    //region 변수선언시작
    public static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();//ArrayList

    Database database;

    public static Context context_main;
    //endregion 변수선언끝

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        v_search = inflater.inflate(R.layout.fragment_search, container, false);


        context_main = getActivity().getApplicationContext();

        //region 변수를 findViewById로 xml값 대입
        //init view
        recyclerView = (RecyclerView) v_search.findViewById(R.id.recycler_search);//recycler view를 사용하겠다 >> xml에 해당 id의 view를 가져옴
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());//widget인 recyclerView는 LayoutManager의 종류에 따라 view의 배치가 달라짐
        recyclerView.setLayoutManager(layoutManager);//recyclerView변수에 LinearLayoutManager를 적용하겠다.
        recyclerView.setHasFixedSize(true);//recyclerView 사이즈 고정

        //init materialSearchBar
        materialSearchBar = (MaterialSearchBar) v_search.findViewById(R.id.search_bar);//materialSearchBar 변수선언 및 가져온다.

        //init DB
        database = new Database(getActivity().getApplicationContext());//database 변수 선언
        //endregion

        //region materialSearchBar 셋팅
        materialSearchBar.setHint("Search");
        materialSearchBar.setCardViewElevation(10);//CardView는 곡선테두리와 음영색을 조절할 수 있다.>>materialSearchBar음영조절
        loadSuggestList();//loadSuggestList()함수 실행
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    //If close Search, just restore default
                    adapter = new SearchAdapter(getActivity().getApplicationContext(), database.getTaenies());//원래는(getBaseContext(),database.getTaenies())
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        //endregion

        //init Adapter default set all result
        adapter = new SearchAdapter(getActivity().getApplicationContext(), database.getTaenies());//Database의 getTaenies에서 실행

        return v_search;
    }

    private void loadSuggestList() {
        suggestList = database.getNames();//Database클래스로 가서 돈다.
        materialSearchBar.setLastSuggestions(suggestList);//위의 suggestList 어레이값을 materialSearchBar의 setLastSuggestions함수 실행
    }

    private void startSearch(String text) {
        adapter = new SearchAdapter(getActivity().getApplicationContext(), database.getTaenieByName(text));
        recyclerView.setAdapter(adapter);
    }


}
