package com.ae.ecommerce.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.ae.ecommerce.database.AppDatabase;
import com.ae.ecommerce.database.AppExecutors;
import com.ae.ecommerce.model.ProductModel;
import com.ae.ecommerce.utils.DatabaseRepository;
import com.ae.ecommerce.utils.base.BaseViewModel;

public class ProductDetailViewModel extends BaseViewModel {
    private AppDatabase mDb;
    private DatabaseRepository repository;
    String id;
    public MutableLiveData<ProductModel> product = new MutableLiveData<>();

    public ProductDetailViewModel(Application app,String id) {
        mDb = AppDatabase.getInstance(app);
        this.id = id;
        getProduct();
    }

    public void getProduct() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                product.postValue(mDb.personDao().getProduct(Integer.parseInt(id)));


            }
        });
    }



}
