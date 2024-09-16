// HomeActivity.kt
package com.stim.blitzcart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Assuming you have a reference to Firebase Database
    private val database = FirebaseDatabase.getInstance()
    private val usersReference = database.getReference("users") // Replace with your actual reference

    // TOOLBAR NAVIGATION ICONS
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> {
                Log.d("Navigation", "Cart icon clicked")
                // Handle cart icon click for both toolbar and bottom navigation
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
            R.id.action_search -> {
                Log.d("Navigation", "Search icon clicked")
                // Handle search icon click for both toolbar and bottom navigation
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }
            R.id.action_account -> {
                Log.d("Navigation", "Account icon clicked")
                // Handle account icon click for both toolbar and bottom navigation
                startActivity(Intent(this, AccountActivity::class.java))
                return true
            }
            R.id.action_home -> {
                Log.d("Navigation", "Home icon clicked")
                // Handle show icon click for bottom navigation
                startActivity(Intent(this, ShowActivity::class.java))
                return true
            }
            R.id.action_shop -> {
                Log.d("Navigation", "Shop icon clicked")
                // Handle shop icon click for bottom navigation
                startActivity(Intent(this, ShopActivity::class.java))
                return true
            }
            R.id.action_wishlist -> {
                Log.d("Navigation", "Wishlist icon clicked")
                // Handle wishlist icon click for bottom navigation
                startActivity(Intent(this, WishlistActivity::class.java))
                return true
            }
            R.id.action_checkout -> {
                Log.d("Navigation", "Checkout icon clicked")
                // Handle checkout icon click for bottom navigation
                startActivity(Intent(this, CheckoutActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }



    //MAIN PART
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //VIDEO SECTION
        val videoView = findViewById<VideoView>(R.id.videoViewAd)
        val videoPath = "android.resource://${packageName}/${R.raw.your_video}"

        // Set the video URI
        videoView.setVideoURI(Uri.parse(videoPath))

         // Add an OnPreparedListener to ensure that the video is properly scaled
        videoView.setOnPreparedListener { mp ->
            val videoWidth = mp.videoWidth
            val videoHeight = mp.videoHeight

            // Adjust the aspect ratio to fit the VideoView
            val aspectRatio: Float = videoWidth.toFloat() / videoHeight.toFloat()
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels

            val layoutParams = videoView.layoutParams
            layoutParams.width = screenWidth
            layoutParams.height = (screenWidth / aspectRatio).toInt()
            videoView.layoutParams = layoutParams
        }

        // Start playing the video
        videoView.start()
        // Set OnCompletionListener to restart the video when it reaches the end
        videoView.setOnCompletionListener { mp ->
            mp.start()
        }


        //AUTO USERNAME SECTION
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let {
            // User is not null, proceed with retrieving data
            usersReference.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    val username = user?.name ?: "Username"

                    // Update the TextView with the obtained username
                    val textViewUsername: TextView = findViewById(R.id.textViewUsername)
                    textViewUsername.text = username
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })
        }





        //BOTTOM BAR
        // Assume your BottomNavigationView has an id 'bottomNavigationView'
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set the listener for bottom navigation items
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    // Handle cart icon click in bottom navigation
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.action_shop -> {
                    // Handle search icon click in bottom navigation
                    startActivity(Intent(this, ShopActivity::class.java))
                    true
                }
                R.id.action_wishlist -> {
                    // Handle account icon click in bottom navigation
                    startActivity(Intent(this, WishlistActivity::class.java))
                    true
                }
                R.id.action_checkout -> {
                    // Handle account icon click in bottom navigation
                    startActivity(Intent(this, CheckoutActivity::class.java))
                    true
                }
                // Add other bottom navigation items as needed
                else -> false
            }
        }


        //BRAND SECTION

        val brandRecyclerView: RecyclerView = findViewById(R.id.brandRecyclerView)
        brandRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val brandList = listOf(
            BrandModel("Brand A", R.drawable.ic_p1),
            BrandModel("Brand B", R.drawable.ic_p2),
            BrandModel("Brand C", R.drawable.ic_p3),
            BrandModel("Brand D", R.drawable.ic_p4),
            BrandModel("Brand E", R.drawable.ic_p5),

            // Add more brands as needed
        )

        val brandAdapter = BrandAdapter(brandList)
        brandRecyclerView.adapter = brandAdapter


        //CATEGORY SECTION
        // Assuming you have a reference to the RecyclerView
        val categoryRecycler = findViewById<RecyclerView>(R.id.categoryRecycler)

       // Create a list of categories
        val categories = listOf(
            CategoryModel("Category 1", R.drawable.ic_c1),
            CategoryModel("Category 2", R.drawable.ic_c2),
            CategoryModel("Category 3", R.drawable.ic_c3),
            CategoryModel("Category 4", R.drawable.ic_c4),
            CategoryModel("Category 5", R.drawable.ic_c5),
            CategoryModel("Category 6", R.drawable.ic_c6),
            // Add more categories as needed
        )

