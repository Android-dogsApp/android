package com.example.dogsandddapters
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TableLayout
//import android.widget.TableRow
//import androidx.fragment.app.Fragment
//import retrofit2.Call
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.Callback
//import retrofit2.Response
//
//class RestApiFragment : Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_rest_api, container, false)
//    }
//
//    private val baseUrl = "https://dog.ceo/api/"
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val dogApiService = retrofit.create(DogApiService::class.java)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //setContentView(R.layout.activity_main)
//
//        val tableLayout: TableLayout = view.findViewById(R.id.tableLayout)
//
//        // Make the API request
//        val call = dogApiService.getDogBreeds()
//        call.enqueue(object : Callback<DogApiResponse> {
//            override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
//                if (response.isSuccessful) {
//                    val breeds = response.body()?.message
//                    breeds?.forEach { breed ->
//                        val tableRow = TableRow(this@MainActivity)
//
//                        // Create TextView for breed name
//                        val breedTextView = TextView(this@MainActivity)
//                        breedTextView.text = breed
//                        breedTextView.setPadding(16, 8, 16, 8)
//                        tableRow.addView(breedTextView)
//
//                        // Add the TableRow to the TableLayout
//                        tableLayout.addView(tableRow)
//                    }
//                } else {
//                    // Handle the error
//                }
//            }
//
//            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
//                // Handle the failure
//            }
//        })
//    }
//
//}

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

//    private lateinit var recyclerView: RecyclerView
//    private lateinit var dogAdapter: DogAdapter
//
//    private val baseUrl = "https://dog.ceo/api/"
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val dogApiService = retrofit.create(DogApiService::class.java)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_rest_api, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val tableLayout: TableLayout = view.findViewById(R.id.tableLayout)
//        recyclerView = view.findViewById(R.id.recyclerView)
//
//
//        // Make the API request
//        val call = dogApiService.getDogBreeds()
//        call.enqueue(object : Callback<DogApiResponse> {
//            override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
//                if (response.isSuccessful) {
//
//                    Log.i("RestApiFragment", "onResponse: ${response.body()}")
//                    val dogBreeds = mutableListOf<DogBreed>()
//
//                    response.body()?.forEach { (breed, subBreeds) ->
//                        dogBreeds.add(DogBreed(breed, subBreeds))
//                    }
//
//                    val dogAdapter = DogAdapter(dogBreeds)
//                    recyclerView.adapter = dogAdapter
//                    recyclerView.layoutManager = LinearLayoutManager(this@YourActivity)
//
//                } else {
//                    // Handle the error
//                }
//            }
//
//            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
//                // Handle the failure
//            }
//        })
//    }

    //WORKS FROM HERE!!!!

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rest_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subBreedsTextView: TextView = view.findViewById(R.id.subBreedsTextView)

        // Make the API request for hound sub-breeds
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
                    // Handle the error
                    subBreedsTextView.text = "Error fetching sub-breeds"
                }
            }

            override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
                // Handle the failure
                subBreedsTextView.text = "Network request failed"
            }
        })
    }



    //NEW:


//
//
//    private lateinit var dogBreeds: DogBreed
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_rest_api, container, false) // Replace with your layout ID
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://dog.ceo/api/breeds/list/all/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(DogApiService::class.java)
//
//        fetchDogBreeds(apiService)
//
//        val tableLayout = view.findViewById<TableLayout>(R.id.breed_table)
//        // Populate table will be called after data is fetched
//
//        return view
//    }
//
//    private fun fetchDogBreeds(apiService: DogApiService) {
//        apiService.getAllBreeds()
//            .enqueue(object : Callback<DogApiResponse> {
//                override fun onResponse(call: Call<DogApiResponse>, response: Response<DogApiResponse>) {
//                    if (response.isSuccessful) {
//                        Log.i("RestApiFragment", "onResponse: ${response}")
//                        val breedsResponse = response.body() ?: return
//                        Log.i("RestApiFragment", "onResponse: ${breedsResponse}")
//                        dogBreeds = DogBreed(breedsResponse?.message ?: emptyMap())
//                        populateTable(dogBreeds, view!!.findViewById(R.id.breed_table)) // Use view!! after data is fetched
//                    } else {
//                        // Handle error case
//                    }
//                }
//
//                override fun onFailure(call: Call<DogApiResponse>, t: Throwable) {
//                    // Handle network failure
//                    Log.e("MyFragment", "Error fetching dog breeds", t) // Log the error message
//
//                    // Display an error message to the user (optional)
//                    Toast.makeText(context, "Error fetching data. Please try again.", Toast.LENGTH_SHORT).show()
//
//                    // Optionally, disable UI elements like progress indicators
//                }
//
//            })
//    }
//
//
//    private fun populateTable(dogBreeds: DogBreed, tableLayout: TableLayout) {
//            // Add header row
//            val headerRow = TableRow(this.context)
//            headerRow.layoutParams = TableRow.LayoutParams(
//                TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT
//            )
//
//            val breedHeaderTextView = TextView(this.context)
//            //breedHeaderTextView.text = getString(R.string.breed_header) // Use string resource for "Breed Name"
//            breedHeaderTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
//            //breedHeaderTextView.textStyle = TextPaint.BOLD
//
//            val subBreedsHeaderTextView = TextView(this.context)
//            //subBreedsHeaderTextView.text = getString(R.string.sub_breeds) // Use string resource for "Sub Breeds"
//            subBreedsHeaderTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
//            //subBreedsHeaderTextView.textStyle = TextPaint.BOLD
//
//            headerRow.addView(breedHeaderTextView)
//            headerRow.addView(subBreedsHeaderTextView)
//            tableLayout.addView(headerRow)
//
//            // Add data rows for each breed
//            for ((breedName, subBreeds) in dogBreeds.message) {
//                val dataRow = TableRow(this.context)
//                dataRow.layoutParams = TableRow.LayoutParams(
//                    TableRow.LayoutParams.MATCH_PARENT,
//                    TableRow.LayoutParams.WRAP_CONTENT
//                )
//
//                val breedTextView = TextView(this.context)
//                breedTextView.text = breedName
//                breedTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
//
//                val subBreedsTextView = TextView(this.context)
//                subBreedsTextView.text = subBreeds.joinToString(", ")
//                subBreedsTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
//
//                dataRow.addView(breedTextView)
//                dataRow.addView(subBreedsTextView)
//                tableLayout.addView(dataRow)
//            }
//        }


}



