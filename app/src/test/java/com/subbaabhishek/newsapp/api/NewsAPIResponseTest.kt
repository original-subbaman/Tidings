package com.subbaabhishek.newsapp.api

import com.google.common.truth.Truth.assertThat
import com.subbaabhishek.newsapp.data.api.NewsAPIService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIResponseTest {

    private lateinit var service : NewsAPIService
    private lateinit var server : MockWebServer

    @Before
    fun setUp(){
       server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
       runBlocking {
           enqueueMockResponse("newsresponse.json")
           val response = service.getTopHeadlines("us", 1).body()
           val request = server.takeRequest()
           assertThat(response).isNotNull()
           assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=bc0f32d7bb5f4d8c8549d05a50a24e73")
       }
    }



    @Test
    fun getTopHeadlines_receiveResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receiveResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            val article = articleList[0]
            assertThat(article.title).isEqualTo("Australia vs. Nigeria highlights: Nigeria stuns Australia with 3-2 comeback - FOX Sports")
            assertThat(article.url).isEqualTo("https://www.foxsports.com/stories/soccer/womens-world-cup-2023-top-plays-australia-nigeria")

        }
    }

    @Test
    fun getNewsFromCategory_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse_category.json")
            val response = service.getNewsFromCategory("de", "business",1 ).body()
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=de&category=business&page=1&apiKey=bc0f32d7bb5f4d8c8549d05a50a24e73")
        }
    }

    @Test
    fun getNewsFromCategory_receiveResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse_category.json")
            val responseBody = service.getNewsFromCategory("us", "de", 1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getNewsFromCategory_receiveResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse_category.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            val article = articleList[0]
            assertThat(article.title).isEqualTo("Tages- und Festgeld: Kurz vor dem Zins-Höhepunkt – So sollten Sparer reagieren - WELT - WELT")
            assertThat(article.url).isEqualTo("https://news.google.com/rss/articles/CBMie2h0dHBzOi8vd3d3LndlbHQuZGUvZmluYW56ZW4vcGx1czI0Njc1ODMxNC9UYWdlcy11bmQtRmVzdGdlbGQtS3Vyei12b3ItZGVtLVppbnMtSG9laGVwdW5rdC1Tby1zb2xsdGVuLVNwYXJlci1yZWFnaWVyZW4uaHRtbNIBAA?oc=5")

        }
    }

    private fun enqueueMockResponse(
        fileName : String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @After
    fun tearDown(){
        server.shutdown()

    }
}