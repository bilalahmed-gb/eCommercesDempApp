package com.ae.ecommerce.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ae.ecommerce.R;
import com.ae.ecommerce.database.AppDatabase;
import com.ae.ecommerce.database.AppExecutors;
import com.ae.ecommerce.databinding.FragmentHomeBinding;
import com.ae.ecommerce.databinding.FragmentProductCatalogBinding;
import com.ae.ecommerce.interfaces.OnItemClickListener;
import com.ae.ecommerce.model.ProductModel;
import com.ae.ecommerce.utils.Constants;
import com.ae.ecommerce.utils.base.BaseFragment;
import com.ae.ecommerce.view.activities.MainActivity;
import com.ae.ecommerce.view.adapters.ProductsAdapter;
import com.ae.ecommerce.viewModel.ProductCatalogViewModel;
import com.ae.ecommerce.viewModel.ProductCatalogViewModelFactory;

import java.util.List;

public class ProductCatalogFragment extends BaseFragment implements View.OnClickListener {
    private FragmentProductCatalogBinding binding;
    List<ProductModel> productList;
    ProductsAdapter adapter;
    ProductCatalogViewModel viewModel;

    public static ProductCatalogFragment newInstance(String param1, String param2, int param3) {
        ProductCatalogFragment fragment = new ProductCatalogFragment();
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

//        ST.logActivityToFirebase(getActivity(),"HomeActivity", ST.SCREEN_HOME, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_catalog, container, false);


        viewModel = ViewModelProviders.of(this, new ProductCatalogViewModelFactory(app)).get(ProductCatalogViewModel.class);
        bindData();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void bindData() {
        mDb = AppDatabase.getInstance(getActivity());

        int spanCount = 2;
        int spacing = 25;
        binding.productRV.setLayoutManager(new GridLayoutManager(mAct, spanCount));
        boolean includeEdge = false;
//        binding.productRV.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        observer();
    }

    private void observer() {
        viewModel.allProducts.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                if (productModels != null) {
                    productList = productModels;
                    setAdapter();
                }
            }
        });
    }

    private void checkLocalDataBase() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productList = mDb.personDao().getAllProducts();
                setAdapter();
            }
        });
    }

    public void upDateData() {
        viewModel.getAllProducts(MainActivity.selectedSort);
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                productList = mDb.personDao().getAllProducts();
//                if (MainActivity.selectedSort == MainActivity.NAME_ASC) {
//                    productList = mDb.personDao().getPersonsSortByAscName();
//                } else if (MainActivity.selectedSort == MainActivity.NAME_DESC) {
//                    productList = mDb.personDao().getPersonsSortByDescName();
//                } else if (MainActivity.selectedSort == MainActivity.PRICE_ASC) {
//                    productList = mDb.personDao().getPersonsSortByAscPrice();
//                } else if (MainActivity.selectedSort == MainActivity.PRICE_DESC) {
//                    productList = mDb.personDao().getPersonsSortByDescPrice();
//                }
//
//                setAdapter();
//            }
//        });
    }

    private void setAdapter() {
        mAct.runOnUiThread(() -> {
            adapter = new ProductsAdapter(mAct, productList, (position, status) -> {
                Bundle bundle = ST.getBundle(MainActivity.FRAGMENT_PRODUCT_DETAIL, 1);
                bundle.putString(ST.ARG_PARAM1, String.valueOf(productList.get(position).getId()));
                ST.startMainActivity(mAct, bundle);
            });
            binding.productRV.setAdapter(adapter);
        });
    }


    @Override
    public void onClick(View view) {

    }
}
