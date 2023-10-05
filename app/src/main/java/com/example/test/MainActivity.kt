package com.example.test

import SettingFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.test.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val homeFragment =FragmentHomeFragment()
    private val favartFragment = favart_fragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bottomNavigationView = binding.bottomNavigation2

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Favert -> {
                    // Navigate to the Favorite Fragment
                    replaceFragment(favartFragment)
                    true
                }
                R.id.Home -> {
                    // Navigate to the Home Fragment
                    replaceFragment(homeFragment)
                    true
                }
                R.id.setting2 -> {
                    // Navigate to the Setting Fragment
                    replaceFragment(settingFragment)
                    true
                }
                else -> false
            }
        }

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.home_fragment_id, homeFragment)
            fragmentTransaction.commit()
        }
       // onBackPressed()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_fragment_id, fragment)
            .commit()
    }


}
