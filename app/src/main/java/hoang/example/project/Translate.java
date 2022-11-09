package hoang.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class Translate extends AppCompatActivity {
    TextView textView;
    EditText editText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);
        ImageButton btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(view -> {
            Intent intent_home = new Intent(Translate.this, MainActivity.class);
            startActivity(intent_home);
        });
        btn.setOnClickListener(view ->{
            translateText(view);
        });
    }

    public void translateText(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.VIETNAMESE)
                        .build();

        final Translator englishFrenchTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();


        englishFrenchTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
                            englishFrenchTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                Toast.makeText(this, (String) translatedText, Toast.LENGTH_LONG).show();
                                                textView.setText((String) translatedText);
//                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });
    }
}
