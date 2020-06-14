package navigationdrawer.navigationdrawer.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
//import android.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import navigationdrawer.navigationdrawer.R;
import navigationdrawer.navigationdrawer.ui.Adapter.SearchAdapter;
import navigationdrawer.navigationdrawer.ui.Database.Database;
import navigationdrawer.navigationdrawer.ui.play.PlayFragment;
import navigationdrawer.navigationdrawer.ui.search.SearchFragment;
import navigationdrawer.navigationdrawer.ui.search.Taenie;

public class HomeFragment extends Fragment {

    public static View v_Home;

    //region 변수선언시작
    public static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    List<String> suggestList = new ArrayList<>();//ArrayList

    Database database;

    public static Context context_home;
    //endregion 변수선언끝
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        v_Home = inflater.inflate(R.layout.fragment_home, container, false);

        context_home = getActivity().getApplicationContext();

        //region 변수를 findViewById로 xml값 대입
        //init view
        recyclerView = (RecyclerView) v_Home.findViewById(R.id.recycler_search);//recycler view를 사용하겠다 >> xml에 해당 id의 view를 가져옴
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());//widget인 recyclerView는 LayoutManager의 종류에 따라 view의 배치가 달라짐
        recyclerView.setLayoutManager(layoutManager);//recyclerView변수에 LinearLayoutManager를 적용하겠다.
        recyclerView.setHasFixedSize(true);//recyclerView 사이즈 고정


        //init DB
        database = new Database(getActivity().getApplicationContext());//database 변수 선언
        //endregion

        adapter = new SearchAdapter(getActivity().getApplicationContext(), database.getTaenies());//원래는(getBaseContext(),database.getTaenies())
        recyclerView.setAdapter(adapter);


        return v_Home;
    }
}