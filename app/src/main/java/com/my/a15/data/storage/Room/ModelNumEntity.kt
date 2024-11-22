package com.my.a15.data.storage.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.my.a15.data.storage.Room.ModelNumEntity.Companion.TABLE_NAME
import com.my.a15.domain.model.VariantGrid
import kotlinx.serialization.Serializable

@Entity(tableName = TABLE_NAME)
data class ModelNumEntity(
    @PrimaryKey
    val id: Int = 1,
    val isVictory: Boolean = false,
    val variantGridEntity: VariantGridEntity = VariantGridEntity.GRID_4X4,
    val finalListEntity: List<Int?> = emptyList(),
    val countStep: Int = 0,
    val sqrt: Int,
    val listCellsEntity: List<MyCellEntity?>
) {
    companion object {
        const val TABLE_NAME = "fifteen_table"
    }
}
@Serializable
data class MyCellEntity(
    val num: Int,
    val colorCellEntity: ColorCellEntity
)
@Serializable
enum class ColorCellEntity {
    DEFAULT, CORRECT_POSITION
}
@Serializable
enum class VariantGridEntity(val count: Int) {
    GRID_4X4(15),
    GRID_5X5(24),
    GRID_6X6(35)
}