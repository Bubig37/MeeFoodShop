package th.ac.rmutsv.meefoodshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private boolean aBoolean = true;
    private String genderString;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //    create toolbar
        createToolbar();
//                Gender controller
        genderController();
    } // Main method

    private void genderController() {
        RadioGroup radioGroup = getView().findViewById(R.id.radGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                aBoolean = false;
                switch (checkedId) {
                    case R.id.radMale:
                        genderString = "Male";
                        break;
                    case R.id.radFemale:
                        genderString = "Female";
                        break;
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_register, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemMenuRegister) {
            checkValue();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkValue() {
//        Get Value From EditText
        EditText nameEditText = getView().findViewById(R.id.edtName);
        EditText userEditText = getView().findViewById(R.id.edtUser);
        EditText passwordEditText = getView().findViewById(R.id.edtPassword);

        String name = nameEditText.getText().toString().trim();
        String user = userEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        MyAlert myAlert = new MyAlert(getActivity());

        if (name.isEmpty() || user.isEmpty() || password.isEmpty()) {
//            Have space
            myAlert.normalDialog( "Have Space","Please Fill All Blank" );
        } else if (aBoolean) {
//            Non choose gender
            myAlert.normalDialog("Gender Choose","Please Choose Gender");
        } else {
//            OK!!!!
            MyConstant myConstant = new MyConstant();
            try {
                AddUserThread addUserThread = new AddUserThread(getActivity());
                addUserThread.execute(name,genderString,user,password,myConstant.getUrlAddUser());

                String response = addUserThread.get();
                Log.d("8mayV1","response = "+response);

                if (Boolean.parseBoolean(response)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }  // end if
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.title_toolbar));
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}  // Main class
