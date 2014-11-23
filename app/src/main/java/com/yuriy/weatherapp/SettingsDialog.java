package com.yuriy.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.yuriy.weatherapp.business.AppPreferencesManager;
import com.yuriy.weatherapp.business.vo.TemperatureFormat;

/**
 * Created by Yuriy Chernyshov
 * At Android Studio
 * On 11/23/14
 * E-Mail: chernyshov.yuriy@gmail.com
 */
public class SettingsDialog extends DialogFragment {

    /**
     * Tag string to use when create a dialog in Activity.
     */
    public static final String FRAGMENT_TAG = SettingsDialog.class.getName().toUpperCase();

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final Context context = getActivity();
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.setting_dialog_layout,
                (ViewGroup) getActivity().findViewById(R.id.item_root));

        final ToggleButton fahrenheitToggle
                = (ToggleButton) layout.findViewById(R.id.fahrenheit_toggle_view);
        final ToggleButton celsiusToggle
                = (ToggleButton) layout.findViewById(R.id.celsius_toggle_view);

        if (AppPreferencesManager.getTemperatureFormat() == TemperatureFormat.CELSIUS) {
            fahrenheitToggle.setChecked(false);
            celsiusToggle.setChecked(true);
        } else {
            fahrenheitToggle.setChecked(true);
            celsiusToggle.setChecked(false);
        }

        fahrenheitToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        processTemperatureFormatSelection(fahrenheitToggle,
                                celsiusToggle,
                                isChecked ? TemperatureFormat.FAHRENHEIT
                                        : TemperatureFormat.CELSIUS);
                    }
                }
        );
        celsiusToggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        processTemperatureFormatSelection(fahrenheitToggle,
                                celsiusToggle,
                                isChecked ? TemperatureFormat.CELSIUS
                                        : TemperatureFormat.FAHRENHEIT);
                    }
                }
        );

        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.action_settings)
                .setView(layout)
                .setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity)getActivity()).updateTemperature();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Process toggle button selection (between Fahrenheit and Celsius).
     *
     * @param fahrenheitToggle Fahrenheit value.
     * @param celsiusToggle    Celsius value.
     * @param format {@link com.yuriy.weatherapp.business.vo.TemperatureFormat}
     */
    private void processTemperatureFormatSelection(final ToggleButton fahrenheitToggle,
                                                   final ToggleButton celsiusToggle,
                                                   final TemperatureFormat format) {
        if (format == TemperatureFormat.CELSIUS) {
            fahrenheitToggle.setChecked(false);
            celsiusToggle.setChecked(true);
            AppPreferencesManager.setTemperatureFormat(TemperatureFormat.CELSIUS);
        } else if (format == TemperatureFormat.FAHRENHEIT) {
            fahrenheitToggle.setChecked(true);
            celsiusToggle.setChecked(false);
            AppPreferencesManager.setTemperatureFormat(TemperatureFormat.FAHRENHEIT);
        }
    }
}
