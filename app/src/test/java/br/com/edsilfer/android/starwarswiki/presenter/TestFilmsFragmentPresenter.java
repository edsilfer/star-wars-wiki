package br.com.edsilfer.android.starwarswiki.presenter;

import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.edsilfer.android.starwarswiki.util.MockedPostman;
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsFragmentViewContract;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Edgar Fernandes on 02/27/2017
 */
@RunWith(PowerMockRunner.class)
public class TestFilmsFragmentPresenter {

    @Mock
    private FilmsFragmentViewContract mMockedView;
    @Mock
    private AppCompatActivity mMockedContext;
    @Mock
    private MockedPostman mMockedPostman;

    private FilmsFragmentPresenter mPresenter;

    @Before
    public void setUp() {
        initMocks(this);
        mPresenter = new MockedFilmsFragmentPresenter(mMockedPostman);
    }

    @Test
    public void test() {

    }

    /*
    HELPER CLASS NEEDED TO INIT MVIEW AND MCONTEXT ALLOWING THE TEST
     */
    public class MockedFilmsFragmentPresenter extends FilmsFragmentPresenter {
        MockedFilmsFragmentPresenter(@NotNull MockedPostman postman) {
            super(postman);
            mView = mMockedView;
            mContext = mMockedContext;
        }
    }

}
