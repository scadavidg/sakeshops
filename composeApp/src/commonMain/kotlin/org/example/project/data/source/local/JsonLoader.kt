package org.example.project.data.source.local

import kotlinx.serialization.json.Json
import org.example.project.data.model.SakeShopsResponse

object JsonLoader {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun loadSakeShops(): SakeShopsResponse {
        return try {
            loadHardcodedData()
        } catch (e: Exception) {
            e.printStackTrace()
            SakeShopsResponse(emptyList())
        }
    }

    private fun loadHardcodedData(): SakeShopsResponse {
        val hardcodedJson = """
        {
            "shops": [
                {
                    "id": "1",
                    "name": "Sake Bar Tokyo",
                    "address": "1-2-3 Shibuya, Shibuya City, Tokyo 150-0002, Japan",
                    "rating": 4.5,
                    "imageUrl": "https://example.com/images/sake-bar-tokyo.jpg",
                    "description": "A traditional sake bar in the heart of Tokyo, offering over 100 varieties of premium sake from across Japan.",
                    "websiteUrl": "https://sakebartokyo.com"
                },
                {
                    "id": "2",
                    "name": "Kyoto Sake House",
                    "address": "456 Gionmachi, Higashiyama Ward, Kyoto 605-0073, Japan",
                    "rating": 4.8,
                    "imageUrl": "https://example.com/images/kyoto-sake-house.jpg",
                    "description": "Experience the authentic taste of Kyoto with our carefully curated selection of local sake and traditional Japanese cuisine.",
                    "websiteUrl": "https://kyotosakehouse.com"
                },
                {
                    "id": "3",
                    "name": "Osaka Sake Garden",
                    "address": "789 Dotonbori, Chuo Ward, Osaka 542-0071, Japan",
                    "rating": 4.3,
                    "imageUrl": "https://example.com/images/osaka-sake-garden.jpg",
                    "description": "A modern sake bar featuring innovative sake cocktails and a vibrant atmosphere in the heart of Osaka.",
                    "websiteUrl": "https://osakasakegarden.com"
                },
                {
                    "id": "4",
                    "name": "Hakata Sake Brewery",
                    "address": "321 Hakata Station, Hakata Ward, Fukuoka 812-0012, Japan",
                    "rating": 4.6,
                    "imageUrl": "https://example.com/images/hakata-sake-brewery.jpg",
                    "description": "Visit our brewery and tasting room to learn about the sake-making process while enjoying our premium selections.",
                    "websiteUrl": "https://hakatasakebrewery.com"
                },
                {
                    "id": "5",
                    "name": "Sapporo Sake Lounge",
                    "address": "654 Susukino, Chuo Ward, Sapporo 064-0805, Japan",
                    "rating": 4.4,
                    "imageUrl": "https://example.com/images/sapporo-sake-lounge.jpg",
                    "description": "Warm up with our selection of hot sake and traditional Hokkaido dishes in this cozy lounge.",
                    "websiteUrl": "https://sapporosakelounge.com"
                },
                {
                    "id": "6",
                    "name": "Nara Sake Temple",
                    "address": "987 Nara Park, Nara 630-8213, Japan",
                    "rating": 4.7,
                    "imageUrl": "https://example.com/images/nara-sake-temple.jpg",
                    "description": "A unique sake bar located near ancient temples, offering a spiritual experience with traditional sake.",
                    "websiteUrl": "https://narasaketemple.com"
                },
                {
                    "id": "7",
                    "name": "Yokohama Sake Harbor",
                    "address": "147 Minato Mirai, Nishi Ward, Yokohama 220-0012, Japan",
                    "rating": 4.2,
                    "imageUrl": "https://example.com/images/yokohama-sake-harbor.jpg",
                    "description": "Modern sake bar with a stunning harbor view, featuring international sake varieties and fusion cuisine.",
                    "websiteUrl": "https://yokohamasakeharbor.com"
                },
                {
                    "id": "8",
                    "name": "Kanazawa Sake Art",
                    "address": "258 Kenrokuen, Kanazawa 920-0936, Japan",
                    "rating": 4.9,
                    "imageUrl": "https://example.com/images/kanazawa-sake-art.jpg",
                    "description": "An artistic approach to sake appreciation, combining traditional brewing with modern presentation in a beautiful garden setting.",
                    "websiteUrl": "https://kanazawasakeart.com"
                }
            ]
        }
        """.trimIndent()

        return json.decodeFromString<SakeShopsResponse>(hardcodedJson)
    }
}
