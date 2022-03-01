package com.example.youtubetto

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.os.Handler


class MainActivity : AppCompatActivity() {

    private companion object{
        private val RC_SIGN_IN = 1
        private var LOG_TAG = "MainActivity"
        private var OAUTH2_SCOPE = "oauth2:https://www.googleapis.com/auth/youtube.readonly"
    }

    private lateinit var accountManager : AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountManager = AccountManager.get(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(this,gso)

        btnSignIn.setOnClickListener {
            val signInIntent = client.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun openSubscryptionActivity(oAuthToken: String){
                startActivity(Intent(this, SubscriptionsActivity::class.java).apply {
            putExtra("token", oAuthToken)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val sHandler = Handler()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                    accountManager.getAuthToken(account.account, OAUTH2_SCOPE, null, this,
                        { AccountManagerBundle ->
                            openSubscryptionActivity(AccountManagerBundle.result.getString(AccountManager.KEY_AUTHTOKEN).toString())
                        }, sHandler
                    )
            } catch (e: ApiException) {
                Log.w("", "Google sign in failed", e)
            }
        }
    }

}
