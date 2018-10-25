package com.turastory.falcon.data.source.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by tura on 2018-10-22.
 *
 * TODO: User class와 연결
 * TODO: 이미지 추가
 */
@Entity(tableName = "feed_data")
//    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["author_id"], onDelete = ForeignKey.CASCADE)])
data class Feed(@PrimaryKey val id: Long,
                @ColumnInfo(name = "author_id") val authorId: String,
                var created: Date = Date(),
                var comments: Int = 0,
                var like: Int = 0,
                var content: String = "",
                var markAsLiked: Boolean)