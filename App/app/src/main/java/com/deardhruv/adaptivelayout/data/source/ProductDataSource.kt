package com.deardhruv.adaptivelayout.data.source

/*
 *  ProductDataSource.kt
 *
 *  Created by Dhruv Patel on 08/10/2025.
 *  Copyright ©2025 DearDhruv. All rights reserved.
 */

import android.util.Log
import com.deardhruv.adaptivelayout.data.model.Product
import kotlinx.coroutines.delay

/**
 * Data source for products
 * In a real app, this would be an API service or database
 */
class ProductDataSource {

    suspend fun getProducts(): List<Product> {
        // Simulate network delay
        // delay(500)

        val uniqueDescriptions = listOf(
            "Experience next-gen performance with this cutting-edge device. Engineered for speed and reliability, it boasts a sleek, ergonomic design and a vibrant display that brings your content to life. Perfect for professionals and creatives alike.",
            "Wrap yourself in unparalleled comfort with this premium fabric garment. Made from 100% organic cotton, it's breathable, durable, and incredibly soft to the touch. A timeless addition to any wardrobe, designed for all-day wear.",
            "Transform your living space with this elegant home accent. Its minimalist design and neutral tones complement any decor, adding a touch of sophistication and warmth. Handcrafted from sustainable materials, it's as eco-friendly as it is beautiful.",
            "Embark on a literary journey with this captivating bestseller. A gripping tale of adventure and discovery that will keep you on the edge of your seat. Critically acclaimed and beloved by readers worldwide.",
            "Elevate your game with this professional-grade sports equipment. Designed for maximum performance and durability, it provides the perfect balance of power and control. Whether you're a beginner or a seasoned pro, this will help you achieve your best.",
            "Unleash your creativity with this versatile gadget. Featuring a high-resolution camera and powerful processing, it's the ultimate tool for content creators. Its intuitive interface makes capturing and editing stunning visuals easier than ever.",
            "Stay stylish and comfortable with this modern apparel piece. Its moisture-wicking technology keeps you cool and dry, while the flexible fabric allows for a full range of motion. Ideal for an active lifestyle or casual outings.",
            "Brighten up your home with this artisanal decor piece. Each one is uniquely crafted, featuring intricate details and a rich texture that adds character to any room. A true conversation starter and a testament to fine craftsmanship.",
            "Expand your horizons with this insightful non-fiction book. It offers a deep dive into its subject matter, presenting complex ideas in an accessible and engaging way. A must-read for lifelong learners and curious minds.",
            "Push your limits with this advanced fitness gear. Built with lightweight yet robust materials, it offers superior support and stability during intense workouts. Train smarter and harder with equipment you can trust.",
            "Discover immersive sound with these state-of-the-art headphones. Featuring active noise cancellation and crystal-clear audio, they let you escape into your own world. The long-lasting battery ensures your music never stops.",
            "Make a statement with this fashion-forward accessory. It combines classic elegance with contemporary flair, making it the perfect finishing touch for any outfit. Crafted with precision and attention to detail.",
            "Create a cozy atmosphere with this charming home essential. Its warm glow and pleasant aroma provide a relaxing ambiance, helping you unwind after a long day. Made with natural waxes and essential oils.",
            "Lose yourself in a world of fantasy with this epic novel. Rich world-building and unforgettable characters make this a modern classic. The first in a series that will leave you wanting more.",
            "Achieve peak performance with these specialized athletic shoes. Engineered for optimal traction and cushioning, they reduce impact and enhance agility. Dominate the field or the track with confidence."
        )

        return List(30) { index ->
            Product(
                id = "product_$index",
                name = "Product ${index + 1}",
                description = uniqueDescriptions[index % uniqueDescriptions.size] +
                        " This item #${index + 1} showcases adaptive layout capabilities and features high-quality materials.",
                price = (index + 1) * 10.99,
                imageUrl = "https://picsum.photos/seed/${index+30}/400/300",
                // imageUrl = "https://via.placeholder.com/400x300.png/282828/FFFFFF?text=Product+${index + 1}",
                // imageUrl = "https://loremflickr.com/400/300/${listOf("electronics", "apparel", "home", "books", "sports")[index % 5]}?lock=$index",
                // imageUrl = "https://placehold.co/600x400?text=Product%0A${index + 1}",
                category = listOf("Electronics", "Clothing", "Home", "Books", "Sports")[index % 5],
                rating = 3.5f + (index % 3) * 0.5f,
                inStock = index % 7 != 0
            ).also {
                Log.d("imageUrl", it.imageUrl)
            }
        }
    }

    suspend fun getProductById(productId: String): Product? {
        //delay(300)
        return getProducts().find { it.id == productId }
    }
}
