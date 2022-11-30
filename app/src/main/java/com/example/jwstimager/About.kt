package com.example.jwstimager

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


//
//
@Composable
fun AboutCard(aboutEntry: AboutData) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.secondary)
    )
    {
        Column() {

            Text(text = aboutEntry.name,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = aboutEntry.aboutText,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = aboutEntry.email,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = aboutEntry.linkedin,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

//
//
data class AboutData(
    val name: String,
    val aboutText: String,
    val email: String,
    val linkedin: String
)

//
//
@Composable
fun AboutPage() {

    val aboutEntryList = listOf<AboutData>(

        AboutData(

            name = androidx.compose.ui.res.stringResource(R.string.nameAndrew),
            aboutText = androidx.compose.ui.res.stringResource(R.string.about_andrew),
            email = androidx.compose.ui.res.stringResource(R.string.andrew_email),
            linkedin = androidx.compose.ui.res.stringResource(R.string.andrew_linkedin),
        ),

        AboutData(
            name = androidx.compose.ui.res.stringResource(R.string.nameDeclan),
            aboutText = androidx.compose.ui.res.stringResource(R.string.about_declan),
            email = androidx.compose.ui.res.stringResource(R.string.declan_email),
            linkedin = androidx.compose.ui.res.stringResource(R.string.declan_linkedin),
        ),

        AboutData(
            name = androidx.compose.ui.res.stringResource(R.string.nameEmmanuel),
            aboutText = androidx.compose.ui.res.stringResource(R.string.about_emmanuel),
            email = androidx.compose.ui.res.stringResource(R.string.emmanuel_email),
            linkedin = androidx.compose.ui.res.stringResource(R.string.emmanuel_linkedin),
        ),

        AboutData(
            name = androidx.compose.ui.res.stringResource(R.string.nameJadrien),
            aboutText = androidx.compose.ui.res.stringResource(R.string.about_jadrien),
            email = androidx.compose.ui.res.stringResource(R.string.jadrien_email),
            linkedin = androidx.compose.ui.res.stringResource(R.string.jadrien_linkedin),
        )
    )
    Column(modifier = Modifier
        .background(color = MaterialTheme.colorScheme.onBackground)
        .padding(bottom = 55.dp)) {
        Text(
            text = "About Us",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally)

        )
        Text(text = "California State University Channel Islands\nCOMP-350-001 Fall 2022\nThe JWSTimager App",
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn {
            items(aboutEntryList) { entry ->
                AboutCard(entry)
            }

        }

    }
}