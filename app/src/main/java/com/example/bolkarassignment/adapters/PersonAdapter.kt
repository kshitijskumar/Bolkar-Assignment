package com.example.bolkarassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bolkarassignment.R
import com.example.bolkarassignment.databinding.HolderUserBinding
import com.example.bolkarassignment.response.PeopleResponseBody

class PersonAdapter(
    private val isMain : Boolean = false
) : ListAdapter<PeopleResponseBody, PersonAdapter.PersonViewHolder>(diffUtil){

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PeopleResponseBody>() {
            override fun areItemsTheSame(
                oldItem: PeopleResponseBody,
                newItem: PeopleResponseBody
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PeopleResponseBody,
                newItem: PeopleResponseBody
            ): Boolean {
                return oldItem._id == newItem._id
            }
        }
    }


    private var host: PeopleResponseBody? = null
    private var speakerList : List<PeopleResponseBody>? = listOf()
    private var moderatorList : List<PeopleResponseBody>? = listOf()

    fun initialiseAdapter(
        mhost : PeopleResponseBody?,
        mspeakerList : List<PeopleResponseBody>?,
        mmoderatorList : List<PeopleResponseBody>?
    ){
        host = mhost
        speakerList = mspeakerList
        moderatorList = mmoderatorList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderUserBinding.inflate(inflater)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvName.text = item.n
            item.u?.let {
                setProfileImage(it, this)
            }
            differentiateUsers(this)
            micOfUsers(item.mic, this)
            setPostOfMain(item, this)
        }
    }

    //visibility of mic icon based on the state passed to the function. State is api response `mic`
    private fun micOfUsers(state : Boolean, binding : HolderUserBinding) {
        if(state) {
            binding.ivMic.visibility = View.GONE
        }
    }

    //if the user is present in speaker list then set post as speaker
    //if the user is present in moderator list then set post as moderator
    //post is the text field below name
    private fun setPostOfMain(user: PeopleResponseBody, binding: HolderUserBinding) {
        if(isMain){
            if(host?._id == user._id){
                binding.ivBadge.visibility = View.VISIBLE
                binding.tvPost.text = "होस्ट"
                return
            }
            speakerList?.let {
                if(it.contains(user)){
                    binding.tvPost.text = "स्पीकर"
                    return
                }
            }
            moderatorList?.let {
                if(it.contains(user)){
                    binding.tvPost.text = "माडरेटर"
                    return
                }
            }
        }
    }

    //isMain = true means top recycler view
    //isMain = false means bottom recycler view
    //mic, post, badge should be invisible for members (bottom recycler view)
    private fun differentiateUsers(binding : HolderUserBinding){
        if (!isMain){
            binding.ivMic.visibility = View.GONE
            binding.tvPost.visibility = View.GONE
            binding.ivBadge.visibility = View.GONE
        }
    }

    private fun setProfileImage(username: String, binding : HolderUserBinding) {
        val url = "https://cdn1.bolkarapp.com/uploads/dp/$username.jpg"
        Glide
            .with(binding.root)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .into(binding.ivProfilePic)

    }

    class PersonViewHolder(val binding : HolderUserBinding) : RecyclerView.ViewHolder(binding.root)
}