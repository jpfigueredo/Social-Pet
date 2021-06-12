package vortex.project.unify.Views.Fragment.Main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_profile.*
import vortex.project.unify.R
import vortex.project.unify.Views.Adapters.PostsAdapter
import vortex.project.unify.Views.Classes.Post
import vortex.project.unify.Views.ViewModel.*

class ProfileFragment : Fragment(), PostsAdapter.OnItemClickListener  {

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var postsUserViewModel: PostsUserViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var petsViewModel: PetsViewModel
    private lateinit var petMainViewModel: PetMainViewModel
    private lateinit var postsUserList: List<Post>
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
                act -> userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
        }
        activity?.let { act ->
            userViewModel = ViewModelProviders.of(act).get(UserViewModel::class.java)
            postsViewModel = ViewModelProviders.of(act).get(PostsViewModel::class.java)
            petsViewModel = ViewModelProviders.of(act).get(PetsViewModel::class.java)
            postsUserViewModel = ViewModelProviders.of(act).get(PostsUserViewModel::class.java)
            petMainViewModel = ViewModelProviders.of(act).get(PetMainViewModel::class.java)
            postsUserList = postsUserViewModel.postsUserListVM.value ?: listOf()
        }
        configRecycleView()
        subscribe()
        setData()
    }
    private fun configRecycleView() {
        userPosts_recyclerView.layoutManager = LinearLayoutManager(activity)
        userPosts_recyclerView.adapter = PostsAdapter(postsUserList, this)
//        post_recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }
    override fun onItemClick(position: Int) {
//        postsUserList = postsUserViewModel.postsUserListVM.value ?: listOf()
//        val petPostName: String = postsUserList[position].petNamePost
//        var postLikes = postsUserList[position].likesPost.toInt()
//        postLikes += 1
//
//        postsUserList[position].likesPost = postLikes.toString()
////        postsViewModel.postsListVM.value = postsUserList

        Toast.makeText(context, "You Can't like your pet post", Toast.LENGTH_SHORT).show()
    }
    private fun subscribe(){
        postsUserViewModel.postsUserListVM.observe(viewLifecycleOwner, Observer { list->
            if (list != null){
                val adapter = userPosts_recyclerView.adapter
                if (adapter is PostsAdapter){
                    adapter.changeData(list)
                }
            }
        })
    }

//    private fun setUpListeners(){
////        settings_ImageButton.setOnClickListener {
////            findNavController().navigate(R.id.action_profile_dest_to_nav_preferences, null)
////        }
//    }

//    private fun setWidgets() {
//        activity?.toolbar_layout!!.visibility = View.VISIBLE
//        activity?.bottom_nav_view!!.visibility = View.VISIBLE
//    }

    private fun setData(){
        petMainViewModel.petMain_nameVM.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotBlank()) {
                tv_pets_name_profile.text = it.toString()
            }
        })

        petMainViewModel.petMain_specieVM.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotBlank()) {
                tv_bio.text = it.toString()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater!!.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_profile_dest_to_loginFragment, null)
            }
            R.id.nav_preferences -> {
                findNavController().navigate(R.id.nav_preferences, null)
            }
        }
        return true
    }
}