// Create and set the adapter
        val categoryAdapter = CategoryAdapter(this, categories)
        categoryRecycler.adapter = categoryAdapter
        categoryRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //ADV1 SECTION
        // Sample ad data
        val adList = listOf(
            AdModel(R.drawable.ic_ad1),
            AdModel(R.drawable.ic_ad2),
            AdModel(R.drawable.ic_ad3),
            AdModel(R.drawable.ic_ad4),
            AdModel(R.drawable.ic_ad5),
            AdModel(R.drawable.ic_ad6)
        )

        // Set up RecyclerView and adapter for ads
        val recyclerViewAds: RecyclerView = findViewById(R.id.recyclerViewAds)
        recyclerViewAds.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAds.adapter = AdAdapter(adList)



        //PRODUCT SECTION
        // Sample product data
        val productList = listOf(
            ProductModel("Product 1", R.drawable.ic_pwater1),
            ProductModel("Product 2", R.drawable.ic_pwater2),
            ProductModel("Product 3", R.drawable.ic_pwater3),
            ProductModel("Product 4", R.drawable.ic_pwater4),
            ProductModel("Product 5", R.drawable.ic_pcd1),
            ProductModel("Product 1", R.drawable.ic_pcd2),
            ProductModel("Product 2", R.drawable.ic_pcd3),
            ProductModel("Product 3", R.drawable.ic_pcd4),
            ProductModel("Product 4", R.drawable.ic_pchoco1),
            ProductModel("Product 5", R.drawable.ic_pchoco2),
            ProductModel("Product 2", R.drawable.ic_pchoco3),
            ProductModel("Product 3", R.drawable.ic_pchoco4),
            ProductModel("Product 4", R.drawable.ic_pnamkeen1),
            ProductModel("Product 5", R.drawable.ic_pnamkeen2),
            ProductModel("Product 1", R.drawable.ic_pnamkeen3),
            ProductModel("Product 2", R.drawable.ic_pnamkeen4),
            ProductModel("Product 3", R.drawable.ic_psoap1),
            ProductModel("Product 4", R.drawable.ic_psoap2),
            ProductModel("Product 5", R.drawable.ic_pdeter),
            ProductModel("Product 1", R.drawable.ic_pclean),
            ProductModel("Product 2", R.drawable.ic_ps1),
            ProductModel("Product 3", R.drawable.ic_k1),
            ProductModel("Product 4", R.drawable.ic_ps3),
            ProductModel("Product 5", R.drawable.ic_ps4),
            ProductModel("Product 3", R.drawable.ic_pbis1),
        )

        // Set up RecyclerView and adapter for products
        val recyclerViewProducts: RecyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerViewProducts.layoutManager = GridLayoutManager(this, 5)
        val productAdapter = ProductAdapter(productList)
        recyclerViewProducts.adapter = productAdapter

// Set item click listener for product items
        productAdapter.setOnItemClickListener { productId ->
            // Handle product item click, navigate to ProductActivity
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("productId", productId)
            startActivity(intent)
        }

       //ADV2 SECTION
       // Sample data for the second advertisement section
         val adsList2 = listOf(
           AdvertisementModel2("Brand X", R.drawable.ic_adv1, "https://www.brandx.com"),
           AdvertisementModel2("Brand Y", R.drawable.ic_adv2, "https://www.brandy.com"),
            AdvertisementModel2("Brand Z", R.drawable.ic_adv3, "https://www.brandz.com"),
            AdvertisementModel2("Brand X", R.drawable.ic_adv4, "https://www.brandx.com"),
            AdvertisementModel2("Brand Y", R.drawable.ic_adv5, "https://www.brandy.com"),
            AdvertisementModel2("Brand Z", R.drawable.ic_adv6,"https://www.brandz.com"),
         )

         // Set up RecyclerView and adapter for the second advertisement section
        val recyclerViewAds2: RecyclerView = findViewById(R.id.recyclerViewAds2)
        recyclerViewAds2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAds2.adapter = AdvertisementAdapter2(adsList2)


        //KITCHEN INGREDIENTS SECTION
        // Sample kitchen product data
        val kitchenProductList = listOf(
           KitchenProductModel("Kitchen Product 1", R.drawable.ic_k1),
            KitchenProductModel("Kitchen Product 2", R.drawable.ic_k2),
            KitchenProductModel("Kitchen Product 3", R.drawable.ic_k3),

        )

// Set up RecyclerView and adapter for kitchen products
       val recyclerViewKitchen: RecyclerView = findViewById(R.id.recyclerViewKitchen)
        recyclerViewKitchen.layoutManager = GridLayoutManager(this, 3)
        recyclerViewKitchen.adapter = KitchenProductAdapter(kitchenProductList)




        //SETTING UP TOOLBAR WITH NAVIGATION SECTION
        drawerLayout = findViewById(R.id.drawerLayout) // Use the member variable instead of creating a new local variable
        navigationView = findViewById(R.id.navigationView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Disable default title (app name)
        toolbar.setLogo(R.drawable.ic_app_icon) // Set your app icon image

        setSupportActionBar(toolbar)


        // Set up the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Customize the menu icon
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.your_icon_color)

        // Customize the text color
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.your_text_color))

        // Set the NavigationView listener
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))
            R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            R.id.nav_address -> startActivity(Intent(this, AddressFragment::class.java))
            R.id.nav_settings -> startActivity(Intent(this, SettingFragment::class.java))
            R.id.nav_help_support -> startActivity(Intent(this, HelpSupportFragment::class.java))
            R.id.nav_orders_payment -> startActivity(Intent(this, OrdersPaymentFragment::class.java))
            R.id.nav_contact_us -> startActivity(Intent(this, ContactUsFragment::class.java))
            // Handle logout option
            R.id.nav_logout-> {
                val sessionManager = SessionManager(this)
                sessionManager.saveLoginStatus(false) // Set login status to false
                startActivity(Intent(this, MainActivity::class.java)) // Navigate to login page
                finish() // Close the current activity
                return true
            }
        }
        return false
    }


    //SWIFT FLOW OF NAV BAR

    private fun replaceFragment(fragment: Fragment) {
        // Replace the current fragment with the selected fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showLogoutPrompt() {
        // Implement logout logic here
        // For now, let's show a toast message
        showToast("Logout Clicked")
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showToast(message: String) {
        // Helper function to show a toast message
        // You can replace this with your desired UI action
        // (e.g., open a new activity or fragment)
        // For now, let's just show a toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
