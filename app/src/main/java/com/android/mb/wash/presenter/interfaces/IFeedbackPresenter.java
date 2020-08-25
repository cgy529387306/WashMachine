package com.android.mb.wash.presenter.interfaces;

import java.io.File;
import java.util.Map;

public interface IFeedbackPresenter {
    void feedback(File file,Map<String, Object> requestMap);

}
