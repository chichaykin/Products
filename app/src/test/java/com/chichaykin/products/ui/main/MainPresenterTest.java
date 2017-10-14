package com.chichaykin.products.ui.main;


import com.chichaykin.products.network.ConnectivityHelper;
import com.chichaykin.products.network.NetworkApi;
import com.chichaykin.products.network.data.Category;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.ImmediateSchedulersRule;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    @Rule
    public final ImmediateSchedulersRule schedulers = new ImmediateSchedulersRule();
    @Mock
    NetworkApi networkApi;
    @Mock
    ConnectivityHelper helper;
    @Mock
    MainView view;

    private MainPresenterImpl presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(networkApi, helper);
    }

    @Test
    public void thereIsDataOnAttach() {
        when(networkApi.getDatum()).thenReturn(Observable.just(Collections.singletonList(new Category())));

        presenter.onAttach(view);

        verify(view).setData(eq(0), anyListOf(Category.class));
    }

    @Test
    public void noDataOnAttach() {
        when(networkApi.getDatum()).thenReturn(Observable.error(IOException::new));

        presenter.onAttach(view);

        verify(view).showError(anyString());
    }
}
