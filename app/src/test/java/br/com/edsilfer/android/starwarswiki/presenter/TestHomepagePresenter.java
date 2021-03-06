package br.com.edsilfer.android.starwarswiki.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.edsilfer.android.starwarswiki.commons.Router;
import br.com.edsilfer.android.starwarswiki.infrastructure.database.CharacterDAO;
import br.com.edsilfer.android.starwarswiki.util.MockedPostman;
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.HomepageViewContract;
import br.com.edsilfer.kotlin_support.service.NotificationCenter;
import br.com.tyllt.infrastructure.database.GenericDAO;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by Edgar Fernandes on 02/24/2017
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
        NotificationCenter.class,
        Log.class,
        LocationServices.class,
        Uri.class,
        FusedLocationProviderApi.class,
        CharacterDAO.class,
        GenericDAO.class
})
public class TestHomepagePresenter {

    @Mock
    private HomepageViewContract mMockedView;
    @Mock
    private AppCompatActivity mMockedContext;
    @Mock
    private MockedPostman mMockedPostman;
    @Mock
    private Intent mMockedIntent;

    private MockedHomepagePresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockStatic(NotificationCenter.class);
        mockStatic(Log.class);
        mockStatic(LocationServices.class);
        mockStatic(Uri.class);
        mockStatic(FusedLocationProviderApi.class);
        mockStatic(CharacterDAO.class);
        mockStatic(GenericDAO.class);

        when(mMockedView.getContext()).thenReturn(mMockedContext);

        mPresenter = new MockedHomepagePresenter(mMockedPostman);
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testSearchByQrCodeWithPermissionGranted() {
        View mockedView = mock(View.class);
        mPresenter.searchByQrCode(mockedView);
        verify(mMockedContext).checkSelfPermission("android.permission.CAMERA");
        verify(mMockedContext).checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
        verify(mMockedContext).startActivityForResult(any(Intent.class), eq(Router.REQUEST_QRCODE_READER));
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testSearchByQrCodeWithPermissionNotGranted() {
        View mockedView = mock(View.class);
        when(mMockedContext.checkSelfPermission("android.permission.CAMERA")).thenReturn(5);
        mPresenter.searchByQrCode(mockedView);
        verify(mMockedView).requestAppPermissions();
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testSearchByUrlWithPermissionGranted() {
        View mockedView = mock(View.class);
        mPresenter.searchByUrl(mockedView);
        verify(mMockedContext).checkSelfPermission("android.permission.CAMERA");
        verify(mMockedContext).checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
        verify(mMockedView).showGetUrlPopUp();
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void testSearchByUrlWithPermissionNotGranted() {
        View mockedView = mock(View.class);
        when(mMockedContext.checkSelfPermission("android.permission.CAMERA")).thenReturn(5);
        mPresenter.searchByUrl(mockedView);
        verify(mMockedView).requestAppPermissions();
    }

    @Test
    public void testOnQRCodeRead() {
        String url = "url";
        mPresenter.processUrl(url);
        verify(mMockedView).showLoading();
        verify(mMockedView).dismissSoftKeyboard();
        verify(mMockedPostman).searchCharacter(mMockedContext, url);

    }

    @Test
    public void testOnUrlType() {
        String url = "url";
        mPresenter.onUrlType(url);
        verify(mMockedView).showLoading();
        verify(mMockedView).dismissSoftKeyboard();
        verify(mMockedPostman).searchCharacter(mMockedContext, url);
    }

    @Test
    public void testOnForkMeClick() {
        mPresenter.onForkMeClick();
        verify(mMockedContext).startActivity(any(Intent.class));
    }

    @Test
    public void testOnCharacterClick() {
        br.com.edsilfer.android.starwarswiki.model.Character mockedCharacter = new br.com.edsilfer.android.starwarswiki.model.Character();
        mPresenter.onCharacterClick(mockedCharacter);
        verify(mMockedContext).startActivity(any(Intent.class));
    }

    /*
    FIXME: How can one mock a Kotlin object class? http://stackoverflow.com/questions/42490850/how-to-mock-kotlins-object-class
     */
    @Test
    public void testDeleteCharacter() {
        /*
        Character mockedCharacter = new Character();
        mPresenter.deleteCharacter(mockedCharacter);
        verifyStatic();
        verify(mMockedView).removeCharacter(mockedCharacter);*/
    }

    /*
    HELPER CLASS NEEDED TO INIT MVIEW AND MCONTEXT ALLOWING THE TEST
     */
    public class MockedHomepagePresenter extends HomepagePresenter {
        MockedHomepagePresenter(@NotNull MockedPostman postman) {
            super(postman);
            mView = mMockedView;
            mContext = mMockedContext;
        }
    }

}
