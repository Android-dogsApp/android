package com.example.dogsandddapters
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestApiFragment : Fragment() {

    private val BASE_URL = "https://dog.ceo/api/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dogApiService: DogApiService = retrofit.create(DogApiService::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rest_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subBreedsTextView: TextView = view.findViewById(R.id.subBreedsTextView)

        val call = dogApiService.getSubBreeds("hound")
        call.enqueue(object : Callback<DogApiResponse> {
            override fun onResponse(
                call: Call<DogApiResponse>,
                response: Response<DogApiResponse>
            ) {
                if (response.isSuccessful) {
                    val subBreeds = response.body()?.message
                    Log.i("RestApiFragment", "onResponse: ${subBreeds}")

                    // Display the list of sub-breeds in the TextView
                    if (!subBreeds.isNullOrEmpty()) {
                        val subBreedsText = subBreeds.joinToString("\n")
                        subBreedsTextView.text = "$subBreedsText"
                    } else {
                        subBreedsTextView.text = "No sub-breeds found"
                    }
                } else {
                    subBreedsTextView.text = "Error fetching sub-breeds"
                }
            }

            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                subBreedsTextView.text = "Network request failed"
            }
        })
    }
}



