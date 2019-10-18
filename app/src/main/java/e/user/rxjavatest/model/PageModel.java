package e.user.rxjavatest.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.interfaces.MultiType;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-10-04
 */
public class PageModel extends ViewModel {
    private MutableLiveData<List<MultiType>> resourceList = new MutableLiveData<>();


}
