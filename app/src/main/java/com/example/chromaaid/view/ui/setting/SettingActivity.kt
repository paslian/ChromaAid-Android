package com.example.chromaaid.view.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.chromaaid.R
import com.example.chromaaid.view.ui.Main.MainActivity
import java.util.Locale


class SettingActivity : AppCompatActivity() {
    @Deprecated("Use onBackClick instead")
    override fun onBackPressed() {
        super.onBackPressed()
        onBackClick(null)
    }
    fun onBackClick(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        setContentView(R.layout.activity_setting)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            val languagePreference = findPreference<ListPreference>(getString(R.string.pref_key_language))


            themePreference?.setOnPreferenceChangeListener { _, newValue ->
                val changeTheme = DarkMode.valueOf(newValue.toString().uppercase(Locale.US))
                updateTheme(changeTheme.value)

                true
            }

            val titleKey = getString(R.string.pref_language_title)
            if (titleKey == getString(R.string.bahasa)) {
                languagePreference?.summary = getString(R.string.indonesia)
            } else {
                languagePreference?.summary = getString(R.string.english)
            }

            languagePreference?.setOnPreferenceClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}
