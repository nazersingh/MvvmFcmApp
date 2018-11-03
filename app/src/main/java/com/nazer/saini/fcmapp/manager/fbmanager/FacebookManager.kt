package com.nazer.saini.fcmapp.manager.fbmanager

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.support.annotation.NonNull
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.pojo.FacebookPojo
import com.nazer.saini.fcmapp.ui.dialogues.Dialogues
import com.nazer.saini.fcmapp.util.PrintLog
import org.json.JSONException
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import android.widget.Toast
import bolts.Task
import com.facebook.AccessToken
import com.facebook.internal.WebDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.nazer.saini.fcmapp.ui.activity.MainActivity


object FacebookManager{

    /**
 * Login to facebook
 */


fun fbLogin(context: Activity, callbackManager: CallbackManager, callBackInterface: GenericApiCallback<FacebookPojo>,firebaseAuth: FirebaseAuth?) {
    //facebookLogout()
    LoginManager.getInstance().logInWithReadPermissions(context, Arrays.asList("public_profile", "user_status", "email", "user_likes", "user_location", "user_photos", "user_posts", "user_friends", "user_videos", "user_birthday"))

    LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code
//                    if(firebaseAuth!=null)
//                        handleFacebookAccessToken(firebaseAuth,loginResult.accessToken,context,callBackInterface)
//                        else
                    callFacebookGraphApi(loginResult.accessToken, callBackInterface)
                }

                override fun onCancel() {
                    // App code
                    PrintLog.e("FACEBOOK_STATUS", "Login has been cancelled")
                    facebookLogout()
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    PrintLog.e("FACEBOOK_LOGIN_ERROR", exception.toString())
                    callBackInterface.onerror(exception)
                    facebookLogout()

                }
            })
}

fun callFacebookGraphApi(accessToken: AccessToken, callBackInterface: GenericApiCallback<FacebookPojo>) {
    try {

        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            PrintLog.e("CommonUtils", response.toString())

            try {
                val facebookPojo = FacebookPojo()
                facebookPojo.userId = (`object`.getString("id"))
                facebookPojo.profilePicture = (URL("https://graph.facebook.com/" + `object`.getString("id") + "/picture?width=500&height=500"))
                if (`object`.has("first_name"))
                    facebookPojo.firstName = (`object`.getString("first_name"))
                if (`object`.has("last_name"))
                    facebookPojo.lastName = (`object`.getString("last_name"))
                if (`object`.has("email"))
                    facebookPojo.email = (`object`.getString("email"))
                else
                    facebookPojo.email = ""
                if (`object`.has("birthday"))
                    facebookPojo.birthday = (`object`.getString("birthday"))
                if (`object`.has("gender"))
                    facebookPojo.gender = (`object`.getString("gender"))

                callBackInterface.onSuccess(facebookPojo)
            } catch (e: JSONException) {
                e.printStackTrace()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
        //Here we put the requested fields to be returned from the JSONObject
        val parameters = Bundle()
        parameters.putString("fields", "id, first_name, last_name, email, birthday, gender")
        request.parameters = parameters
        request.executeAsync()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun facebookLogout() {
    try {
        LoginManager.getInstance().logOut()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/*
    //Share on facebook
    */
//    fun shareYourContentOnFacebook(context: Activity, callbackManager: CallbackManager,callBackInterface: CallBackInterface<FacebookPojo>) {
fun shareYourContentOnFacebook(context: Activity, callbackManager: CallbackManager, shareUrl: String) {
    try {
        //            LoginManager.getInstance().logInWithPublishPermissions(context, Arrays.asList("publish_actions"));

        val shareDialog = ShareDialog(context)
        shareDialog.registerCallback(callbackManager, object : FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result) {
//                    callBackInterface.onSuccess(FacebookPojo())
                PrintLog.e("on", "shared successfully")
                Dialogues.setTitle("MyApp")
                        .isCancelAble(false)
                        .setMessage("Link Shared Successfully")
                        .setPositiveButtonText("Ok")
                        .showDialogueOnlyText(context)
            }

            override fun onCancel() {
                PrintLog.e("Cancel ", "sharing cancelled")
            }

            override fun onError(error: FacebookException) {
//                    callBackInterface.onFailure(error)
                PrintLog.e("error ", "sharing error")
            }
        }, 10901)

        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val linkContent = ShareLinkContent.Builder()
                    //                    .setContentTitle("Jam Detected")
                    //                    .setContentDescription("Jam detected at this location" )
                    .setContentUrl(Uri.parse(shareUrl))
                    //                    .setImageUrl(Uri.parse("https://i.pinimg.com/736x/07/c3/45/07c345d0eca11d0bc97c894751ba1b46--ginger-kitten-ginger-cats.jpg"))
                    .build()

            shareDialog.show(linkContent)
        }
        shareDialog.shouldFailOnDataError
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/*
to check isfb user logged in
*/
fun isLoggedIn(): Boolean {
    val accessToken = AccessToken.getCurrentAccessToken()
    return accessToken != null
}


    private fun handleFacebookAccessToken(auth: FirebaseAuth,token: AccessToken,context: Activity,callBackInterface: GenericApiCallback<FacebookPojo>) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(context as MainActivity, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: com.google.android.gms.tasks.Task<AuthResult>) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.getCurrentUser()
                            PrintLog.e(" firebase login "," "+user?.toString())
                        } else {
                            // If sign in fails, display a message to the user.
                            PrintLog.e(" firebase login error"," ")
                        }

                    }
                })
    }


}