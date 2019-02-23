package com.christopherhield.notes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText name;
    private EditText description;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.prodName);
        description = (EditText) findViewById(R.id.prodDesc);

        description.setMovementMethod(new ScrollingMovementMethod());
        description.setTextIsSelectable(true);
    }

    @Override
    protected void onResume() {
        product = loadFile();
        if (product != null) {
            name.setText(product.getName());
            description.setText(product.getDescription());
        }
        super.onResume();
    }

    private Product loadFile() {

        Log.d(TAG, "loadFile: Loading XML File");
        XmlPullParserFactory xmlFactoryObject;
        product = new Product();
        try {

            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String name = parser.getName();
                if (name == null || !name.equals("product")) {
                    eventType = parser.next();
                    continue;
                }
                eventType = parser.next();
                while (eventType != XmlPullParser.END_TAG) {
                    name = parser.getName();

                    if (name == null) {
                        eventType = parser.next();
                        continue;
                    }
                    switch (name) {
                        case "name":
                            product.setName(parser.nextText());
                            break;
                        case "description":
                            product.setDescription(parser.nextText());
                            break;
                        default:
                    }
                    eventType = parser.next();
                }
            }
            return product;
        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    protected void onPause() {
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveProduct();
        super.onStop();
    }

    private void saveProduct() {

        Log.d(TAG, "saveProduct: Saving XML File");

        try {

            StringWriter writer = new StringWriter();

            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "products");
            xmlSerializer.startTag(null, "product");
            if (product != null) {
                xmlSerializer.startTag(null, "name");
                xmlSerializer.text(product.getName());
                xmlSerializer.endTag(null, "name");
                xmlSerializer.startTag(null, "description");
                xmlSerializer.text(product.getDescription());
                xmlSerializer.endTag(null, "description");
            }
            xmlSerializer.endTag(null, "product");
            xmlSerializer.endTag(null, "products");
            xmlSerializer.endDocument();
            xmlSerializer.flush();

            FileOutputStream fos =
                    getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            fos.write(writer.toString().getBytes());
            fos.close();

            /// You do not need to do the below - this is
            /// only done here to show the XML content.
            ///
            Log.d(TAG, "saveProduct: XML:\n" + writer.toString());
            ///
            ///
            Toast.makeText(this, getString(R.string.data_saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
