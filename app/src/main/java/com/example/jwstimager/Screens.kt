package com.example.jwstimager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jwstimager.ui.theme.JWSTimagerTheme

//
//
@Composable
fun Home() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                //TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Home()
}

//
//
@Composable
fun Grid() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                //TitleBar()
                ScrollingGridList(imageList = SampleData.sampleImageList)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GridScreenPreview() {
    Grid()
}


//
//
@Composable
fun Favorites() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                //TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    Favorites()
}


//
//
@Composable
fun News() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                //TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    News()
}


//
//
@Composable
fun About() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                //TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    About()
}