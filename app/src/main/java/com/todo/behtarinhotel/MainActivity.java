package com.todo.behtarinhotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.PopupMenu;

import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.todo.behtarinhotel.fragments.MainFragment;
import com.todo.behtarinhotel.fragments.PaymentCardsFragment;
import com.todo.behtarinhotel.fragments.SearchFragment;
import com.todo.behtarinhotel.fragments.WishListFragment;
import com.todo.behtarinhotel.payment.MyPayPall;
import com.todo.behtarinhotel.simpleobjects.RoomQueryGuestSO;
import com.todo.behtarinhotel.simpleobjects.SearchRoomSO;
import com.todo.behtarinhotel.simpleobjects.UserSO;
import com.todo.behtarinhotel.supportclasses.AppState;

import org.json.JSONException;

import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;


public class MainActivity extends MaterialNavigationDrawer {

    MyPayPall myPayPall;
    public SearchFragment searchFragment;
    MainFragment mainFragment;
    PopupMenu popupMenu;

    @Override
    public void init(Bundle savedInstanceState) {

        searchFragment = new SearchFragment();

        initDrawer();
    }

    private void initDrawer() {



        setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_ANYWHERE);
        allowArrowAnimation();
        disableLearningPattern();
            UserSO user = AppState.getLoggedUser();
            addAccount(new MaterialAccount(
                    getResources(),
                    user.getFirstName() + " " + user.getLastName(), "",
                    R.drawable.behtarin_logo_b,
                    R.drawable.dubai));

        addSection(newSection(getString(R.string.fragment_searchhotels), searchFragment));
        addSection(newSection("Room Management", new WishListFragment()));
        addSection(newSection("Credit cards", new PaymentCardsFragment()));
        addSection(newSection("Log out", new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection materialSection) {
                Intent in = new Intent(MainActivity.this, LauncherActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
                AppState.userLoggedOut();
            }
        }));
        addSubheader("Subheader title");
    }


    public void setMainSearchFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    public void updateArrayListInFragment(ArrayList<RoomQueryGuestSO> guests, int position) {

        if (position == 9) {
            searchFragment.addRoom(new SearchRoomSO(guests));
        } else {
            searchFragment.addRoom(new SearchRoomSO(guests), position);
        }
    }


}
