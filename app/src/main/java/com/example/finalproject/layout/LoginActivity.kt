package com.example.finalproject.layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.finalproject.R
import com.google.android.material.tabs.TabLayout


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        if (fileExists(this, "login")) {
            val intent = Intent(this, MainActivity::class.java)
            val file = getFileStreamPath("login")
            intent.putExtra("player_name", file.readLines()[0])
            startActivity(intent)
        }

        title = "Login Screen"
    }

    private fun fileExists(context: Context, filename: String): Boolean {
        val file = context.getFileStreamPath(filename)
        return file != null && file.exists()
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    LoginFragment()
                }

                else -> {
                    SignupFragment()
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> {
                    "Login"
                }

                else -> {
                    "Sign Up"
                }
            }
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    }
}