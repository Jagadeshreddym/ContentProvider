package com.example.uiandmaterialdesign

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uiandmaterialdesign.ui.theme.UiAndMaterialDesignTheme
import com.example.uiandmaterialdesign.ui.theme.typography
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UiAndMaterialDesignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge
    )
}

@SuppressLint("Range")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var inputvalue by remember { mutableStateOf("") }
    var textValue by remember { mutableStateOf("") }
    val context:Context = LocalContext.current
    var cr:ContentResolver = context.contentResolver
    UiAndMaterialDesignTheme {
        // on below line we are
        // creating a simple column
        Column(
            // inside this column we are specifying modifier
            // to specify max width and max height
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            // on below line we are specifying horizontal alignment
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // on below line we are creating a simple text
            // view for displaying heading for our application
            Text(
                text = "Simple ListView Example",
                // in modifier we are specifying padding
                // for our text from all sides.
                modifier = Modifier.padding(10.dp),
                // on below line we are specifying
                // style for our text
                style = TextStyle(
                    color = Color.Black,
                    fontSize = TextUnit(value = 20.0F, type = TextUnitType.Sp)
                ), fontWeight = FontWeight.Black
            )

            TextField(
                // below line is used to get
                // value of text field,
                value = inputvalue,

                // below line is used to get value in text field
                // on value change in text field.
                onValueChange = { inputvalue = it },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
            )
            
            Button(onClick = {
                // class to add values in the database
                val values = ContentValues()

                // fetching text from user
                values.put(MyContentProvider.name, inputvalue)

                // inserting into database through content URI
                cr.insert(MyContentProvider.CONTENT_URI, values)

                // displaying a toast message
                Toast.makeText(context, "New Record Inserted", Toast.LENGTH_LONG).show()
            }) {
                Text(text = "Add Data")
            }
            
            Button(onClick = {


                // creating a cursor object of the
                // content URI
                val cursor = cr.query(Uri.parse("content://com.demo.user.provider/users"), null, null, null, null)

                // iteration of the cursor
                // to print whole table
                if (cursor!!.moveToFirst()) {
                    val strBuild = StringBuilder()
                    while (!cursor.isAfterLast) {
                        strBuild.append("""
     
                    ${cursor.getString(cursor.getColumnIndex("id"))}-${cursor.getString(cursor.getColumnIndex("name"))}
                    """.trimIndent())
                        cursor.moveToNext()
                    }
                    textValue = strBuild.toString()
                } else {
                    textValue = "No Records Found"
                }
            }) {
                Text(text = "Fetch Data")
            }


            Text( text = textValue,

                    // for our text from all sides.
                    modifier = Modifier.padding(10.dp),
                // on below line we are specifying
                // style for our text
                style = TextStyle(
                    color = Color.Black,
                    fontSize = TextUnit(value = 20.0F, type = TextUnitType.Sp)
                ), fontWeight = FontWeight.Black
            )


        }
    }
}


// below is the Composable function
// which we have created for our ListView.
@OptIn(ExperimentalUnitApi::class)
@Composable
fun displayList() {
    // on below line we arecreating a simple list
    // of strings and adding different programming
    // languages in it.
    val languages = listOf(
        "C++", "C", "C#", "Java", "Kotlin", "Dart", "Python", "Javascript", "SpringBoot",
        "XML", "Dart", "Node JS", "Typescript", "Dot Net", "GoLang", "MongoDb",
    )
    // on below line we are
    // creating a simple column
    Column(
        // inside this column we are specifying modifier
        // to specify max width and max height
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        // on below line we are specifying horizontal alignment
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are creating a simple text
        // view for displaying heading for our application
        Text(
            text = "Simple ListView Example",
            // in modifier we are specifying padding
            // for our text from all sides.
            modifier = Modifier.padding(10.dp),
            // on below line we are specifying
            // style for our text
            style = TextStyle(
                color = Color.Black,
                fontSize = TextUnit(value = 20.0F, type = TextUnitType.Sp)
            ), fontWeight = FontWeight.Black
        )
        // on below line we are calling lazy column
        // for displaying listview.
        LazyColumn {
            // on below line we are populating
            // items for listview.
            items(languages) { language ->
                // on below line we are specifying ui for each item of list view.
                // we are specifying a simple text for each item of our list view.
                Column(
                    // inside this column we are specifying modifier
                    // to specify max width and max height
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clickable {

                            Log.i("Test", "selected value--->" + language)
                            //Toast.makeText(this,language,Toast.LENGTH_LONG).show()

                        },

                    // on below line we are specifying horizontal alignment
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(language,
                        modifier = Modifier.padding(15.dp),
                        style =  MaterialTheme.typography.displayLarge
                    )

                }

                // on below line we are specifying
                // divider for each list item
                Divider()
            }
        }
    }
}