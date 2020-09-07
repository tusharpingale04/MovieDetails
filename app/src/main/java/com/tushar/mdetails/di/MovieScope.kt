package com.tushar.mdetails.di

import javax.inject.Qualifier

/**
 * This annotation class provides scoping for module wise injection
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieScope