package com.example.weeksix.phonecontact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weeksix.ItemEntity
import com.example.weeksix.R


class PhoneAdapter(var phoneEntity: ArrayList<ItemEntity>) :
    RecyclerView.Adapter<PhoneAdapter.MyViewHolder>() {

    /**
     *The adapter populating the contact list with itemEntities holding contact details to be fetched from the phone contact list.
     * */

    class MyViewHolder(myView: View) : RecyclerView.ViewHolder(myView) {
        var phoneName = myView.findViewById<TextView>(R.id.phone_name)
        var phoneNumber = myView.findViewById<TextView>(R.id.phone_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var Rootie =
            LayoutInflater.from(parent.context).inflate(R.layout.phone_contact_item, parent, false)
        return MyViewHolder(Rootie)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var myItem = phoneEntity[position]
        holder.phoneName.text = myItem.fullName
        holder.phoneNumber.text = myItem.phone
    }

    override fun getItemCount(): Int = phoneEntity.size

}