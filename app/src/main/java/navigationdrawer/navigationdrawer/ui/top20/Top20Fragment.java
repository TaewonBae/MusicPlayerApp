package navigationdrawer.navigationdrawer.ui.top20;

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

public class Top20Fragment extends Fragment {

    public static View v_Top20;
    //region 변수선언시작
    public static RecyclerView recyclerView_top20;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    List<String> suggestList = new ArrayList<>();//ArrayList

    Database database;

    public static Context context_top_20;
    //endregion 변수선언끝

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v_Top20 = inflater.inflate(R.layout.fragment_top20, container, false);

        context_top_20 = getActivity().getApplicationContext();

        //region 변수를 findViewById로 xml값 대입
        //init view
        recyclerView_top20 = (RecyclerView) v_Top20.findViewById(R.id.recycler_search);//recycler view를 사용하겠다 >> xml에 해당 id의 view를 가져옴
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());//widget인 recyclerView는 LayoutManager의 종류에 따라 view의 배치가 달라짐
        recyclerView_top20.setLayoutManager(layoutManager);//recyclerView변수에 LinearLayoutManager를 적용하겠다.
        recyclerView_top20.setHasFixedSize(true);//recyclerView 사이즈 고정


        //init DB
        database = new Database(getActivity().getApplicationContext());//database 변수 선언
        //endregion

        adapter = new SearchAdapter(getActivity().getApplicationContext(), database.getTaenies());//원래는(getBaseContext(),database.getTaenies())
        recyclerView_top20.setAdapter(adapter);

        return v_Top20;
    }



}