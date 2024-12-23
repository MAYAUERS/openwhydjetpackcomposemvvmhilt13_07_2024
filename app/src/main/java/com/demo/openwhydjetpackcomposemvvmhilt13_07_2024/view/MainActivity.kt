package com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.R
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.model.Tracks
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.ui.theme.OpenwhydJetpackComposeMVVMHilt13_07_2024Theme
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.utils.ApiState
import com.demo.openwhydjetpackcomposemvvmhilt13_07_2024.viewmodel.OpenWhyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val openWhyViewModel:OpenWhyViewModel by viewModels()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenwhydJetpackComposeMVVMHilt13_07_2024Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetListData(openWhyViewModel = openWhyViewModel)
                }
            }
        }
    }
}

@Composable
fun GetListData(openWhyViewModel: OpenWhyViewModel){
    //working code for livedata
   /* openWhyViewModel.getData()
    val electoData by openWhyViewModel.electoData.observeAsState(emptyList())
    val error by openWhyViewModel.error.observeAsState()

    LazyColumn{
        items(electoData){
            ShowDataInList(tracks = it)
        }
    }*/
    //StateFlow
    val electroData by openWhyViewModel.electoDataflow.collectAsStateWithLifecycle(initialValue = ApiState.Loading, minActiveState = Lifecycle.State.STARTED)
    when(electroData){
        is ApiState.Success -> {
            LazyColumn{
                items((electroData as ApiState.Success).data.tracks){
                    ShowDataInList(tracks = it)
                }
            }
        }
        is ApiState.Failure->{
            Text(text = "${(electroData as ApiState.Failure).msg}")
        }
        is ApiState.Loading->{
            CallProgress()
        }
        is ApiState.Empty->{
        }
        else -> {}
    }
}

@Composable
fun CallProgress(){
    Card (modifier =
    Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.clip(CircleShape),
                Color.Yellow,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                trackColor = Color.Cyan)
        }
    }

}

@Composable
fun ShowDataInList(tracks: Tracks) {
    Card (modifier =
    Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ){
        Column (/*modifier =
        Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp),*/
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top){
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(tracks.img)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Error Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
                Text(text = tracks._id, fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif)

                Text(text = tracks.name, fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.SansSerif)

            }

        }
    }
}

