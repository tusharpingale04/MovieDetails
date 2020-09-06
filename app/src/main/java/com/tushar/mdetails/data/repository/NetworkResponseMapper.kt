package com.tushar.mdetails.data.repository


interface NetworkResponseMapper<in FROM : NetworkResponseModel> {
  fun onLastPage(response: FROM): Boolean
}
