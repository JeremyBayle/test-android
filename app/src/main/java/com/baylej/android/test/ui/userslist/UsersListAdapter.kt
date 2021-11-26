package com.baylej.android.test.ui.userslist

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.baylej.android.core.model.User

import android.content.Context

import android.view.LayoutInflater
import android.widget.TextView
import com.baylej.android.test.R


class UsersListAdapter(private val context: Context, private var users: List<Pair<Char, List<User>>>) : BaseExpandableListAdapter() {

    fun updateUsers(users: List<Pair<Char, List<User>>>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getGroupCount(): Int = users.size

    override fun getChildrenCount(position: Int): Int = users[position].second.size

    override fun getGroup(position: Int) = users[position].first

    override fun getChild(position: Int, expandedListPosition: Int) = users[position].second[expandedListPosition]

    override fun getGroupId(position: Int): Long = position.toLong()

    override fun getChildId(position: Int, expandedListPosition: Int): Long = expandedListPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val listTitle = getGroup(listPosition)
        val view: View = if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.list_users_group, null)
        } else {
            convertView
        }
        val listTitleTextView = view.findViewById<TextView>(R.id.list_group_title)
        listTitleTextView.text = listTitle.toString()
        return view
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val user = getChild(listPosition, expandedListPosition)
        val view: View = if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.list_users_item, null)
        } else {
            convertView
        }
        val userLastNameTextView = view.findViewById<TextView>(R.id.user_last_name)
        userLastNameTextView.text = user.lastName
        return view
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean = true
}