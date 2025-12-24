package com.tolga.stocks_playground.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileSession @Inject constructor() {
    private var _userProfile: UserProfile? = null
    
    val userProfile: UserProfile
        get() = _userProfile ?: run {
            _userProfile = MockUserProfile.create()
            _userProfile!!
        }
    
    fun initialize(profile: UserProfile) {
        _userProfile = profile
    }
    
    fun updateProfile(profile: UserProfile) {
        _userProfile = profile
    }
    
    fun clear() {
        _userProfile = null
    }
    
    fun getHoldingForSymbol(symbol: String): StockHolding? {
        return userProfile.holdings.find { it.symbol == symbol }
    }
}

