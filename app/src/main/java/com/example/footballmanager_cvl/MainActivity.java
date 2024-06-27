package com.example.footballmanager_cvl;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.footballmanager_cvl.databinding.ActivityMainBinding;
import com.example.footballmanager_cvl.ui.fecha.FechaFragment;
import com.example.footballmanager_cvl.ui.home.HomeFragment;
import com.example.footballmanager_cvl.ui.save.SaveFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 1;

    //Creamos los objetos Firebase y GoogleSignInClient:
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    //Crear los boyones, programar el metodo onClick y registrar
    //el listener a los botones
    SignInButton signInButton;
    //Button signOutButton, accessButton;
    ImageButton btnsignIn, btnsignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken("525819697501-2tn97tnovtighavekm64c9a5ibgi1rkl.apps.googleusercontent.com").
                requestEmail().
                build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = binding.signInButton;
        btnsignIn = binding.imageButtonAcceder;
        btnsignOut = binding.imageButtonCerrar;
        signInButton.setVisibility(View.VISIBLE);
        btnsignIn.setVisibility(View.GONE);
        btnsignOut.setVisibility(View.GONE);

        signInButton.setOnClickListener(this::signInOut);
        btnsignIn.setOnClickListener(this::signInOut);
        btnsignOut.setOnClickListener(this::signInOut);


    }

    //Metodos onClick
    public void signInOut(View v){

        int i = v.getId();
        if(i == R.id.signInButton || i == R.id.imageButtonAcceder){
            singIn();


        }else if(i == R.id.imageButtonCerrar){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cerrar sesión")
                    .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    signOut();
                }
            })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }

    }

    //Metodos de inicio de sesion:
    private void singIn(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    //Metodos de cierre de sesion:
    private void signOut(){
        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
        SaveFragment.fechaSeleccionada = null;//Limpiamos el valor de la fecha
        HomeFragment.reset();//Limpiamos el array de convocados
    }

    //Metodos para recoger los resultados una vez registrados para establecer la conexion:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Resultado devuelto al iniciar el Intent desde GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount>task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //El inicio de sesión de Google fue exitoso, autentícate con Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuthWithGoogle: "+ account.getId());
                firebaseAuthWithGoogle(account.getIdToken());//(*)


            }catch(ApiException e){
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);//(*2)
                // [END_EXCLUDE]
            }
        }

    }

    //Método para obtener las credenciales del usuario que se acaba de autentificar.
    private void firebaseAuthWithGoogle(String idToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Si el acceso es correcto, inicia la actividad que tiene el drawer
                            Intent intent = new Intent(getBaseContext(), DrawerActivity.class);
                            startActivity(intent);
                        }else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });

    }

    //Si las credenciales se obtienen correctamente, se actualiza la interfaz de usuario mostrando
    //el botón de cierre de sesión y de acceder y ocultando el de login a través del método updateIU.
    //Por el contrario, si la autentificación no fue correcta, se actualiza la interfaz de usuario
    //mostrando el botón de login y ocultando, si estuviera mostrado, el botón de cerrar sesión:
    public void updateUI(FirebaseUser user){

        if(user !=null){
            signInButton.setVisibility(View.GONE);
            btnsignOut.setVisibility(View.VISIBLE);
            btnsignIn.setVisibility(View.VISIBLE);
        }else{
            signInButton.setVisibility(View.VISIBLE);
            btnsignOut.setVisibility(View.GONE);
            btnsignIn.setVisibility(View.GONE);
        }
    }

    //Metodo que comprueba si hay un usuario con la sesion inciciada y actualiza la interfaz de usuario en base a eso
    @Override
    protected void onStart() {
        super.onStart();

        // Verificar si hay una sesión iniciada
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Actualizar la interfaz de usuario según si hay una sesión iniciada o no
        updateUI(currentUser);
    }

}