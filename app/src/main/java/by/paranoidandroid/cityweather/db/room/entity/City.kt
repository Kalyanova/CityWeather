package by.paranoidandroid.cityweather.db.room.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "city")
data class City(@PrimaryKey(autoGenerate = true)
                var id: Int,
                var name: String,
                var country: String,
                var temperature: String?,
                var url: String?)