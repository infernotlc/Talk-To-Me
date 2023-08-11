package com.orion.talktome.source

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.orion.talktome.models.Accounts
import com.orion.talktome.models.AccountsData
import kotlinx.coroutines.tasks.await


class FirebaseAccountsDataSource {
    private val ref = FirebaseDatabase.getInstance().getReference("/userSetList")
    var accountList: DataSnapshot? = null
    suspend fun getAccounts(): DataSnapshot? {

        try {
            println("istek baslangic")
            val doc = ref.get().await()
            if (doc != null) {
                println("atama yapildi")
                return doc
            }
        } catch (e: Exception) {
            println(e)
        }
        println("dox")
        return null
    }

    fun getAccounts(callback: (Accounts) -> Unit) {
        val accountDataList = ArrayList<AccountsData>()
        val accountNameList = ArrayList<String>()
        lateinit var accounts: Accounts

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val account = snapshot.getValue(AccountsData::class.java)
                val accountName = snapshot.ref.key
                account?.let { accountDataList.add(it) }
                accountName?.let { accountNameList.add(it) }
                accounts = Accounts(accountNameList, accountDataList)
                callback(accounts)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}