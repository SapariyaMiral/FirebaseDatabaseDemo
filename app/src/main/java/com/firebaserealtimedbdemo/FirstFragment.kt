package com.ahmedadeltito.firebaserealtimedbdemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmedadeltito.firebaserealtimedbdemo.databinding.FragmentFirstBinding
import com.ahmedadeltito.firebaserealtimedbdemo.model.UserInfo
import com.google.firebase.database.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dbReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        //get reference to the "users" node Users is DB name first node genrate
        dbReference = FirebaseDatabase.getInstance().getReference("users")
        //write
        writeNewUser("1","miral", "9089785645")
        //read
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot: DataSnapshot in dataSnapshot.children) {


                    Log.d("firebasechanges ::::: ","========> "+postSnapshot.key);

                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        dbReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                for (postSnapshot: DataSnapshot in dataSnapshot.children) {

                    val user: UserInfo? = postSnapshot.getValue(UserInfo::class.java)
                    Log.d("=======", user?.name.toString() + " " + user?.mobile)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        return binding.root

    }
    fun writeNewUser(userId: String, name: String, email: String) {
        val user = UserInfo(name, email)

        //user node create under Users
        dbReference.child("user").child(userId).setValue(user)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}