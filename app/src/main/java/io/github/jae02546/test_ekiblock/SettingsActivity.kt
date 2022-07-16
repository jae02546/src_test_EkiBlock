package io.github.jae02546.test_ekiblock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {

    private var beforeDatasetNo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()

        //ActionBar設定
        supportActionBar?.setTitle(R.string.menu_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //homeでMainActivityに戻る

                val intent = Intent().apply {
//                    putExtra("before", beforeDatasetNo)
//                    putExtra("after", datasetNo)
                }
                setResult(Activity.RESULT_OK, intent)

                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

