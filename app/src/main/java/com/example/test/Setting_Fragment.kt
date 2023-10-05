import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.test.LoginScreen
import com.example.test.Modle.GeneralData
import com.example.test.R
import com.example.test.databinding.FragmentSettingBinding
import java.util.*

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonEn: RadioButton
    private lateinit var radioButtonAr: RadioButton
    private lateinit var button2: Button
    private lateinit var switch: Switch
    private var isNightModeOn: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedLanguageCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize sharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        GeneralData.isChcekedLogin = sharedPreferences.getBoolean("isCheckedLogin", false)

        initializeViews()

        button2.setOnClickListener {

            saveIsCheckedLogin(false)
            val intent = Intent(requireContext(), LoginScreen::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Load saved settings
        isNightModeOn = sharedPreferences.getBoolean("night_mode", AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
        switch.isChecked = isNightModeOn

        // Load saved language
        selectedLanguageCode = sharedPreferences.getString("language", "") ?: ""

        if (selectedLanguageCode.isNotEmpty()) {
            setLocale(requireContext(), selectedLanguageCode)
            if (selectedLanguageCode == "ar") {
                radioButtonAr.isChecked = true

            } else {
                radioButtonEn.isChecked = true

            }
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            // Switch to day mode if isChecked is false, otherwise switch to night mode
          isNightModeOn = isChecked
            GeneralData.isChcekedMode = isNightModeOn
            saveNightModeSetting(isNightModeOn)

            // Initialize AppCompatDelegate based on the value of isNightModeOn
            AppCompatDelegate.setDefaultNightMode(if (isNightModeOn) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Handle language change here
            when (checkedId) {
                R.id.Ar -> updateLanguage("ar")
                R.id.En -> updateLanguage("us")
            }
        }
    }

    private fun updateLanguage(languageCode: String) {
        if (selectedLanguageCode != languageCode) {
            setLocale(requireContext(), languageCode)
            saveLanguageSetting(languageCode)
            requireActivity().recreate()
        }
    }

    private fun setLocale(context: Context, languageCode: String) {
        if (languageCode == "ar") {
            GeneralData.selectedLanguage = "ae"
        } else {
            GeneralData.selectedLanguage = languageCode
        }

        val locale = Locale(languageCode)
       Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        context.resources.updateConfiguration(
            configuration,
            context.resources.displayMetrics
        )
    }

    private fun saveNightModeSetting(isNightModeOn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("night_mode", isNightModeOn)
        editor.apply()
    }

    private fun saveLanguageSetting(languageCode: String) {
        val editor = sharedPreferences.edit()
        editor.putString("language", languageCode)
        editor.apply()
    }

    private fun initializeViews() {
        switch = binding.root.findViewById(R.id.switch1)
        radioGroup = binding.root.findViewById(R.id.radio_group)
        radioButtonAr = binding.root.findViewById(R.id.Ar)
        radioButtonEn = binding.root.findViewById(R.id.En)
        button2 = binding.root.findViewById(R.id.button2)
    }

    private fun saveIsCheckedLogin(isChecked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isChcekedLogin", isChecked)
        editor.apply()
    }
}
