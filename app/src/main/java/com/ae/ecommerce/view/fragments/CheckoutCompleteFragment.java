package com.ae.ecommerce.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ae.ecommerce.R;
import com.ae.ecommerce.database.AppDatabase;
import com.ae.ecommerce.databinding.FragmentCheckoutCompleteBinding;
import com.ae.ecommerce.databinding.FragmentProductCatalogBinding;
import com.ae.ecommerce.model.ProductModel;
import com.ae.ecommerce.utils.Constants;
import com.ae.ecommerce.utils.base.BaseFragment;
import com.ae.ecommerce.view.activities.MainActivity;
import com.ae.ecommerce.view.adapters.ProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckoutCompleteFragment extends BaseFragment implements View.OnClickListener {
    private FragmentCheckoutCompleteBinding binding;

    public static CheckoutCompleteFragment newInstance(String param1, String param2, int param3) {
        CheckoutCompleteFragment fragment = new CheckoutCompleteFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        args.putInt(Constants.ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAct = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(Constants.ARG_PARAM1, "");
            mParam2 = getArguments().getString(Constants.ARG_PARAM2, "");
            mParam3 = getArguments().getInt(Constants.ARG_PARAM3, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_complete, container, false);
        bindData();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void bindData() {
        mDb = AppDatabase.getInstance(getActivity());
        init();
        setListener();

    }

    private void init(){

    }

    private void setListener(){
        binding.shoopingBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(binding.shoopingBt)){
            ST.cartItemList = new ArrayList<>();
            ST.startActivity(mAct , MainActivity.class , ST.START_ACTIVITY_WITH_CLEAR_BACK_STACK);
        }
    }
}
