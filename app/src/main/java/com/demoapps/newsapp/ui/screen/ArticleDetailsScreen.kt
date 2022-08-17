import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.demoapps.newsapp.model.Article
import com.demoapps.newsapp.ui.screen.ArticleImage
import com.demoapps.newsapp.ui.screen.ArticleTitle
import com.demoapps.newsapp.ui.screen.ArticleView
import com.demoapps.newsapp.ui.theme.color2

@Composable
fun ArticleDetailScreen(navController: NavHostController,article: Article) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "News API App")
                },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                },
                backgroundColor = color2,
            )
        }, content = {
            Column(modifier = Modifier.padding(4.dp)
                .verticalScroll(rememberScrollState())){
                ArticleTitle(
                    article.title ?: "No Title",Modifier.padding(4.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (article.urlToImage != null && article.urlToImage.isNotEmpty())
                        ArticleImage(
                            article.urlToImage,
                            modifier = Modifier
                                .padding(end = 16.dp).height(200.dp)
                        )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween) {
                    if(article.author != null)Text(
                        text = "Author: ${article.author}",
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if(article.source != null)Text(
                        text = "Source: ${article.source.name}",
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    text = "Published at: ${article.publishedAt}",
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

                if(article.content != null)Text(
                    text = article.content,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1,
                )

                if(article.description != null)Text(
                    text = article.description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body2,
                )
            }
        })
}