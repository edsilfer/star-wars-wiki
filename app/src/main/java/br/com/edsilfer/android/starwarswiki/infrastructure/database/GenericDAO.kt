package br.com.tyllt.infrastructure.database

import android.util.Log
import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmPrimaryKeyConstraintException

open class GenericDAO<E : RealmObject>(val mType: Class<E>, val mColumnID: String) {
    open fun create(source: E): E? {
        val realm = Realm.getDefaultInstance()
        var realmObject: E? = null
        realm.executeTransaction {
            try {
                realmObject = realm.copyToRealm(source)
            } catch (e: RealmPrimaryKeyConstraintException) {
                Log.e(GenericDAO::class.simpleName, "Attemp to add a register with the same previous primary key")
            }
        }
        realm.close()
        return realmObject
    }

    open fun read(id: Long): E? {
        val realm = Realm.getDefaultInstance()
        val realmObject = realm.where(mType).equalTo(mColumnID, id).findFirst()
        return realmObject
    }

    open fun list(): List<E> {
        val realm = Realm.getDefaultInstance()
        return realm.where(mType).findAll().toList()
    }

    open fun update(source: E) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(source)
        }
        realm.close()
    }

    open fun delete(id: Long) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            val realmObject = read(id)
            realmObject?.deleteFromRealm()
        }
        realm.close()
    }

    open fun deleteAll() {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.delete(mType)
        }
        realm.close()
    }
}