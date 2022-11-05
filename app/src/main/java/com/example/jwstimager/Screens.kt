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
fun HomeScreen() {
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
    HomeScreen()
}

//
//
@Composable
fun GridScreen() {
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
    GridScreen()
}


//
//
@Composable
fun FavoritesScreen() {
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
    FavoritesScreen()
}


//
//
@Composable
fun NewsScreen() {
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
    NewsScreen()
}


//
//
@Composable
fun AboutScreen() {
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
    AboutScreen()
}