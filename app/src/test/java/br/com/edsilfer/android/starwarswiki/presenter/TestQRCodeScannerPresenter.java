package br.com.edsilfer.android.starwarswiki.presenter;

import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.edsilfer.android.starwarswiki.util.MockedPostman;
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.QRCodeScannerViewContract;

/**
 * Created by Edgar Fernandes on 02/27/2017
 */
@RunWith(PowerMockRunner.class)
public class TestQRCodeScannerPresenter {

    @Mock
    private QRCodeScannerViewContract mMockedView;
    @Mock
    private AppCompatActivity mMockedContext;

    /*
    HELPER CLASS NEEDED TO INIT MVIEW AND MCONTEXT ALLOWING THE TEST
     */
    public class MockedQRCodeScannerPresenter extends QRCodeScannerPresenter {
        MockedQRCodeScannerPresenter(@NotNull MockedPostman postman) {
            super(postman);
            mView = mMockedView;
            mContext = mMockedContext;
        }
    }
}
