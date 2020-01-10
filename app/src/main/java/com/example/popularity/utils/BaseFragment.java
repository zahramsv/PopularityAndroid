package com.example.popularity.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.popularity.R;

public class BaseFragment extends Fragment {

    public void openFragment(Fragment fragment, Boolean addStack){


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (!addStack)
        {
            transaction.replace(R.id.your_placeholder, fragment);
        }
        else
        {
            transaction.replace(R.id.your_placeholder,fragment);
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }
}
