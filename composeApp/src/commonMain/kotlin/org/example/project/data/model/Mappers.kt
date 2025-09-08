package org.example.project.data.model

import org.example.project.domain.model.SakeShopDetail
import org.example.project.domain.model.SakeShopPreview

fun ShopDto.toSakeShopPreview(): SakeShopPreview {
    return SakeShopPreview(
        id = this.id,
        name = this.name,
        address = this.address,
        rating = this.rating.toFloat()
    )
}

fun ShopDto.toSakeShopDetail(): SakeShopDetail {
    return SakeShopDetail(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        rating = this.rating.toFloat(),
        address = this.address,
        websiteUrl = this.websiteUrl
    )
}
