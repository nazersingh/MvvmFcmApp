package com.nazer.saini.fcmapp.ui.base

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.nazer.saini.fcmapp.callbacks.GenericApiCallback
import com.nazer.saini.fcmapp.ui.fragment.signup.mobileverification.MobileVerificationViewModel
import com.nazer.saini.fcmapp.util.PrintLog
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.google.firebase.database.*
import org.w3c.dom.Comment


class FcmModel {

    private val TAG: String = this.javaClass.simpleName
    private lateinit var mVerificationCallBack: MobileVerificationViewModel.onOtpVerification


    fun startPhoneNumberVerification(phoneNumber: String, otpVerification: MobileVerificationViewModel.onOtpVerification) {
        // [START start_phone_auth]
        mVerificationCallBack = otpVerification
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                TaskExecutors.MAIN_THREAD,             // Activity/Executor (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks
    }

    /**
     * ============================ OTP OnVerificationStateChangedCallbacks
     */
    var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            PrintLog.e(TAG, "onVerificationCompleted:${credential.smsCode}")
        }
        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            PrintLog.e(TAG, "onVerificationFailed " + e)
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                PrintLog.e(TAG, "Invalid phone number.")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                PrintLog.e(TAG, "Quota exceeded.")
            }
        }

        override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            PrintLog.e(TAG, "onCodeSent:" + verificationId!!)
            mVerificationCallBack.onCodeSent(verificationId, token.toString())
            // Save verification ID and resending token so we can use them later
        }
    }

    /**
     * ======================== sign In With PhoneAuthCredential
     */
     fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, genericApiCallback: GenericApiCallback<String>) {
          FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(it: Task<AuthResult>) {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            PrintLog.e(TAG, "signInWithPhone:success")
                            val user = it.result?.user
                            PrintLog.e(TAG, " " + it.result?.user?.phoneNumber)
                            genericApiCallback.onSuccess(it.result?.user?.phoneNumber.toString())
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (it.exception is FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                PrintLog.e(TAG, " code is invalid " + it.exception?.message)
                                genericApiCallback.onerror(Exception(it.exception?.message))
                            }
                        }
                    }
                })
    }

    fun signInWithCredential(authCredential: AuthCredential,genericApiCallback: GenericApiCallback<String>) {
        FirebaseAuth.getInstance().signInWithCredential(authCredential)
                .addOnCompleteListener {  if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    PrintLog.e(TAG, "signInWithEmail:success")
                    val user = FirebaseAuth.getInstance().currentUser
                    genericApiCallback.onSuccess(it.result?.user?.phoneNumber.toString())
                } else {
                    // If sign in fails, display a message to the user.
                    PrintLog.e(TAG, "signInWithEmail:failure"+it.exception?.message)
                    genericApiCallback.onerror(Exception(it.exception?.message))
                } }
    }



    fun onChildEvent()
    {
        FirebaseDatabase.getInstance().reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                PrintLog.d(TAG, "onChildAdded:" + p0.getKey())
                // A new comment has been added, add it to the displayed list
                val comment = p0.getValue(Comment::class.java)
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                PrintLog.d(TAG, "onChildRemoved:" + p0.getKey())
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                PrintLog.d(TAG, "onChildChanged:" + p0.getKey())
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                val newComment = p0.getValue(Comment::class.java)
                val commentKey = p0.getKey()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                PrintLog.d(TAG, "onChildMoved:" + p0.getKey())
                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                val movedComment = p0.getValue(Comment::class.java)
                val commentKey = p0.getKey()
            }

            override fun onCancelled(p0: DatabaseError) {
                PrintLog.d(TAG, "onCancelled:"+p0.message)
            }
        })
    }

    /**
     * Query on ChildNode= Orderbychild
     * orderBy="starCount"
     * orderBy=""metrics/views""
     */
    fun onValueAddEvent(mChildName:String,myUserId:String,orderBy:String)
    {
        queryListener( FirebaseDatabase.getInstance().getReference().child(mChildName).child(myUserId)
                .orderByChild(orderBy))
    }

    // Last 100 posts, these are automatically the 100 most recent
    // due to sorting by push() keys
    fun recentPosts(childName:String,count:Int) {
       queryListener(FirebaseDatabase.getInstance().reference.child(childName).limitToFirst(count))
    }

    fun queryListener(query: Query)
    {
        query.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (postSnapshot in dataSnapshot.children) {

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            PrintLog.e(TAG, "loadPost:onCancelled"+databaseError.toException())
        }
    })
    }

}
