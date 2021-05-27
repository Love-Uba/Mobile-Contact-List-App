package com.example.weeksix.firebasecontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weeksix.ItemEntity
import com.example.weeksix.R

class ContactAdapter(
    var myDataEntity: ArrayList<ItemEntity>,
    private val listener: MyItemClickListener
) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    /**
     *The adapter populating the contact list with itemEntities holding contact details specified
     * and a custom click listener to make the items clickable.
     * */


    inner class MyViewHolder(myItem: View) : RecyclerView.ViewHolder(myItem) {
        var contactName = myItem.findViewById<TextView>(R.id.contact_name)

        init {
            myItem.setOnClickListener {
                var positionClicked = adapterPosition
                if (positionClicked != RecyclerView.NO_POSITION) {
                    listener.onItemClick(positionClicked)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val myRootView =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)

        return MyViewHolder(myRootView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var myItem = myDataEntity[position]
        holder.contactName.text = myItem.fullName

    }

    override fun getItemCount(): Int = myDataEntity.size
    interface MyItemClickListener {
        fun onItemClick(position: Int)
    }

}