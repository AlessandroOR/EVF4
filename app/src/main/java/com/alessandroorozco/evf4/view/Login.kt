package com.alessandroorozco.evf4.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.alessandroorozco.evf4.R
import com.alessandroorozco.evf4.databinding.ActivityLoginBinding



class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleLauncher:ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding =ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupView()
    firebaseAuth =Firebase.auth

    googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    signInFirebaseWithGoogle(account.idToken)
                } else {
                    Log.e("GoogleSignIn", "GoogleSignInAccount is null")
                }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "GoogleSignIn failed: ${e.statusCode}")
                Toast.makeText(this, "Error al iniciar sesión con Google: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Unknown error: ${e.message}")
                Toast.makeText(this, "Error desconocido al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun onLoginButtonClick(view: View) {
    val email = emailEditText.text.toString()
    val password = passwordEditText.text.toString()

    if (isValidEmail(email) && password.length >= 6) {
        if (email == "ejemplo@idat.edu.pe" && password == "123456") {

            Toast.makeText(this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            Toast.makeText(this, "Credenciales incorrectas. Inténtalo nuevamente.", Toast.LENGTH_SHORT).show()
        }
    } else {

        Toast.makeText(this, "Ingresa un correo y contraseña válidos.", Toast.LENGTH_SHORT).show()
    }
}

private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun signInFirebaseWithGoogle(idToken: String?) {
    val authCredential = GoogleAuthProvider.getCredential(idToken,null)
    firebaseAuth.signInWithCredential(authCredential)
        .addOnCompleteListener(this){
            if(it.isSuccessful){
                val user:FirebaseUser = firebaseAuth.currentUser!!
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Ocurrio un error ", Toast.LENGTH_SHORT).show()
            }
        }
}

private fun setupView() {

    binding.loginButton.setOnClickListener {
        signInWithEmailPassword()
    }
    binding.btnGoogle.setOnClickListener {
        signInWithGoogle()
    }
    binding.btnSingUp.setOnClickListener {
        signUpWithEmailPassword()
    }

}

private fun signUpWithEmailPassword() {
    val email = binding.txtUser.editText?.text.toString()
    val password = binding.txtPass.editText?.text.toString()

    if (validateInputs(email,password)){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Usuario Creado exitosamente ", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Usuario no creado ", Toast.LENGTH_SHORT).show()
                }
            }

    }
}

private fun signInWithEmailPassword() {

    val email = binding.txtUser.editText?.text.toString()
    val password = binding.txtPass.editText?.text.toString()

    signInFirebaseWihEmail(email,password)

}

private fun signInFirebaseWihEmail(email:String,password: String) {
    firebaseAuth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(this){
            if (it.isSuccessful){
                val user=firebaseAuth.currentUser
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "El Usuario no se Encontro", Toast.LENGTH_SHORT).show()
            }
        }
}

private fun isCredentialsValidated(): Boolean {
    return validateInputs(binding.txtUser.editText?.text.toString(),binding.txtPass.editText?.text.toString())
}

private fun signInWithGoogle(){
    val googleSignOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.CLAVE))
        .requestEmail().build()
    val client:GoogleSignInClient = GoogleSignIn.getClient(this,googleSignOptions)
    val intent = client.signInIntent
    googleLauncher.launch(intent)

}

private fun validateInputs(email:String,password:String):Boolean{

    val isEmailOk=email.isNotEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordOk=password.length>=6
    return isPasswordOk && isEmailOk

}



}