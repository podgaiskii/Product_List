package com.example.productlist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productlist.db.dao.PostDAO
import com.example.productlist.db.dao.UserDAO
import com.example.productlist.db.entity.PostEntity
import com.example.productlist.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProductsDatabase : RoomDatabase() {

    abstract val postDAO: PostDAO
    abstract val userDAO: UserDAO

    companion object {
        @Volatile
        private var INSTANCE: ProductsDatabase? = null

        fun getInstance(context: Context): ProductsDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProductsDatabase::class.java,
                        "products_database"
                    )
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}